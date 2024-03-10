<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${contextPath }/resources/css/hot_article.css" />
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<h1 class="subject">중고거래 인기매물</h1>
	<div id="select-region">
		<form action="${contextPath }/article/hotArticle" method="GET" id="regionForm">
			<select id="region1">
				<option value="" selected>지역을 선택하세요</option>
				<option value="서울특별시">서울특별시</option>
				<option value="경기도">경기도</option>
			</select>
			<select id="region2" disabled>
				<option value="" selected>동네를 선택하세요</option>
				<option value="가평군">가평군</option>
				<option value="고양시 덕양구">고양시 덕양구</option>
				<option value="고양시 일산동구">고양시 일산동구</option>
				<option value="김포시">김포시</option>
			</select>
		</form>
	</div>
	<div id="item-wrap">
		<div id="item-list">
			<c:forEach var="article" items="${articles}">
				<c:set var="images" value="${article.filesName }" />
				<div id="item">
					<a href="${contextPath }/article/${article.productId}">
						<c:choose>
							<c:when test="${images.size() == 0 }">
								<img src="${contextPath }/resources/image/product_image/empty.png">
								<br>
							</c:when>
							<c:otherwise>
								<img src="${contextPath }/resources/image/product_image/${article.productId}/${images[0] }" />
								<br>
							</c:otherwise>
						</c:choose>
						<div class="article_title">${article.title }</div>
						<div class="price">${article.price }원</div>
						<div class="region">${article.region }</div>
						<div class="likeAndChat">
							<span class="">관심 ${article.likeCount}</span>
							·
							<span class="">채팅 ${article.chatCount }</span>
						</div>
					</a>
				</div>
			</c:forEach>
		</div>
	</div>
	<sec:authorize access="isAuthenticated()">
		<a class="register-item" href="${contextPath }/article/new">
			<img src="${contextPath}/resources/image/register.png">
		</a>
	</sec:authorize>
	<script src="${contextPath }/resources/js/article/hotArticle.js"></script>
</body>
</html>
