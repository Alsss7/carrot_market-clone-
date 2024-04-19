package com.mycompany.carrotMarket.review.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mycompany.carrotMarket.review.dao.ReviewDAO;
import com.mycompany.carrotMarket.review.dto.ReviewDTO;
import com.mycompany.carrotMarket.review.service.ReviewService;
import com.mycompany.carrotMarket.review.vo.ReviewVO;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	ReviewDAO reviewDAO;

	@Override
	public boolean insertReview(ReviewDTO review) throws DataAccessException {
		int result = reviewDAO.insertReview(review);
		if (result != 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public ReviewVO selectReview(int productId, String userId) throws DataAccessException {
		return reviewDAO.selectReview(new ReviewDTO(productId, userId));
	}

}
