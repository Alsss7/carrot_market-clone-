package com.mycompany.carrotMarket.article.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.mycompany.carrotMarket.article.vo.ImageVO;
import com.mycompany.carrotMarket.article.vo.LikeVO;
import com.mycompany.carrotMarket.chat.service.ChatService;
import com.mycompany.carrotMarket.chat.service.MessageService;
import com.mycompany.carrotMarket.chat.vo.ChatVO;
import com.mycompany.carrotMarket.chat.vo.MessageVO;
import com.mycompany.carrotMarket.member.service.MemberService;
import com.mycompany.carrotMarket.member.vo.MemberVO;
import com.mycompany.carrotMarket.review.service.ReviewService;
import com.mycompany.carrotMarket.review.vo.ReviewVO;
import com.mycompany.carrotMarket.trade.service.TradeService;
import com.mycompany.carrotMarket.trade.vo.TradeVO;

@RestController
@RequestMapping("/article")
public class ArticleController {

	private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

	@Autowired
	ArticleService articleService;

	@Autowired
	MemberService memberService;

	@Autowired
	ChatService chatService;

	@Autowired
	MessageService messageService;

	@Autowired
	TradeService tradeService;

	@Autowired
	ReviewService reviewService;

	/*
	 * 중고거래 탭
	 */
	@RequestMapping(value = "/fleamarket", method = RequestMethod.GET)
	public ModelAndView fleamarket(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String loginId = getLoginId();

		ModelAndView mav = new ModelAndView();

		List<ArticleVO> articleList;
		if (loginId.equals("anonymousUser")) {
			articleList = articleService.getArticlesByRandom(12);
		} else {
			MemberVO member = memberService.findById(loginId);
			articleList = articleService.getArticlesByRegion(member.getRegion1());
			int allCount = articleService.countArticlesByRegion(member.getRegion1());

			mav.addObject("region", member.getRegion1());
			mav.addObject("allCount", allCount);
		}

		mav.addObject("articles", articleList);
		mav.addObject("articleCount", articleList.size());
		mav.addObject("pageTitle", "trade");
		mav.setViewName("fleamarket");
		return mav;
	}

	/*
	 * 중고거래 탭에서 더보기 버튼을 클릭 시
	 */
	@ResponseBody
	@RequestMapping(value = "/getMoreArticle/{articleListSize}/{region}", method = RequestMethod.GET)
	public List<ArticleVO> getMoreArticle(@PathVariable int articleListSize, @PathVariable String region) {
		return articleService.getMoreArticlesByRegion(articleListSize, articleListSize + 12, region);
	}

	/*
	 * 인기매물 탭
	 */
	@RequestMapping(value = "/hotArticle", method = RequestMethod.GET)
	public ModelAndView hotArticle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		List<ArticleVO> articleList = articleService.getArticlesByRandom(100);
		mav.addObject("pageTitle", "trade");
		mav.addObject("articles", articleList);
		mav.setViewName("hotArticle");
		return mav;
	}

	/*
	 * 인기매물 탭(시,도 선택 시)
	 */
	@RequestMapping(value = "/hotArticle/{region1}", method = RequestMethod.GET)
	public ModelAndView hotArticle(@PathVariable String region1) throws Exception {
		ModelAndView mav = new ModelAndView();
		List<ArticleVO> articleList = articleService.getRandomArticlesByContainRegion(region1);
		mav.addObject("region1", region1);
		mav.addObject("pageTitle", "trade");
		mav.addObject("articles", articleList);
		mav.setViewName("hotArticle");
		return mav;
	}

	/*
	 * 인기매물 탭(군,구 선택 시)
	 */
	@RequestMapping(value = "/hotArticle/{region1}/{region2}", method = RequestMethod.GET)
	public ModelAndView hotArticle(@PathVariable String region1, @PathVariable String region2) throws Exception {
		ModelAndView mav = new ModelAndView();
		List<ArticleVO> articleList = articleService.getRandomArticlesByContainRegion(region1 + " " + region2);
		mav.addObject("region1", region1);
		mav.addObject("region2", region2);
		mav.addObject("pageTitle", "trade");
		mav.addObject("articles", articleList);
		mav.setViewName("hotArticle");
		return mav;
	}

	/*
	 * 글쓰기 버튼 클릭 시
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView articleForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String loginId = getLoginId();

		MemberVO member = memberService.findById(loginId);
		mav.addObject("region", member.getRegion1());
		mav.setViewName("articleForm");
		return mav;
	}

	/*
	 * 글 작성 완료 후 등록 버튼 클릭 시
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerArticle(@ModelAttribute("article") ArticleVO article, RedirectAttributes attributes,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();

		MemberVO member = memberService.findById(article.getUserId());
		article.setRegion(member.getRegion1());

		boolean result = articleService.registerArticle(article);

		String uploadResult = result ? "등록 성공" : "등록 실패";
		attributes.addFlashAttribute("result", uploadResult);
		mav.setViewName("redirect:/article/fleamarket");
		return mav;
	}

	/*
	 * 글 수정 클릭 시
	 */
	@RequestMapping(value = "/modify/{productId}", method = RequestMethod.GET)
	public ModelAndView modifyArticleForm(@PathVariable int productId) throws Exception {
		String loginId = getLoginId();

		ModelAndView mav = new ModelAndView();

		ArticleVO article = articleService.getArticle(productId);
		List<ImageVO> images = articleService.getArticleImages(productId);

		if (loginId.equals(article.getUserId())) {
			mav.addObject("article", article);
			mav.addObject("images", images);
			mav.setViewName("modifyArticleForm");
		} else {
			mav.addObject("msg", "잘못된 접근입니다.");
			mav.setViewName("redirect:/article/" + productId);
		}
		return mav;
	}

	/*
	 * 글 수정 완료 후 수정하기 버튼 클릭 시
	 */
	@RequestMapping(value = "/modify/{productId}", method = RequestMethod.POST)
	public ModelAndView modifyArticle(@PathVariable int productId, @ModelAttribute("article") ArticleVO articleVO,
			HttpServletRequest req, RedirectAttributes attributes) throws Exception {
		ModelAndView mav = new ModelAndView();

		boolean result = articleService.modifyArticle(productId, articleVO, req);

		if (result) {
			ArticleVO article = articleService.getArticle(productId);
			attributes.addFlashAttribute("article", article);
		}
		attributes.addFlashAttribute("modifyResult", result ? true : false);
		mav.setViewName(result ? "redirect:/article/" + productId : "redirect:/article/modify/" + productId);
		return mav;
	}

	/*
	 * 물건 이름 검색 시
	 */
	@RequestMapping(value = "/search/{value}", method = RequestMethod.GET)
	public ModelAndView searchArticles(@PathVariable String value) throws Exception {
		String loginId = getLoginId();
		ModelAndView mav = new ModelAndView();

		List<ArticleVO> articles;
		int allCount;
		if (loginId == "anonymousUser") {
			articles = articleService.getArticlesBySearch(value);
			allCount = articleService.countArticlesBySearch(value);
		} else {
			MemberVO member = memberService.findById(loginId);
			String[] regionArray = member.getRegion1().split(" ");
			String region = regionArray[0] + " " + regionArray[1];
			articles = articleService.getArticlesBySearch(value, region);
			allCount = articleService.countArticlesBySearch(value, region);
			mav.addObject("region", region);
		}
		mav.addObject("allCount", allCount);
		mav.addObject("articleCount", articles.size());
		mav.addObject("articles", articles);
		mav.addObject("search", value);
		mav.setViewName("searchResult");
		return mav;
	}

	/*
	 * 검색 결과 더 불러오기(로그인)
	 */
	@ResponseBody
	@RequestMapping(value = "/search/getMoreArticle/{value}/{articleSize}/{region}", method = RequestMethod.GET)
	public List<ArticleVO> getMoreArticlesAtSearch(@PathVariable String value, @PathVariable int articleSize,
			@PathVariable String region) throws Exception {
		return articleService.getMoreArticlesBySearch(value, articleSize, articleSize + 6, region);
	}

	/*
	 * 검색 결과 더 불러오기
	 */
	@ResponseBody
	@RequestMapping(value = "/search/getMoreArticle/{value}/{articleSize}", method = RequestMethod.GET)
	public List<ArticleVO> getMoreArticlesAtSearch(@PathVariable String value, @PathVariable int articleSize)
			throws Exception {
		return articleService.getMoreArticlesBySearch(value, articleSize, articleSize + 6);
	}

	/*
	 * 게시글 상세창
	 */
	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
	public ModelAndView viewArticle(@PathVariable int productId, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		String loginId = getLoginId();
		ModelAndView mav = new ModelAndView();

		ArticleVO article = articleService.getArticle(productId);
		if (article != null) {
			Date currentTime = new Date();
			Date dbTimeStamp = article.getCreatedAt();
			long timeDiff = currentTime.getTime() - dbTimeStamp.getTime();

			MemberVO member = memberService.findById(article.getUserId());
			mav.addObject("isExists", "true");
			mav.addObject("article", article);
			mav.addObject("timeDiff", timeDiff);
			mav.addObject("member", member);
			if (loginId != null) {
				LikeVO like = new LikeVO();
				like.setUserId(loginId);
				like.setProductId(productId);
				boolean isLiked = articleService.isLikedByUser(like);
				mav.addObject("like", isLiked);
				increaseView(req, res, productId);
			}
			ReviewVO review = reviewService.getReview(productId, loginId);
			if (review != null) {
				mav.addObject("isReviewed", "true");
			}
		} else {
			mav.addObject("isExists", "false");
		}
		mav.setViewName("article");
		return mav;
	}

	/*
	 * 찜하기 버튼 클릭 시
	 */
	@RequestMapping(value = "/like/{productId}", method = RequestMethod.GET)
	public ModelAndView likeArticle(@PathVariable int productId, RedirectAttributes attributes) {
		String loginId = getLoginId();
		ModelAndView mav = new ModelAndView();

		if (loginId.equals("anonymousUser")) {
			attributes.addFlashAttribute("loginFirst", "loginFirst");
			mav.setViewName("redirect:/article/" + productId);
		} else {
			LikeVO like = new LikeVO();
			like.setUserId(loginId);
			like.setProductId(productId);
			boolean isLiked = articleService.isLikedByUser(like);
			if (isLiked) {
				boolean result = articleService.removeLike(like);
				attributes.addFlashAttribute("removeResult", result);
			} else {
				boolean result = articleService.addLike(like);
				attributes.addFlashAttribute("addResult", result);
			}
			mav.setViewName("redirect:/article/" + productId);
		}
		return mav;
	}

	/*
	 * 글 삭제 버튼 클릭 시
	 */
	@ResponseBody
	@RequestMapping(value = "/delete/{productId}", method = RequestMethod.DELETE)
	public ResponseEntity<Map<String, String>> deleteArticle(@PathVariable int productId) throws Exception {
		Map<String, String> response = new HashMap<String, String>();

		String loginId = getLoginId();

		ArticleVO article = articleService.getArticle(productId);
		boolean result = false;
		if (loginId.equals(article.getUserId())) {
			result = articleService.removeArticleById(productId);
			response.put("msg", "success");
		} else {
			response.put("msg", "not valid");
		}

		response.put("result", String.valueOf(result));

		return ResponseEntity.ok(response);
	}

	/*
	 * 게시글 상태 변경 시
	 */
	@ResponseBody
	@RequestMapping(value = "/updateStat/{productId}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> updateArticleStatus(@PathVariable int productId,
			@RequestBody Map<String, String> requestBody) throws Exception {
		String status = requestBody.get("status");
		String buyerId = requestBody.get("buyerId");

		if (status.equals("Active")) {
			buyerId = "";
		}

		Map<String, String> response = new HashMap<String, String>();

		boolean result = articleService.modifyArticleStatus(productId, status, buyerId);
		TradeVO trade = tradeService.selectTradeByProductId(productId);
		if (trade != null) {
			response.put("tradeId", String.valueOf(trade.getTradeId()));
		}
		response.put("result", String.valueOf(result));
		response.put("buyerId", buyerId);

		return ResponseEntity.ok(response);
	}

	/*
	 * 게시글 상태(예약중, 거래완료) 변경 시 --> 구매자 선택이 필요한 경우
	 */
	@RequestMapping(value = "/updateStat/{productId}/selectBuyer", method = RequestMethod.GET)
	public ModelAndView selectBuyer(@PathVariable int productId, @RequestParam("status") String status,
			@RequestParam("pre") String pre) throws Exception {
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
				for (ChatVO chat : chats) {
					members.add(memberService.findById(chat.getBuyerId()));
					List<MessageVO> messages = messageService.getMessagesByChatId(chat.getChatId());
					lastMessages.add(messages.get(messages.size() - 1).getContent());
					timeDiffs.add(currentTime.getTime() - messages.get(messages.size() - 1).getSentAt().getTime());
				}
				mav.addObject("chats", chats);
				mav.addObject("members", members);
				mav.addObject("lastMessages", lastMessages);
				mav.addObject("timeDiffs", timeDiffs);
				mav.addObject("article", article);
				mav.addObject("preStatus", article.getStatus());
			} else {
				mav.addObject("msg", "잘못된 접근입니다.");
			}
		} else {
			mav.addObject("msg", "존재하지 않는 게시글입니다.");
		}
		mav.addObject("preUri", pre);
		mav.addObject("productStatus", status);
		mav.setViewName("buyerList");
		return mav;
	}

	/*
	 * 게시글 숨기기 버튼 클릭 시
	 */
	@ResponseBody
	@RequestMapping(value = "/updateHidden/{productId}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> updateArticleHidden(@PathVariable int productId,
			@RequestBody Map<String, Integer> requestBody) throws Exception {
		int hidden = requestBody.get("hide");

		Map<String, String> response = new HashMap<String, String>();
		boolean result = articleService.modifyArticleHidden(productId, hidden);
		response.put("result", String.valueOf(result));
		if (hidden == 0) {
			response.put("hidden", "show");
		} else {
			response.put("hidden", "hide");
		}

		return ResponseEntity.ok(response);
	}

	/*
	 * 게시글 조회수 증가 메서드
	 */
	private void increaseView(HttpServletRequest req, HttpServletResponse res, int productId) {
		Cookie oldCookie = null;
		Cookie[] cookies = req.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("articleView")) {
				oldCookie = cookie;
			}
		}

		if (oldCookie != null) {
			if (!oldCookie.getValue().contains("[" + productId + "]")) {
				articleService.increaseView(productId);
				oldCookie.setValue(oldCookie.getValue() + "_[" + productId + "]");
				oldCookie.setPath("/");
				oldCookie.setMaxAge(60 * 60 * 24);
				res.addCookie(oldCookie);
			}
		} else {
			articleService.increaseView(productId);
			Cookie newCookie = new Cookie("articleView", "[" + productId + "]");
			newCookie.setPath("/");
			newCookie.setMaxAge(60 * 60 * 24);
			res.addCookie(newCookie);
		}
	}

	private String getLoginId() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
}
