package com.mycompany.carrotMarket.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mycompany.carrotMarket.member.dto.NicknameDTO;

@Component
public class NicknameValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return NicknameDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		NicknameDTO dto = (NicknameDTO) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nickname", "field.required", "닉네임은 필수입니다.");

		if (dto.getNickname() != null && dto.getNickname().length() < 2) {
			errors.rejectValue("nickname", "field.minlength", "닉네임은 2글자 이상 입력해야 합니다.");
		}
	}

}
