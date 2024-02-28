package com.mycompany.carrotMarket.article.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.article.vo.ArticleVO;

public interface ArticleService {
	public boolean addArticle(ArticleVO articleVO) throws DataAccessException;

	public Map<ArticleVO, List<String>> selectArticles() throws DataAccessException;
}
