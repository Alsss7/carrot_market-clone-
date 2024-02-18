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
<link rel="stylesheet" href="${contextPath }/resources/css/member/loginForm.css" />
<c:choose>
	<c:when test="${result == 'success'}">
		<script>
			window.onload = function() {
				alert("회원가입 성공!");
			};
		</script>
	</c:when>
	<c:when test="${param.error == 'true' }">
		<script>
			window.onload = function() {
				alert("아이디나 비밀번호가 다릅니다.	");
			}
		</script>
	</c:when>
</c:choose>
</head>
<body>
	<div id="loginForm">
		<form action="<c:url value='/login' />" method="post">
			<div id="login_subject">
				<span>
					<img src="${contextPath }/resources/image/logo.webp" id="logo">
				</span>
				<span>&nbsp;로그인</span>
			</div>
			<table>
				<tr>
					<td class="label">아이디&nbsp;</td>
					<td>
						<input type="text" name="username" class="input" required autofocus />
					</td>
				</tr>

				<tr>
					<td class="label">비밀번호&nbsp;</td>
					<td>
						<input type="password" name="password" class="input" required />
					</td>
				</tr>
			</table>
			<span>
				<input type="submit" value="로그인" id="login_bt" />
			</span>
			<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}" />
		</form>
	</div>
</body>
</html>
