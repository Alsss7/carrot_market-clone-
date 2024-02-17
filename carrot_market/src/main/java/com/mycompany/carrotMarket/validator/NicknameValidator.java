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

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nickname", "field.required", "�г����� �ʼ��Դϴ�.");

		if (dto.getNickname() != null && dto.getNickname().length() < 2) {
			errors.rejectValue("nickname", "field.minlength", "�г����� 2���� �̻� �Է��ؾ� �մϴ�.");
		}
	}

}
