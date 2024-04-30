package com.mycompany.carrotMarket.article.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.article.vo.ArticleVO;
import com.mycompany.carrotMarket.article.vo.ImageVO;
import com.mycompany.carrotMarket.article.vo.LikeVO;

public interface ArticleService {
	public boolean registerArticle(ArticleVO dto) throws DataAccessException;

	public List<ArticleVO> getArticles() throws DataAccessException;

	public List<ArticleVO> getArticlesByRandom(int count) throws DataAccessException;

	public List<ArticleVO> getArticlesBySearch(String value) throws DataAccessException;

	public int countArticlesBySearch(String value) throws DataAccessException;

	public List<ArticleVO> getArticlesBySearch(String value, String region) throws DataAccessException;

	public int countArticlesBySearch(String value, String region) throws DataAccessException;

	public List<ArticleVO> getMoreArticlesBySearch(String value, int beginSize, int endSize, String region)
			throws DataAccessException;

	public List<ArticleVO> getMoreArticlesBySearch(String value, int beginSize, int endSize) throws DataAccessException;

	public List<ArticleVO> getArticlesByRegion(String region) throws DataAccessException;

	public List<ArticleVO> getMoreArticlesByRegion(int beginSize, int endSize, String region)
			throws DataAccessException;

	public int countArticlesByRegion(String region) throws DataAccessException;

	public List<ArticleVO> getRandomArticlesByContainRegion(String region) throws DataAccessException;

	public List<ArticleVO> getArticlesByProductIdList(List<Integer> productIdList) throws DataAccessException;

	public List<ArticleVO> getArticlesByUserIdAndStat(String loginId, String status) throws DataAccessException;

	public List<ArticleVO> getHiddenArticles(String userId) throws DataAccessException;

	public Map<String, Integer> countArticlesByStatus(String userId) throws DataAccessException;

	public List<ArticleVO> getArticlesPurchasedById(String buyerId) throws DataAccessException;

	public ArticleVO getArticle(int productId) throws DataAccessException;

	public boolean modifyArticle(int productId, ArticleVO articleVO, HttpServletRequest req) throws DataAccessException;

	public boolean modifyArticleStatus(int productId, String status, String buyerId) throws DataAccessException;

	public boolean modifyArticleHidden(int productId, int hidden) throws DataAccessException;

	public boolean removeArticleById(int productId) throws DataAccessException;

	public List<ImageVO> getArticleImages(int productId) throws DataAccessException;

	public List<LikeVO> getLikeList(String loginId) throws DataAccessException;

	public boolean isLikedByUser(LikeVO vo) throws DataAccessException;

	public boolean addLike(LikeVO vo) throws DataAccessException;

	public boolean removeLike(LikeVO vo) throws DataAccessException;

	public int increaseChat(int productId) throws DataAccessException;

	public int decreaseChat(int productId) throws DataAccessException;

	public void increaseView(int productId) throws DataAccessException;
}
