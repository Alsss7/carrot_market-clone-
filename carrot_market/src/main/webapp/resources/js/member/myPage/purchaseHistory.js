function sendReview(productId) {
    $.ajax({
        url: contextPath + '/trade/getTradeInfo/' + productId,
        type: 'GET',
        headers: {
            "content-Type": 'application/json; charset=UTF-8'
       },
       success: function(response) {
            var tradeId = response.tradeId;
            window.location.href = contextPath + '/review/' + tradeId;
       },
       error: function(error) {
            console.log(error);
       }
    });
}
