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
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<c:if test="${not empty result}">
	<script>
		var result = "${result}";
		window.onload = function() {
			alert(result);
		}
	</script>
</c:if>

<c:if test="${not empty deleteResult }">
	<c:choose>
		<c:when test="${deleteResult == true }">
			<script>
			window.onload = function() {
				alert('삭제 성공');
			}
		</script>
		</c:when>
		<c:otherwise>
			<script>
			window.onload = function() {
				alert('삭제 실패');
			}
		</script>
		</c:otherwise>
	</c:choose>
</c:if>
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
			<span id="hide">
				<a href="${contextPath }/member/myPage/salesHistory/hidden">숨김</a>
			</span>
		</div>
		<div class="line"></div>
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
								</div>
							</div>
						</a>
						<div id="like-chat">
							<div id="indicator-wrapper">
								<div id="indicator" onclick="openModal(this, ${article.productId})">
									<img src="${contextPath }/resources/image/myPage/indicator.png" />
								</div>
							</div>
							<div id="like-chat-count">
								<c:if test="${article.chatCount != 0 }">
									<div id="chat-count">☏&nbsp;${article.chatCount }</div>
								</c:if>
								<c:if test="${article.likeCount != 0 }">
									<div id="like-count">♡&nbsp;${article.likeCount}</div>
								</c:if>
							</div>
						</div>
					</div>
					<div class="line"></div>


					<div class="modal-container" id="modalContainer${article.productId }">
						<div id="modal${article.productId }" class="modal">
							<div class="modal-content">
								<c:choose>
									<c:when test="${article.hidden == 1 }">
										<div id="booking" class="options">
											<a onclick="updateHidden(0, '${article.productId}')">숨기기 해제</a>
										</div>
										<div class="line"></div>
										<div id="modify" class="options">
											<a href="${contextPath }/article/modify/${article.productId}">게시글 수정</a>
										</div>
										<div class="line"></div>
										<div id="delete" class="options">
											<a onclick="deleteArticle('${article.productId}'); return confirm('정말로 삭제 하시겠습니까?')">삭제</a>
										</div>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${article.status == 'Booking' }">
												<div id="active" class="options">
													<a onclick="updateStat(this, 'Active', '${article.productId}')">판매중</a>
												</div>
												<div class="line"></div>
												<div id="modify" class="options">
													<a href="${contextPath }/article/modify/${article.productId}">게시글 수정</a>
												</div>
											</c:when>
											<c:when test="${article.status == 'Sold' }">
												<div id="active" class="options">
													<a onclick="updateStat(this, 'Active', '${article.productId}')">판매중</a>
												</div>
												<div class="line"></div>
												<div id="modify" class="options">
													<a href="${contextPath }/article/modify/${article.productId}">게시글 수정</a>
												</div>
												<div class="line"></div>
												<div id="hidden" class="options">
													<a onclick="updateHidden(1, '${article.productId}')">숨기기</a>
												</div>
												<div class="line"></div>
												<div id="delete" class="options">
													<a onclick="deleteArticle('${article.productId}'); return confirm('정말로 삭제 하시겠습니까?')">삭제</a>
												</div>
											</c:when>
											<c:otherwise>
												<div id="booking" class="options">
													<a onclick="updateStat(this, 'Booking', '${article.productId}')">예약중</a>
												</div>
												<div class="line"></div>
												<div id="complete" class="options">
													<a onclick="updateStat(this, 'Sold', '${article.productId}')">거래완료</a>
												</div>
												<div class="line"></div>
												<div id="modify" class="options">
													<a href="${contextPath }/article/modify/${article.productId}">게시글 수정</a>
												</div>
												<div class="line"></div>
												<div id="hidden" class="options">
													<a onclick="updateHidden(1, '${article.productId}')">숨기기</a>
												</div>
												<div class="line"></div>
												<div id="delete" class="options">
													<a onclick="deleteArticle('${article.productId}');return confirm('정말로 삭제 하시겠습니까?')">삭제</a>
												</div>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${hidden == 0 }">
						<h1 style="text-align: center; color: orange">숨긴 상품이 없습니다!</h1>
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
			</c:otherwise>
		</c:choose>
	</main>
	<script>
		var contextPath = "${contextPath}";
	</script>
	<script src="${contextPath }/resources/js/member/myPage/salesHistory.js"></script>
</body>
</html>
