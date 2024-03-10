package com.mycompany.carrotMarket.article.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mycompany.carrotMarket.article.dao.ArticleDAO;
import com.mycompany.carrotMarket.article.dto.LikeDTO;
import com.mycompany.carrotMarket.article.dto.SalesDTO;
import com.mycompany.carrotMarket.article.vo.ArticleVO;

@Repository
public class ArticleDAOImpl implements ArticleDAO {

	@Autowired
	SqlSession sqlSession;

	@Override
	public int insertArticle(ArticleVO articleVO) throws DataAccessException {
		int result = sqlSession.insert("mappers.article.insertArticle", articleVO);
		return result;
	}

	@Override
	public int insertImageFiles(ArticleVO articleVO) throws DataAccessException {
		int result = sqlSession.update("mappers.article.insertProductImages", articleVO);
		return result;
	}

	@Override
	public List<ArticleVO> selectArticles() throws DataAccessException {
		List<ArticleVO> list = sqlSession.selectList("mappers.article.selectArticles");
		return list;
	}

	@Override
	public List<ArticleVO> selectArticlesByProductIdList(List<Integer> productIdList) throws DataAccessException {
		List<ArticleVO> list = sqlSession.selectList("mappers.article.selectArticlesByProductIdList", productIdList);
		return list;
	}

	@Override
	public List<ArticleVO> selectArticlesByUserIdAndStat(SalesDTO salesDTO) throws DataAccessException {
		List<ArticleVO> list = sqlSession.selectList("mappers.article.selectArticlesByUserIdAndStat", salesDTO);
		return list;
	}

	@Override
	public List<String> selectImages(int productId) throws DataAccessException {
		List<String> list = sqlSession.selectList("mappers.article.selectImages", productId);
		return list;
	}

	@Override
	public ArticleVO selectArticle(int productId) throws DataAccessException {
		ArticleVO article = sqlSession.selectOne("mappers.article.selectArticle", productId);
		return article;
	}

	@Override
	public List<LikeDTO> selectLikeList(String loginId) throws DataAccessException {
		List<LikeDTO> likeList = sqlSession.selectList("mappers.article.selectLikeList", loginId);
		return likeList;
	}

	@Override
	public boolean selectLike(LikeDTO likeDTO) throws DataAccessException {
		int result = sqlSession.selectOne("mappers.article.selectLike", likeDTO);
		if (result == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int addLike(LikeDTO likeDTO) throws DataAccessException {
		int result = sqlSession.insert("mappers.article.addLike", likeDTO);
		return result;
	}

	@Override
	public int increaseLike(int productId) throws DataAccessException {
		int result = sqlSession.update("mappers.article.increaseLike", productId);
		return result;
	}

	@Override
	public int removeLike(LikeDTO likeDTO) throws DataAccessException {
		int result = sqlSession.delete("mappers.article.removeLike", likeDTO);
		return result;
	}

	@Override
	public int decreaseLike(int productId) throws DataAccessException {
		int result = sqlSession.update("mappers.article.decreaseLike", productId);
		return result;
	}

	@Override
	public void increaseView(int productId) throws DataAccessException {
		sqlSession.update("mappers.article.increaseView", productId);
	}

}
