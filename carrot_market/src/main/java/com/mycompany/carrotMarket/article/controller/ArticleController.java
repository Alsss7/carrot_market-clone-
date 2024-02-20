package com.mycompany.carrotMarket.article.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mycompany.carrotMarket.article.service.ArticleService;
import com.mycompany.carrotMarket.article.vo.ArticleVO;
import com.mycompany.carrotMarket.member.service.MemberService;
import com.mycompany.carrotMarket.member.vo.MemberVO;

@RestController
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	ArticleService articleService;

	@Autowired
	MemberService memberService;

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView articleForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("articleForm");
		return mav;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerArticle(@ModelAttribute("article") ArticleVO articleVO, RedirectAttributes attributes) {
		ModelAndView mav = new ModelAndView();
		MemberVO member = memberService.findById(articleVO.getUserId());
		articleVO.setRegion(member.getRegion1());
		boolean result = articleService.addArticle(articleVO);
		if (result) {
			attributes.addFlashAttribute("result", "success");
		} else {
			attributes.addFlashAttribute("result", "failed");
		}
		mav.setViewName("redirect:/hot_article");
		return mav;
	}
}
