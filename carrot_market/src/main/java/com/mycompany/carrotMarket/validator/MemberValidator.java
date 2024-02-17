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

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pw", "field.required", "비밀번호는 필수입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required", "이름은 필수입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "field.required", "이메일은 필수입니다.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "region1", "field.required", "동네는 필수입니다. 동네 찾기를 눌러주세요.");

		if (member.getPw() != null && member.getPw().length() < 8) {
			errors.rejectValue("pw", "field.minlength", "비밀번호는 8글자 이상 입력해야 합니다.");
		} else if (!PASSWORD_PATTERN.matcher(member.getPw()).matches()) {
			errors.rejectValue("pw", "field.invalid", "비밀번호는 숫자, 영문, 특수문자를 1개 이상 포함하여 입력해주세요.");
		}

		if (member.getName() != null && member.getName().length() < 2) {
			errors.rejectValue("name", "field.minlength", "이름은 2글자 이상 입력해야 합니다.");
		}

		if (member.getEmail() != null && member.getEmail().length() < 5) {
			errors.rejectValue("email", "field.minlength", "이메일은 5글자 이상 입력해야 합니다.");
		} else if (!EMAIL_PATTERN.matcher(member.getEmail()).matches() || !member.getEmail().contains("@")) {
			errors.rejectValue("email", "field.invalid", "이메일 형식이 올바르지 않습니다.");
		}
	}

}
