package com.mycompany.carrotMarket.member.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mycompany.carrotMarket.member.dto.LoginMemberDTO;
import com.mycompany.carrotMarket.member.service.MemberService;
import com.mycompany.carrotMarket.member.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	SqlSession sqlSession;

	@Override
	public int login(LoginMemberDTO dto) throws DataAccessException {
		return sqlSession.selectOne("mappers.member.loginById", dto);
	}

	@Override
	public int addMember(MemberVO memberVO) throws DataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

}
