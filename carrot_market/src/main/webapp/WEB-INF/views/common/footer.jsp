<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${contextPath }/resources/css/common/footer.css" />
</head>
<body>
	<hr id="seperate" />
	<div id="footer">
		<div>
			<ul>
				<li><a href="${contextPath }/fleamarket">중고거래</a></li>
				<li>동네업체</li>
				<li>당근알바</li>
				<li>부동산 직거래</li>
				<li>중고차 직거래</li>
			</ul>
		</div>

		<div>
			<ul>
				<li>당근 비즈니스</li>
				<li>채팅하기</li>
			</ul>
		</div>

		<div>
			<ul>
				<li>자주 묻는 질문</li>
				<li>회사 소개</li>
				<li>인재 채용</li>
			</ul>
		</div>

		<div id="install">
			<p>당근 앱 다운로드</p>
			<span>
				<a href="https://play.google.com/store/apps/details?id=com.towneers.www&hl=ko-KR"> Google
					Play </a>
			</span>
			<span>
				<a href="https://apps.apple.com/kr/app/%EB%8B%B9%EA%B7%BC/id1018769995">App Store</a>
			</span>
		</div>
	</div>
	<div class="copyright">
		<a
			href="https://kr.freepik.com/icon/user_747376#fromView=search&term=%EC%82%AC%EB%9E%8C&track=ais&page=1&position=32&uuid=fd044a7f-bd3c-43fd-a305-0633829ee21f">
			Designed by Freepik </a>
	</div>
</body>
</html>
