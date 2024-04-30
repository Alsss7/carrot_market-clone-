package com.mycompany.carrotMarket.review.service;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.review.vo.ReviewVO;

public interface ReviewService {
	public boolean addReview(ReviewVO review) throws DataAccessException;

	public ReviewVO getReview(int productId, String userId) throws DataAccessException;
}
