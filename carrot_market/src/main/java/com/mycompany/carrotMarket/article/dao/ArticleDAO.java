package com.mycompany.carrotMarket.article.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.article.dto.LikeDTO;
import com.mycompany.carrotMarket.article.dto.SalesDTO;
import com.mycompany.carrotMarket.article.dto.UpdateHiddenDTO;
import com.mycompany.carrotMarket.article.dto.UpdateImagesDTO;
import com.mycompany.carrotMarket.article.dto.UpdateStatusDTO;
import com.mycompany.carrotMarket.article.vo.ArticleVO;
import com.mycompany.carrotMarket.article.vo.ImageVO;

public interface ArticleDAO {
	public int insertArticle(ArticleVO articleVO) throws DataAccessException;

	public int insertImageFiles(ArticleVO articleVO) throws DataAccessException;

	public List<ArticleVO> selectArticles() throws DataAccessException;

	public List<ArticleVO> selectArticlesByProductIdList(List<Integer> productIdList) throws DataAccessException;

	public List<ArticleVO> selectTradedArticles(String buyerId) throws DataAccessException;

	public List<ArticleVO> selectArticlesByUserIdAndStat(SalesDTO salesDTO) throws DataAccessException;

	public List<ArticleVO> selectHiddenArticles(String userId) throws DataAccessException;

	public int selectActiveArticlesCount(String userId) throws DataAccessException;

	public int selectSoldArticlesCount(String userId) throws DataAccessException;

	public int selectHiddenArticlesCount(String userId) throws DataAccessException;

	public ArticleVO selectArticle(int productId) throws DataAccessException;

	public int updateArticle(ArticleVO articleVO) throws DataAccessException;

	public int updateImages(UpdateImagesDTO updateImagesDTO) throws DataAccessException;

	public int updateArticleStatus(UpdateStatusDTO updateStatusDTO) throws DataAccessException;

	public int updateArticleHidden(UpdateHiddenDTO updateHiddenDTO) throws DataAccessException;

	public int deleteArticleById(int productId) throws DataAccessException;

	public List<String> selectImagesName(int productId) throws DataAccessException;

	public List<ImageVO> selectArticleImages(int productId) throws DataAccessException;

	public int deleteImagesById(int productId) throws DataAccessException;

	public List<LikeDTO> selectLikeList(String loginId) throws DataAccessException;

	public int selectLike(LikeDTO likeDTO) throws DataAccessException;

	public int addLike(LikeDTO likeDTO) throws DataAccessException;

	public int increaseLike(int productId) throws DataAccessException;

	public int removeLike(LikeDTO likeDTO) throws DataAccessException;

	public int decreaseLike(int productId) throws DataAccessException;

	public int deleteLikesById(int productId) throws DataAccessException;

	public int increaseChat(int productId) throws DataAccessException;

	public int decreaseChat(int productId) throws DataAccessException;

	public void increaseView(int productId) throws DataAccessException;
}
