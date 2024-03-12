package com.mycompany.carrotMarket.member.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	private final MemberService memberService;

	private final ArticleService articleService;

	private final MemberValidator memberValidator;

	private final IdValidator idValidator;

	private final NicknameValidator nicknameValidator;

	@Autowired
	public MemberController(MemberService memberService, ArticleService articleService, MemberValidator memberValidator,
			IdValidator idValidator, NicknameValidator nicknameValidator) {
		this.memberService = memberService;
		this.articleService = articleService;
		this.memberValidator = memberValidator;
		this.idValidator = idValidator;
		this.nicknameValidator = nicknameValidator;
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
		mav.setViewName("myPage");
		return mav;
	}

	/*
	 * 마이페이지 메서드
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
			RedirectAttributes attributes) throws Exception {
		ModelAndView mav = new ModelAndView();
		attributes.addFlashAttribute("isMatch", true);
		String password = memberVO.getPw();
		memberValidator.validate(memberVO, bindingResult);
		if (bindingResult.hasErrors()) {
			for (FieldError error : bindingResult.getFieldErrors()) {
				attributes.addFlashAttribute(error.getField(), error.getDefaultMessage());
			}
			attributes.addFlashAttribute("member", memberVO);
		} else {
			boolean result = memberService.modifyMember(memberVO);
			if (result) {
				attributes.addFlashAttribute("result", "success");
				MemberVO modifiedMember = memberService.findById(memberVO.getId());
				modifiedMember.setPw(password);
				attributes.addFlashAttribute("member", modifiedMember);
			} else {
				attributes.addFlashAttribute("result", "failed");
				attributes.addFlashAttribute("member", memberVO);
				System.out.println(attributes.getAttribute("memeber"));
			}
		}
		mav.setViewName("redirect:/member/profile");
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
		mav.addObject("articles", articleList);
		mav.addObject("status", status);
		mav.setViewName("salesHistory");
		return mav;
	}

	/*
	 * 회원가입 메서드
	 */
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public ModelAndView addMember(@ModelAttribute("member") MemberVO memberVO, BindingResult bindingResult,
			RedirectAttributes attributes, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		memberValidator.validate(memberVO, bindingResult); // 입력 데이터 유효성 검사

		if (bindingResult.hasErrors()) { // 유효성 검사 결과 에러가 있으면
			for (FieldError error : bindingResult.getFieldErrors()) {
				attributes.addFlashAttribute(error.getField(), error.getDefaultMessage()); // 에러 결과 포함
			}
			attributes.addFlashAttribute("member", memberVO); // 입력되었던 데이터들
			mav.setViewName("redirect:/join"); // 리다이렉트
		} else { // 에러가 없다면
			boolean result = memberService.addMember(memberVO); // 회원가입 실행
			if (result) { // 완료되었다면
				attributes.addFlashAttribute("result", "success");
			} else { // 실패했다면
				attributes.addFlashAttribute("result", "failed");
			}
			mav.setViewName("redirect:/login");
		}
		return mav;
	}

	/*
	 * 아이디 체크 메서드
	 */
	@ResponseBody
	@RequestMapping(value = "/checkId", method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> checkId(@RequestBody IdDTO dto, BindingResult result) throws Exception {
		MemberVO member = memberService.findById(dto.getId()); // 해당 아이디를 가진 회원 반환 --> 없다면 null
		Map<String, String> response = validateIdAndNick(dto, result, member); // 중복 및 유효성 검사

		return ResponseEntity.ok(response);
	}

	/*
	 * 닉네임 체크 메서드
	 */
	@ResponseBody
	@RequestMapping(value = "/checkNickname", method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> checkNickname(@RequestBody NicknameDTO dto, BindingResult result)
			throws Exception {
		MemberVO member = memberService.findByNickname(dto.getNickname()); // 해당 닉네임을 가진 회원 반환 --> 없다면 null
		Map<String, String> response = validateIdAndNick(dto, result, member); // 중복 및 유효성 검사

		return ResponseEntity.ok(response);
	}

	/*
	 * 아이디, 닉네임 중복 및 유효성 체크 메서드
	 */
	private Map<String, String> validateIdAndNick(Object object, BindingResult result, MemberVO member) {
		Map<String, String> response = new HashMap<String, String>();
		String target = "unknown"; // 유효성 검사를 진행할 대상
		String isAvailable = "false"; // 사용 가능한지 구분
		if (member != null) { // 회원이 있다면
			isAvailable = "false";
		} else { // 회원이 없다면
			if (object instanceof IdDTO) { // 검사 대상이 아이디면
				idValidator.validate((IdDTO) object, result);
				target = "idAvailable";
			} else if (object instanceof NicknameDTO) { // 검사 대상이 닉네임이면
				nicknameValidator.validate((NicknameDTO) object, result);
				target = "nicknameAvailable";
			}

			if (result.hasErrors()) { // 에러가 있으면
				for (FieldError error : result.getFieldErrors()) {
					response.put(error.getField(), error.getDefaultMessage());
				}
				isAvailable = "false";
			} else { // 에러가 없으면
				isAvailable = "true";
			}
		}

		response.put(target, isAvailable);

		return response;
	}

}
