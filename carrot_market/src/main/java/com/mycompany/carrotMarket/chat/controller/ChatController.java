package com.mycompany.carrotMarket.chat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.carrotMarket.article.service.ArticleService;
import com.mycompany.carrotMarket.article.vo.ArticleVO;
import com.mycompany.carrotMarket.member.service.MemberService;
import com.mycompany.carrotMarket.member.vo.MemberVO;

@RestController
@RequestMapping("/chat")
public class ChatController {

	private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
	private static final Logger fileLogger = LoggerFactory.getLogger("fileLogger");

	@Autowired
	private ArticleService articleService;

	@Autowired
	private MemberService memberService;

	public static void main(String[] args) {
		logger.info("ChatController");
		fileLogger.info("fileLogger logger point");
	}

	@RequestMapping(value = "/chatList", method = RequestMethod.GET)
	public ModelAndView getChatList() throws Exception {
		ModelAndView mav = new ModelAndView();

		return mav;
	}

	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
	public ModelAndView getChatting(@PathVariable int productId) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginId = authentication.getName();
		ModelAndView mav = new ModelAndView();

		ArticleVO article = articleService.selectArticle(productId);
		MemberVO member = memberService.findById(article.getUserId());
		mav.addObject("article", article);
		mav.addObject("member", member);

		logger.info("==================================");
		logger.info("@ChatController, GET Chat / Username : " + loginId);

		mav.setViewName("chat");
		return mav;
	}
}
