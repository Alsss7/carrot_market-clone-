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
</head>
<body>
  <div id="joinForm">
    <form action="member/addMember.do" method="post">
      <div id="join_subject">
        <span>
          <img src="${contextPath }/resources/image/logo.webp" id="logo">
        </span>
        <span>&nbsp;회원가입</span>
      </div>
      <table>
        <tr>
          <td class="label">아이디&nbsp;</td>
          <td><input type="text" name="id" class="input" /></td>
        </tr>

        <tr>
          <td class="label">비밀번호&nbsp;</td>
          <td><input type="password" name="pw" class="input" /></td>
        </tr>
        <tr>
          <td class="label">비밀번호 확인&nbsp;</td>
          <td><input type="password" name="pw" class="input" /></td>
        </tr>


        <tr>
          <td class="label">이름&nbsp;</td>
          <td><input type="text" name="name" class="input" /></td>
        </tr>

        <tr>
          <td class="label">닉네임&nbsp;</td>
          <td><input type="text" name="nickname" class="input" /></td>
        </tr>

        <tr>
          <td class="label">이메일&nbsp;</td>
          <td><input type="text" name="email" class="input" /></td>
        </tr>
      </table>
      <span>
        <input type="submit" value="회원가입" id="join_bt" />
      </span>
    </form>
  </div>
</body>
</html>
