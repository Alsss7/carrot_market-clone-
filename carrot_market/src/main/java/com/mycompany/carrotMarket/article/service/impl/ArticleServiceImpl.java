package com.mycompany.carrotMarket.article.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.carrotMarket.article.dao.ArticleDAO;
import com.mycompany.carrotMarket.article.dao.TradeDAO;
import com.mycompany.carrotMarket.article.dto.LikeDTO;
import com.mycompany.carrotMarket.article.dto.SalesDTO;
import com.mycompany.carrotMarket.article.dto.TradeDTO;
import com.mycompany.carrotMarket.article.dto.UpdateHiddenDTO;
import com.mycompany.carrotMarket.article.dto.UpdateImagesDTO;
import com.mycompany.carrotMarket.article.dto.UpdateStatusDTO;
import com.mycompany.carrotMarket.article.service.ArticleService;
import com.mycompany.carrotMarket.article.vo.ArticleVO;
import com.mycompany.carrotMarket.article.vo.ImageVO;
import com.mycompany.carrotMarket.article.vo.TradeVO;
import com.mycompany.carrotMarket.chat.service.ChatService;

@Service
public class ArticleServiceImpl implements ArticleService {

	private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

	@Autowired
	ChatService chatService;

	@Autowired
	ArticleDAO articleDAO;

	@Autowired
	TradeDAO tradeDAO;

	@Override
	@Transactional
	public boolean addArticle(ArticleVO articleVO) throws DataAccessException {
		int result = articleDAO.insertArticle(articleVO);
		if (articleVO.getFilesName() != null && articleVO.getFilesName().size() != 0) {
			articleDAO.insertImageFiles(articleVO);
		}
		if (result != 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public List<ArticleVO> selectArticles() throws DataAccessException {
		List<ArticleVO> articleList = articleDAO.selectArticles();
		if (articleList.size() > 0) {
			for (ArticleVO article : articleList) {
				List<String> imageList = articleDAO.selectImagesName(article.getProductId());
				article.setFilesName(imageList);
			}
		}
		return articleList;
	}

	@Override
	@Transactional
	public List<ArticleVO> selectArticlesByProductIdList(List<Integer> productIdList) throws DataAccessException {
		List<ArticleVO> articleList = articleDAO.selectArticlesByProductIdList(productIdList);
		if (articleList.size() > 0) {
			for (ArticleVO article : articleList) {
				List<String> imageList = articleDAO.selectImagesName(article.getProductId());
				article.setFilesName(imageList);
			}
		}
		return articleList;
	}

	@Override
	@Transactional
	public List<ArticleVO> selectArticlesByUserIdAndStat(SalesDTO salesDTO) throws DataAccessException {
		List<ArticleVO> articleList = articleDAO.selectArticlesByUserIdAndStat(salesDTO);
		if (articleList.size() > 0) {
			for (ArticleVO article : articleList) {
				List<String> imageList = articleDAO.selectImagesName(article.getProductId());
				article.setFilesName(imageList);
			}
		}
		return articleList;
	}

	@Override
	@Transactional
	public List<ArticleVO> selectHiddenArticles(String userId) throws DataAccessException {
		List<ArticleVO> articleList = articleDAO.selectHiddenArticles(userId);
		if (articleList.size() > 0) {
			for (ArticleVO article : articleList) {
				List<String> imageList = articleDAO.selectImagesName(article.getProductId());
				article.setFilesName(imageList);
			}
		}
		return articleList;
	}

	@Override
	public Map<String, Integer> selectArticlesCountByStatus(String userId) throws DataAccessException {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int activeCount = articleDAO.selectActiveArticlesCount(userId);
		int soldCount = articleDAO.selectSoldArticlesCount(userId);
		int hiddenCount = articleDAO.selectHiddenArticlesCount(userId);

		map.put("activeCount", activeCount);
		map.put("soldCount", soldCount);
		map.put("hiddenCount", hiddenCount);

		return map;
	}

	@Override
	@Transactional
	public List<ArticleVO> selectArticlesPurchasedById(String buyerId) throws DataAccessException {
		List<ArticleVO> articleList = articleDAO.selectTradedArticles(buyerId);
		if (articleList.size() > 0) {
			for (ArticleVO article : articleList) {
				List<String> imageList = articleDAO.selectImagesName(article.getProductId());
				article.setFilesName(imageList);
			}
		}
		return articleList;
	}

	@Override
	@Transactional
	public ArticleVO selectArticle(int productId) throws DataAccessException {
		ArticleVO article = articleDAO.selectArticle(productId);
		if (article != null) {
			List<String> imageList = articleDAO.selectImagesName(productId);
			article.setFilesName(imageList);
		}
		return article;
	}

	@Override
	@Transactional
	public boolean updateArticle(UpdateImagesDTO updateImagesDTO, ArticleVO articleVO) throws DataAccessException {
		int result = articleDAO.updateArticle(articleVO);

		List<Integer> keepImages = updateImagesDTO.getKeepImages();
		if (keepImages != null && keepImages.size() != 0) {
			articleDAO.updateImages(updateImagesDTO);
		} else {
			articleDAO.deleteImagesById(articleVO.getProductId());
		}

		if (articleVO.getFilesName() != null && articleVO.getFilesName().size() != 0) {
			articleDAO.insertImageFiles(articleVO);
		}

		if (result != 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean updateArticleStatus(UpdateStatusDTO updateStatusDTO) throws DataAccessException {
		int productId = updateStatusDTO.getProductId();
		String buyerId = updateStatusDTO.getBuyerId();
		String status = updateStatusDTO.getStatus();
		logger.info("productId: {}", String.valueOf(productId));
		logger.info("buyerId : {}", buyerId);
		logger.info("status : {}", status);

		int result1 = articleDAO.updateArticleStatus(updateStatusDTO);

		int result2;
		if (status.equals("Active")) {
			result2 = tradeDAO.deleteTradeByProductId(productId);
		} else {
			// status.equals("Booking") || status.equals("Sold")
			TradeVO trade = tradeDAO.selectTradeByProductId(productId);
			if (trade != null) {
				logger.info("tradeDAO.update");
				result2 = tradeDAO.updateTradeByProductId(new TradeDTO(productId, buyerId));
			} else {
				logger.info("tradeDAO.insert");
				result2 = tradeDAO.insertTrade(new TradeDTO(productId, buyerId));
			}
		}

		if (result1 != 0 && result2 != 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean updateArticleHidden(UpdateHiddenDTO updateHiddenDTO) throws DataAccessException {
		int result = articleDAO.updateArticleHidden(updateHiddenDTO);
		if (result != 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean deleteArticleById(int productId) throws DataAccessException {
		ArticleVO article = selectArticle(productId);

		if (article.getFilesName().size() > 0) {
			int result = articleDAO.deleteImagesById(productId);
			System.out.println(result);
		}

		articleDAO.deleteLikesById(productId);

		chatService.deleteChatByProductId(productId);

		int result = articleDAO.deleteArticleById(productId);

		if (result != 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<ImageVO> selectArticleImages(int productId) throws DataAccessException {
		List<ImageVO> list = articleDAO.selectArticleImages(productId);
		return list;
	}

	@Override
	public List<LikeDTO> selectLikeList(String loginId) throws DataAccessException {
		List<LikeDTO> likeList = articleDAO.selectLikeList(loginId);
		return likeList;
	}

	@Override
	public boolean selectLike(LikeDTO likeDTO) throws DataAccessException {
		int result = articleDAO.selectLike(likeDTO);
		if (result == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean addLike(LikeDTO likeDTO) throws DataAccessException {
		int result1 = articleDAO.addLike(likeDTO);
		int result2 = articleDAO.increaseLike(likeDTO.getProductId());
		if (result1 != 0 && result2 != 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean removeLike(LikeDTO likeDTO) throws DataAccessException {
		int result1 = articleDAO.removeLike(likeDTO);
		int result2 = articleDAO.decreaseLike(likeDTO.getProductId());
		if (result1 != 0 && result2 != 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int increaseChat(int productId) throws DataAccessException {
		int result = articleDAO.increaseChat(productId);
		return result;
	}

	@Override
	public int decreaseChat(int productId) throws DataAccessException {
		int result = articleDAO.decreaseChat(productId);
		return result;
	}

	@Override
	public void increaseView(int productId) throws DataAccessException {
		articleDAO.increaseView(productId);
	}

}
