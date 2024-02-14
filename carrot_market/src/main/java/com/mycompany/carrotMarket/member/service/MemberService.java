package com.mycompany.carrotMarket.member.service;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.member.vo.MemberVO;

public interface MemberService {
	public boolean addMember(MemberVO memberVO) throws DataAccessException;
}
