<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${contextPath }/resources/css/chat/chat.css" />
<meta charset="UTF-8">
</head>
<body>
	<div class="chat-container">
		<div class="user-wrapper">
			<div class="user-id">${member.id }</div>
			<div class="manner-temperature">${member.manner }&#8451;</div>
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
						<c:when test="${article.status == 'Booking' }">
							<span class="stat" style="background-color: green;">예약 중</span>
						</c:when>
						<c:when test="${article.status == 'Sold' }">
							<span class="stat" style="background-color: black;">거래완료</span>
						</c:when>
						<c:otherwise>
							<span class="stat">판매중</span>
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
			<div class="chat-content"></div>
			<form action="${contextPath }/chat" method="POST" id="message-form" class="message-form">
				<input type="text" name="message" id="input-message" />
				<input type="submit" value="보내기" id="submit-message" />
			</form>
		</div>
	</div>
</body>
</html>
