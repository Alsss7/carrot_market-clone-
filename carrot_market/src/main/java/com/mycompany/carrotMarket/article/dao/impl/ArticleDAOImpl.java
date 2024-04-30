package com.mycompany.carrotMarket.article.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mycompany.carrotMarket.article.dao.ArticleDAO;
import com.mycompany.carrotMarket.article.vo.ArticleVO;
import com.mycompany.carrotMarket.article.vo.ImageVO;
import com.mycompany.carrotMarket.article.vo.LikeVO;

@Repository
public class ArticleDAOImpl implements ArticleDAO {

	@Autowired
	SqlSession sqlSession;

	@Override
	public int insertArticle(ArticleVO vo) throws DataAccessException {
		int result = sqlSession.insert("mappers.article.insertArticle", vo);
		return result;
	}

	@Override
	public int insertImageFiles(ArticleVO vo) throws DataAccessException {
		int result = sqlSession.update("mappers.article.insertProductImages", vo);
		return result;
	}

	@Override
	public List<ArticleVO> selectArticles() throws DataAccessException {
		List<ArticleVO> list = sqlSession.selectList("mappers.article.selectArticles");
		return list;
	}

	@Override
	public List<ArticleVO> selectArticlesByRandom(int count) throws DataAccessException {
		List<ArticleVO> list = sqlSession.selectList("mappers.article.selectArticlesByRandom", count);
		return list;
	}

	@Override
	public List<ArticleVO> selectArticlesBySearch(String value) throws DataAccessException {
		List<ArticleVO> list = sqlSession.selectList("mappers.article.selectArticlesBySearch", value);
		return list;
	}

	@Override
	public int countArticlesBySearch(String value) throws DataAccessException {
		int count = sqlSession.selectOne("mappers.article.countArticlesBySearch", value);
		return count;
	}

	@Override
	public List<ArticleVO> selectArticlesBySearch(String value, String region) throws DataAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("value", value);
		map.put("region", region);
		List<ArticleVO> list = sqlSession.selectList("mappers.article.selectArticlesBySearchInRegion", map);
		return list;
	}

	@Override
	public int countArticlesBySearch(String value, String region) throws DataAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("value", value);
		map.put("region", region);
		int count = sqlSession.selectOne("mappers.article.countArticlesBySearchInRegion", map);
		return count;
	}

	@Override
	public List<ArticleVO> selectMoreArticlesBySearch(String value, int beginSize, int endSize, String region)
			throws DataAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("value", value);
		map.put("beginSize", beginSize);
		map.put("endSize", endSize);
		map.put("region", region);
		List<ArticleVO> list = sqlSession.selectList("mappers.article.selectMoreArticlesBySearch", map);
		return list;
	}

	@Override
	public List<ArticleVO> selectMoreArticlesBySearch(String value, int beginSize, int endSize)
			throws DataAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("value", value);
		map.put("beginSize", beginSize);
		map.put("endSize", endSize);
		List<ArticleVO> list = sqlSession.selectList("mappers.article.selectMoreArticlesBySearch", map);
		return list;
	}

	@Override
	public List<ArticleVO> selectArticlesByRegion(String region) throws DataAccessException {
		List<ArticleVO> list = sqlSession.selectList("mappers.article.selectArticlesByRegion", region);
		return list;
	}

	@Override
	public List<ArticleVO> selectMoreArticlesByRegion(int beginSize, int endSize, String region)
			throws DataAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("beginSize", beginSize);
		map.put("endSize", endSize);
		map.put("region", region);
		List<ArticleVO> list = sqlSession.selectList("mappers.article.selectMoreArticlesByRegion", map);
		return list;
	}

	@Override
	public int countArticlesByRegion(String region) throws DataAccessException {
		int count = sqlSession.selectOne("mappers.article.countArticlesByRegion", region);
		return count;
	}

	@Override
	public List<ArticleVO> selectRandomArticlesByContainRegion(String region) throws DataAccessException {
		List<ArticleVO> list = sqlSession.selectList("mappers.article.selectRandomArticlesByContainRegion", region);
		return list;
	}

	@Override
	public List<ArticleVO> selectArticlesByProductIdList(List<Integer> productIdList) throws DataAccessException {
		List<ArticleVO> list = sqlSession.selectList("mappers.article.selectArticlesByProductIdList", productIdList);
		return list;
	}

	@Override
	public List<ArticleVO> selectTradedArticles(String buyerId) throws DataAccessException {
		List<ArticleVO> list = sqlSession.selectList("mappers.article.selectTradedArticles", buyerId);
		return list;
	}

	@Override
	public List<ArticleVO> selectArticlesByUserIdAndStat(String loginId, String status) throws DataAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", loginId);
		map.put("status", status);
		List<ArticleVO> list = sqlSession.selectList("mappers.article.selectArticlesByUserIdAndStat", map);
		return list;
	}

	@Override
	public List<ArticleVO> selectHiddenArticles(String userId) throws DataAccessException {
		List<ArticleVO> list = sqlSession.selectList("mappers.article.selectHiddenArticles", userId);
		return list;
	}

	@Override
	public int countActiveArticles(String userId) throws DataAccessException {
		int result = sqlSession.selectOne("mappers.article.countActiveArticles", userId);
		return result;
	}

	@Override
	public int countSoldArticles(String userId) throws DataAccessException {
		int result = sqlSession.selectOne("mappers.article.countSoldArticles", userId);
		return result;
	}

	@Override
	public int countHiddenArticles(String userId) throws DataAccessException {
		int result = sqlSession.selectOne("mappers.article.countHiddenArticles", userId);
		return result;
	}

	@Override
	public ArticleVO selectArticle(int productId) throws DataAccessException {
		ArticleVO article = sqlSession.selectOne("mappers.article.selectArticle", productId);
		return article;
	}

	@Override
	public int updateArticle(ArticleVO articleVO) throws DataAccessException {
		int result = sqlSession.update("mappers.article.updateArticle", articleVO);
		return result;
	}

	@Override
	public int updateImages(int productId, List<Integer> keepImages) throws DataAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("keepImages", keepImages);
		int result = sqlSession.update("mappers.article.updateImages", map);
		return result;
	}

	@Override
	public int updateArticleStatus(int productId, String status, String buyerId) throws DataAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("status", status);
		map.put("buyerId", buyerId);
		int result = sqlSession.update("mappers.article.updateArticleStatus", map);
		return result;
	}

	@Override
	public int updateArticleHidden(int productId, int hidden) throws DataAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("hidden", hidden);
		int result = sqlSession.update("mappers.article.updateArticleHidden", map);
		return result;
	}

	@Override
	public int deleteArticleById(int productId) throws DataAccessException {
		int result = sqlSession.delete("mappers.article.deleteArticleById", productId);
		return result;
	}

	@Override
	public List<String> selectImagesName(int productId) throws DataAccessException {
		List<String> list = sqlSession.selectList("mappers.article.selectImagesName", productId);
		return list;
	}

	@Override
	public List<ImageVO> selectArticleImages(int productId) throws DataAccessException {
		List<ImageVO> list = sqlSession.selectList("mappers.article.selectArticleImages", productId);
		return list;
	}

	@Override
	public int deleteImagesById(int productId) throws DataAccessException {
		int result = sqlSession.delete("mappers.article.deleteImagesById", productId);
		return result;
	}

	@Override
	public List<LikeVO> selectLikeList(String loginId) throws DataAccessException {
		List<LikeVO> likeList = sqlSession.selectList("mappers.article.selectLikeList", loginId);
		return likeList;
	}

	@Override
	public int selectLike(LikeVO vo) throws DataAccessException {
		int result = sqlSession.selectOne("mappers.article.selectLike", vo);
		return result;
	}

	@Override
	public int insertLike(LikeVO vo) throws DataAccessException {
		int result = sqlSession.insert("mappers.article.insertLike", vo);
		return result;
	}

	@Override
	public int deleteLike(LikeVO vo) throws DataAccessException {
		int result = sqlSession.delete("mappers.article.deleteLike", vo);
		return result;
	}

	@Override
	public int increaseLike(int productId) throws DataAccessException {
		int result = sqlSession.update("mappers.article.increaseLike", productId);
		return result;
	}

	@Override
	public int decreaseLike(int productId) throws DataAccessException {
		int result = sqlSession.update("mappers.article.decreaseLike", productId);
		return result;
	}

	@Override
	public int deleteLikesById(int productId) throws DataAccessException {
		int result = sqlSession.delete("mappers.article.deleteLikesById", productId);
		return result;
	}

	@Override
	public int increaseChat(int productId) throws DataAccessException {
		int result = sqlSession.update("mappers.article.increaseChat", productId);
		return result;
	}

	@Override
	public int decreaseChat(int productId) throws DataAccessException {
		int result = sqlSession.update("mappers.article.decreaseChat", productId);
		return result;
	}

	@Override
	public void increaseView(int productId) throws DataAccessException {
		sqlSession.update("mappers.article.increaseView", productId);
	}

}
