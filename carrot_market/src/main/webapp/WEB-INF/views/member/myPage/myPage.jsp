<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${contextPath }/resources/css/member/myPage/myPage.css">
<meta charset="UTF-8">
</head>
<body>
	<main>
		<div id="profile">
			<div id="image-and-name">
				<span id="profile-img">
					<img
						src="https://d1unjqcospf8gs.cloudfront.net/assets/users/default_profile_80-c649f052a34ebc4eee35048815d8e4f73061bf74552558bb70e07133f25524f9.png" />
				</span>
				<span id="username">
					<sec:authentication property="principal.username" />
				</span>
			</div>
			<div id="modify-profile">
				<a href="${contextPath }/member/myPage/profile">
					<span>프로필 수정</span>
				</a>
			</div>
		</div>
		<div id="line"></div>
		<div id="my-trade">
			<div id="trade-title">나의 거래</div>
			<a href="${contextPath }/member/myPage/likeList">
				<span id="like-list" class="list">
					<img src="${contextPath }/resources/image/myPage/like_icon.png" />&nbsp;관심 목록
				</span>
			</a>
			<a href="#">
				<span id="sell-list" class="list">
					<img src="${contextPath }/resources/image/myPage/receipt.png" />&nbsp;판매 내역
				</span>
			</a>
			<a href="#">
				<span id="buy-list" class="list">
					<img src="${contextPath }/resources/image/myPage/shopping.png" />&nbsp;구매 내역
				</span>
			</a>
		</div>
	</main>
</body>
</html>
