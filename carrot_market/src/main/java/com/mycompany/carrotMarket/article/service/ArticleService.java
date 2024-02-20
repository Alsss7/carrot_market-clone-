package com.mycompany.carrotMarket.article.service;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.article.vo.ArticleVO;

public interface ArticleService {
	public boolean addArticle(ArticleVO articleVO) throws DataAccessException;
}
