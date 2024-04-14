<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${contextPath }/resources/css/article/hotArticle.css" />
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<c:choose>
		<c:when test="${not empty region1 and not empty region2 }">
			<h1 class="subject">${region1 }&nbsp;${region2 }&nbsp;중고거래&nbsp;인기매물</h1>
		</c:when>
		<c:when test="${not empty region1 }">
			<h1 class="subject">${region1 }&nbsp;중고거래&nbsp;인기매물</h1>
		</c:when>
		<c:otherwise>
			<h1 class="subject">중고거래&nbsp;인기매물</h1>
		</c:otherwise>
	</c:choose>
	<div id="select-region-container">
		<select class="select-region" id="region1">
			<option value="" selected>지역을 선택하세요</option>
		</select>
		<select class="select-region" id="region2" disabled>
			<option value="" selected>동네를 선택하세요</option>
		</select>
	</div>
	<c:choose>
		<c:when test="${not empty articles }">
			<div id="item-wrap">
				<div id="item-list">
					<c:forEach var="article" items="${articles}">
						<c:set var="images" value="${article.filesName }" />
						<div class="item">
							<a href="${contextPath }/article/${article.productId}">
								<c:choose>
									<c:when test="${images.size() == 0 }">
										<img src="${contextPath }/resources/image/product_image/empty.png">
										<br>
									</c:when>
									<c:otherwise>
										<img
											src="${contextPath }/resources/image/product_image/${article.productId}/${images[0] }" />
										<br>
									</c:otherwise>
								</c:choose>
								<div class="article-title">${article.title }</div>
								<div id="status-price">
									<c:choose>
										<c:when test="${article.status == 'Booking' }">
											<span class="status" style="background-color: green;">예약 중</span>
										</c:when>
										<c:when test="${article.status == 'Sold' }">
											<span class="status" style="background-color: black;">거래완료</span>
										</c:when>
									</c:choose>
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
		</c:when>
		<c:otherwise>
			<h1 style="text-align: center;">등록된 상품이 없습니다!</h1>
		</c:otherwise>
	</c:choose>
	<sec:authorize access="isAuthenticated()">
		<a class="register-item" href="${contextPath }/article/new">
			<img src="${contextPath}/resources/image/register.png">
		</a>
	</sec:authorize>
	<script>
		var contextPath = '${contextPath}';
	</script>
	<script src="${contextPath }/resources/js/article/hotArticle.js"></script>
</body>
</html>
