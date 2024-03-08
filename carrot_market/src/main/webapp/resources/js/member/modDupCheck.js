var storedNickname = document.getElementById("nickname").value;
var nickInput = document.getElementById("nickname");
var nickChecked = true;
document.getElementById("checkNickname").disabled = true;

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
	
nickInput.addEventListener("input", function() {
	if(storedNickname != nickInput.value) {
		document.getElementById("checkNickname").disabled = false;
		nickChecked = false;
	} else {
		document.getElementById("checkNickname").disabled = true;
		$("#nickname").css({
			"border-width": "0 0 1px",
			"border-color": "gray"
		})
		nickChecked = true;
	}
});

var modifyButton = document.getElementById("modify_bt");
modifyButton.onclick = function() {
	if(nickChecked) {
		document.getElementById("profileForm").submit();
	} else {
		alert("닉네임 중복 확인을 해주세요!");
	}
};