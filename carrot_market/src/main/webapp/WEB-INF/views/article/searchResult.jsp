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
<link rel="stylesheet" href="${contextPath }/resources/css/article/searchResult.css" />
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="subject-wrapper">
			<c:choose>
				<c:when test="${not empty region}">
					<h3 class="subject">${region }&nbsp;중고거래</h3>
				</c:when>
				<c:otherwise>
					<h3 class="subject">중고거래</h3>
					<h5 class="subject">로그인하고 우리 동네 물품 확인하세요!</h5>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="item-container">
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
										<div class="region">${article.region }</div>
										<div class="price-likeCount">
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
											<div>
												<span class="like-icon">♡</span>
												<span class="like-count">${article.likeCount}</span>
											</div>
										</div>
									</a>
								</div>
							</c:forEach>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<h1 style="text-align: center;">검색 결과가 없습니다!</h1>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<div class="more-item" id="moreItem">더 보기</div>
	<script>
		var contextPath = '${contextPath}';
	</script>
	<script src="${contextPath }/resources/js/article/searchResult.js"></script>
</body>
</html>
