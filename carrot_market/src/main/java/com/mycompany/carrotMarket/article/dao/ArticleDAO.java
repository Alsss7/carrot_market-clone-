package com.mycompany.carrotMarket.article.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.article.dto.LikeDTO;
import com.mycompany.carrotMarket.article.vo.ArticleVO;

public interface ArticleDAO {
	public int insertArticle(ArticleVO articleVO) throws DataAccessException;

	public int insertImageFiles(ArticleVO articleVO) throws DataAccessException;

	public List<ArticleVO> selectArticles() throws DataAccessException;

	public List<String> selectImages(int productId) throws DataAccessException;

	public ArticleVO selectArticle(int productId) throws DataAccessException;

	public boolean selectLike(LikeDTO likeDTO) throws DataAccessException;

	public int addLike(LikeDTO likeDTO) throws DataAccessException;

	public int increaseLike(int productId) throws DataAccessException;

	public int removeLike(LikeDTO likeDTO) throws DataAccessException;

	public int decreaseLike(int productId) throws DataAccessException;

	public void increaseView(int productId) throws DataAccessException;
}
