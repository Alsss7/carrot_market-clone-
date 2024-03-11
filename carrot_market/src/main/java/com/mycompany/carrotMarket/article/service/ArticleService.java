package com.mycompany.carrotMarket.article.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.article.dto.LikeDTO;
import com.mycompany.carrotMarket.article.dto.SalesDTO;
import com.mycompany.carrotMarket.article.dto.UpdateStatusDTO;
import com.mycompany.carrotMarket.article.vo.ArticleVO;

public interface ArticleService {
	public boolean addArticle(ArticleVO articleVO) throws DataAccessException;

	public List<ArticleVO> selectArticles() throws DataAccessException;

	public List<ArticleVO> selectArticlesByProductIdList(List<Integer> productIdList) throws DataAccessException;

	public List<ArticleVO> selectArticlesByUserIdAndStat(SalesDTO salesDTO) throws DataAccessException;

	public ArticleVO selectArticle(int productId) throws DataAccessException;

	public boolean updateArticleStatus(UpdateStatusDTO updateStatusDTO) throws DataAccessException;

	public List<LikeDTO> selectLikeList(String loginId) throws DataAccessException;

	public boolean selectLike(LikeDTO likeDTO) throws DataAccessException;

	public boolean addLike(LikeDTO likeDTO) throws DataAccessException;

	public boolean removeLike(LikeDTO likeDTO) throws DataAccessException;

	public void increaseView(int productId) throws DataAccessException;
}
