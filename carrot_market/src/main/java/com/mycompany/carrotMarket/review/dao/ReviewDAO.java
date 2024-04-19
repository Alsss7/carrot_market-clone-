package com.mycompany.carrotMarket.review.dao;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.review.dto.ReviewDTO;
import com.mycompany.carrotMarket.review.vo.ReviewVO;

public interface ReviewDAO {
	public int insertReview(ReviewDTO review) throws DataAccessException;
	
	public ReviewVO selectReview(ReviewDTO review) throws DataAccessException;
	
	public int deleteReviewByProductId(int productId) throws DataAccessException;
}
