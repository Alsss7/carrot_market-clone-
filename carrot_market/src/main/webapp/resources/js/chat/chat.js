//전송 버튼 누르는 이벤트
$("#send-message").on("click", function(e) {
	sendMessage();
	$('#input-message').val('');
});

var sock = new SockJS('http://localhost:8090/carrotMarket/chatting');
sock.onmessage = onMessage;
sock.onclose = onClose;
sock.onopen = onOpen;

function sendMessage() {
    var msg = {
        productId: productId,
        buyerId: buyerId,
        sellerId: sellerId,
        msg: $("#input-message").val()
    };
    var jsonData = JSON.stringify(msg);
	sock.send(jsonData);

    var div = document.createElement('div');
    div.classList.add('my-message-wrapper');

    var innerDiv = document.createElement('div');
    innerDiv.classList.add('my-message');
    innerDiv.innerHTML = $("#input-message").val();

    div.appendChild(innerDiv);

    document.getElementById('message-area').appendChild(div);
}
//서버에서 메시지를 받았을 때
function onMessage(msg) {
	
	var data = msg.data;
	var sessionId = null; //데이터를 보낸 사람
	var message = null;
	
	var arr = data.split(":");
	
	for(var i=0; i<arr.length; i++){
		console.log('arr[' + i + ']: ' + arr[i]);
	}
	
	var cur_session = loginId; //현재 세션에 로그인 한 사람
	console.log("cur_session : " + cur_session);
	
	sessionId = arr[0];
	message = arr[1];
	
    //로그인 한 클라이언트와 타 클라이언트를 분류하기 위함
	if(sessionId == cur_session){
        var div = document.createElement('div');
        div.classList.add('my-message-wrapper');

        var innerDiv = document.createElement('div');
        innerDiv.classList.add('my-message');
        innerDiv.innerHTML = message;

        div.appendChild(innerDiv);

        document.getElementById('message-area').appendChild(div);
	}
	else{
        var div = document.createElement('div');
        div.classList.add('your-message-wrapper');

        var innerDiv = document.createElement('div');
        innerDiv.classList.add('your-message');
        innerDiv.innerHTML = message;

        div.appendChild(innerDiv);

        document.getElementById('message-area').appendChild(div);
	}
	
}
//채팅창에서 나갔을 때
function onClose(evt) {

}

//채팅창에 들어왔을 때
function onOpen(evt) {

}