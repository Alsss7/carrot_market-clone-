package com.mycompany.carrotMarket.article.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.carrotMarket.article.dao.ArticleDAO;
import com.mycompany.carrotMarket.article.dto.LikeDTO;
import com.mycompany.carrotMarket.article.dto.SalesDTO;
import com.mycompany.carrotMarket.article.dto.UpdateHiddenDTO;
import com.mycompany.carrotMarket.article.dto.UpdateImagesDTO;
import com.mycompany.carrotMarket.article.dto.UpdateStatusDTO;
import com.mycompany.carrotMarket.article.service.ArticleService;
import com.mycompany.carrotMarket.article.vo.ArticleVO;
import com.mycompany.carrotMarket.article.vo.ImageVO;
import com.mycompany.carrotMarket.chat.service.ChatService;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ChatService chatService;

	@Autowired
	ArticleDAO articleDAO;

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
	public List<ArticleVO> selectArticlesByHidden(String userId) throws DataAccessException {
		List<ArticleVO> articleList = articleDAO.selectArticlesByHidden(userId);
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
	public boolean updateArticleStatus(UpdateStatusDTO updateStatusDTO) throws DataAccessException {
		int result = articleDAO.updateArticleStatus(updateStatusDTO);
		if (result != 0) {
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
