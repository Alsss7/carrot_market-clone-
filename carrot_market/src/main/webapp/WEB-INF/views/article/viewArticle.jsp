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
<meta charset="UTF-8">
<link rel="stylesheet" href="${contextPath }/resources/css/article/view-article.css" />
<c:if test="${addResult }">
	<c:choose>
		<c:when test="${addResult == true }">
			<script>
				alert('찜하기 성공!');
			</script>
		</c:when>
		<c:otherwise>
			<script>
				alert('찜하기 실패!');
			</script>
		</c:otherwise>
	</c:choose>
</c:if>
<c:if test="${removeResult }">
	<c:choose>
		<c:when test="${removeResult == true }">
			<script>
				alert('찜하기 해제!');
			</script>
		</c:when>
		<c:otherwise>
			<script>
				alert('에러!');
			</script>
		</c:otherwise>
	</c:choose>
</c:if>
<c:if test="${loginFirst == 'loginFirst' }">
	<script>
		window.onload = function() {
			alert('로그인 후 사용 가능한 기능입니다.');
		}
	</script>
</c:if>
</head>
<body>
	<main>
		<c:choose>
			<c:when test="${msg == 'success' }">
				<div id="images-and-user">
					<div id="images">
						<div class="slider-btn" id="prevBtn" onclick="prevSlide()">&#10094;</div>
						<div id="slides">
							<c:set var="images" value="${article.filesName }" />
							<c:choose>
								<c:when test="${images.size() == 0}">
									<div class="slide">
										<img src="${contextPath }/resources/image/product_image/empty.png" />
									</div>
								</c:when>
								<c:otherwise>
									<c:forEach var="image" items="${images}" varStatus="status">
										<div class="slide ${status.first ? 'active' : ''}">
											<img src="${contextPath}/resources/image/product_image/${article.productId}/${image}">
										</div>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</div>
						<div class="slider-btn" id="nextBtn" onclick="nextSlide()">&#10095;</div>
					</div>
					<div id="user-info">
						<div id="profile">
							<div id="profile-image">
								<img
									src="https://d1unjqcospf8gs.cloudfront.net/assets/users/default_profile_80-c649f052a34ebc4eee35048815d8e4f73061bf74552558bb70e07133f25524f9.png" />
							</div>
							<div id="info">
								<span id="user-id">${member.id }</span>
								<br>
								<span id="user-region">${article.region }</span>
							</div>
						</div>
						<div id="manner">
							<div id="manner-detail">
								<div id="user-manner">
									<span> ${member.manner } &#8451; </span>
									<br>
									<span>
										<progress value="${member.manner }" max="100"></progress>
									</span>
								</div>
								<div id="manner-image">
									<img src="${contextPath }">
								</div>
								<br>
							</div>
							<div id="manner-temperature">매너온도</div>
						</div>
					</div>
					<div id="line"></div>
				</div>
				<div id="details">
					<div>
						<div id="title">${article.title }</div>
						<div id="category-date">
							<span>${article.category }</span>
							·
							<span id="created-at"></span>
						</div>
						<div id="price">
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
						<div id="description">${article.description }</div>
						<div id="count">
							<span>관심 ${article.likeCount }</span>
							·
							<span>채팅 ${article.chatCount }</span>
							·
							<span>조회 ${article.viewCount }</span>
						</div>
					</div>
				</div>
				<div id="line"></div>
				<div id="like-and-chat">
					<a href="${contextPath }/article/like/${article.productId}" class="like">
						<c:choose>
							<c:when test="${like == true }">
								<span> ♥ </span>
							</c:when>
							<c:otherwise>
								<span> ♡ </span>
							</c:otherwise>
						</c:choose>
					</a>
					<sec:authorize access="isAuthenticated()">
						<c:set var="writerId" value="${member.id }" />
						<c:choose>
							<c:when test="${loginId == writerId}">
								<button class="chat">대화 중인 채팅방</button>
							</c:when>
							<c:otherwise>
								<button class="chat">채팅하기</button>
							</c:otherwise>
						</c:choose>
					</sec:authorize>
					<sec:authorize access="!isAuthenticated()">
						<button class="chat">로그인 후 채팅하기</button>
					</sec:authorize>
				</div>
			</c:when>
			<c:otherwise>
				<h1>게시글이 존재하지 않습니다!</h1>
			</c:otherwise>
		</c:choose>
	</main>
	<script>
		var timeDiff = "${timeDiff}";
	</script>
	<script src="${contextPath }/resources/js/article/viewArticle.js"></script>
</body>
</html>