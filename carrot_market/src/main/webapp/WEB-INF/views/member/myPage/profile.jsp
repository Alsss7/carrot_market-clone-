<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%
request.setCharacterEncoding("utf-8");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<c:set var="isMatch" value="${isMatch }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${contextPath }/resources/css/member/myPage/profile.css">
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<div id="profileDiv">
		<c:choose>
			<c:when test="${empty isMatch or isMatch == false }">
				<c:if test="${isMatch == false }">
					<script>
						window.onload = function() {
							alert("비밀번호가 틀립니다! 다시 시도하세요.");
						}
					</script>
				</c:if>
				<form action="${contextPath }/member/myPage/profile" method="post" id="profileForm">
					<div class="profile_subject">
						<span>
							<img src="${contextPath }/resources/image/logo.webp" id="logo">
						</span>
						<span>&nbsp;프로필</span>
					</div>
					<table>
						<tr>
							<td class="label">비밀번호&nbsp;</td>
							<td colspan="2">
								<input type="password" name="pw" id="pw" class="input" required />
							</td>
						</tr>
					</table>
					<button type="submit" id="confirm_bt">확인</button>
					<input type="hidden" name="id" id="id"
						value="<sec:authentication property="principal.username" />" />
				</form>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${!empty result }">
						<c:choose>
							<c:when test="${result == 'success' }">
								<script>
									window.onload = function() {
										alert("회원 정보 수정 완료!");
									}
								</script>
							</c:when>
							<c:otherwise>
								<script>
									window.onload = function() {
										alert("회원 정보 수정 실패!");
									}
								</script>
							</c:otherwise>
						</c:choose>
					</c:when>
				</c:choose>
				<form action="${contextPath }/member/myPage/profile/modify" method="post" id="profileForm"
					enctype="multipart/form-data">
					<div class="profile_subject">
						<span>
							<img src="${contextPath }/resources/image/logo.webp" id="logo">
						</span>
						<span>&nbsp;프로필</span>
					</div>
					<table>
						<tr style="text-align: center;">
							<td></td>
							<td>
								<div id="imagePreviewContainer">
									<input type="file" name="profile_image" id="fileInput" style="display: none;" />
									<c:choose>
										<c:when test="${not empty member.fileName }">
											<input type="hidden" name="fileName" value="${member.fileName }" id="originImage" />
											<img id="previewImage"
												src="${contextPath }/resources/image/profile_image/${member.id}/${member.fileName}" />
											<a id="deleteButton" class="delete-button" onclick="removeImage(event)"
												style="display: flex">X</a>
										</c:when>
										<c:otherwise>
											<img id="previewImage"
												src="https://d1unjqcospf8gs.cloudfront.net/assets/users/default_profile_80-c649f052a34ebc4eee35048815d8e4f73061bf74552558bb70e07133f25524f9.png" />
											<a id="deleteButton" class="delete-button" onclick="removeImage(event)">X</a>
										</c:otherwise>
									</c:choose>
								</div>
							</td>
						</tr>
						<tr>
							<td class="label">아이디&nbsp;</td>
							<td>
								<input type="text" name="id" id="id" class="input" value="${member.id }" required readonly />
							</td>
						</tr>
						<tr class="errors">
							<td></td>
							<td id="id_error" class="error_msg" colspan="2"></td>
						</tr>

						<tr>
							<td class="label">닉네임&nbsp;</td>
							<td>
								<input type="text" name="nickname" id="nickname" class="input" value="${member.nickname }"
									required />
							</td>
							<td>
								<input type="button" id="checkNickname" class="button" value="중복 확인" />
							</td>
						</tr>
						<tr class="errors">
							<td></td>
							<td id="nickname_error" class="error_msg" colspan="2"></td>
						</tr>

						<tr>
							<td class="label">비밀번호&nbsp;</td>
							<td colspan="2">
								<input type="password" name="pw" id="pw" value="${member.pw }" class="input" required />
							</td>
						</tr>
						<tr class="errors">
							<td></td>
							<c:choose>
								<c:when test="${not empty pw}">
									<td class="error_msg" colspan="2">*${pw}</td>
								</c:when>
								<c:otherwise>
									<td></td>
								</c:otherwise>
							</c:choose>
						</tr>

						<tr>
							<td class="label">이름&nbsp;</td>
							<td colspan="2">
								<input type="text" name="name" id="name" value="${member.name }" class="input" required
									readonly />
							</td>
						</tr>
						<tr class="errors">
							<td></td>
							<c:choose>
								<c:when test="${not empty name}">
									<td class="error_msg" colspan="2">*${name}</td>
								</c:when>
								<c:otherwise>
									<td></td>
								</c:otherwise>
							</c:choose>
						</tr>

						<tr>
							<td class="label">이메일&nbsp;</td>
							<td colspan="2">
								<input type="text" name="email" id="email" value="${member.email }" class="input" required />
							</td>
						</tr>
						<tr class="errors">
							<td></td>
							<c:choose>
								<c:when test="${not empty email}">
									<td class="error_msg" colspan="2">*${email}</td>
								</c:when>
								<c:otherwise>
									<td></td>
								</c:otherwise>
							</c:choose>
						</tr>

						<tr>
							<td class="label">동네&nbsp;</td>
							<td>
								<input type="text" name="region1" id="region1" class="input" value="${member.region1 }"
									required readonly />
							</td>
							<td>
								<input type="button" class="button" id="find_region" value="동네 찾기" />
							</td>
						</tr>
						<tr class="errors">
							<c:choose>
								<c:when test="${not empty region1}">
									<td></td>
									<td class="error_msg" colspan="2">*${region1}</td>
								</c:when>
								<c:otherwise>
									<td></td>
									<td></td>
								</c:otherwise>
							</c:choose>
						</tr>
					</table>
					<button type="submit" id="modify_bt">수정하기</button>
				</form>
			</c:otherwise>
		</c:choose>
	</div>
	<script>
        // 클라이언트 측에서 조건을 검사하여 동적으로 스크립트 추가
    	var result = ${!empty isMatch and isMatch};
        if (result) {
            var scriptElement1 = document.createElement("script");
            scriptElement1.src = "${contextPath}/resources/js/member/modDupCheck.js";
            document.body.appendChild(scriptElement1);
            
            var scriptElement2 = document.createElement("script");
            scriptElement2.src = "${contextPath}/resources/js/member/getRegion.js";
            document.body.appendChild(scriptElement2);
        }
    </script>
	<script src="${contextPath }/resources/js/member/myPage/profile.js"></script>
</body>
</html>

