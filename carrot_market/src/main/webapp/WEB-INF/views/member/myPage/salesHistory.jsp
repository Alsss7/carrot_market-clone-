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
<c:if test="${not empty result}">
	<script>
		var result = "${result}";
		window.onload = function() {
			alert(result);
		}
	</script>
</c:if>

<c:choose>
	<c:when test="${deleteMsg == 'success' }">
		<script>
			window.onload = function() {
				alert('삭제 성공!');
			}
		</script>
	</c:when>
	<c:when test="${deleteMsg == 'failed' }">
		<script>
			window.onload = function() {
				alert('삭제 실패!');
			}
		</script>
	</c:when>
</c:choose>
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
				<a href="${contextPath }/member/myPage/salesHistory?status=Hide">숨김</a>
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
							<div id="indicator" onclick="openModal(this, ${article.productId})">
								<img src="${contextPath }/resources/image/myPage/indicator.png" />
							</div>
							<div>
								<c:if test="${article.chatCount != 0 }">
									<div id="chat-count">${article.chatCount }</div>
								</c:if>
								<div id="like-count">♡&nbsp;${article.likeCount}</div>
							</div>
						</div>
					</div>
					<div id="line"></div>


					<div class="modal-container" id="modalContainer${article.productId }">
						<div id="modal${article.productId }" class="modal">
							<div class="modal-content">
								<c:choose>
									<c:when test="${article.status == 'Booking' }">
										<div id="booking" class="options">
											<a
												href="${contextPath }/article/updateStat/${article.productId}/salesHistory?status=Active">판매중</a>
										</div>
										<div id="line"></div>
										<div id="modify" class="options">
											<a href="">게시글 수정</a>
										</div>
									</c:when>
									<c:when test="${article.status == 'Sold' }">
										<div id="booking" class="options">
											<a
												href="${contextPath }/article/updateStat/${article.productId}/salesHistory?status=Active">판매중</a>
										</div>
										<div id="line"></div>
										<div id="modify" class="options">
											<a href="">게시글 수정</a>
										</div>
										<div id="line"></div>
										<div id="hidden" class="options">
											<a
												href="${contextPath }/article/updateStat/${article.productId}/salesHistory?status=Hide">숨기기</a>
										</div>
										<div id="line"></div>
										<div id="delete" class="options">
											<a href="" onclick="confirmDelete(${article.productId})">삭제</a>
										</div>
									</c:when>
									<c:when test="${article.status == 'Hide' }">
										<div id="booking" class="options">
											<a
												href="${contextPath }/article/updateStat/${article.productId}/salesHistory?status=Active">숨기기
												해제</a>
										</div>
										<div id="line"></div>
										<div id="modify" class="options">
											<a href="">게시글 수정</a>
										</div>
										<div id="line"></div>
										<div id="delete" class="options">
											<a href="" onclick="confirmDelete(${article.productId})">삭제</a>
										</div>
									</c:when>
									<c:otherwise>
										<div id="booking" class="options">
											<a
												href="${contextPath }/article/updateStat/${article.productId}/salesHistory?status=Booking">예약중</a>
										</div>
										<div id="line"></div>
										<div id="complete" class="options">
											<a
												href="${contextPath }/article/updateStat/${article.productId}/salesHistory?status=Sold">거래완료</a>
										</div>
										<div id="line"></div>
										<div id="modify" class="options">
											<a href="">게시글 수정</a>
										</div>
										<div id="line"></div>
										<div id="hidden" class="options">
											<a
												href="${contextPath }/article/updateStat/${article.productId}/salesHistory?status=Hide">숨기기</a>
										</div>
										<div id="line"></div>
										<div id="delete" class="options">
											<a href="" onclick="confirmDelete(${article.productId})">삭제</a>
										</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
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
					<c:when test="${status == 'Hide' }">
						<h1 style="text-align: center; color: orange">숨긴 상품이 없습니다!</h1>
					</c:when>
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