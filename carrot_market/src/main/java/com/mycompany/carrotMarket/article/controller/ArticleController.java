package com.mycompany.carrotMarket.article.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mycompany.carrotMarket.article.dto.LikeDTO;
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
		ModelAndView mav = new ModelAndView();
		List<ArticleVO> articleList = articleService.selectArticles();
		mav.addObject("articles", articleList);
		mav.setViewName("fleamarket");
		return mav;
	}

	@RequestMapping(value = "/hotArticle", method = RequestMethod.GET)
	public ModelAndView hotArticle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		List<ArticleVO> articleList = articleService.selectArticles();
		mav.addObject("articles", articleList);
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
			uploadResult = "��� ����";
		} else {
			uploadResult = "��� ����";
		}
		System.out.println(uploadResult);
		attributes.addFlashAttribute("result", uploadResult);
		mav.setViewName("redirect:/article/fleamarket");
		return mav;

	}

	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
	public ModelAndView viewArticle(@PathVariable int productId, HttpServletRequest req, HttpServletResponse res) {
		ModelAndView mav = new ModelAndView();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginId = authentication.getName();
		ArticleVO article = articleService.selectArticle(productId);
		if (article != null) {
			Date currentTime = new Date();
			Date dbTimeStamp = article.getCreatedAt();
			long timeDiff = currentTime.getTime() - dbTimeStamp.getTime();

			MemberVO member = memberService.findById(article.getUserId());
			mav.addObject("msg", "success");
			mav.addObject("article", article);
			mav.addObject("timeDiff", timeDiff);
			mav.addObject("member", member);
			if (loginId != null) {
				LikeDTO likeDTO = new LikeDTO(loginId, productId);
				boolean isLiked = articleService.selectLike(likeDTO);
				mav.addObject("like", isLiked);
				increaseView(req, res, productId);
			}
		} else {
			mav.addObject("msg", "fail");
		}
		mav.setViewName("article");
		return mav;
	}

	@RequestMapping(value = "/like/{productId}", method = RequestMethod.GET)
	public ModelAndView likeArticle(@PathVariable int productId, RedirectAttributes attributes) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginId = authentication.getName();
		ModelAndView mav = new ModelAndView();
		System.out.println(productId + " " + loginId);

		if (loginId.equals("anonymousUser")) {
			attributes.addFlashAttribute("loginFirst", "loginFirst");
			mav.setViewName("redirect:/article/" + productId);
		} else {
			LikeDTO likeDTO = new LikeDTO(loginId, productId);
			boolean isLiked = articleService.selectLike(likeDTO);
			if (isLiked) {
				boolean result = articleService.removeLike(likeDTO);
				attributes.addFlashAttribute("removeResult", result);
			} else {
				boolean result = articleService.addLike(likeDTO);
				attributes.addFlashAttribute("addResult", result);
			}
			mav.setViewName("redirect:/article/" + productId);
		}
		return mav;
	}

	private void imageFileUpload(int productId, List<MultipartFile> files, HttpServletRequest request) {
		for (MultipartFile file : files) {
			if (!file.isEmpty()) {
				try {
					String uploadDir = servletContext.getRealPath("/resources/image/product_image/" + productId);
					String fileName = file.getOriginalFilename();
					String filePath = uploadDir + "\\" + fileName;

					// ���丮�� �������� ������ ����
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

	private void increaseView(HttpServletRequest req, HttpServletResponse res, int productId) {
		Cookie oldCookie = null;
		Cookie[] cookies = req.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("articleView")) {
				oldCookie = cookie;
			}
		}

		if (oldCookie != null) {
			if (!oldCookie.getValue().contains("[" + productId + "]")) {
				articleService.increaseView(productId);
				oldCookie.setValue(oldCookie.getValue() + "_[" + productId + "]");
				oldCookie.setPath("/");
				oldCookie.setMaxAge(60 * 60 * 24);
				res.addCookie(oldCookie);
			}
		} else {
			articleService.increaseView(productId);
			Cookie newCookie = new Cookie("articleView", "[" + productId + "]");
			newCookie.setPath("/");
			newCookie.setMaxAge(60 * 60 * 24);
			res.addCookie(newCookie);
		}
	}
}