<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${contextPath }/resources/css/article/articleForm.css">
<meta charset="UTF-8">
</head>
<body>
	<div id="article-form-container">
		<form action="${contextPath }/article/register" method="post" id="article-form"
			enctype="multipart/form-data" onsubmit="return validateForm()">
			<div id="image-upload-wrapper">
				<label class="upload-button" onclick="addFile()"><img
					src="${contextPath }/resources/image/camera.png" /></label>
				<div id="image-preview-container"></div>
			</div>
			<div>
				<div class="label">제목</div>
				<div id="input-title">
					<input type="text" id="title" name="title" />
				</div>
			</div>
			<div>
				<div class="label">카테고리</div>
				<div id="select-category">
					<select name="category" id="category">
						<option value="" selected disabled>선택해주세요</option>
						<option value="디지털기기">디지털기기</option>
						<option value="생활가전">생활가전</option>
						<option value="가구/인테리어">가구/인테리어</option>
						<option value="생활/주방">생활/주방</option>
						<option value="유아동">유아동</option>
						<option value="유아도서">유아도서</option>
						<option value="여성의류">여성의류</option>
						<option value="여성잡화">여성잡화</option>
						<option value="남성패션/잡화">남성패션/잡화</option>
						<option value="뷰티/미용">뷰티/미용</option>
						<option value="스포츠/레저">스포츠/레저</option>
						<option value="취미/게임/음반">취미/게임/음반</option>
						<option value="도서">도서</option>
						<option value="티켓/교환권">티켓/교환권</option>
						<option value="가공식품">가공식품</option>
						<option value="반려동물용품">반려동물용품</option>
						<option value="식물">식물</option>
						<option value="기타 중고물품">기타 중고물품</option>
						<option value="삽니다">삽니다</option>
					</select>
				</div>
			</div>
			<div>
				<div class="label">거래 방식</div>
				<div id="select-style">
					<select name="sellOrShare" id="trade-style" onchange="tradeStyleChange(this)">
						<option value="sell">판매하기</option>
						<option value="share">나눔하기</option>
					</select>
				</div>
				<div id="input-price">
					<input type="text" id="price" name="price" placeholder="가격을 입력해주세요."
						oninput="numberFormat(this)" onclick="numberFormat(this)" onblur="removeCommas(this)" />
					<span id="won">원</span>
				</div>
			</div>
			<div>
				<div class="label">자세한 설명</div>
				<div id="input-explain">
					<textarea id="description" name="description"
						placeholder="${region }에 올릴 게시글 내용을 작성해 주세요.(판매 금지 물품은 게시가 제한될 수 있어요.) 신뢰할 수 있는 거래를 위해 자세히 적어주세요."></textarea>
				</div>
			</div>
			<div>
				<div class="label">거래 희망 장소</div>
				<div id="input-place">
					<input type="text" id="place" name="place" />
				</div>
			</div>
			<div id="article-bt">
				<button type="submit" id="submit-bt">작성 완료</button>
			</div>
			<input type="hidden" name="userId" value="<sec:authentication property="principal.username" />" />
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
	</div>
	<script src="${contextPath }/resources/js/article/articleForm.js"></script>
</body>
</html>
