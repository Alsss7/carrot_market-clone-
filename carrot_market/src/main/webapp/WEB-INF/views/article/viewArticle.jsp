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
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<c:if test="${not empty msg }">
	<script>
		alert('${msg}');
	</script>
</c:if>
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
		<c:set var="images" value="${article.filesName }" />
		<c:choose>
			<c:when test="${isExists == 'true' }">
				<div id="images-and-user">
					<div id="images">
						<c:if test="${images.size() > 1}">
							<div class="slider-btn" id="prevBtn" onclick="prevSlide()">&#10094;</div>
						</c:if>
						<div id="slides">
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
							<c:if test="${images.size() > 1}">
								<div id="dots">
									<c:forEach var="number" begin="0" end="${images.size() - 1 }" varStatus="status">
										<span class="dot ${status.first ? 'active' : ''}" id="dot${number }"></span>
									</c:forEach>
								</div>
							</c:if>
						</div>
						<c:if test="${images.size() > 1}">
							<div class="slider-btn" id="nextBtn" onclick="nextSlide()">&#10095;</div>
						</c:if>
					</div>
					<div id="user-info">
						<div id="profile">
							<div id="profile-image">
								<c:choose>
									<c:when test="${not empty member.fileName }">
										<img src="${contextPath }/resources/image/profile_image/${member.id}/${member.fileName}" />
									</c:when>
									<c:otherwise>
										<img
											src="https://d1unjqcospf8gs.cloudfront.net/assets/users/default_profile_80-c649f052a34ebc4eee35048815d8e4f73061bf74552558bb70e07133f25524f9.png" />
									</c:otherwise>
								</c:choose>
							</div>
							<div id="info">
								<span id="user-id">${member.id }</span>
								<br>
								<span id="user-region">${article.region }</span>
							</div>
						</div>
						<div id="manner">
							<div id="manner-detail">
								<c:choose>
									<c:when test="${member.manner < 21.3 }">
										<div id="user-manner">
											<div>${member.manner }&#8451;</div>
											<div class="progress-wrapper">
												<div class="progress-value" id="progressValue"></div>
											</div>
										</div>
										<div id="manner-image">😠</div>
									</c:when>
									<c:when test="${member.manner < 33.3 }">
										<div id="user-manner">
											<div>${member.manner }&#8451;</div>
											<div class="progress-wrapper">
												<div class="progress-value" id="progressValue"></div>
											</div>
										</div>
										<div id="manner-image">😒</div>
									</c:when>
									<c:when test="${member.manner < 43.5 }">
										<div id="user-manner">
											<div>${member.manner }&#8451;</div>
											<div class="progress-wrapper">
												<div class="progress-value" id="progressValue"></div>
											</div>
										</div>
										<div id="manner-image">🙂</div>
									</c:when>
									<c:when test="${member.manner < 58 }">
										<div id="user-manner">
											<div>${member.manner }&#8451;</div>
											<div class="progress-wrapper">
												<div class="progress-value" id="progressValue"></div>
											</div>
										</div>
										<div id="manner-image">😀</div>
									</c:when>
									<c:when test="${member.manner < 76.8 }">
										<div id="user-manner">
											<div>${member.manner }&#8451;</div>
											<div class="progress-wrapper">
												<div class="progress-value" id="progressValue"></div>
											</div>
										</div>
										<div id="manner-image">😄</div>
									</c:when>
									<c:otherwise>
										<div id="user-manner">
											<div>${member.manner }&#8451;</div>
											<div class="progress-wrapper">
												<div class="progress-value" id="progressValue"></div>
											</div>
										</div>
										<div id="manner-image">😆</div>
									</c:otherwise>
								</c:choose>
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
								<select id="status-select" name="status" onchange="updateStatus()">
									<option value="Active">판매중</option>
									<option value="Booking">예약중</option>
									<option value="Sold">거래완료</option>
								</select>
							</c:when>
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
								<a href="${contextPath }/chat/chatList/${article.productId}" class="chat">대화 중인
									채팅방&nbsp;${article.chatCount }</a>
								<div id="indicator" onclick="openModal(this)">
									<img src="${contextPath }/resources/image/myPage/indicator.png" />
								</div>
							</c:when>
							<c:otherwise>
								<form action="${contextPath }/chat/${article.productId}" method="GET">
									<input type="submit" value="채팅하기" class="chat" />
								</form>
							</c:otherwise>
						</c:choose>
					</sec:authorize>
					<sec:authorize access="!isAuthenticated()">
						<form action="${contextPath }/chat/condition" method="GET">
							<input type="hidden" value="${article.productId }" name="productId" />
							<input type="submit" value="로그인 후 채팅하기" class="chat" />
						</form>
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
								<a onclick="updateHidden(0)">숨기기 해제</a>
							</div>
							<div id="line"></div>
							<div id="modify" class="options">
								<a href="${contextPath }/article/modify/${article.productId}">게시글 수정</a>
							</div>
							<div id="line"></div>
							<div id="delete" class="options">
								<a onclick="deleteArticle(); return confirm('정말로 삭제 하시겠습니까?')">삭제</a>
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
										<a onclick="updateHidden(1)">숨기기</a>
									</div>
									<div id="line"></div>
									<div id="delete" class="options">
										<a onclick="deleteArticle(); return confirm('정말로 삭제 하시겠습니까?')">삭제</a>
									</div>
								</c:when>
								<c:otherwise>
									<div id="modify" class="options">
										<a href="${contextPath }/article/modify/${article.productId}">게시글 수정</a>
									</div>
									<div id="line"></div>
									<div id="hidden" class="options">
										<a onclick="updateHidden(1)">숨기기</a>
									</div>
									<div id="line"></div>
									<div id="delete" class="options">
										<a onclick="deleteArticle(); return confirm('정말로 삭제 하시겠습니까?')">삭제</a>
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
		var contextPath = "${contextPath}";
		var timeDiff = "${timeDiff}";
		var productId = "${article.productId}";
		var productStatus = "${article.status}";
		var manner = "${member.manner}";

		var isReviewed = "${isReviewed}";
	</script>
	<script src="${contextPath }/resources/js/article/viewArticle.js"></script>
</body>
</html>