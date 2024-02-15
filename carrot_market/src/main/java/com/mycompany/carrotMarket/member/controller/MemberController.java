package com.mycompany.carrotMarket.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mycompany.carrotMarket.member.service.MemberService;
import com.mycompany.carrotMarket.member.vo.MemberVO;
import com.mycompany.carrotMarket.validator.MemberValidator;

@Controller
@RequestMapping("/member/*")
public class MemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberValidator memberValidator;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new MemberValidator());
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public ModelAndView addMember(@ModelAttribute("member") MemberVO memberVO, BindingResult bindingResult,
			RedirectAttributes attributes, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ModelAndView mav = new ModelAndView();

		memberValidator.validate(memberVO, bindingResult);
		System.out.println(memberVO.toString());
		if (bindingResult.hasErrors()) {
			for (FieldError error : bindingResult.getFieldErrors()) {
				String fieldName = error.getField();
				String errorMessage = error.getDefaultMessage();

				System.out.println(fieldName + " : " + errorMessage);
				attributes.addFlashAttribute(fieldName, errorMessage);
			}
			mav.setViewName("redirect:/join");
		} else {
			boolean result = memberService.addMember(memberVO);
			if (result == true) {
				attributes.addFlashAttribute("result", "success");
				mav.setViewName("redirect:/login");
			} else {
				attributes.addFlashAttribute("result", "failed");
				mav.setViewName("redirect:/join");
			}
		}
		return mav;
	}
}
