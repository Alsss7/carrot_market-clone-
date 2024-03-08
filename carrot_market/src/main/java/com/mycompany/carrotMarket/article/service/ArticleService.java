package com.mycompany.carrotMarket.article.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.article.dto.LikeDTO;
import com.mycompany.carrotMarket.article.vo.ArticleVO;

public interface ArticleService {
	public boolean addArticle(ArticleVO articleVO) throws DataAccessException;

	public List<ArticleVO> selectArticles() throws DataAccessException;

	public ArticleVO selectArticle(int productId) throws DataAccessException;

	public boolean selectLike(LikeDTO likeDTO) throws DataAccessException;

	public boolean addLike(LikeDTO likeDTO) throws DataAccessException;

	public boolean removeLike(LikeDTO likeDTO) throws DataAccessException;
	
	public void increaseView(int productId) throws DataAccessException;
}
