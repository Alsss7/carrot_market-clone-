var messageArea = document.getElementById('message-area');
messageArea.scrollTop = messageArea.scrollHeight;

//전송 버튼 누르는 이벤트
$("#send-message").on("click", function(e) {
	sendMessage();
	$('#input-message').val('');
});

$('#input-message').on('keypress', function(e) {
    if(e.keyCode === 13) {
        sendMessage();
        $('#input-message').val('');
    }
})

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

    var currentDate = new Date();
    var formattedDate = currentDate.toLocaleDateString('ko-KR', {year:'numeric', month: '2-digit', day: '2-digit', weekday: 'short' });
    var formattedTime = currentDate.toLocaleTimeString('ko-KR', { hour12: true, hour: '2-digit', minute: '2-digit' });

    
    var div = document.createElement('div');
    var timeDiv = document.createElement('div');
    timeDiv.classList.add('sent-time');
    timeDiv.innerHTML = formattedTime;
    var innerDiv = document.createElement('div');

    console.log(isFirstMessage);
    if(lastSentDate != formattedDate || isFirstMessage) {
        var sentDateDiv = document.createElement('div');
        div.classList.add('sent-date');
        document.getElementById('message-area').appendChild(sentDateDiv);
        lastSentDate = formattedDate;
    }
    //로그인 한 클라이언트와 타 클라이언트를 분류하기 위함
	if(sessionId == cur_session){
        div.classList.add('my-message-wrapper');

        innerDiv.classList.add('my-message');
        innerDiv.innerHTML = message;

        div.appendChild(timeDiv);
        div.appendChild(innerDiv);

	}
	else{
        div.classList.add('your-message-wrapper');

        innerDiv.classList.add('your-message');
        innerDiv.innerHTML = message;

        div.appendChild(innerDiv);
        div.appendChild(timeDiv);

	}
    document.getElementById('message-area').appendChild(div);
    messageArea.scrollTop = messageArea.scrollHeight;
	
}
//채팅창에서 나갔을 때
function onClose(evt) {

}

//채팅창에 들어왔을 때
function onOpen(evt) {

} 