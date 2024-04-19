package com.mycompany.carrotMarket.chat.controller;

import java.time.LocalDate;
import java.time.ZoneId;
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
import org.springframework.security.core.Authentication;
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
import com.mycompany.carrotMarket.chat.dto.ChatDTO;
import com.mycompany.carrotMarket.chat.dto.ChatIdDTO;
import com.mycompany.carrotMarket.chat.service.ChatService;
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
	private static final Logger fileLogger = LoggerFactory.getLogger("fileLogger");

	@Autowired
	private ArticleService articleService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ChatService chatService;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private HttpSession httpSession;

	@RequestMapping(value = "/chatList/{productId}", method = RequestMethod.GET)
	public ModelAndView getChatList(@PathVariable int productId) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginId = authentication.getName();

		ModelAndView mav = new ModelAndView();
		ArticleVO article = articleService.selectArticle(productId);
		if (article != null) {
			if (article.getUserId().equals(loginId)) {
				List<ChatVO> chats = chatService.selectChatListByProductId(productId);
				List<MemberVO> members = new ArrayList<MemberVO>();
				List<String> lastMessages = new ArrayList<String>();
				List<Long> timeDiffs = new ArrayList<Long>();
				Date currentTime = new Date();
				for (int i = 0; i < chats.size(); i++) {
					ChatVO chat = chats.get(i);
					List<MessageVO> messages = chatService.selectMessagesByChatId(chat.getChatId());
					if (messages.size() == 0) {
						chats.remove(i);
					} else {
						members.add(memberService.findById(chat.getBuyerId()));
						lastMessages.add(messages.get(messages.size() - 1).getContent());
						long timeDiff = currentTime.getTime() - messages.get(messages.size() - 1).getSentAt().getTime();
						timeDiffs.add(timeDiff);
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

	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
	public ModelAndView getChatting(@PathVariable int productId,
			@RequestParam(value = "buyerId", required = false) String buyerId, RedirectAttributes attributes)
			throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginId = authentication.getName();

		ModelAndView mav = new ModelAndView();

		ArticleVO article = articleService.selectArticle(productId);

		ChatVO chat;
		MemberVO seller;
		MemberVO buyer;
		if (buyerId != null) {
			chat = chatService.selectChat(new ChatDTO(article.getUserId(), buyerId, productId));
			seller = memberService.findById(article.getUserId());
			buyer = memberService.findById(buyerId);
		} else {
			chat = chatService.selectChat(new ChatDTO(article.getUserId(), loginId, productId));
			seller = memberService.findById(article.getUserId());
			buyer = memberService.findById(loginId);
		}

		if (chat != null) {
			if (chat.getSellerId().equals(loginId) || chat.getBuyerId().equals(loginId)) {
				List<MessageVO> messages = chatService.selectMessagesByChatId(chat.getChatId());
				mav.addObject("messages", messages);
				if (messages.size() > 0) {
					Date date = messages.get(messages.size() - 1).getSentAt();
					LocalDate lastMessageDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					mav.addObject("lastMsgDate", lastMessageDate);
				}

				mav.addObject("chatId", chat.getChatId());
				mav.addObject("messageSize", messages.size());
				httpSession.setAttribute("chatId", chat.getChatId());

				ReviewVO review = reviewService.selectReview(productId, loginId);
				if (review != null) {
					mav.addObject("isReviewed", "true");
				}
			} else {
				logger.info("not valid");
				attributes.addFlashAttribute("msg", "잘못된 접근입니다.");
				mav.setViewName("redirect:/article/" + article.getProductId());
			}
		} else {
			chatService.insertChat(new ChatVO(productId, seller.getId(), buyer.getId()));
			ChatVO existChat = chatService.selectChat(new ChatDTO(seller.getId(), buyer.getId(), productId));

			mav.addObject("chatId", existChat.getChatId());
			mav.addObject("messageSize", 0);
			httpSession.setAttribute("chatId", existChat.getChatId());
		}

		if (loginId.equals(seller.getId())) {
			mav.addObject("target", buyer);
		} else {
			mav.addObject("target", seller);
		}

		mav.addObject("article", article);
		mav.addObject("sellerId", seller.getId());
		mav.addObject("buyerId", buyer.getId());
		mav.setViewName("chat");
		return mav;
	}

	@RequestMapping(value = "/condition", method = RequestMethod.GET)
	public ModelAndView chatOrChatList(@RequestParam("productId") int productId) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginId = authentication.getName();
		ModelAndView mav = new ModelAndView();

		ArticleVO article = articleService.selectArticle(productId);
		if (article.getUserId().equals(loginId)) {
			mav.setViewName("redirect:/chat/chatList/" + productId);
		} else {
			mav.setViewName("redirect:/chat/" + productId);
		}
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/getMessageSize", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Integer>> getChatId(@RequestBody ChatIdDTO dto) throws Exception {
		Map<String, Integer> response = new HashMap<String, Integer>();

		int messageSize = chatService.selectMessagesCountByChatId(dto.getChatId());
		response.put("messageSize", messageSize);

		return ResponseEntity.ok(response);
	}

	@ResponseBody
	@RequestMapping(value = "/exit", method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> exitChatroom(@RequestBody ChatIdDTO dto) throws Exception {
		Map<String, String> response = new HashMap<String, String>();

		logger.info("chatId : {}", dto.getChatId());
		boolean result = chatService.deleteChatById(dto.getChatId());
		if (result) {
			httpSession.removeAttribute("chatId");
			response.put("result", "true");
		} else {
			response.put("result", "false");
		}
		return ResponseEntity.ok(response);
	}

	public static void main(String[] args) {
		logger.info("ChatController");
		fileLogger.info("fileLogger logger point");
	}
}
