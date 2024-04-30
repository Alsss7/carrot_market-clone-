package com.mycompany.carrotMarket.member.service.impl;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.carrotMarket.member.dao.MemberDAO;
import com.mycompany.carrotMarket.member.service.MemberService;
import com.mycompany.carrotMarket.member.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDAO memberDAO;

	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	ServletContext servletContext;

	@Override
	@Transactional
	public boolean addMember(MemberVO memberVO) throws DataAccessException {
		memberVO.setFileName(memberVO.getProfile_image().getOriginalFilename());
		memberVO.setPw(encoder.encode(memberVO.getPw()));
		int result1 = memberDAO.insertMember(memberVO);
		int result2 = memberDAO.insertAuthority(memberVO);
		if (result1 == 1 && result2 == 1) {
			uploadImageFile(memberVO.getId(), memberVO.getProfile_image());
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
	public MemberVO findByNickname(String nickname) throws DataAccessException {
		MemberVO member = memberDAO.findByNickname(nickname);
		return member;
	}

	@Override
	public boolean modifyMember(MemberVO memberVO, String paramValue) throws DataAccessException {
		memberVO.setPw(encoder.encode(memberVO.getPw()));
		if (paramValue != null) {
			memberVO.setFileName(paramValue);
		} else {
			memberVO.setFileName(memberVO.getProfile_image().getOriginalFilename());
		}

		int result = memberDAO.updateMember(memberVO);
		if (result != 0) {
			if (paramValue == null) {
				updateImageFile(memberVO, memberVO.getProfile_image());
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean updateMemberManner(String buyerId, int review) throws DataAccessException {
		float amountOfIncrease;
		if (review == 1) {
			amountOfIncrease = (float) -0.5;
		} else if (review == 2) {
			amountOfIncrease = (float) 0.1;
		} else {
			amountOfIncrease = (float) 0.2;
		}

		int result = memberDAO.updateMemberManner(buyerId, amountOfIncrease);
		if (result != 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean matchesPassword(String inputPw, String encodedPw) {
		return encoder.matches(inputPw, encodedPw);
	}

	private void uploadImageFile(String id, MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				String uploadDir = servletContext.getRealPath("/resources/image/profile_image/" + id);
				String fileName = file.getOriginalFilename();
				String filePath = uploadDir + "\\" + fileName;

				// 디렉토리가 존재하지 않으면 생성
				File directory = new File(uploadDir);
				if (!directory.exists()) {
					directory.mkdir();
				}

				file.transferTo(new File(filePath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void deleteImageFile(String id) {
		String uploadDir = servletContext.getRealPath("/resources/image/profile_image/" + id);
		File directory = new File(uploadDir);
		if (directory.exists()) {
			File[] files = directory.listFiles();
			for (File file : files) {
				file.delete();
			}
			directory.delete();
		}
	}

	private void updateImageFile(MemberVO member, MultipartFile newFile) {
		String uploadDir = servletContext.getRealPath("/resources/image/profile_image/" + member.getId());
		File directory = new File(uploadDir);
		if (directory.exists()) {
			File[] existFiles = directory.listFiles();
			for (File file : existFiles) {
				boolean isFileExists = false;
				if (file.getName().equals(member.getFileName())) {
					isFileExists = true;
				}

				if (!isFileExists) {
					file.delete();
				}
			}
		}

		if (newFile != null) {
			uploadImageFile(member.getId(), newFile);
		}
	}

}
