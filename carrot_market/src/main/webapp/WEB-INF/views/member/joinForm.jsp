<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
request.setCharacterEncoding("utf-8");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${contextPath }/resources/css/member/joinForm.css" />
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<div id="joinFormDiv">
		<form id="joinForm" action="${contextPath }/member/join" method="post"
			enctype="multipart/form-data">
			<div id="join_subject">
				<span>
					<img src="${contextPath }/resources/image/logo.webp" id="logo">
				</span>
				<span>&nbsp;회원가입</span>
			</div>
			<table>
				<tr style="text-align: center;">
					<td></td>
					<td>
						<div id="imagePreviewContainer">
							<input type="file" name="profile_image" id="fileInput" style="display: none;" />
							<img id="previewImage"
								src="https://d1unjqcospf8gs.cloudfront.net/assets/users/default_profile_80-c649f052a34ebc4eee35048815d8e4f73061bf74552558bb70e07133f25524f9.png" />
							<a id="deleteButton" class="delete-button" onclick="removeImage(event)">X</a>
						</div>
					</td>
				</tr>
				<tr>
					<td class="label">아이디&nbsp;</td>
					<td>
						<input type="text" name="id" id="id" class="input" value="${member.id }" required autofocus />
					</td>
					<td>
						<input type="button" id="checkId" class="button" value="중복 확인" />
					</td>
				</tr>
				<tr class="errors">
					<td></td>
					<td id="id_error" class="error_msg" colspan="2"></td>
				</tr>

				<tr>
					<td class="label">닉네임&nbsp;</td>
					<td>
						<input type="text" name="nickname" id="nickname" class="input" value="${member.nickname }"
							required />
					</td>
					<td>
						<input type="button" id="checkNickname" class="button" value="중복 확인" />
					</td>
				</tr>
				<tr class="errors">
					<td></td>
					<td id="nickname_error" class="error_msg" colspan="2"></td>
				</tr>

				<tr>
					<td class="label">비밀번호&nbsp;</td>
					<td colspan="2">
						<input type="password" name="pw" id="pw" value="${member.pw }" class="input" required />
					</td>
				</tr>
				<tr class="errors">
					<td></td>
					<c:choose>
						<c:when test="${not empty pw}">
							<td class="error_msg" colspan="2">*${pw}</td>
						</c:when>
						<c:otherwise>
							<td colspan="2" class="default_msg">영문, 숫자, 특수문자를 1개 이상 포함(8글자 이상)</td>
						</c:otherwise>
					</c:choose>
				</tr>

				<tr>
					<td class="label">이름&nbsp;</td>
					<td colspan="2">
						<input type="text" name="name" id="name" value="${member.name }" class="input" required />
					</td>
				</tr>
				<tr class="errors">
					<td></td>
					<c:choose>
						<c:when test="${not empty name}">
							<td class="error_msg" colspan="2">*${name}</td>
						</c:when>
						<c:otherwise>
							<td></td>
						</c:otherwise>
					</c:choose>
				</tr>

				<tr>
					<td class="label">이메일&nbsp;</td>
					<td colspan="2">
						<input type="text" name="email" id="email" value="${member.email }" class="input" required />
					</td>
				</tr>
				<tr class="errors">
					<td></td>
					<c:choose>
						<c:when test="${not empty email}">
							<td class="error_msg" colspan="2">*${email}</td>
						</c:when>
						<c:otherwise>
							<td></td>
						</c:otherwise>
					</c:choose>
				</tr>

				<tr>
					<td class="label">동네&nbsp;</td>
					<td>
						<input type="text" name="region1" id="region1" class="input" readonly value="경기 김포시 감정동"
							required />
					</td>
					<td>
						<input type="button" class="button" id="find_region" value="동네 찾기" />
					</td>
				</tr>
				<tr class="errors">
					<c:choose>
						<c:when test="${not empty region1}">
							<td></td>
							<td class="error_msg" colspan="2">*${region1}</td>
						</c:when>
						<c:otherwise>
							<td></td>
							<td></td>
						</c:otherwise>
					</c:choose>
				</tr>
			</table>
			<button type="button" id="join_bt">회원가입</button>
			<input name="authority" type="hidden" value="USER" />
			<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}" />
		</form>
	</div>
	<script src="${contextPath }/resources/js/member/getRegion.js"></script>
	<script src="${contextPath }/resources/js/member/joinDupCheck.js"></script>
	<script src="${contextPath }/resources/js/member/joinForm.js"></script>
</body>
</html>
