package com.mycompany.carrotMarket.member.service;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.member.vo.MemberVO;

public interface MemberService {
	public boolean addMember(MemberVO memberVO) throws DataAccessException;

	public MemberVO findById(String id) throws DataAccessException;

	public MemberVO findByNickname(String nickname) throws DataAccessException;

	public boolean modifyMember(MemberVO memberVO, String paramValue) throws DataAccessException;

	public boolean updateMemberManner(String targetId, int review) throws DataAccessException;

	public boolean matchesPassword(String inputPw, String encodedPw);

}
