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

    if(!isChatExists) {
        $.ajax({
            url: contextPath + '/chat/getMessageSize',
            type: 'POST',
            headers: {
                "content-Type": 'application/json; charset=UTF-8'
            },
            data: JSON.stringify({
                chatId : chatId
            }),
            success: function(response) {
                var messageSize = response.messageSize;
                if(messageSize != 0) {
                    isChatExists = true;
                }
            },
            error: function(error) {
    
            }
        });
    }
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
    var formattedDate = formatDate(currentDate);

    if(lastMsgDate != formattedDate) {
        var sentDateDiv = document.createElement('div');
        sentDateDiv.classList.add('sent-date');
        sentDateDiv.style.textAlign = 'center';
        sentDateDiv.innerHTML = showDate(currentDate);
        document.getElementById('message-area').appendChild(sentDateDiv);
        lastMsgDate = formattedDate;
    }
    
    var formattedTime = currentDate.toLocaleTimeString('ko-KR', { hour12: true, hour: '2-digit', minute: '2-digit' });
    
    var div = document.createElement('div');
    var timeDiv = document.createElement('div');
    timeDiv.classList.add('sent-time');
    timeDiv.innerHTML = formattedTime;
    var innerDiv = document.createElement('div');

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

var statusFormElement = document.getElementById('status-form');
var statusSelectElement = document.getElementById('status-select');
if(statusSelectElement !== null) {
    statusSelectElement.value = productStatus;
}

function formatDate(date) {
    var currentYear = date.getFullYear();
    var currentMonth = (date.getMonth() + 1).toString().padStart(2, '0');
    var currentDay = date.getDate().toString().padStart(2, '0');
    return currentYear + "-" + currentMonth + "-" + currentDay;
}

function showDate(date) {
    var currentYear = date.getFullYear();
    var currentMonth = (date.getMonth() + 1).toString().padStart(2, '0');
    var currentDate = date.getDate().toString().padStart(2, '0');
    var currentDay = getDateOfWeek(date);
    return currentYear + ". " + currentMonth + ". " + currentDate + ". " + currentDay;
}

function getDateOfWeek(date) {
    var dayOfWeek = date.getDay();
    var dayOfWeekStrings = ["일", "월", "화", "수", "목", "금", "토"];
    return "(" + dayOfWeekStrings[dayOfWeek] + ")";
}

$(document).ready(function() {
    $("#input-message").on("propertychange change keyup paste input", function() {
        var currentVal = $(this).val();
        if(currentVal.trim() !== '') {
            $("#send-message").prop('disabled', false);
        } else {
            $("#send-message").prop('disabled', true);
        }
    });
});

$(document).ready(function() {
    $('#status-select').change(function() {
        var selectedValue = $(this).val();

        $.ajax({
            url: contextPath + '/article/updateStat/' + productId,
            type: 'POST',
            headers: {
                 "content-Type": 'application/json; charset=UTF-8'
            },
            data: JSON.stringify({
                    status: selectedValue,
                    buyerId: buyerId
            }),
		    success: function(response) {
                var tradeId = response.tradeId;
                var result = response.result;
                var buyerId = response.buyerId;

                if(result == 'true') {
                    if(selectedValue == 'Active') {
                        alert('판매중으로 변경되었습니다.');
                    } else if(selectedValue == 'Booking') {
                        alert(buyerId + '님이 예약중입니다!');
                    } else {
                        alert(buyerId + '님과 거래완료 되었습니다!');
                        toReview(tradeId);
                    }
                } else {
                    alert('상태 변경에 실패했습니다.');
                }
		    },
		    error: function(error) {
                
		    }
        });
    });
});

$('document').ready(function() {
    $('#exit-button').click(function() {
        if(isChatExists && confirm('채팅방을 나가면 채팅 목록 및 대화 내용이 삭제되고 복구할 수 없어요. 채팅방에서 나가시겠어요?')){
            $.ajax({
                url: contextPath + '/chat/exit',
                type: 'POST',
                headers: {
                     "content-Type": 'application/json; charset=UTF-8'
                },
                data: JSON.stringify({
                    chatId: chatId
                }),
                success: function(response) {
                    var result = response.result;
    
                    if(result == 'true') {
                        alert('채팅방에서 나갔습니다.');
                        window.location.href = contextPath + '/article/' + productId;
                    } else {
                        alert('채팅방 나가기에 실패했습니다.');
                    }
                },
                error: function(error) {
                    
                }
            });
        } else {
            alert('아직 채팅방이 생성되지 않았습니다.');
        }
    });
});

function toReview(tradeId) {
    if(confirm('거래 후기를 남기시겠습니까?')) {
        window.location.href = contextPath + '/review/' + tradeId;
    }
}