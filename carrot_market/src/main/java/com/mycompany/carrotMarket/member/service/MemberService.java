package com.mycompany.carrotMarket.member.service;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.member.vo.MemberVO;

public interface MemberService {
	public boolean addMember(MemberVO memberVO) throws DataAccessException;

	public MemberVO findById(String id) throws DataAccessException;

	public boolean isAvailableId(String id) throws DataAccessException;

	public boolean isAvailableNickname(String nickname) throws DataAccessException;
}
