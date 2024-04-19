<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<c:set var="loginId" value="${pageContext.request.userPrincipal.name }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${contextPath }/resources/css/article/buyerList.css" />
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<main>
		<h1>채팅한 이웃</h1>
		<c:choose>
			<c:when test="${empty chats }">
				<h3>채팅한 이웃이 없습니다!</h3>
			</c:when>
			<c:otherwise>
				<h3>거래할 이웃을 선택하세요!</h3>
				<c:forEach var="chat" items="${chats }" varStatus="status">
					<div class="chat-wrapper"
						onclick="submit('${chat.buyerId}', '${productStatus }', '${article.productId }')">
						<div class="image-wrapper">
							<img class="image-background" src="${contextPath }/resources/image/white.png" />
							<c:choose>
								<c:when test="${article.filesName.size() == 0 }">
									<img id="product-image" src="${contextPath }/resources/image/product_image/empty.png" />
								</c:when>
								<c:otherwise>
									<img id="product-image"
										src="${contextPath }/resources/image/product_image/${article.productId}/${article.filesName[0]}" />
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${not empty members[status.index].fileName }">
									<img id="profile-image"
										src="${contextPath }/resources/image/profile_image/${members[status.index].id}/${members[status.index].fileName}" />
								</c:when>
								<c:otherwise>
									<img id="profile-image"
										src="https://d1unjqcospf8gs.cloudfront.net/assets/users/default_profile_80-c649f052a34ebc4eee35048815d8e4f73061bf74552558bb70e07133f25524f9.png" />
								</c:otherwise>
							</c:choose>
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
											return Math.floor(timeDiff / 1000)
													+ '초 전';
										} else if (timeDiff < 3600000) {
											return Math.floor(timeDiff / 60000)
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
					<div class="line"></div>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</main>
	<script>
		var contextPath = '${contextPath}';
		var preUri = '${preUri}';
		var preStatus = '${preStatus}';
	</script>
	<script src="${contextPath }/resources/js/article/buyerList.js"></script>
</body>
</html>
