package com.mycompany.carrotMarket.article.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mycompany.carrotMarket.article.dao.ArticleDAO;
import com.mycompany.carrotMarket.article.vo.ArticleVO;

@Repository
public class ArticleDAOImpl implements ArticleDAO {

	@Autowired
	SqlSession sqlSession;

	@Override
	public int insertArticle(ArticleVO articleVO) throws DataAccessException {
		int result1 = sqlSession.insert("mappers.article.insertArticle", articleVO);
//		imageFileVO.setPostId(articleVO.getProductId());
//		int result2 = sqlSession.insert("mappers.article.insertImageFile", imageFileVO);

		return result1;
	}

}
