package com.mycompany.carrotMarket.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.carrotMarket.member.dao.MemberDAO;
import com.mycompany.carrotMarket.member.service.MemberService;
import com.mycompany.carrotMarket.member.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDAO memberDAO;

	@Autowired
	BCryptPasswordEncoder encoder;

	@Transactional
	@Override
	public boolean addMember(MemberVO memberVO) throws DataAccessException {
		memberVO.setPw(encoder.encode(memberVO.getPw()));
		int result1 = memberDAO.insertMember(memberVO);
		int result2 = memberDAO.insertAuthority(memberVO);
		if (result1 == 1 && result2 == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public MemberVO findById(String id) throws DataAccessException {
		MemberVO member = memberDAO.findById(id);
		return member;
	}

	@Override
	public boolean isAvailableId(String id) throws DataAccessException {
		MemberVO member = memberDAO.findById(id);
		if (member != null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean isAvailableNickname(String nickname) throws DataAccessException {
		MemberVO member = memberDAO.findByNickname(nickname);
		if (member != null) {
			return false;
		} else {
			return true;
		}
	}

}
