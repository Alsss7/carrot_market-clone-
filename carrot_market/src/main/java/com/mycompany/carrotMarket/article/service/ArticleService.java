package com.mycompany.carrotMarket.article.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.article.dto.LikeDTO;
import com.mycompany.carrotMarket.article.dto.SalesDTO;
import com.mycompany.carrotMarket.article.dto.UpdateHiddenDTO;
import com.mycompany.carrotMarket.article.dto.UpdateImagesDTO;
import com.mycompany.carrotMarket.article.dto.UpdateStatusDTO;
import com.mycompany.carrotMarket.article.vo.ArticleVO;
import com.mycompany.carrotMarket.article.vo.ImageVO;

public interface ArticleService {
	public boolean addArticle(ArticleVO articleVO) throws DataAccessException;

	public List<ArticleVO> selectArticles() throws DataAccessException;

	public List<ArticleVO> selectArticlesByRegion(String region) throws DataAccessException;

	public List<ArticleVO> selectArticlesByContainRegion(String region) throws DataAccessException;

	public List<ArticleVO> selectArticlesByProductIdList(List<Integer> productIdList) throws DataAccessException;

	public List<ArticleVO> selectArticlesByUserIdAndStat(SalesDTO salesDTO) throws DataAccessException;

	public List<ArticleVO> selectHiddenArticles(String userId) throws DataAccessException;

	public Map<String, Integer> selectArticlesCountByStatus(String userId) throws DataAccessException;

	public List<ArticleVO> selectArticlesPurchasedById(String buyerId) throws DataAccessException;

	public ArticleVO selectArticle(int productId) throws DataAccessException;

	public boolean updateArticle(UpdateImagesDTO updateImagesDTO, ArticleVO articleVO) throws DataAccessException;

	public boolean updateArticleStatus(UpdateStatusDTO updateStatusDTO) throws DataAccessException;

	public boolean updateArticleHidden(UpdateHiddenDTO updateHiddenDTO) throws DataAccessException;

	public boolean deleteArticleById(int productId) throws DataAccessException;

	public List<ImageVO> selectArticleImages(int productId) throws DataAccessException;

	public List<LikeDTO> selectLikeList(String loginId) throws DataAccessException;

	public boolean selectLike(LikeDTO likeDTO) throws DataAccessException;

	public boolean addLike(LikeDTO likeDTO) throws DataAccessException;

	public boolean removeLike(LikeDTO likeDTO) throws DataAccessException;

	public int increaseChat(int productId) throws DataAccessException;

	public int decreaseChat(int productId) throws DataAccessException;

	public void increaseView(int productId) throws DataAccessException;
}
