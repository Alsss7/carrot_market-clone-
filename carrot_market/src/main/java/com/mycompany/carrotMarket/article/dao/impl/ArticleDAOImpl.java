package com.mycompany.carrotMarket.article.dao.impl;

import java.util.List;

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
		int result = sqlSession.insert("mappers.article.insertArticle", articleVO);
		return result;
	}

	@Override
	public int insertImageFiles(ArticleVO articleVO) throws DataAccessException {
		int result = sqlSession.update("mappers.article.insertProductImages", articleVO);
		return result;
	}

	@Override
	public List<ArticleVO> selectArticles() throws DataAccessException {
		List<ArticleVO> list = sqlSession.selectList("mappers.article.selectArticles");
		return list;
	}

	@Override
	public List<String> selectImages(int productId) throws DataAccessException {
		List<String> list = sqlSession.selectList("mappers.article.selectImages", productId);
		return list;
	}

}
