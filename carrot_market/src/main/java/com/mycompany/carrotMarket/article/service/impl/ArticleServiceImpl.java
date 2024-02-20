package com.mycompany.carrotMarket.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mycompany.carrotMarket.article.dao.ArticleDAO;
import com.mycompany.carrotMarket.article.service.ArticleService;
import com.mycompany.carrotMarket.article.vo.ArticleVO;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ArticleDAO articleDAO;

	@Override
	public boolean addArticle(ArticleVO articleVO) throws DataAccessException {
		int result = articleDAO.insertArticle(articleVO);
		if (result == 1) {
			return true;
		} else {
			return false;
		}
	}

}
