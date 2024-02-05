package com.mycompany.carrotMarket.member.service;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.member.dto.LoginMemberDTO;
import com.mycompany.carrotMarket.member.vo.MemberVO;

public interface MemberService {
	public MemberVO login(LoginMemberDTO dto) throws DataAccessException;

	public int addMember(MemberVO memberVO) throws DataAccessException;
}
