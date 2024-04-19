package com.mycompany.carrotMarket.review.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mycompany.carrotMarket.review.dao.ReviewDAO;
import com.mycompany.carrotMarket.review.dto.ReviewDTO;
import com.mycompany.carrotMarket.review.vo.ReviewVO;

@Repository
public class ReviewDAOImpl implements ReviewDAO {

	@Autowired
	SqlSession sqlSession;

	@Override
	public int insertReview(ReviewDTO review) throws DataAccessException {
		return sqlSession.insert("mappers.review.insertReview", review);
	}

	@Override
	public ReviewVO selectReview(ReviewDTO review) throws DataAccessException {
		return sqlSession.selectOne("mappers.review.selectReview", review);
	}

	@Override
	public int deleteReviewByProductId(int productId) throws DataAccessException {
		return sqlSession.delete("mappers.review.deleteReviewByProductId", productId);
	}
}
