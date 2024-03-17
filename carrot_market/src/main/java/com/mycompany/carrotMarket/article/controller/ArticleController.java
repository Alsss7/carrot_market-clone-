package com.mycompany.carrotMarket.article.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mycompany.carrotMarket.article.dto.LikeDTO;
import com.mycompany.carrotMarket.article.dto.UpdateHiddenDTO;
import com.mycompany.carrotMarket.article.dto.UpdateImagesDTO;
import com.mycompany.carrotMarket.article.dto.UpdateStatusDTO;
import com.mycompany.carrotMarket.article.service.ArticleService;
import com.mycompany.carrotMarket.article.vo.ArticleVO;
import com.mycompany.carrotMarket.article.vo.ImageVO;
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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginId = authentication.getName();
		MemberVO member = memberService.findById(loginId);
		mav.addObject("region", member.getRegion1());
		mav.setViewName("articleForm");
		return mav;
	}

	@RequestMapping(value = "/modify/{productId}", method = RequestMethod.GET)
	public ModelAndView modifyArticleForm(@PathVariable int productId) throws Exception {
		ModelAndView mav = new ModelAndView();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginId = authentication.getName();
		ArticleVO article = articleService.selectArticle(productId);
		List<ImageVO> images = articleService.selectArticleImages(productId);
		if (loginId.equals(article.getUserId())) {
			mav.addObject("article", article);
			mav.addObject("images", images);
			mav.setViewName("modifyArticleForm");
		} else {
			mav.setViewName("redirect:/article/" + productId);
		}
		return mav;
	}

	@RequestMapping(value = "/modify/{productId}", method = RequestMethod.POST)
	public ModelAndView modifyArticle(@PathVariable int productId, @ModelAttribute("article") ArticleVO articleVO,
			HttpServletRequest request, RedirectAttributes attributes) throws Exception {
		ModelAndView mav = new ModelAndView();

		List<MultipartFile> files = articleVO.getFiles();
		List<String> filesName = new ArrayList<String>();
		if (files != null && !files.isEmpty()) {
			for (MultipartFile file : files) {
				if (!file.isEmpty()) {
					String fileName = file.getOriginalFilename();
					filesName.add(fileName);
				}
			}
			articleVO.setFilesName(filesName);
		}

		List<Integer> keepImages = new ArrayList<Integer>();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			if (!paramName.startsWith("image")) {
				continue;
			} else {
				int imageId = Integer.parseInt(paramName.replace("image", ""));
				String paramValue = request.getParameter(paramName);
				if (paramValue.equals("true")) {
					keepImages.add(imageId);
				}
			}
		}
		
		boolean result = articleService.updateArticle(new UpdateImagesDTO(productId, keepImages), articleVO);

		if (result) {
			ArticleVO article = articleService.selectArticle(productId);
			updateImageFile(article, files);
			attributes.addFlashAttribute("modifyResult", true);
			attributes.addFlashAttribute("article", article);
			mav.setViewName("redirect:/article/" + productId);
		} else {
			attributes.addFlashAttribute("modifyResult", false);
			mav.setViewName("redirect:/article/modify/" + productId);
		}
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
		if (result) {
			imageFileUpload(articleVO.getProductId(), files);
			uploadResult = "등록 성공";
		} else {
			uploadResult = "등록 실패";
		}
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

	@RequestMapping(value = "/delete/{productId}/{preUri}", method = RequestMethod.GET)
	public ModelAndView deleteArticle(@PathVariable int productId, @PathVariable String preUri,
			@RequestParam(value = "status", required = false) String status, RedirectAttributes attributes)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		boolean result = articleService.deleteArticleById(productId);
		if (result) {
			deleteImageFile(productId);
		}

		if (preUri.equals("viewArticle")) {
			mav.setViewName("redirect:/article/fleamarket");
		} else if (preUri.equals("salesHistory")) {
			if (status == null) {
				mav.setViewName("redirect:/member/myPage/salesHistory/hidden");
			} else {
				mav.setViewName("redirect:/member/myPage/salesHistory?status=" + status);
			}
		} else {
			mav.setViewName("redirect:/home");
		}
		attributes.addFlashAttribute("deleteResult", result);
		return mav;
	}

	@RequestMapping(value = "/updateStat/{productId}/{preUri}", method = RequestMethod.GET)
	public ModelAndView updateArticleStatus(@PathVariable int productId, @PathVariable String preUri,
			@RequestParam("status") String status, RedirectAttributes attributes) throws Exception {
		ModelAndView mav = new ModelAndView();
		boolean result = articleService.updateArticleStatus(new UpdateStatusDTO(productId, status));
		if (result == true) {
			attributes.addFlashAttribute("result", "상태가 변경되었습니다.");
		} else {
			attributes.addFlashAttribute("result", "변경에 실패했습니다.");
		}
		if (preUri.equals("viewArticle")) {
			mav.setViewName("redirect:/article/" + productId);
		} else if (preUri.equals("salesHistory")) {
			if (status.equals("Booking")) {
				mav.setViewName("redirect:/member/myPage/salesHistory?status=Active");
			} else {
				mav.setViewName("redirect:/member/myPage/salesHistory?status=" + status);
			}
		}
		return mav;
	}

	@RequestMapping(value = "/updateHidden/{productId}/{preUri}", method = RequestMethod.GET)
	public ModelAndView updateArticleHidden(@PathVariable int productId, @PathVariable String preUri,
			@RequestParam(value = "hide", required = false) int hidden, RedirectAttributes attributes)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		boolean result = articleService.updateArticleHidden(new UpdateHiddenDTO(productId, hidden));
		if (result == true) {
			attributes.addFlashAttribute("result", "상태가 변경되었습니다.");
		} else {
			attributes.addFlashAttribute("result", "변경에 실패했습니다.");
		}
		if (preUri.equals("viewArticle")) {
			mav.setViewName("redirect:/article/fleamarket");
		} else if (preUri.equals("salesHistory")) {
			ArticleVO article = articleService.selectArticle(productId);
			if (hidden == 1) {
				mav.setViewName("redirect:/member/myPage/salesHistory/hidden");
			} else {
				mav.setViewName("redirect:/member/myPage/salesHistory?status=" + article.getStatus());
			}
		}
		return mav;
	}

	private void imageFileUpload(int productId, List<MultipartFile> files) {
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
			imageFileUpload(article.getProductId(), files);
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
