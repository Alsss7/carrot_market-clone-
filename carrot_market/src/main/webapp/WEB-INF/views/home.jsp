<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
<script type="text/javascript">
	function search(e) {
		const code = e.code;
		var input = document.getElementById("search");
		if(code == 'Enter') {
			console.log(input.value);
			input.value = '';
		}
	}
</script>
</head>
<body>
   <header>
      <nav>
         <h1>당근</h1>
         <span>중고거래</span>
         <span>동네업체</span>
         <span>알바</span>
         <span>부동산 직거래</span>
         <span>중고차 직거래</span>
         <span><input type="search" id="search" name="search" onkeypress="search(event)"/></span>
      </nav>
   </header>
</body>
</html>