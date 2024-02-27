<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${contextPath }/resources/css/fleamarket.css" />
<c:if test="${not empty result }">
	<script>
		var message = "${result}";
		window.onload = function() {
			alert(message);
		}
	</script>
</c:if>
</head>
<body>
	<main>
		<div id="introduce">
			<div id="intro-left">
				<p class="comment1">
					믿을 만한<br>이웃 간 중고거래
				</p>
				<p class="comment2">
					동네 주민들과 가깝고 따뜻한 거래를<br>지금 경험해보세요.
				</p>
			</div>
			<div id="intro-right">
				<img
					src="https://d1unjqcospf8gs.cloudfront.net/assets/home/main/3x/fleamarket-39d1db152a4769a6071f587fa9320b254085d726a06f61d544728b9ab3bd940a.webp" />
			</div>
		</div>
		<h1 class="subject">중고거래 인기매물</h1>
		<div id="item-wrap">
			<div id="item-list">
				<div id="item">
					<a href="#">
						<img src="${contextPath }/resources/image/empty.png"><br>
						<div class="article_title">사과 한박스</div>
						<div class="price">1,000원</div>
						<div class="region">경기도 용인시 수지구 상현동</div>
						<div class="likeAndChat">
							<span class="">관심 15</span>
							·
							<span class="">채팅 46</span>
						</div>
					</a>
				</div>
				<div id="item">
					<a href="#">
						<img src="${contextPath }/resources/image/empty.png"><br>
						<div class="article_title">사과 한박스</div>
						<div class="price">1,000원</div>
						<div class="region">경기도 용인시 수지구 상현동</div>
						<div class="likeAndChat">
							<span class="">관심 15</span>
							·
							<span class="">채팅 46</span>
						</div>
					</a>
				</div>
				<div id="item">
					<a href="#">
						<img src="${contextPath }/resources/image/empty.png"><br>
						<div class="article_title">사과 한박스</div>
						<div class="price">1,000원</div>
						<div class="region">경기도 용인시 수지구 상현동</div>
						<div class="likeAndChat">
							<span class="">관심 15</span>
							·
							<span class="">채팅 46</span>
						</div>
					</a>
				</div>
				<div id="item">
					<a href="#">
						<img src="${contextPath }/resources/image/empty.png"><br>
						<div class="article_title">사과 한박스</div>
						<div class="price">1,000원</div>
						<div class="region">경기도 용인시 수지구 상현동</div>
						<div class="likeAndChat">
							<span class="">관심 15</span>
							.
							<span class="">채팅 46</span>
						</div>
					</a>
				</div>
			</div>
			<div id="more-item">
				<a href="${contextPath }/hot_article">인기매물 더 보기</a>
			</div>
		</div>
		<sec:authorize access="isAuthenticated()">
			<a class="register-item" href="${contextPath }/article/new">
				<img src="${contextPath}/resources/image/register.png">
			</a>
		</sec:authorize>
	</main>
</body>
</html>
