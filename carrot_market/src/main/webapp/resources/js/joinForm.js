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

$(function() {
	$("#find_region").click(function() {
		getRegion();
	})
	
	$("#checkId").click(function() {
		checkId();
	})
	
	$("#checkNickname").click(function() {
		checkNickname();
	})
});

var idChecked = false;
var nickChecked = false;

function checkId() {
    var id = document.getElementById("id").value;
    console.log(id);

    $.ajax({
        url: contextPath + "/member/checkId?id=" + id,
        type: "GET",
        success: function(response) {
        	var isAvailable = response.idAvailable;
			console.log(isAvailable);
			if(isAvailable) {
				alert('사용 가능한 아이디입니다.');
				$("#id").css({
					"border": "1px solid blue"
				})
				idChecked = true;
				//$("#unavailableId").text("");
				//$("#availableId").text("사용 가능한 아이디입니다.");
			} else {
				alert('이미 사용 중인 아이디입니다.');
				$("#id").css({
					"border": "1px solid red"
				})
				idChecked = false;
				//$("#availableId").text("");
				//$("#unavailableId").text("사용 중인 아이디입니다.");
			}
        },
        error: function(error) {
        	
        }
    })
}

function checkNickname() {
	var nickname = document.getElementById("nickname").value;
    console.log(nickname);
	
	$.ajax({
		url: contextPath + "/member/checkNickname?nickname=" + nickname,
		type: "GET",
		success: function(response) {
			var isAvailable = response.nicknameAvailable;
			console.log(isAvailable);
			if(isAvailable) {
				alert('사용 가능한 닉네임입니다.');
				$("#nickname").css({
					"border": "1px solid blue"
				})
				nickChecked = true;
				//$("#unavailableNickname").text("");
				//$("#availableNickname").text("사용 가능한 닉네임입니다.");
			} else {
				alert('이미 사용 중인 닉네임입니다.');
				$("#nickname").css({
					"border": "1px solid red"
				})
				nickChecked = false;
				//$("#availableNickname").text("");
				//$("#unavailableNickname").text("사용 중인 닉네임입니다.");
			}
		},
		error: function(error) {
			
		}
	})
}

var joinButton = document.getElementById("join_bt");

if (joinButton) {
    joinButton.onclick = function() {
        if (idChecked && nickChecked) {
            document.getElementById("joinForm").submit();
        } else {
            alert("아이디, 닉네임 중복 확인을 해주세요!");
        }
    };
} else {
    console.error("join_bt 엘리먼트를 찾을 수 없습니다.");
}