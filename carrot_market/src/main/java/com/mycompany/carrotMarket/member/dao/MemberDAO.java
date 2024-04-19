package com.mycompany.carrotMarket.member.dao;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.member.dto.MannerDTO;
import com.mycompany.carrotMarket.member.vo.MemberVO;

public interface MemberDAO {
	public int insertMember(MemberVO memberVO) throws DataAccessException;

	public int updateMember(MemberVO memberVO) throws DataAccessException;

	public MemberVO findById(String id) throws DataAccessException;

	public int updateMemberManner(MannerDTO dto) throws DataAccessException;

	public MemberVO findByNickname(String nickname) throws DataAccessException;

	public int insertAuthority(MemberVO memberVO) throws DataAccessException;
}
