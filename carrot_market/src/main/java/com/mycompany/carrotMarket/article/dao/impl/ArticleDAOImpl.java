package com.mycompany.carrotMarket.article.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mycompany.carrotMarket.article.dao.ArticleDAO;
import com.mycompany.carrotMarket.article.dto.LikeDTO;
import com.mycompany.carrotMarket.article.dto.MoreArticleDTO;
import com.mycompany.carrotMarket.article.dto.SalesDTO;
import com.mycompany.carrotMarket.article.dto.SearchDTO;
import com.mycompany.carrotMarket.article.dto.UpdateHiddenDTO;
import com.mycompany.carrotMarket.article.dto.UpdateImagesDTO;
import com.mycompany.carrotMarket.article.dto.UpdateStatusDTO;
import com.mycompany.carrotMarket.article.vo.ArticleVO;
import com.mycompany.carrotMarket.article.vo.ImageVO;

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
	public int selectArticlesCountBySearch(String value) throws DataAccessException {
		int count = sqlSession.selectOne("mappers.article.selectArticlesCountBySearch", value);
		return count;
	}

	@Override
	public List<ArticleVO> selectArticlesBySearch(SearchDTO dto) throws DataAccessException {
		List<ArticleVO> list = sqlSession.selectList("mappers.article.selectArticlesBySearchInRegion", dto);
		return list;
	}

	@Override
	public int selectArticlesCountBySearch(SearchDTO dto) throws DataAccessException {
		int count = sqlSession.selectOne("mappers.article.selectArticlesCountBySearchInRegion", dto);
		return count;
	}

	@Override
	public List<ArticleVO> selectMoreArticlesBySearch(MoreArticleDTO dto) throws DataAccessException {
		List<ArticleVO> list = sqlSession.selectList("mappers.article.selectMoreArticlesBySearch", dto);
		return list;
	}

	@Override
	public List<ArticleVO> selectArticlesByRegion(String region) throws DataAccessException {
		List<ArticleVO> list = sqlSession.selectList("mappers.article.selectArticlesByRegion", region);
		return list;
	}

	@Override
	public List<ArticleVO> selectMoreArticlesByRegion(MoreArticleDTO dto) throws DataAccessException {
		List<ArticleVO> list = sqlSession.selectList("mappers.article.selectMoreArticlesByRegion", dto);
		return list;
	}

	@Override
	public int selectArticlesCountByRegion(String region) throws DataAccessException {
		int count = sqlSession.selectOne("mappers.article.selectArticlesCountByRegion", region);
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
	public List<ArticleVO> selectArticlesByUserIdAndStat(SalesDTO salesDTO) throws DataAccessException {
		List<ArticleVO> list = sqlSession.selectList("mappers.article.selectArticlesByUserIdAndStat", salesDTO);
		return list;
	}

	@Override
	public List<ArticleVO> selectHiddenArticles(String userId) throws DataAccessException {
		List<ArticleVO> list = sqlSession.selectList("mappers.article.selectHiddenArticles", userId);
		return list;
	}

	@Override
	public int selectActiveArticlesCount(String userId) throws DataAccessException {
		int result = sqlSession.selectOne("mappers.article.selectActiveArticlesCount", userId);
		return result;
	}

	@Override
	public int selectSoldArticlesCount(String userId) throws DataAccessException {
		int result = sqlSession.selectOne("mappers.article.selectSoldArticlesCount", userId);
		return result;
	}

	@Override
	public int selectHiddenArticlesCount(String userId) throws DataAccessException {
		int result = sqlSession.selectOne("mappers.article.selectHiddenArticlesCount", userId);
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
	public int updateImages(UpdateImagesDTO updateImagesDTO) throws DataAccessException {
		int result = sqlSession.update("mappers.article.updateImages", updateImagesDTO);
		return result;
	}

	@Override
	public int updateArticleStatus(UpdateStatusDTO updateStatusDTO) throws DataAccessException {
		int result = sqlSession.update("mappers.article.updateArticleStatus", updateStatusDTO);
		return result;
	}

	@Override
	public int updateArticleHidden(UpdateHiddenDTO updateHiddenDTO) throws DataAccessException {
		int result = sqlSession.update("mappers.article.updateArticleHidden", updateHiddenDTO);
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
	public List<LikeDTO> selectLikeList(String loginId) throws DataAccessException {
		List<LikeDTO> likeList = sqlSession.selectList("mappers.article.selectLikeList", loginId);
		return likeList;
	}

	@Override
	public int selectLike(LikeDTO likeDTO) throws DataAccessException {
		int result = sqlSession.selectOne("mappers.article.selectLike", likeDTO);
		return result;
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
