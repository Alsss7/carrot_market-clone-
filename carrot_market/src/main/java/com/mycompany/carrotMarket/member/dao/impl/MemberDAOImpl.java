package com.mycompany.carrotMarket.member.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.carrotMarket.member.dao.MemberDAO;
import com.mycompany.carrotMarket.member.vo.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO {

	@Autowired
	SqlSession sqlSession;

	@Override
	public int insertMember(MemberVO memberVO) throws DataAccessException {
		int result = sqlSession.insert("mappers.member.insertMember", memberVO);
		return result;
	}

	@Override
	public int updateMember(MemberVO memberVO) throws DataAccessException {
		int result = sqlSession.update("mappers.member.updateMember", memberVO);
		return result;
	}

	@Override
	public MemberVO findById(String id) throws DataAccessException {
		MemberVO member = sqlSession.selectOne("mappers.member.findById", id);
		return member;
	}

	@Override
	public MemberVO findByNickname(String nickname) throws DataAccessException {
		MemberVO member = sqlSession.selectOne("mappers.member.findByNickname", nickname);
		return member;
	}

	@Override
	public int insertAuthority(MemberVO memberVO) throws DataAccessException {
		int result = sqlSession.insert("mappers.member.insertAuthority", memberVO);
		return result;
	}

}
