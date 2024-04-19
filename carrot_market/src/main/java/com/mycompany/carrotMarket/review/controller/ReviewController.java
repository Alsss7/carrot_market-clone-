package com.mycompany.carrotMarket.review.controller;

import java.util.HashMap;
import java.util.Map;

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

import com.mycompany.carrotMarket.article.service.ArticleService;
import com.mycompany.carrotMarket.article.vo.ArticleVO;
import com.mycompany.carrotMarket.review.dto.ReviewDTO;
import com.mycompany.carrotMarket.review.service.ReviewService;
import com.mycompany.carrotMarket.review.vo.ReviewVO;
import com.mycompany.carrotMarket.trade.service.TradeService;
import com.mycompany.carrotMarket.trade.vo.TradeVO;

@RestController
@RequestMapping("/review")
public class ReviewController {

	private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

	@Autowired
	ArticleService articleService;

	@Autowired
	TradeService tradeService;

	@Autowired
	ReviewService reviewService;

	@RequestMapping(value = "/{tradeId}", method = RequestMethod.GET)
	public ModelAndView getReviewPage(@PathVariable int tradeId, @RequestParam(required = false) String preUri)
			throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginId = authentication.getName();
		ModelAndView mav = new ModelAndView();
		logger.info("tradeId : {}", tradeId);
		TradeVO trade = tradeService.selectTrade(tradeId);
		logger.info("productId : {}", trade.getProductId());
		ArticleVO article = articleService.selectArticle(trade.getProductId());
		ReviewVO review = reviewService.selectReview(article.getProductId(), loginId);
		if (review == null) {
			if (loginId.equals(article.getUserId())) {
				mav.addObject("targetId", trade.getBuyerId());
			} else {
				mav.addObject("targetId", article.getUserId());
			}
			if (preUri != null) {
				mav.addObject("preUri", preUri);
			}
			mav.addObject("article", article);
			mav.addObject("loginId", loginId);
		} else {
			mav.addObject("error", "already reviewed");
		}
		mav.setViewName("newReview");
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> registerReview(@RequestBody ReviewDTO review) throws Exception {
		Map<String, String> response = new HashMap<String, String>();
		boolean result = reviewService.insertReview(review);
		response.put("result", String.valueOf(result));
		return ResponseEntity.ok(response);
	}
}
