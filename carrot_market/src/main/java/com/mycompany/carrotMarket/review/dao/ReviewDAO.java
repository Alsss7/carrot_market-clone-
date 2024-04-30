package com.mycompany.carrotMarket.review.dao;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.review.vo.ReviewVO;

public interface ReviewDAO {
	public int insertReview(ReviewVO review) throws DataAccessException;

	public ReviewVO selectReview(int productId, String userId) throws DataAccessException;

	public int deleteReviewByProductId(int productId) throws DataAccessException;
}
