package com.mycompany.carrotMarket.member.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
import com.mycompany.carrotMarket.article.dto.SalesDTO;
import com.mycompany.carrotMarket.article.service.ArticleService;
import com.mycompany.carrotMarket.article.vo.ArticleVO;
import com.mycompany.carrotMarket.member.dto.IdDTO;
import com.mycompany.carrotMarket.member.dto.MemberDTO;
import com.mycompany.carrotMarket.member.dto.NicknameDTO;
import com.mycompany.carrotMarket.member.service.MemberService;
import com.mycompany.carrotMarket.member.vo.MemberVO;
import com.mycompany.carrotMarket.validator.IdValidator;
import com.mycompany.carrotMarket.validator.MemberValidator;
import com.mycompany.carrotMarket.validator.NicknameValidator;

@RestController
@RequestMapping("/member")
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	private final MemberService memberService;

	private final ArticleService articleService;

	private final MemberValidator memberValidator;

	private final IdValidator idValidator;

	private final NicknameValidator nicknameValidator;

	private final ServletContext servletContext;

	@Autowired
	public MemberController(MemberService memberService, ArticleService articleService, MemberValidator memberValidator,
			IdValidator idValidator, NicknameValidator nicknameValidator, ServletContext servletContext) {
		this.memberService = memberService;
		this.articleService = articleService;
		this.memberValidator = memberValidator;
		this.idValidator = idValidator;
		this.nicknameValidator = nicknameValidator;
		this.servletContext = servletContext;
	}

	@InitBinder("id")
	protected void initIdBinder(WebDataBinder binder) {
		binder.addValidators(idValidator);
	}

	@InitBinder("member")
	protected void initMemberBinder(WebDataBinder binder) {
		binder.addValidators(memberValidator);
	}

	@InitBinder("nickname")
	protected void initNicknameBinder(WebDataBinder binder) {
		binder.addValidators(nicknameValidator);
	}

	@RequestMapping(value = "/myPage", method = RequestMethod.GET)
	public ModelAndView myPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginId = authentication.getName();
		MemberVO member = memberService.findById(loginId);

		mav.addObject("member", member);
		mav.setViewName("myPage");
		return mav;
	}

	/*
	 * ���������� �޼���
	 */
	@RequestMapping(value = "/myPage/profile", method = RequestMethod.GET)
	public ModelAndView profile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("profile");
		return mav;
	}

	@RequestMapping(value = "/myPage/profile", method = RequestMethod.POST)
	public ModelAndView getInfo(@ModelAttribute MemberDTO dto, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		MemberVO member = memberService.findById(dto.getId());
		boolean isMatch = memberService.matchesPassword(dto.getPw(), member.getPw());
		System.out.println(isMatch);
		mav.addObject("isMatch", isMatch);
		if (isMatch) {
			member.setPw(dto.getPw());
			mav.addObject("member", member);
		}
		mav.setViewName("profile");
		return mav;
	}

	@RequestMapping(value = "/myPage/profile/modify", method = RequestMethod.POST)
	public ModelAndView modifyMember(@ModelAttribute("member") MemberVO memberVO, BindingResult bindingResult,
			HttpServletRequest request, RedirectAttributes attributes) throws Exception {
		logger.info("modifyMember");
		ModelAndView mav = new ModelAndView();
		attributes.addFlashAttribute("isMatch", true);
		String password = memberVO.getPw();
		memberValidator.validate(memberVO, bindingResult);

		if (bindingResult.hasErrors()) {
			logger.info("hasError");
			for (FieldError error : bindingResult.getFieldErrors()) {
				logger.info("error.getField() : {}", error.getField());
				logger.info("error.getDefaultMessage() : {}", error.getDefaultMessage());
				attributes.addFlashAttribute(error.getField(), error.getDefaultMessage());
			}
			attributes.addFlashAttribute("member", memberVO);
		} else {
			logger.info("NoError");
			String paramValue = request.getParameter("fileName");
			if (paramValue != null) {
				logger.info("originImage : {}", paramValue);
				memberVO.setFileName(paramValue);
			} else {
				logger.info("changeImage : {}", memberVO.getProfile_image().getOriginalFilename());
				memberVO.setFileName(memberVO.getProfile_image().getOriginalFilename());
			}
			boolean result = memberService.modifyMember(memberVO);
			if (result) {
				logger.info("modify Success");
				if (paramValue == null) {
					updateImageFile(memberVO, memberVO.getProfile_image());
					logger.info("image changed");
				}
				attributes.addFlashAttribute("result", "success");
				MemberVO modifiedMember = memberService.findById(memberVO.getId());
				modifiedMember.setPw(password);
				attributes.addFlashAttribute("member", modifiedMember);
			} else {
				logger.info("modify fail");
				attributes.addFlashAttribute("result", "failed");
				attributes.addFlashAttribute("member", memberVO);
			}
		}
		mav.setViewName("redirect:/member/myPage/profile");
		return mav;
	}

	@RequestMapping(value = "/myPage/likeList", method = RequestMethod.GET)
	public ModelAndView likeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginId = authentication.getName();
		List<LikeDTO> likeList = articleService.selectLikeList(loginId);
		List<Integer> likedProducts = new ArrayList<Integer>();
		for (LikeDTO like : likeList) {
			likedProducts.add(like.getProductId());
		}
		List<ArticleVO> articleList = articleService.selectArticlesByProductIdList(likedProducts);
		mav.addObject("articles", articleList);
		mav.setViewName("likeList");
		return mav;
	}

	@RequestMapping(value = "/myPage/likeList/remove/{productId}", method = RequestMethod.GET)
	public ModelAndView removeLikeAtLikeList(@PathVariable int productId, RedirectAttributes attributes)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginId = authentication.getName();
		boolean result = articleService.removeLike(new LikeDTO(loginId, productId));
		attributes.addFlashAttribute("removeResult", result);

		mav.setViewName("redirect:/member/myPage/likeList");
		return mav;
	}

	@RequestMapping(value = "/myPage/salesHistory", method = RequestMethod.GET)
	public ModelAndView getSalesHistory(@RequestParam("status") String status) throws Exception {
		ModelAndView mav = new ModelAndView();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginId = authentication.getName();
		List<ArticleVO> articleList = articleService.selectArticlesByUserIdAndStat(new SalesDTO(loginId, status));
		Map<String, Integer> map = articleService.selectArticlesCountByStatus(loginId);

		mav.addObject("articles", articleList);
		mav.addObject("status", status);
		mav.addObject("articleCount", map);
		mav.setViewName("salesHistory");
		return mav;
	}

	@RequestMapping(value = "/myPage/salesHistory/hidden", method = RequestMethod.GET)
	public ModelAndView getSalesHidden() throws Exception {
		ModelAndView mav = new ModelAndView();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginId = authentication.getName();
		List<ArticleVO> articleList = articleService.selectHiddenArticles(loginId);
		Map<String, Integer> map = articleService.selectArticlesCountByStatus(loginId);

		mav.addObject("articles", articleList);
		mav.addObject("articleCount", map);
		if (articleList.size() != 0) {
			mav.addObject("hidden", 1);
		} else {
			mav.addObject("hidden", 0);
		}
		mav.setViewName("salesHistory");
		return mav;
	}

	@RequestMapping(value = "/myPage/purchaseHistory", method = RequestMethod.GET)
	public ModelAndView getPurchaseHistory() throws Exception {
		ModelAndView mav = new ModelAndView();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginId = authentication.getName();
		List<ArticleVO> articlesList = articleService.selectArticlesPurchasedById(loginId);

		mav.addObject("articles", articlesList);
		mav.setViewName("purchaseHistory");
		return mav;
	}

	/*
	 * ȸ������ �޼���
	 */
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public ModelAndView addMember(@ModelAttribute("member") MemberVO memberVO, BindingResult bindingResult,
			RedirectAttributes attributes, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		memberValidator.validate(memberVO, bindingResult); // �Է� ������ ��ȿ�� �˻�

		if (bindingResult.hasErrors()) { // ��ȿ�� �˻� ��� ������ ������
			for (FieldError error : bindingResult.getFieldErrors()) {
				attributes.addFlashAttribute(error.getField(), error.getDefaultMessage()); // ���� ��� ����
			}
			attributes.addFlashAttribute("member", memberVO); // �ԷµǾ��� �����͵�
			mav.setViewName("redirect:/join"); // �����̷�Ʈ
		} else { // ������ ���ٸ�
			memberVO.setFileName(memberVO.getProfile_image().getOriginalFilename());
			boolean result = memberService.addMember(memberVO); // ȸ������ ����
			if (result) { // �Ϸ�Ǿ��ٸ�
				uploadImageFile(memberVO.getId(), memberVO.getProfile_image());
				attributes.addFlashAttribute("result", "success");
			} else { // �����ߴٸ�
				attributes.addFlashAttribute("result", "failed");
			}
			mav.setViewName("redirect:/login");
		}
		return mav;
	}

	/*
	 * ���̵� üũ �޼���
	 */
	@ResponseBody
	@RequestMapping(value = "/checkId", method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> checkId(@RequestBody IdDTO dto, BindingResult result) throws Exception {
		MemberVO member = memberService.findById(dto.getId()); // �ش� ���̵� ���� ȸ�� ��ȯ --> ���ٸ� null
		Map<String, String> response = validateIdAndNick(dto, result, member); // �ߺ� �� ��ȿ�� �˻�

		return ResponseEntity.ok(response);
	}

	/*
	 * �г��� üũ �޼���
	 */
	@ResponseBody
	@RequestMapping(value = "/checkNickname", method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> checkNickname(@RequestBody NicknameDTO dto, BindingResult result)
			throws Exception {
		MemberVO member = memberService.findByNickname(dto.getNickname()); // �ش� �г����� ���� ȸ�� ��ȯ --> ���ٸ� null
		Map<String, String> response = validateIdAndNick(dto, result, member); // �ߺ� �� ��ȿ�� �˻�

		return ResponseEntity.ok(response);
	}

	/*
	 * ���̵�, �г��� �ߺ� �� ��ȿ�� üũ �޼���
	 */
	private Map<String, String> validateIdAndNick(Object object, BindingResult result, MemberVO member) {
		Map<String, String> response = new HashMap<String, String>();
		String target = "unknown"; // ��ȿ�� �˻縦 ������ ���
		String isAvailable = "false"; // ��� �������� ����
		if (member != null) { // ȸ���� �ִٸ�
			isAvailable = "false";
		} else { // ȸ���� ���ٸ�
			if (object instanceof IdDTO) { // �˻� ����� ���̵��
				idValidator.validate((IdDTO) object, result);
				target = "idAvailable";
			} else if (object instanceof NicknameDTO) { // �˻� ����� �г����̸�
				nicknameValidator.validate((NicknameDTO) object, result);
				target = "nicknameAvailable";
			}

			if (result.hasErrors()) { // ������ ������
				for (FieldError error : result.getFieldErrors()) {
					response.put(error.getField(), error.getDefaultMessage());
				}
				isAvailable = "false";
			} else { // ������ ������
				isAvailable = "true";
			}
		}

		response.put(target, isAvailable);

		return response;
	}

	private void uploadImageFile(String id, MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				String uploadDir = servletContext.getRealPath("/resources/image/profile_image/" + id);
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

	private void deleteImageFile(String id) {
		String uploadDir = servletContext.getRealPath("/resources/image/profile_image/" + id);
		File directory = new File(uploadDir);
		if (directory.exists()) {
			File[] files = directory.listFiles();
			for (File file : files) {
				file.delete();
			}
			directory.delete();
		}
	}

	private void updateImageFile(MemberVO member, MultipartFile newFile) {
		String uploadDir = servletContext.getRealPath("/resources/image/profile_image/" + member.getId());
		File directory = new File(uploadDir);
		if (directory.exists()) {
			File[] existFiles = directory.listFiles();
			for (File file : existFiles) {
				boolean isFileExists = false;
				if (file.getName().equals(member.getFileName())) {
					isFileExists = true;
				}

				if (!isFileExists) {
					file.delete();
				}
			}
		}

		if (newFile != null) {
			uploadImageFile(member.getId(), newFile);
		}
	}

}
