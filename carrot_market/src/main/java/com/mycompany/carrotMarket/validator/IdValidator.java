package com.mycompany.carrotMarket.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mycompany.carrotMarket.member.dto.IdDTO;

@Component
public class IdValidator implements Validator {

	private static final String ID_REGEX = "^[a-zA-Z0-9]+$";
	private static final Pattern ID_PATTERN = Pattern.compile(ID_REGEX);

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

		if (!ID_PATTERN.matcher(dto.getId()).matches()) {
			errors.rejectValue("id", "field.invalid", "����� ���ڸ� ����ؾ� �մϴ�.");
		}
	}

}
