<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${contextPath }/resources/css/member/myPage/purchaseHistory.css" />
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<main>
		<h2 style="text-align: center">구매내역</h2>
		<c:choose>
			<c:when test="${articles.size() != 0 }">
				<c:forEach var="entry" items="${articles }">
					<c:set var="article" value="${entry.key }" />
					<c:set var="review" value="${entry.value }" />
					<c:set var="images" value="${article.filesName }" />
					<div id="product-wrapper">
						<a href="${contextPath }/article/${article.productId}">
							<div id="product">
								<div id="thumbnail">
									<c:choose>
										<c:when test="${images.size() == 0 }">
											<img src="${contextPath }/resources/image/product_image/empty.png" />
										</c:when>
										<c:otherwise>
											<img
												src="${contextPath }/resources/image/product_image/${article.productId}/${images[0]}" />
										</c:otherwise>
									</c:choose>
								</div>
								<div id="product-detail">
									<div class="title">${article.title }</div>
									<div class="region">${article.region }</div>
									<div id="status-price">
										<span class="status" style="background-color: black;">거래완료</span>
										<c:choose>
											<c:when test="${article.price == 0 }">
												<div class="price">나눔</div>
											</c:when>
											<c:otherwise>
												<div class="price">
													<c:set var="productPrice" value="${article.price }" />
													<fmt:formatNumber value="${productPrice }" pattern="#,##0" var="formattedPrice" />
													${formattedPrice }원
												</div>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
						</a>
						<div id="like-chat-count">
							<c:if test="${article.chatCount != 0 }">
								<div id="chat-count">☏&nbsp;${article.chatCount }</div>
							</c:if>
							<c:if test="${article.likeCount != 0 }">
								<div id="like-count">♡&nbsp;${article.likeCount}</div>
							</c:if>
						</div>
					</div>
					<div class="button-wrapper">
						<c:if test="${article.status == 'Sold' }">
							<c:choose>
								<c:when test="${not empty review}">
									<div class="sent-review">후기 전송 완료</div>
								</c:when>
								<c:otherwise>
									<div class="send-review" id="sendReview" onclick="sendReview(${article.productId})">✏️
										후기 보내기</div>
								</c:otherwise>
							</c:choose>
						</c:if>
					</div>
					<div class="line"></div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<h1 style="text-align: center; color: orange">구매한 상품이 없습니다!</h1>
			</c:otherwise>
		</c:choose>
		<script src="${contextPath }/resources/js/member/myPage/purchaseHistory.js"></script>
	</main>
</body>
</html>
