package com.mycompany.carrotMarket.article.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.carrotMarket.article.dao.ArticleDAO;
import com.mycompany.carrotMarket.article.service.ArticleService;
import com.mycompany.carrotMarket.article.vo.ArticleVO;
import com.mycompany.carrotMarket.article.vo.ImageVO;
import com.mycompany.carrotMarket.article.vo.LikeVO;
import com.mycompany.carrotMarket.chat.service.ChatService;
import com.mycompany.carrotMarket.review.dao.ReviewDAO;
import com.mycompany.carrotMarket.trade.dao.TradeDAO;
import com.mycompany.carrotMarket.trade.vo.TradeVO;

@Service
public class ArticleServiceImpl implements ArticleService {

	private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

	@Autowired
	ArticleDAO articleDAO;

	@Autowired
	ChatService chatService;

	@Autowired
	TradeDAO tradeService;

	@Autowired
	ReviewDAO reviewService;

	@Autowired
	private ServletContext servletContext;

	@Override
	@Transactional
	public boolean registerArticle(ArticleVO vo) throws DataAccessException {
		List<MultipartFile> files = vo.getFiles();
		vo.setFilesNameFromMultipart(files);

		int result = articleDAO.insertArticle(vo);
		if (vo.getFilesName() != null && vo.getFilesName().size() != 0) {
			articleDAO.insertImageFiles(vo);
		}

		if (result != 0) {
			uploadImageFile(vo.getProductId(), files);
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public List<ArticleVO> getArticles() throws DataAccessException {
		List<ArticleVO> articleList = articleDAO.selectArticles();
		setArticleImages(articleList);
		return articleList;
	}

	@Override
	@Transactional
	public List<ArticleVO> getArticlesByRandom(int count) throws DataAccessException {
		List<ArticleVO> articleList = articleDAO.selectArticlesByRandom(count);
		setArticleImages(articleList);
		return articleList;
	}

	@Override
	@Transactional
	public List<ArticleVO> getArticlesBySearch(String value) throws DataAccessException {
		List<ArticleVO> articleList = articleDAO.selectArticlesBySearch(value);
		setArticleImages(articleList);
		return articleList;
	}

	@Override
	public int countArticlesBySearch(String value) throws DataAccessException {
		return articleDAO.countArticlesBySearch(value);
	}

	@Override
	@Transactional
	public List<ArticleVO> getArticlesBySearch(String value, String region) throws DataAccessException {
		List<ArticleVO> articleList = articleDAO.selectArticlesBySearch(value, region);
		setArticleImages(articleList);
		return articleList;
	}

	@Override
	public int countArticlesBySearch(String value, String region) throws DataAccessException {
		return articleDAO.countArticlesBySearch(value, region);
	}

	@Override
	@Transactional
	public List<ArticleVO> getMoreArticlesBySearch(String value, int beginSize, int endSize, String region)
			throws DataAccessException {
		List<ArticleVO> articleList = articleDAO.selectMoreArticlesBySearch(value, beginSize, endSize, region);
		setArticleImages(articleList);
		return articleList;
	}

	@Override
	@Transactional
	public List<ArticleVO> getMoreArticlesBySearch(String value, int beginSize, int endSize)
			throws DataAccessException {
		List<ArticleVO> articleList = articleDAO.selectMoreArticlesBySearch(value, beginSize, endSize);
		setArticleImages(articleList);
		return articleList;
	}

	@Override
	@Transactional
	public List<ArticleVO> getArticlesByRegion(String region) throws DataAccessException {
		List<ArticleVO> articleList = articleDAO.selectArticlesByRegion(region);
		setArticleImages(articleList);
		return articleList;
	}

	@Override
	@Transactional
	public List<ArticleVO> getMoreArticlesByRegion(int beginSize, int endSize, String region)
			throws DataAccessException {
		List<ArticleVO> articleList = articleDAO.selectMoreArticlesByRegion(beginSize, endSize, region);
		setArticleImages(articleList);
		return articleList;
	}

	@Override
	public int countArticlesByRegion(String region) throws DataAccessException {
		int count = articleDAO.countArticlesByRegion(region);
		return count;
	}

	@Override
	@Transactional
	public List<ArticleVO> getRandomArticlesByContainRegion(String region) throws DataAccessException {
		List<ArticleVO> articleList = articleDAO.selectRandomArticlesByContainRegion(region);
		setArticleImages(articleList);
		return articleList;
	}

	@Override
	@Transactional
	public List<ArticleVO> getArticlesByProductIdList(List<Integer> productIdList) throws DataAccessException {
		List<ArticleVO> articleList = articleDAO.selectArticlesByProductIdList(productIdList);
		setArticleImages(articleList);
		return articleList;
	}

	@Override
	@Transactional
	public List<ArticleVO> getArticlesByUserIdAndStat(String loginId, String status) throws DataAccessException {
		List<ArticleVO> articleList = articleDAO.selectArticlesByUserIdAndStat(loginId, status);
		setArticleImages(articleList);
		return articleList;
	}

	@Override
	@Transactional
	public List<ArticleVO> getHiddenArticles(String userId) throws DataAccessException {
		List<ArticleVO> articleList = articleDAO.selectHiddenArticles(userId);
		setArticleImages(articleList);
		return articleList;
	}

	@Override
	public Map<String, Integer> countArticlesByStatus(String userId) throws DataAccessException {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int activeCount = articleDAO.countActiveArticles(userId);
		int soldCount = articleDAO.countSoldArticles(userId);
		int hiddenCount = articleDAO.countHiddenArticles(userId);

		map.put("activeCount", activeCount);
		map.put("soldCount", soldCount);
		map.put("hiddenCount", hiddenCount);
		return map;
	}

	@Override
	@Transactional
	public List<ArticleVO> getArticlesPurchasedById(String buyerId) throws DataAccessException {
		List<ArticleVO> articleList = articleDAO.selectTradedArticles(buyerId);
		setArticleImages(articleList);
		return articleList;
	}

	@Override
	@Transactional
	public ArticleVO getArticle(int productId) throws DataAccessException {
		ArticleVO article = articleDAO.selectArticle(productId);
		if (article != null) {
			List<String> imageList = articleDAO.selectImagesName(productId);
			article.setFilesNameFromString(imageList);
		}
		return article;
	}

	@Override
	@Transactional
	public boolean modifyArticle(int productId, ArticleVO articleVO, HttpServletRequest req)
			throws DataAccessException {
		List<MultipartFile> files = articleVO.getFiles();
		articleVO.setFilesNameFromMultipart(files);

		int result = articleDAO.updateArticle(articleVO);

		List<Integer> keepImages = new ArrayList<Integer>();
		@SuppressWarnings("unchecked")
		Enumeration<String> parameterNames = req.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			if (!paramName.startsWith("image")) {
				continue;
			} else {
				int imageId = Integer.parseInt(paramName.replace("image", ""));
				String paramValue = req.getParameter(paramName);
				if (paramValue.equals("true")) {
					keepImages.add(imageId);
				}
			}
		}

		if (keepImages != null && keepImages.size() != 0) {
			articleDAO.updateImages(productId, keepImages);
		} else {
			articleDAO.deleteImagesById(articleVO.getProductId());
		}

		if (articleVO.getFilesName() != null && articleVO.getFilesName().size() != 0) {
			articleDAO.insertImageFiles(articleVO);
		}

		if (result != 0) {
			updateImageFile(articleVO, files);
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean modifyArticleStatus(int productId, String status, String buyerId) throws DataAccessException {
		int result1 = articleDAO.updateArticleStatus(productId, status, buyerId);

		int result2;
		if (status.equals("Active")) {
			reviewService.deleteReviewByProductId(productId);
			result2 = tradeService.deleteTradeByProductId(productId);
		} else {
			// status.equals("Booking") || status.equals("Sold")
			TradeVO trade = tradeService.selectTradeByProductId(productId);
			TradeVO newTrade = new TradeVO();
			newTrade.setProductId(productId);
			newTrade.setBuyerId(buyerId);
			if (trade != null) {
				result2 = tradeService.updateTradeByProductId(newTrade);
			} else {
				result2 = tradeService.insertTrade(newTrade);
			}
		}

		if (result1 != 0 && result2 != 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean modifyArticleHidden(int productId, int hidden) throws DataAccessException {
		int result = articleDAO.updateArticleHidden(productId, hidden);
		if (result != 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean removeArticleById(int productId) throws DataAccessException {
		ArticleVO article = getArticle(productId);

		if (article.getFilesName().size() > 0) {
			int result = articleDAO.deleteImagesById(productId);
			System.out.println(result);
		}

		articleDAO.deleteLikesById(productId);

		chatService.removeChatByProductId(productId);

		reviewService.deleteReviewByProductId(productId);

		tradeService.deleteTradeByProductId(productId);

		int result = articleDAO.deleteArticleById(productId);

		if (result != 0) {
			deleteImageFile(productId);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<ImageVO> getArticleImages(int productId) throws DataAccessException {
		List<ImageVO> list = articleDAO.selectArticleImages(productId);
		return list;
	}

	@Override
	public List<LikeVO> getLikeList(String loginId) throws DataAccessException {
		List<LikeVO> likeList = articleDAO.selectLikeList(loginId);
		return likeList;
	}

	@Override
	public boolean isLikedByUser(LikeVO vo) throws DataAccessException {
		int result = articleDAO.selectLike(vo);
		if (result != 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean addLike(LikeVO vo) throws DataAccessException {
		int result1 = articleDAO.insertLike(vo);
		int result2 = articleDAO.increaseLike(vo.getProductId());
		if (result1 != 0 && result2 != 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean removeLike(LikeVO vo) throws DataAccessException {
		int result1 = articleDAO.deleteLike(vo);
		int result2 = articleDAO.decreaseLike(vo.getProductId());
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

	private void setArticleImages(List<ArticleVO> articleList) {
		if (articleList.size() > 0) {
			for (ArticleVO article : articleList) {
				List<String> imageList = articleDAO.selectImagesName(article.getProductId());
				article.setFilesNameFromString(imageList);
			}
		}
	}

	/*
	 * 게시글 이미지 업로드 메서드
	 */
	private void uploadImageFile(int productId, List<MultipartFile> files) {
		for (MultipartFile file : files) {
			if (!file.isEmpty()) {
				try {
					String uploadDir = servletContext.getRealPath("/resources/image/product_image/" + productId);
					String fileName = file.getOriginalFilename();
					String filePath = uploadDir + "\\" + fileName;

					// 디렉토리가 존재하지 않으면 생성
					File directory = new File(uploadDir);
					if (!directory.exists()) {
						directory.mkdir();
					}

					file.transferTo(new File(filePath));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * 게시글 이미지 삭제 메서드
	 */
	private void deleteImageFile(int productId) {
		String uploadDir = servletContext.getRealPath("/resources/image/product_image/" + productId);
		File directory = new File(uploadDir);
		if (directory.exists()) {
			File[] files = directory.listFiles();
			for (File file : files) {
				file.delete();
			}
			directory.delete();
		}
	}

	/*
	 * 게시글 이미지 업데이트 메서드
	 */
	private void updateImageFile(ArticleVO article, List<MultipartFile> files) {
		String uploadDir = servletContext.getRealPath("/resources/image/product_image/" + article.getProductId());
		File directory = new File(uploadDir);
		if (directory.exists()) {
			File[] existFiles = directory.listFiles();
			for (File file : existFiles) {
				boolean isFileExists = false;
				for (String fileName : article.getFilesName()) {
					if (file.getName().equals(fileName)) {
						isFileExists = true;
					}
				}
				if (!isFileExists) {
					file.delete();
				}
			}
		}
		if (files != null && files.size() != 0) {
			uploadImageFile(article.getProductId(), files);
		}
	}
}
