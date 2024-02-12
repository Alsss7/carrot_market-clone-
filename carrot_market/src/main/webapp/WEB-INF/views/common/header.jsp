<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
  isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${contextPath }/resources/css/common/header.css" />
<meta charset="UTF-8">
</head>
<body>
  <header>
    <div class="header-wrapper">
      <nav>
        <span style="display: inline" id="danggeun">
          <a href="${contextPath }/home">
            <img src="${contextPath }/resources/image/logo.webp" />
          </a>
        </span>
        <span class="menu">
          <a href="#">중고거래</a>
        </span>
        <span class="menu">동네업체</span>
        <span class="menu">알바</span>
        <span class="menu">부동산 직거래</span>
        <span class="menu">중고차 직거래</span>
        <form name="searchForm" style="display: inline">
          <span>
            <input type="search" id="search" name="search" onkeypress="search(event)"
              placeholder="물품이나 동네를 검색해보세요" size="30" />
          </span>
        </form>
        <sec:authorize access="isAuthenticated()">
          <form action="${contextPath }/logout" method="post">
            <span>
              <button type="submit" id="logout_bt">로그아웃</button>
              <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}" />
            </span>
          </form>
          &nbsp;&nbsp;
          <b><sec:authentication property="principal.username" /></b>님
          &nbsp;&nbsp;
          <a href="${contextPath }/mypage">
            <img src="${contextPath }/resources/image/mypage.png"
              style="width: 20px; height: 20px;" />
          </a>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
          <span class="member">
            <a href="${contextPath }/login">로그인</a>
          </span>
          <span class="member">
            <a href="${contextPath }/join">회원가입</a>
          </span>
        </sec:authorize>
      </nav>
    </div>
  </header>

</body>
</html>
