<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>당근 - 회원가입</title>
</head>
<body>
   <form action="member/addMember.do" method="post">
      아이디 : <input type="text" name="id" /><br>
      비밀번호 : <input type="password" name="pw" /><br>
      이름 : <input type="text" name="name" /><br>
      닉네임 : <input type="text" name="nickname" /><br>
      이메일 : <input type="text" name="email" /><br>
   </form>
</body>
</html>