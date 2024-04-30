package com.mycompany.carrotMarket.chat.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mycompany.carrotMarket.article.service.ArticleService;
import com.mycompany.carrotMarket.article.vo.ArticleVO;
import com.mycompany.carrotMarket.chat.service.ChatService;
import com.mycompany.carrotMarket.chat.service.MessageService;
import com.mycompany.carrotMarket.chat.vo.ChatVO;
import com.mycompany.carrotMarket.chat.vo.MessageVO;
import com.mycompany.carrotMarket.member.service.MemberService;
import com.mycompany.carrotMarket.member.vo.MemberVO;
import com.mycompany.carrotMarket.review.service.ReviewService;
import com.mycompany.carrotMarket.review.vo.ReviewVO;

@RestController
@RequestMapping("/chat")
public class ChatController {

	private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

	@Autowired
	private ArticleService articleService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ChatService chatService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private HttpSession httpSession;

	/*
	 * 하나의 게시글에 대한 채팅목록을 불러오는 메소드
	 */
	@RequestMapping(value = "/chatList/{productId}", method = RequestMethod.GET)
	public ModelAndView getChatList(@PathVariable int productId) throws Exception {
		String loginId = getLoginId();

		ModelAndView mav = new ModelAndView();
		ArticleVO article = articleService.getArticle(productId);
		if (article != null) {
			if (article.getUserId().equals(loginId)) {
				List<ChatVO> chats = chatService.getChatListByProductId(productId);
				List<MemberVO> members = new ArrayList<MemberVO>();
				List<String> lastMessages = new ArrayList<String>();
				List<Long> timeDiffs = new ArrayList<Long>();
				Date currentTime = new Date();
				for (int i = 0; i < chats.size(); i++) {
					ChatVO chat = chats.get(i);
					List<MessageVO> messages = messageService.getMessagesByChatId(chat.getChatId());
					if (messages.size() == 0) {
						chats.remove(i);
					} else {
						members.add(memberService.findById(chat.getBuyerId()));
						lastMessages.add(messages.get(messages.size() - 1).getContent());
						timeDiffs.add(currentTime.getTime() - messages.get(messages.size() - 1).getSentAt().getTime());
					}
				}
				mav.addObject("chats", chats);
				mav.addObject("members", members);
				mav.addObject("lastMessages", lastMessages);
				mav.addObject("timeDiffs", timeDiffs);
				mav.addObject("article", article);
			} else {
				mav.addObject("msg", "잘못된 접근입니다.");
			}
		} else {
			mav.addObject("msg", "존재하지 않는 게시글입니다.");
		}
		mav.setViewName("chatList");
		return mav;
	}

	/*
	 * 하나의 채팅을 불러오는 메서드
	 */
	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
	public ModelAndView getChatting(@PathVariable int productId,
			@RequestParam(value = "buyerId", required = false) String buyerId, RedirectAttributes attributes)
			throws Exception {
		String loginId = getLoginId();

		ModelAndView mav = new ModelAndView();

		ArticleVO article = articleService.getArticle(productId);
		ChatVO chat = chatService.getChat(productId, buyerId);
		MemberVO seller = memberService.findById(article.getUserId());
		MemberVO buyer = memberService.findById(buyerId != null ? buyerId : loginId);

		if (chat != null) {
			mav.addObject("messages", messageService.getMessagesByChatId(chat.getChatId()));
			mav.addObject("messageSize", chatService.getMessagesCountByChatId(chat.getChatId()));
			mav.addObject("lastMsgDate", messageService.getLastMessageDate(chat.getChatId()));

			ReviewVO review = reviewService.getReview(productId, buyer.getId());
			mav.addObject("isReviewed", review != null ? "true" : "false");
		} else {
			chatService.addChat(new ChatVO(productId, seller.getId(), buyer.getId()));
			chat = chatService.getChat(productId, buyer.getId());
			mav.addObject("messageSize", 0);
		}
		httpSession.setAttribute("chatId", chat.getChatId());

		mav.addObject("chatId", chat.getChatId());
		mav.addObject("target", loginId.equals(seller.getId()) ? buyer : seller);
		mav.addObject("article", article);
		mav.addObject("sellerId", seller.getId());
		mav.addObject("buyerId", buyer.getId());

		mav.setViewName("chat");
		return mav;
	}

	/*
	 * 로그인 전에 채팅하기 클릭 시 판매자, 구매자를 구분하는 메서드
	 */
	@RequestMapping(value = "/condition", method = RequestMethod.GET)
	public ModelAndView chatOrChatList(@RequestParam("productId") int productId) throws Exception {
		String loginId = getLoginId();

		ModelAndView mav = new ModelAndView();

		ArticleVO article = articleService.getArticle(productId);
		if (article.getUserId().equals(loginId)) {
			mav.setViewName("redirect:/chat/chatList/" + productId);
		} else {
			mav.setViewName("redirect:/chat/" + productId);
		}
		return mav;
	}

	/*
	 * 메시지 크기를 반환하는 메서드
	 */
	@ResponseBody
	@RequestMapping(value = "/getMessageSize", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Integer>> getChatId(@RequestBody Map<String, Integer> requestBody)
			throws Exception {
		Map<String, Integer> response = new HashMap<String, Integer>();

		int chatId = requestBody.get("chatId");
		int messageSize = chatService.getMessagesCountByChatId(chatId);
		response.put("messageSize", messageSize);

		return ResponseEntity.ok(response);
	}

	/*
	 * 채팅방 나가기 메서드
	 */
	@ResponseBody
	@RequestMapping(value = "/exit", method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> exitChatroom(@RequestBody Map<String, Integer> requestBody)
			throws Exception {
		Map<String, String> response = new HashMap<String, String>();

		int chatId = requestBody.get("chatId");
		boolean result = chatService.removeChatById(chatId);
		if (result) {
			httpSession.removeAttribute("chatId");
			response.put("result", "true");
		} else {
			response.put("result", "false");
		}
		return ResponseEntity.ok(response);
	}

	private String getLoginId() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	public static void main(String[] args) {
		logger.info("ChatController");
	}
}
