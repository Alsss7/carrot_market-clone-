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