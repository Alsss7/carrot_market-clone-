package com.mycompany.carrotMarket.review.service;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.review.dto.ReviewDTO;
import com.mycompany.carrotMarket.review.vo.ReviewVO;

public interface ReviewService {
	public boolean insertReview(ReviewDTO review) throws DataAccessException;

	public ReviewVO selectReview(int productId, String userId) throws DataAccessException;
}
