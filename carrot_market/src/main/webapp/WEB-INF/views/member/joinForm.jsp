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
		<form id="joinForm" action="${contextPath }/member/join" method="post">
			<div id="join_subject">
				<span>
					<img src="${contextPath }/resources/image/logo.webp" id="logo">
				</span>
				<span>&nbsp;회원가입</span>
			</div>
			<table>
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
					<c:choose>
						<c:when test="${not empty id}">
							<td class="error_msg" colspan="2">*${id}</td>
						</c:when>
						<c:otherwise>
							<td></td>
						</c:otherwise>
					</c:choose>
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
							<td></td>
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
					<c:choose>
						<c:when test="${not empty nickname}">
							<td class="error_msg" colspan="2">*${nickname}</td>
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
						<input type="text" name="region1" id="region1" class="input" readonly value="감정동" required />
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
			<button type="button" id="join_bt" onclick="joinSubmit()">회원가입</button>
			<input name="authority" type="hidden" value="USER" />
			<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}" />
		</form>
	</div>
	<script src="${contextPath }/resources/js/joinForm.js"></script>
</body>
</html>
