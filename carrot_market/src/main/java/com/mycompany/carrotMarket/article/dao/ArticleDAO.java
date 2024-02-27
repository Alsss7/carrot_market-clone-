package com.mycompany.carrotMarket.article.dao;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.article.vo.ArticleVO;

public interface ArticleDAO {
	public int insertArticle(ArticleVO articleVO) throws DataAccessException;

	public int insertImageFiles(ArticleVO articleVO) throws DataAccessException;
}
