function submit(buyerId, status, productId) {
    $.ajax({
        url: contextPath + '/article/updateStat/' + productId,
        type: 'POST',
        headers: {
            "content-Type": 'application/json; charset=UTF-8'
        },
        data: JSON.stringify({
                status: status,
                buyerId: buyerId
        }),
		success: function(response) {
            var result = response.result;
            var buyerId = response.buyerId;
            var tradeId = response.tradeId;

            if(result == 'true') {
                if(status == 'Active') {
                    alert('판매중으로 변경되었습니다.');
                } else if(status == 'Booking') {
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
            console.log(error);
		}
    });
}

function toReview(tradeId) {
    if(confirm('거래 후기를 남기시겠습니까?')) {
        window.location.href = contextPath + '/review/' + tradeId + '?preUri=' + preUri;
    } else {
        if(preUri == 'salesHistory') {
            window.location.href = contextPath + '/member/myPage/salesHistory?status=' + preStatus;
        } else {
            window.location.href = contextPath + '/article/' + productId;
        }
    }
}