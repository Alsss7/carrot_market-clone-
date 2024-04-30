package com.mycompany.carrotMarket.review.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mycompany.carrotMarket.review.dao.ReviewDAO;
import com.mycompany.carrotMarket.review.vo.ReviewVO;

@Repository
public class ReviewDAOImpl implements ReviewDAO {

	@Autowired
	SqlSession sqlSession;

	@Override
	public int insertReview(ReviewVO review) throws DataAccessException {
		return sqlSession.insert("mappers.review.insertReview", review);
	}

	@Override
	public ReviewVO selectReview(int productId, String userId) throws DataAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("reviewerId", userId);
		return sqlSession.selectOne("mappers.review.selectReview", map);
	}

	@Override
	public int deleteReviewByProductId(int productId) throws DataAccessException {
		return sqlSession.delete("mappers.review.deleteReviewByProductId", productId);
	}
}
