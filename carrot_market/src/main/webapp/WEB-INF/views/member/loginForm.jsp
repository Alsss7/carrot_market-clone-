<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>당근 - 로그인</title>
</head>
<body>
   <form action="/member/login.do" method="post">
      아이디 : <input type="text" name="user_id" /> <br>
      패스워드 : <input type="password" name="user_pw" />
   </form>
</body>
</html>