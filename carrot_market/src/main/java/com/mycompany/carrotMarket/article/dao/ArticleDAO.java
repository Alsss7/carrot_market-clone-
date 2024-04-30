package com.mycompany.carrotMarket.article.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.article.vo.ArticleVO;
import com.mycompany.carrotMarket.article.vo.ImageVO;
import com.mycompany.carrotMarket.article.vo.LikeVO;

public interface ArticleDAO {
	public int insertArticle(ArticleVO vo) throws DataAccessException;

	public int insertImageFiles(ArticleVO vo) throws DataAccessException;

	public List<ArticleVO> selectArticles() throws DataAccessException;

	public List<ArticleVO> selectArticlesByRandom(int count) throws DataAccessException;

	public List<ArticleVO> selectArticlesBySearch(String value) throws DataAccessException;

	public int countArticlesBySearch(String value) throws DataAccessException;

	public List<ArticleVO> selectArticlesBySearch(String value, String region) throws DataAccessException;

	public int countArticlesBySearch(String value, String region) throws DataAccessException;

	public List<ArticleVO> selectMoreArticlesBySearch(String value, int beginSize, int endSize, String region)
			throws DataAccessException;

	public List<ArticleVO> selectMoreArticlesBySearch(String value, int beginSize, int endSize)
			throws DataAccessException;

	public List<ArticleVO> selectArticlesByRegion(String region) throws DataAccessException;

	public List<ArticleVO> selectMoreArticlesByRegion(int beginSize, int endSize, String region)
			throws DataAccessException;

	public int countArticlesByRegion(String region) throws DataAccessException;

	public List<ArticleVO> selectRandomArticlesByContainRegion(String region) throws DataAccessException;

	public List<ArticleVO> selectArticlesByProductIdList(List<Integer> productIdList) throws DataAccessException;

	public List<ArticleVO> selectTradedArticles(String buyerId) throws DataAccessException;

	public List<ArticleVO> selectArticlesByUserIdAndStat(String loginId, String status) throws DataAccessException;

	public List<ArticleVO> selectHiddenArticles(String userId) throws DataAccessException;

	public int countActiveArticles(String userId) throws DataAccessException;

	public int countSoldArticles(String userId) throws DataAccessException;

	public int countHiddenArticles(String userId) throws DataAccessException;

	public ArticleVO selectArticle(int productId) throws DataAccessException;

	public int updateArticle(ArticleVO articleVO) throws DataAccessException;

	public int updateImages(int productId, List<Integer> keepImages) throws DataAccessException;

	public int updateArticleStatus(int productId, String status, String buyerId) throws DataAccessException;

	public int updateArticleHidden(int productId, int hidden) throws DataAccessException;

	public int deleteArticleById(int productId) throws DataAccessException;

	public List<String> selectImagesName(int productId) throws DataAccessException;

	public List<ImageVO> selectArticleImages(int productId) throws DataAccessException;

	public int deleteImagesById(int productId) throws DataAccessException;

	public List<LikeVO> selectLikeList(String loginId) throws DataAccessException;

	public int selectLike(LikeVO vo) throws DataAccessException;

	public int insertLike(LikeVO vo) throws DataAccessException;

	public int deleteLike(LikeVO vo) throws DataAccessException;

	public int increaseLike(int productId) throws DataAccessException;

	public int decreaseLike(int productId) throws DataAccessException;

	public int deleteLikesById(int productId) throws DataAccessException;

	public int increaseChat(int productId) throws DataAccessException;

	public int decreaseChat(int productId) throws DataAccessException;

	public void increaseView(int productId) throws DataAccessException;
}
