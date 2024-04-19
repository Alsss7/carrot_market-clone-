<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${contextPath }/resources/css/review/newReview.css" />
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<c:choose>
		<c:when test="${not empty error }">
			<div class="container">
				<h2>이미 후기를 남겼습니다.</h2>
			</div>
		</c:when>
		<c:otherwise>
			<div class="container">
				<div class="whole">
					<a href="${contextPath }/article/${article.productId}">
						<div class="item-wrapper">
							<div class="product-image">
								<c:choose>
									<c:when test="${article.filesName.size() == 0 }">
										<img src="${contextPath }/resources/image/product_image/empty.png" />
									</c:when>
									<c:otherwise>
										<img
											src="${contextPath }/resources/image/product_image/${article.productId}/${article.filesName[0]}" />
									</c:otherwise>
								</c:choose>
							</div>
							<div class="title">&nbsp;&nbsp;${article.title }</div>
						</div>
					</a>
					<div class="subject">
						${loginId }님,<br>${targetId }님과 거래가 어떠셨나요?
					</div>
					<div id="reviewForm" class="review-form">
						<div class="select">
							<input type="radio" id="review1" name="review" value="1">
							<label for="review1">별로예요&nbsp;</label>
							<input type="radio" id="review2" name="review" value="2">
							<label for="review2">좋아요&nbsp;</label>
							<input type="radio" id="review3" name="review" value="3">
							<label for="review3">최고예요&nbsp;</label><br>
						</div>
						<input type="button" value="후기 보내기" class="submit-button" id="submitButton" disabled>
					</div>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
	<script>
		var tradeId = '${tradeId}';
		var productId = '${article.productId}';
		var reviewerId = '${loginId}';
		var preUri = '${preUri}';

		var error = '${error}';
	</script>
	<script src="${contextPath }/resources/js/review/newReview.js"></script>
</body>
</html>
