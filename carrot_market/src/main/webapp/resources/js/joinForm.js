let hostIndex = location.href.indexOf( location.host ) + location.host.length;
let contextPath = location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );

var lat;
var lon;

var options = {
	enableHighAccuracy : true,
	timeout : 5000,
	maximumAge : 0
};
	
function regionRequest() {
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
};

function getPosition(options) {
    return new Promise((resolve, reject) => 
        navigator.geolocation.getCurrentPosition(resolve, reject, options)
    );
}

async function getRegion() {
	try {
	    const position = await getPosition(options);
	    console.log(position);
	    lat = position.coords.latitude;
	    lon = position.coords.longitude;
	    regionRequest();
	} catch (err) {
		console.warn('ERROR(' + err.code + '): ' + err.message);
		lat = 36.366701;
		lon = 127.344307;
		
		regionRequest();
	};
};

document.getElementById("find_region").onclick = function() {
	getRegion();
}

var idChecked = false;
var nickChecked = false;

document.getElementById("checkId").onclick = function() {
    var id = document.getElementById("id").value;

    $.ajax({
        url: contextPath + "/member/checkId",
        type: "POST",
		headers: {
			 "X-CSRF-TOKEN": csrfToken,
			 "content-Type": 'application/json; charset=UTF-8'
		},
		data: JSON.stringify({
			id: id
		}),
        success: function(response) {
        	var isAvailable = response.idAvailable;
        	var error_msg = response.id;
			if(isAvailable == 'true') {
				$('#id_error').text("");
				alert('사용 가능한 아이디입니다.');
				$("#id").css({
					"border": "1px solid blue"
				})
				idChecked = true;
			} else {
				if(error_msg != null) {
					$('#id_error').text(error_msg);
					idChecked = false;
				} else {
					$('#id_error').text("");
					alert('이미 사용 중인 아이디입니다.');
					$("#id").css({
						"border": "1px solid red"
					})
					idChecked = false;
				}
			}
        },
        error: function(error) {
        	
        }
    })
}

document.getElementById("checkNickname").onclick = function() {
	var nickname = document.getElementById("nickname").value;
	
	$.ajax({
		url: contextPath + "/member/checkNickname",
        type: "POST",
		headers: {
			 "X-CSRF-TOKEN": csrfToken,
			 "content-Type": 'application/json; charset=UTF-8'
		},
		data: JSON.stringify({
			nickname: nickname
		}),
		success: function(response) {
			var isAvailable = response.nicknameAvailable;
			var error_msg = response.nickname;
			if(isAvailable == 'true') {
				$('#nickname_error').text("");
				alert('사용 가능한 닉네임입니다.');
				$("#nickname").css({
					"border": "1px solid blue"
				})
				nickChecked = true;
			} else {
				if(error_msg != null) {
					$('#nickname_error').text(error_msg);
					nickChecked = false;
				} else {
					$('#nickname_error').text("");
					alert('이미 사용 중인 닉네임입니다.');
					$("#nickname").css({
						"border": "1px solid red"
					})
					nickChecked = false;
				}
			}
		},
		error: function(error) {
			
		}
	});
}

var joinButton = document.getElementById("join_bt");
joinButton.onclick = function() {
    if (idChecked && nickChecked) {
        document.getElementById("joinForm").submit();
    } else {
        alert("아이디, 닉네임 중복 확인을 해주세요!");
    }
};