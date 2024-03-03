package com.mycompany.carrotMarket.article.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.carrotMarket.article.dao.ArticleDAO;
import com.mycompany.carrotMarket.article.service.ArticleService;
import com.mycompany.carrotMarket.article.vo.ArticleVO;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ArticleDAO articleDAO;

	@Override
	@Transactional
	public boolean addArticle(ArticleVO articleVO) throws DataAccessException {
		int result1 = articleDAO.insertArticle(articleVO);
		System.out.println("productId : " + articleVO.getProductId());
		System.out.print("filesName : ");
		for (String filename : articleVO.getFilesName()) {
			System.out.println(filename);
		}
		if (articleVO.getFilesName() != null && articleVO.getFilesName().size() != 0) {
			articleDAO.insertImageFiles(articleVO);
		}
		if (result1 != 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public Map<ArticleVO, List<String>> selectArticles() throws DataAccessException {
		Map<ArticleVO, List<String>> map = new HashMap<ArticleVO, List<String>>();

		List<ArticleVO> articleList = articleDAO.selectArticles();
		if (articleList.size() > 0) {
			List<String> imageList = new ArrayList<String>();
			for (ArticleVO article : articleList) {
				System.out.println("productId : " + article.getProductId());
				imageList = articleDAO.selectImages(article.getProductId());
				map.put(article, imageList);
			}
		}
		return map;
	}

	@Override
	@Transactional
	public ArticleVO selectArticle(int productId) throws DataAccessException {
		ArticleVO article = articleDAO.selectArticle(productId);

		if (article != null) {
			List<String> imageList = articleDAO.selectImages(productId);
			article.setFilesName(imageList);
		}
		return article;
	}

}
