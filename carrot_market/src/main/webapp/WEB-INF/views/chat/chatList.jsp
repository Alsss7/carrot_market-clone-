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
		<c:forEach var="chat" items="${chats }" varStatus="status">
			<form action="${contextPath }/chat/${article.productId}" method="POST" id="chatting"
				style="cursor: pointer" onclick="submit()">
				<input type="hidden" value="${article.productId }" name="productId" />
				<input type="hidden" value="${loginId }" name="sellerId" />
				<input type="hidden" value="${members[status.index].id }" name="buyerId" />
				<div class="chat-wrapper">
					<div class="image-wrapper">
						<img class="image-background" src="${contextPath }/resources/image/white.png" /> 
						<img id="product-image"
							src="${contextPath }/resources/image/product_image/${article.productId}/${article.filesName[0]}" />
						<img id="profile-image"
							src="https://d1unjqcospf8gs.cloudfront.net/assets/users/default_profile_80-c649f052a34ebc4eee35048815d8e4f73061bf74552558bb70e07133f25524f9.png" />
					</div>
					<div class="user-wrapper">
						<div class="user">
							<span class="target-id">${members[status.index].id }</span>
							<span class="region">${members[status.index].region1 }</span>
							·
							<span id="sent-at"></span>
							<script>
								var timeDiff = '${timeDiffs[status.index]}';
								console.log(timeDiff);
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
	</main>
	<script src="${contextPath }/resources/js/chat/chatList.js"></script>
</body>
</html>
