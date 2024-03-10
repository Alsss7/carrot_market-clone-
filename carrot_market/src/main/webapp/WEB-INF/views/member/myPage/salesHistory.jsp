<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${contextPath }/resources/css/member/myPage/salesHistory.css" />
</head>
<body>
	<main>
		<div id="my-sales">
			<div id="mySales-title">
				<h2>나의 판매내역</h2>
			</div>
			<div id="write-new">
				<a href="${contextPath }/article/new">글쓰기</a>
			</div>
		</div>
		<div id="status-wrapper">
			<span id="active">
				<a href="${contextPath }/member/myPage/salesHistory?status=Active">판매 중</a>
			</span>
			<span id="sold">
				<a href="${contextPath }/member/myPage/salesHistory?status=Sold">거래 완료</a>
			</span>
		</div>
		<div id="line"></div>
		<c:choose>
			<c:when test="${articles.size() != 0 }">
				<c:forEach var="article" items="${articles }">
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
						</a>
						<div id="like-chat">
							<div>
								<c:if test="${article.chatCount != 0 }">
									<div id="chat-count">${article.chatCount }</div>
								</c:if>
								<div id="like-count">♡&nbsp;${article.likeCount}</div>
							</div>
						</div>
					</div>
					<div id="line"></div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${status == 'Active' }">
						<h1 style="text-align: center; color: orange">판매 중인 상품이 없습니다!</h1>
					</c:when>
					<c:when test="${status == 'Sold' }">
						<h1 style="text-align: center; color: orange">거래 완료된 상품이 없습니다!</h1>
					</c:when>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</main>
	<script src="${contextPath }/resources/js/member/myPage/salesHistory.js"></script>
</body>
</html>
