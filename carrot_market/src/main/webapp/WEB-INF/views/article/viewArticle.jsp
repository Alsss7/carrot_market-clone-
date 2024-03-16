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
<link rel="stylesheet" href="${contextPath }/resources/css/article/viewArticle.css" />
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
<c:if test="${modifyResult }">
	<c:choose>
		<c:when test="${modifyResult == true }">
			<script>
				alert('수정 성공!');
			</script>
		</c:when>
		<c:otherwise>
			<script>
				alert('수정 실패!');
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
									<img src="${contextPath }/resources/image/product_image/empty.png">
								</div>
								<br>
							</div>
							<div id="manner-temperature">매너온도</div>
						</div>
					</div>
					<div id="line"></div>
				</div>
				<div id="details">
					<sec:authorize access="isAuthenticated()">
						<c:set var="writerId" value="${member.id }" />
						<c:choose>
							<c:when test="${loginId == writerId}">
								<form id="statusForm" method="get"
									action="${contextPath }/article/updateStat/${article.productId}/viewArticle">
									<select id="status-select" name="status" onchange="submitForm()">
										<option value="Active">판매중</option>
										<option value="Booking">예약중</option>
										<option value="Sold">거래완료</option>
									</select>
								</form>
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
					</sec:authorize>
					<div>
						<div id="title">${article.title }</div>
						<div id="category-date">
							<span>${article.category }</span>
							·
							<span id="created-at"></span>
						</div>
						<div id="status-price">
							<c:choose>
								<c:when test="${article.status == 'Booking' }">
									<span class="status" style="background-color: green;">예약 중</span>
								</c:when>
								<c:when test="${article.status == 'Sold' }">
									<span class="status" style="background-color: black;">거래완료</span>
								</c:when>
							</c:choose>
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
								<div id="indicator" onclick="openModal(this)">
									<img src="${contextPath }/resources/image/myPage/indicator.png" />
								</div>
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

		<div class="modal-container" id="modalContainer">
			<div id="modal" class="modal">
				<div class="modal-content">
					<c:choose>
						<c:when test="${article.hidden == 1 }">
							<div id="booking" class="options">
								<a href="${contextPath }/article/updateHidden/${article.productId}/viewArticle?hide=0">숨기기
									해제</a>
							</div>
							<div id="line"></div>
							<div id="modify" class="options">
								<a href="${contextPath }/article/modify/${article.productId}">게시글 수정</a>
							</div>
							<div id="line"></div>
							<div id="delete" class="options">
								<a href="${contextPath }/article/delete/${article.productId}/viewArticle"
									onclick="return confirm('정말로 삭제 하시겠습니까?')">삭제</a>
							</div>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${article.status == 'Booking' }">
									<div id="modify" class="options">
										<a href="${contextPath }/article/modify/${article.productId}">게시글 수정</a>
									</div>
								</c:when>
								<c:when test="${article.status == 'Sold' }">
									<div id="modify" class="options">
										<a href="${contextPath }/article/modify/${article.productId}">게시글 수정</a>
									</div>
									<div id="line"></div>
									<div id="hidden" class="options">
										<a href="${contextPath }/article/updateHidden/${article.productId}/viewArticle?hide=1">숨기기</a>
									</div>
									<div id="line"></div>
									<div id="delete" class="options">
										<a href="${contextPath }/article/delete/${article.productId}/viewArticle"
											onclick="return confirm('정말로 삭제 하시겠습니까?')">삭제</a>
									</div>
								</c:when>
								<c:otherwise>
									<div id="modify" class="options">
										<a href="${contextPath }/article/modify/${article.productId}">게시글 수정</a>
									</div>
									<div id="line"></div>
									<div id="hidden" class="options">
										<a href="${contextPath }/article/updateHidden/${article.productId}/viewArticle?hide=1">숨기기</a>
									</div>
									<div id="line"></div>
									<div id="delete" class="options">
										<a href="${contextPath }/article/delete/${article.productId}/viewArticle"
											onclick="return confirm('정말로 삭제 하시겠습니까?')">삭제</a>
									</div>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</main>
	<script>
		var timeDiff = "${timeDiff}";
		var contextPath = "${contextPath}";
		var productId = "${article.productId}";
		var productStatus = "${article.status}";
	</script>
	<script src="${contextPath }/resources/js/article/viewArticle.js"></script>
</body>
</html>