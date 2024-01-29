package com.mycompany.carrotMarket.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/member")
public class MemberController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "login";
	}
}
