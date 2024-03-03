package com.mycompany.carrotMarket.article.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.article.vo.ArticleVO;

public interface ArticleDAO {
	public int insertArticle(ArticleVO articleVO) throws DataAccessException;

	public int insertImageFiles(ArticleVO articleVO) throws DataAccessException;

	public List<ArticleVO> selectArticles() throws DataAccessException;

	public List<String> selectImages(int productId) throws DataAccessException;

	public ArticleVO selectArticle(int productId) throws DataAccessException;
}
