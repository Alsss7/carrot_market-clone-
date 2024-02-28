<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${contextPath }/resources/css/hot_article.css" />
<meta charset="UTF-8">
</head>
<body>
	<h1 class="subject">중고거래 인기매물</h1>
	<div id="select-region">
		<form>
			<select>
				<option value="경기도">경기도</option>
			</select>
			<select>
				<option value="가평군">가평군</option>
				<option value="고양시 덕양구">고양시 덕양구</option>
				<option value="고양시 일산동구">고양시 일산동구</option>
				<option value="김포시">김포시</option>
			</select>
		</form>
	</div>
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
			<a href="#">인기매물 더 보기</a>
		</div>
	</div>
	<sec:authorize access="isAuthenticated()">
		<a class="register-item" href="${contextPath }/article/new">
			<img src="${contextPath}/resources/image/register.png">
		</a>
	</sec:authorize>
</body>
</html>
