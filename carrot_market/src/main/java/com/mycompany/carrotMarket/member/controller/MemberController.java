package com.mycompany.carrotMarket.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.carrotMarket.member.dto.LoginMemberDTO;
import com.mycompany.carrotMarket.member.service.MemberService;

@Controller
@RequestMapping("/member/*")
public class MemberController {

	@Autowired
	MemberService memberService;

	@RequestMapping(value = "/loginForm.do", method = RequestMethod.GET)
	public ModelAndView loginForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member/loginForm");
		return mav;
	}

	@RequestMapping(value = "/joinForm.do", method = RequestMethod.GET)
	public ModelAndView memberForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member/joinForm");
		return mav;
	}

	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");

		ModelAndView mav = new ModelAndView();
		LoginMemberDTO dto = new LoginMemberDTO(user_id, user_pw);
		memberService.login(dto);
		/*
		 * 로그인 로직 작성
		 */

		return mav;
//		boolean isLogOn = memberService.login(user_id, user_pw);
//		if (isLogon) {
//			HttpSession session = request.getSession();
//			session.setAttribute("isLogOn", isLogOn);
//			
//		}
	}

	@RequestMapping(value = "/addMember.do", method = RequestMethod.POST)
	public ModelAndView addMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ModelAndView mav = new ModelAndView();

		/*
		 * 회원가입 로직 작성
		 */

		return mav;
	}
}
