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
</head>
<body>
  <div id="loginForm">
    <form action="/member/login.do" method="post">
      <div id="login_subject">
        <span>
          <img src="${contextPath }/resources/image/logo.webp" id="logo">
        </span>
        <span>&nbsp;로그인</span>
      </div>
      <table>
        <tr>
          <td class="label">아이디&nbsp;</td>
          <td><input type="text" name="user_id" class="input" /></td>
        </tr>

        <tr>
          <td class="label">비밀번호&nbsp;</td>
          <td><input type="password" name="user_pw" class="input" /></td>
        </tr>
      </table>
      <span>
        <input type="submit" value="로그인" id="login_bt" />
      </span>
    </form>
  </div>
</body>
</html>
