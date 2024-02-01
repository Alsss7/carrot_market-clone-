<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>당신 근처의 클론 당근</title>
<link rel="stylesheet" href="resources/css/home.css" />
<script type="text/javascript">
	function search(e) {
		const code = e.code;
		var input = document.getElementById("search");
		var form = document.getElementById("")
		if (code == 'Enter') {
			// 검색어 처리 로직
			console.log(input.value);
			input.value = '';
		}
	}
</script>
</head>
<body>
   <header>
      <div class="header-wrapper">
         <nav>
            <span style="display: inline" id="danggeun">
               <a href="/carrotMarket/home"><img src="resources/image/logo.webp" /></a>
            </span>
            <span class="menu">
               <a href="#">중고거래</a>
            </span>
            <span class="menu">동네업체</span>
            <span class="menu">알바</span>
            <span class="menu">부동산 직거래</span>
            <span class="menu">중고차 직거래</span>
            <form name="searchForm" style="display: inline">
               <span>
                  <input type="search" id="search" name="search" onkeypress="search(event)"
                     placeholder="물품이나 동네를 검색해보세요" size="30" />
               </span>
            </form>
            <span class="member">
               <a href="#">로그인</a>
            </span>
            <span class="member">
               <a href="#">회원가입</a>
            </span>
         </nav>
      </div>
   </header>

   <main>
      <div id="introduce">
         <div id="intro-left">
            <p class="comment1">
               당신 근처의<br>지역 생활 커뮤니티
            </p>
            <p class="comment2">
               동네라서 가까운 모든 것<br>당근에서 가까운 이웃과 함께해요.
            </p>
            <p class="install">
               <span>
                  <a href="#"><img
                     src="https://d1unjqcospf8gs.cloudfront.net/assets/home/base/header/google-play-white-0531cab5dbe15262e226cfb4acebeb316708ae0034d50b86ad4d809a03b6f5f0.svg" />&nbsp;Google
                     Play</a>
               </span>
               <span>
                  <a href="#"><img
                     src="https://d1unjqcospf8gs.cloudfront.net/assets/home/base/header/apple-store-white-9ebb10b431c549dd19f032f70e6762df307939b5add030265d9c2dea888b2d03.svg" />&nbsp;App
                     Store</a>
               </span>
            </p>
         </div>
         <div id="intro-right">
            <img
               src="https://d1unjqcospf8gs.cloudfront.net/assets/home/main/3x/rebranded-image-top-e765d561ee9df7f5ab897f622b8b5a35aaa70314f734e097ea70e6c83bdd73f1.webp" />
         </div>
      </div>
   </main>
</body>
</html>