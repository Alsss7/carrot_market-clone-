package com.mycompany.carrotMarket.article.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mycompany.carrotMarket.article.service.ArticleService;
import com.mycompany.carrotMarket.article.vo.ArticleVO;
import com.mycompany.carrotMarket.member.service.MemberService;
import com.mycompany.carrotMarket.member.vo.MemberVO;

@RestController
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	ArticleService articleService;

	@Autowired
	MemberService memberService;

	@Autowired
	private ServletContext servletContext;

	@RequestMapping(value = "/fleamarket", method = RequestMethod.GET)
	public ModelAndView fleamarket(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("fleamarket");
		ModelAndView mav = new ModelAndView();
		Map<ArticleVO, List<String>> map = articleService.selectArticles();
		mav.addObject("map", map);
		mav.setViewName("fleamarket");
		return mav;
	}

	@RequestMapping(value = "/hot_article", method = RequestMethod.GET)
	public ModelAndView hotArticle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		Map<ArticleVO, List<String>> map = articleService.selectArticles();
		mav.addObject("map", map);
		mav.setViewName("hotArticle");
		return mav;
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView articleForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("articleForm");
		return mav;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerArticle(@ModelAttribute("article") ArticleVO articleVO, RedirectAttributes attributes,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView mav = new ModelAndView();

		MemberVO member = memberService.findById(articleVO.getUserId());
		articleVO.setRegion(member.getRegion1());

		List<MultipartFile> files = articleVO.getFiles();
		List<String> filesName = new ArrayList<String>();
		String uploadResult;

		if (files != null && !files.isEmpty()) {
			for (MultipartFile file : files) {
				if (!file.isEmpty()) {
					String fileName = file.getOriginalFilename();
					filesName.add(fileName);
				}
			}
			articleVO.setFilesName(filesName);
		}
		boolean result = articleService.addArticle(articleVO);
		imageFileUpload(articleVO.getProductId(), files, request);
		if (result) {
			uploadResult = "등록 성공";
		} else {
			uploadResult = "등록 실패";
		}
		System.out.println(uploadResult);
		attributes.addFlashAttribute("result", uploadResult);
		mav.setViewName("redirect:/article/fleamarket");
		return mav;

	}

	private void imageFileUpload(int productId, List<MultipartFile> files, HttpServletRequest request) {

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

					System.out.println(filePath);
					file.transferTo(new File(filePath));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
