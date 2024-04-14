package com.mycompany.carrotMarket.article.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mycompany.carrotMarket.article.dto.LikeDTO;
import com.mycompany.carrotMarket.article.dto.SearchDTO;
import com.mycompany.carrotMarket.article.dto.UpdateHiddenDTO;
import com.mycompany.carrotMarket.article.dto.UpdateImagesDTO;
import com.mycompany.carrotMarket.article.dto.UpdateStatusDTO;
import com.mycompany.carrotMarket.article.service.ArticleService;
import com.mycompany.carrotMarket.article.vo.ArticleVO;
import com.mycompany.carrotMarket.article.vo.ImageVO;
import com.mycompany.carrotMarket.chat.service.ChatService;
import com.mycompany.carrotMarket.chat.vo.ChatVO;
import com.mycompany.carrotMarket.chat.vo.MessageVO;
import com.mycompany.carrotMarket.member.service.MemberService;
import com.mycompany.carrotMarket.member.vo.MemberVO;

@RestController
@RequestMapping("/article")
public class ArticleController {

	private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

	@Autowired
	ArticleService articleService;

	@Autowired
	MemberService memberService;

	@Autowired
	ChatService chatService;

	@Autowired
	private ServletContext servletContext;

	@RequestMapping(value = "/fleamarket", method = RequestMethod.GET)
	public ModelAndView fleamarket(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginId = authentication.getName();

		logger.info("loginId : {}", loginId);
		List<ArticleVO> articleList;
		if (loginId.equals("anonymousUser")) {
			articleList = articleService.selectArticles();
		} else {
			MemberVO member = memberService.findById(loginId);
			logger.info("member : {}", member.toString());
			logger.info("region : {}", member.getRegion1());
			articleList = articleService.selectArticlesByRegion(member.getRegion1());
			mav.addObject("region", member.getRegion1());
		}
		mav.addObject("articles", articleList);
		mav.addObject("pageTitle", "trade");
		mav.setViewName("fleamarket");
		return mav;
	}

	@RequestMapping(value = "/hotArticle", method = RequestMethod.GET)
	public ModelAndView hotArticle(@PathVariable(required = false) String region1,
			@PathVariable(required = false) String region2, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		List<ArticleVO> articleList = articleService.selectArticles();
		mav.addObject("pageTitle", "trade");
		mav.addObject("articles", articleList);
		mav.setViewName("hotArticle");
		return mav;
	}

	@RequestMapping(value = "/hotArticle/{region1}", method = RequestMethod.GET)
	public ModelAndView hotArticle(@PathVariable String region1) throws Exception {
		ModelAndView mav = new ModelAndView();
		List<ArticleVO> articleList = articleService.selectArticlesByContainRegion(region1);
		mav.addObject("region1", region1);
		mav.addObject("pageTitle", "trade");
		mav.addObject("articles", articleList);
		mav.setViewName("hotArticle");
		return mav;
	}

	@RequestMapping(value = "/hotArticle/{region1}/{region2}", method = RequestMethod.GET)
	public ModelAndView hotArticle(@PathVariable String region1, @PathVariable String region2) throws Exception {
		ModelAndView mav = new ModelAndView();
		List<ArticleVO> articleList = articleService.selectArticlesByContainRegion(region1 + " " + region2);
		mav.addObject("region1", region1);
		mav.addObject("region2", region2);
		mav.addObject("pageTitle", "trade");
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
			mav.addObject("msg", "잘못된 접근입니다.");
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

		@SuppressWarnings("unchecked")
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

		if (files != null && !files.isEmpty()) {
			for (MultipartFile file : files) {
				if (!file.isEmpty()) {
					String fileName = file.getOriginalFilename();
					filesName.add(fileName);
				}
			}
			articleVO.setFilesName(filesName);
		}

		String uploadResult;
		boolean result = articleService.addArticle(articleVO);
		if (result) {
			uploadImageFile(articleVO.getProductId(), files);
			uploadResult = "등록 성공";
		} else {
			uploadResult = "등록 실패";
		}
		attributes.addFlashAttribute("result", uploadResult);
		mav.setViewName("redirect:/article/fleamarket");
		return mav;

	}

	@RequestMapping(value = "/search/{value}", method = RequestMethod.GET)
	public ModelAndView searchArticles(@PathVariable String value) throws Exception {
		ModelAndView mav = new ModelAndView();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginId = authentication.getName();
		List<ArticleVO> articles;
		if (loginId == "anonymousUser") {
			articles = articleService.selectArticlesBySearch(value);
		} else {
			MemberVO member = memberService.findById(loginId);
			String[] regionArray = member.getRegion1().split(" ");
			String region = regionArray[0] + " " + regionArray[1];
			logger.info("region : {}", region);
			articles = articleService.selectArticlesBySearchInRegion(new SearchDTO(value, region));
			mav.addObject("region", region);
		}
		mav.addObject("articles", articles);
		mav.setViewName("searchResult");
		return mav;
	}

	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
	public ModelAndView viewArticle(@PathVariable int productId, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginId = authentication.getName();
		ArticleVO article = articleService.selectArticle(productId);
		if (article != null) {
			Date currentTime = new Date();
			Date dbTimeStamp = article.getCreatedAt();
			long timeDiff = currentTime.getTime() - dbTimeStamp.getTime();

			MemberVO member = memberService.findById(article.getUserId());
			mav.addObject("isExists", "true");
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
			mav.addObject("isExists", "false");
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

	@ResponseBody
	@RequestMapping(value = "/delete/{productId}", method = RequestMethod.DELETE)
	public ResponseEntity<Map<String, String>> deleteArticle(@PathVariable int productId) throws Exception {
		Map<String, String> response = new HashMap<String, String>();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginId = authentication.getName();
		ArticleVO article = articleService.selectArticle(productId);
		boolean result = false;
		if (loginId.equals(article.getUserId())) {
			result = articleService.deleteArticleById(productId);
			response.put("msg", "success");
		} else {
			response.put("msg", "not valid");
		}

		if (result) {
			deleteImageFile(productId);
		}
		response.put("result", String.valueOf(result));

		return ResponseEntity.ok(response);
	}

	@ResponseBody
	@RequestMapping(value = "/updateStat/{productId}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> updateArticleStatus(@PathVariable int productId,
			@RequestBody Map<String, String> requestBody) throws Exception {
		String status = requestBody.get("status");
		String buyerId = requestBody.get("buyerId");

		Map<String, String> response = new HashMap<String, String>();
		if (status.equals("Active")) {
			buyerId = "";
		}

		boolean result = articleService.updateArticleStatus(new UpdateStatusDTO(productId, status, buyerId));
		response.put("result", String.valueOf(result));
		response.put("buyerId", buyerId);

		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/updateStat/{productId}/selectBuyer", method = RequestMethod.GET)
	public ModelAndView selectBuyer(@PathVariable int productId, @RequestParam("status") String status,
			@RequestParam("pre") String pre) throws Exception {
		ModelAndView mav = new ModelAndView();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginId = authentication.getName();

		ArticleVO article = articleService.selectArticle(productId);
		if (article != null) {
			if (article.getUserId().equals(loginId)) {
				List<ChatVO> chats = chatService.selectChatListByProductId(productId);
				List<MemberVO> members = new ArrayList<MemberVO>();
				List<String> lastMessages = new ArrayList<String>();
				List<Long> timeDiffs = new ArrayList<Long>();
				Date currentTime = new Date();
				for (ChatVO chat : chats) {
					members.add(memberService.findById(chat.getBuyerId()));
					List<MessageVO> messages = chatService.selectMessagesByChatId(chat.getChatId());
					lastMessages.add(messages.get(messages.size() - 1).getContent());
					long timeDiff = currentTime.getTime() - messages.get(messages.size() - 1).getSentAt().getTime();
					timeDiffs.add(timeDiff);
				}
				mav.addObject("chats", chats);
				mav.addObject("members", members);
				mav.addObject("lastMessages", lastMessages);
				mav.addObject("timeDiffs", timeDiffs);
				mav.addObject("article", article);
				mav.addObject("preStatus", article.getStatus());
			} else {
				mav.addObject("msg", "잘못된 접근입니다.");
			}
		} else {
			mav.addObject("msg", "존재하지 않는 게시글입니다.");
		}
		mav.addObject("preUri", pre);
		mav.addObject("productStatus", status);
		mav.setViewName("buyerList");
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/updateHidden/{productId}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> updateArticleHidden(@PathVariable int productId,
			@RequestBody Map<String, Integer> requestBody) throws Exception {
		int hidden = requestBody.get("hide");
		Map<String, String> response = new HashMap<String, String>();
		boolean result = articleService.updateArticleHidden(new UpdateHiddenDTO(productId, hidden));
		response.put("result", String.valueOf(result));
		if (hidden == 0) {
			response.put("hidden", "show");
		} else {
			response.put("hidden", "hide");
		}

		return ResponseEntity.ok(response);
	}

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
			uploadImageFile(article.getProductId(), files);
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
