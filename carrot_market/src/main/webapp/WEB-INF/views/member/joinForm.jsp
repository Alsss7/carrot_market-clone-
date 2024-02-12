<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
  isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
request.setCharacterEncoding("utf-8");
%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${contextPath }/resources/css/member/joinForm.css" />
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script>
	var lat;
	var lon;

	var options = {
		enableHighAccuracy : true,
		timeout : 5000,
		maximumAge : 0
	};
	
	function getPosition(options) {
	    return new Promise((resolve, reject) => 
	        navigator.geolocation.getCurrentPosition(resolve, reject, options)
	    );
	}
	
	function request() {
		$.ajax({
			url : 'https://dapi.kakao.com/v2/local/geo/coord2address.json?x='
					+ lon + '&y=' + lat,
			type : 'GET',
            dataType:"json",
			headers : {
				'Authorization' : 'KakaoAK 6756ca77ed4e62750b5d2bd424b2d90b'
			},
			success : function(data) {
				console.log(data);
				var region = data.documents[0].address.region_3depth_name;
				console.log(region);
				$('#region1').val(region);
			},
			error : function(e) {
				console.log(e);
			}
		});
		
	}
	
	async function getRegion() {
		try {
		    const position = await getPosition(options);
		    console.log(position);
		    lat = position.coords.latitude;
		    lon = position.coords.longitude;
		    request();
		} catch (err) {
			console.warn('ERROR(' + err.code + '): ' + err.message);

			lat = 36.366701;
			lon = 127.344307;
			
			request();
		}
	}

	$(function() {
		$("#find_region").click(function() {
			getRegion();
		})
	});
</script>
</head>
<body>
  <div id="joinForm">
    <form action="member/addMember.do" method="post">
      <div id="join_subject">
        <span>
          <img src="${contextPath }/resources/image/logo.webp" id="logo">
        </span>
        <span>&nbsp;회원가입</span>
      </div>
      <table>

        <tr>
          <td class="label">아이디&nbsp;</td>
          <td colspan="2"><input type="text" name="id" class="input" /></td>
        </tr>

        <tr>
          <td class="label">비밀번호&nbsp;</td>
          <td colspan="2"><input type="password" name="pw" class="input" /></td>
        </tr>

        <tr>
          <td class="label">이름&nbsp;</td>
          <td colspan="2"><input type="text" name="name" class="input" /></td>
        </tr>

        <tr>
          <td class="label">닉네임&nbsp;</td>
          <td colspan="2"><input type="text" name="nickname" class="input" /></td>
        </tr>

        <tr>
          <td class="label">이메일&nbsp;</td>
          <td colspan="2"><input type="text" name="email" class="input" /></td>
        </tr>

        <tr>
          <td class="label">동네&nbsp;</td>
          <td><input type="text" name="region1" class="input" readonly id="region1" /></td>
          <td><input type="button" class="input" id="find_region" value="동네 찾기" /></td>
        </tr>
      </table>
      <span>
        <input type="submit" value="회원가입" id="join_bt" />
      </span>
    </form>
  </div>
</body>
</html>
