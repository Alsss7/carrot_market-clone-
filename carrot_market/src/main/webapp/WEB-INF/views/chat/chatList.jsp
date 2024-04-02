<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<c:set var="loginId" value="${pageContext.request.userPrincipal.name }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${contextPath }/resources/css/chat/chatList.css" />
</head>
<body>
	<main>
		<h1>채팅목록</h1>
		<c:choose>
			<c:when test="${empty chats }">
				<h2 style="text-align: center;">채팅 중인 이웃이 없습니다!</h2>
			</c:when>
			<c:otherwise>
				<c:forEach var="chat" items="${chats }" varStatus="status">
					<form action="${contextPath }/chat/${article.productId}" method="GET" id="chatting"
						style="cursor: pointer" onclick="submit()">
						<input type="hidden" name="buyerId" value="${chat.buyerId}" />
						<div class="chat-wrapper">
							<div class="image-wrapper">
								<img class="image-background" src="${contextPath }/resources/image/white.png" /> <img
									id="product-image"
									src="${contextPath }/resources/image/product_image/${article.productId}/${article.filesName[0]}" />
								<img id="profile-image"
									src="https://d1unjqcospf8gs.cloudfront.net/assets/users/default_profile_80-c649f052a34ebc4eee35048815d8e4f73061bf74552558bb70e07133f25524f9.png" />
							</div>
							<div class="user-wrapper">
								<div class="user" id="user${status.index }">
									<span class="target-id">${members[status.index].id }</span>
									<span class="region">${members[status.index].region1 }</span>
									·
									<script>
										var index = '${status.index}';
										var timeDiff = '${timeDiffs[status.index]}';

										function formattimeDiff(timeDiff) {
											if (timeDiff < 60000) {
												return Math
														.floor(timeDiff / 1000)
														+ '초 전';
											} else if (timeDiff < 3600000) {
												return Math
														.floor(timeDiff / 60000)
														+ '분 전';
											} else if (timeDiff < 86400000) {
												return Math
														.floor(timeDiff / 3600000)
														+ '시간 전';
											} else if (timeDiff < 2592000000) {
												return Math
														.floor(timeDiff / 86400000)
														+ '일 전';
											} else if (timeDiff < 31536000000) {
												return Math
														.floor(timeDiff / 2592000000)
														+ '달 전';
											} else {
												return Math
														.floor(timeDiff / 31536000000)
														+ '년 전';
											}
										}
										var formattedtimeDiff = formattimeDiff(timeDiff);
										var userWrapper = document
												.getElementById('user' + index);
										var dateSpan = document
												.createElement('span');
										dateSpan.classList.add('sent-at');
										dateSpan.innerHTML = formattedtimeDiff;

										userWrapper.appendChild(dateSpan);
									</script>
								</div>
								<div class="last-message">
									<div>${lastMessages[status.index] }</div>
								</div>
							</div>
						</div>
					</form>
					<div class="line"></div>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</main>
	<script src="${contextPath }/resources/js/chat/chatList.js"></script>
</body>
</html>
