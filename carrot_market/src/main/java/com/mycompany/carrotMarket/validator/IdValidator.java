package com.mycompany.carrotMarket.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mycompany.carrotMarket.member.dto.IdDTO;

@Component
public class IdValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return IdDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		IdDTO dto = (IdDTO) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "field.required", "���̵�� �ʼ��Դϴ�.");

		if (dto.getId() != null && dto.getId().length() < 4) {
			errors.rejectValue("id", "field.minlength", "���̵�� 4���� �̻� �Է��ؾ� �մϴ�.");
		}
	}

}
