var radioButtons = document.querySelectorAll('input[type=radio][name="review"]');
var submitButton = document.getElementById('submitButton');

var selectedValue = null;
radioButtons.forEach(function(radioButton) {
    radioButton.addEventListener('change', function() {
        if(this.checked) {
            submitButton.disabled = false;
            submitButton.style.backgroundColor = '#EE7832';
            submitButton.style.cursor = 'pointer';
            selectedValue = this.value;
        }
    });
});

$('document').ready(function() {
    $('#submitButton').click(function() {
        $.ajax({
            url: contextPath + '/review/register',
            type: 'POST',
            headers: {
                "content-Type": 'application/json; charset=UTF-8'
            },
            data: JSON.stringify({
                tradeId : tradeId,
                productId : productId,
                reviewerId : reviewerId,
                review : selectedValue
            }),
            success: function(response) {
                var result = response.result;
                if(result == 'true') {
                    alert('거래 후기가 전달되었습니다.');
                    if(preUri == 'salesHistory') {
                        window.location.href = contextPath + '/member/myPage/salesHistory?status=Sold';
                    } else {
                        window.location.href = contextPath + '/article/' + productId;
                    }
                } else {
                    alert('거래 후기 전달에 실패했습니다.');
                }
            },
            error: function(error) {
                console.log(error);
            }
        });
    });
});