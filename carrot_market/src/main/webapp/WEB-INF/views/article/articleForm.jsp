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
	<div id="article-div">
		<form action="${contextPath }/article/register" method="post" id="article-form">
			<div>
				<div id="input-photo">
					<img src="${contextPath }/resources/image/empty.png" />
				</div>
			</div>
			<div>
				<div class="label">제목</div>
				<div id="title">
					<input type="text" name="title" />
				</div>
			</div>
			<div>
				<div class="label">카테고리</div>
				<div id="category">
					<select name="category">
						<option>의류</option>
						<option>전자제품</option>
					</select>
				</div>
			</div>
			<div>
				<div class="label">거래 방식</div>
				<div id="select-style">
					<select name="sellOrShare">
						<option value="sell">판매하기</option>
						<option value="share">나눔하기</option>
					</select>
				</div>
				<div id="price">
					<input type="text" name="price" placeholder="가격을 입력해주세요." />
				</div>
			</div>
			<div>
				<div class="label">자세한 설명</div>
				<div id="input-explain">
					<textarea rows="4" cols="50" name="description"
						placeholder="에 올릴 게시글 내용을 작성해 주세요.(판매 금지 물품은 게시가 제한될 수 있어요.)\n\n신뢰할 수 있는 거래를 위해 자세히 적어주세요.">
					</textarea>
				</div>
			</div>
			<div>
				<div class="label">거래 희망 장소</div>
				<div id="trade-place">
					<input type="text" name="place" />
				</div>
			</div>
			<div id="article_bt">
				<button type="submit">작성 완료</button>
			</div>
			<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}" />
			<input type="hidden" name="userId" value="<sec:authentication property="principal.username" />" />
		</form>
	</div>
	<script src="${contextPath }/resources/css/article/articleForm.css"></script>
</body>
</html>
