<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${contextPath }/resources/css/article/fleamarket.css" />
<c:if test="${not empty result }">
	<script>
		var message = "${result}";
		window.onload = function() {
			alert(message);
		}
	</script>
</c:if>
<c:if test="${not empty deleteResult }">
	<c:choose>
		<c:when test="${deleteResult == true }">
			<script>
				window.onload = function() {
					alert("삭제 성공");
				}
			</script>
		</c:when>
		<c:otherwise>
			<script>
				window.onload = function() {
					alert("삭제 실패");
				}
			</script>
		</c:otherwise>
	</c:choose>
</c:if>
</head>
<body>
	<main>
		<div id="introduce">
			<div id="intro-left">
				<p class="comment1">
					믿을 만한<br>이웃 간 중고거래
				</p>
				<p class="comment2">
					동네 주민들과 가깝고 따뜻한 거래를<br>지금 경험해보세요.
				</p>
			</div>
			<div id="intro-right">
				<img
					src="https://d1unjqcospf8gs.cloudfront.net/assets/home/main/3x/fleamarket-39d1db152a4769a6071f587fa9320b254085d726a06f61d544728b9ab3bd940a.webp" />
			</div>
		</div>
		<c:choose>
			<c:when test="${not empty region }">
				<h1 class="subject">${region }</h1>
			</c:when>
			<c:otherwise>
				<h1 class="subject">중고거래&nbsp;인기매물</h1>
			</c:otherwise>
		</c:choose>

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
							<div class="like-and-chat">
								<span class="">관심 ${article.likeCount}</span>
								·
								<span class="">채팅 ${article.chatCount }</span>
							</div>
						</a>
					</div>
				</c:forEach>
			</div>
			<div id="more-item">
				<a href="${contextPath }/article/hotArticle">
					<c:choose>
						<c:when test="${not empty region }">
							다른 동네 인기매물 더 보기
						</c:when>
						<c:otherwise>
							인기매물 더 보기
						</c:otherwise>
					</c:choose>
				</a>
			</div>
		</div>
		<sec:authorize access="isAuthenticated()">
			<a class="register-item" href="${contextPath }/article/new">
				<img src="${contextPath}/resources/image/register.png">
			</a>
		</sec:authorize>
	</main>
</body>
</html>
