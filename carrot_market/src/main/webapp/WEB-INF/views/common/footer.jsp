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
        <li><a href="#">중고거래</a></li>
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
        <a href="#">Google Play</a>
      </span>
      <span>
        <a href="#">App Store</a>
      </span>
    </div>
  </div>
</body>
</html>
