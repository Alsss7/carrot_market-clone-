package com.mycompany.carrotMarket.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mycompany.carrotMarket.member.vo.MemberVO;

@Component
public class MemberValidator implements Validator {

	private static final String EMAIL_REGEX = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

	private static final String PASSWORD_REGEX = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$";
	private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

	@Override
	public boolean supports(Class<?> clazz) {
		return MemberVO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MemberVO member = (MemberVO) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pw", "field.required", "��й�ȣ�� �ʼ��Դϴ�.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required", "�̸��� �ʼ��Դϴ�.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "field.required", "�̸����� �ʼ��Դϴ�.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "region1", "field.required", "���״� �ʼ��Դϴ�. ���� ã�⸦ �����ּ���.");

		if (member.getPw() != null && member.getPw().length() < 8) {
			errors.rejectValue("pw", "field.minlength", "��й�ȣ�� 8���� �̻� �Է��ؾ� �մϴ�.");
		} else if (!PASSWORD_PATTERN.matcher(member.getPw()).matches()) {
			errors.rejectValue("pw", "field.invalid", "��й�ȣ�� ����, ����, Ư�����ڸ� 1�� �̻� �����Ͽ� �Է����ּ���.");
		}

		if (member.getName() != null && member.getName().length() < 2) {
			errors.rejectValue("name", "field.minlength", "�̸��� 2���� �̻� �Է��ؾ� �մϴ�.");
		}

		if (member.getEmail() != null && member.getEmail().length() < 5) {
			errors.rejectValue("email", "field.minlength", "�̸����� 5���� �̻� �Է��ؾ� �մϴ�.");
		} else if (!EMAIL_PATTERN.matcher(member.getEmail()).matches() || !member.getEmail().contains("@")) {
			errors.rejectValue("email", "field.invalid", "�̸��� ������ �ùٸ��� �ʽ��ϴ�.");
		}
	}

}
