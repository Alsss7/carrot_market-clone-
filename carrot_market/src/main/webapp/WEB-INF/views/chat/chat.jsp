<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<c:set var="loginId" value="${pageContext.request.userPrincipal.name }" />
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${contextPath }/resources/css/chat/chat.css" />
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1.5.1/dist/sockjs.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<meta charset="UTF-8">
</head>
<body>
	<div class="chat-container">
		<div class="user-wrapper">
			<div class="user-container">
				<div class="user-id">${target.id }</div>
				<div class="manner-temperature">${target.manner }&#8451;</div>
			</div>
			<div class="exit-container" id="exit-button">
				<img src="${contextPath }/resources/image/exit.png" />
			</div>
		</div>
		<div class="line"></div>
		<div class="product-wrapper">
			<div class="product-image">
				<img
					src="${contextPath}/resources/image/product_image/${article.productId}/${article.filesName[0]}" />
			</div>
			<div class="product-content">
				<div class="stat-and-title">
					<c:choose>
						<c:when test="${loginId == article.userId}">
							<select id="status-select" name="status">
								<option value="Active">판매중</option>
								<option value="Booking">예약중</option>
								<option value="Sold">거래완료</option>
							</select>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${article.status == 'Booking' }">
									<span class="stat">예약 중</span>
								</c:when>
								<c:when test="${article.status == 'Sold' }">
									<span class="stat">거래완료</span>
								</c:when>
								<c:otherwise>
									<span class="stat">판매중</span>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
					<div class="title">${article.title }</div>
				</div>
				<div class="price">
					<c:set var="productPrice" value="${article.price }" />
					<c:choose>
						<c:when test="${productPrice == 0 }">
						나눔
					</c:when>
						<c:otherwise>
							<fmt:formatNumber value="${productPrice }" pattern="#,##0" var="formattedPrice" />
						${formattedPrice }원
					</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
		<div class="line"></div>
		<div class="chat-wrapper">
			<div class="message-area" id="message-area">
				<c:set var="previousDate" value="" />
				<c:forEach var="message" items="${messages }">
					<fmt:formatDate value="${message.sentAt }" pattern="yyyy. MM. dd. (E)" var="sentDate" />
					<c:if test="${previousDate != sentDate }">
						<div class="sent-date" style="text-align: center;">${sentDate }</div>
					</c:if>
					<c:set var="previousDate" value="${sentDate }" />

					<fmt:formatDate value="${message.sentAt }" pattern="a hh:mm" var="sentTime" />
					<c:choose>
						<c:when test="${message.sender == loginId }">
							<div class="my-message-wrapper">
								<div class="sent-time">${sentTime }</div>
								<div class="my-message">${message.content }</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="your-message-wrapper">
								<div class="your-message">${message.content }</div>
								<div class="sent-time">${sentTime }</div>
							</div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
			<div id="message-form" class="message-form">
				<input type="text" name="message" id="input-message" autofocus="autofocus" />
				<input type="submit" value="보내기" id="send-message" disabled />
			</div>
		</div>
	</div>
	<script>
		var contextPath = '${contextPath}';
		var productId = '${article.productId}';
		var sellerId = '${sellerId}';
		var buyerId = '${buyerId}';
		var loginId = '${loginId}';
		var productStatus = "${article.status}";

		var lastMsgDate = '${lastMsgDate}';
		var chatId = '${chatId}';
	</script>
	<c:choose>
		<c:when test="${messageSize == 0 }">
			<script>
				var isChatExists = false;
			</script>
		</c:when>
		<c:otherwise>
			<script>
				var isChatExists = true;
			</script>
		</c:otherwise>
	</c:choose>
	<script src="${contextPath }/resources/js/chat/chat.js"></script>
</body>
</html>

