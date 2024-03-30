var currentURL = window.location.search;

if(currentURL == "?status=Active") {
    document.getElementById('active').style.fontWeight = 'bold';
    document.getElementById('sold').style.fontWeight = 'normal';
    document.getElementById('hide').style.fontWeight = 'normal';
} else if(currentURL == "?status=Sold") {
    document.getElementById('sold').style.fontWeight = 'bold';
    document.getElementById('active').style.fontWeight = 'normal';
    document.getElementById('hide').style.fontWeight = 'normal';
} else if(currentURL == "?status=Hide") {
    document.getElementById('hide').style.fontWeight = 'bold';
    document.getElementById('active').style.fontWeight = 'normal';
    document.getElementById('sold').style.fontWeight = 'normal';
}

function openModal(clickedElement, productId) {
    var modalContainer = document.getElementById('modalContainer' + productId);

    // 모달 및 모달 컨테이너 표시
    modalContainer.style.display = 'flex';

    // 스크롤 잠금 해제
    document.body.style.overflow = 'hidden';

    // 모달 외부를 클릭하면 모달 닫기
    modalContainer.addEventListener('click', function(event){
    	closeModal(event, productId);
    });
}

function closeModal(event, productId) {
    var modalContainer = document.getElementById('modalContainer' + productId);
    // 모달 외부를 클릭한 경우에만 모달 닫기
    if (event.target === modalContainer) {
        // 스크롤 잠금 해제
        document.body.style.overflow = 'auto';
        modalContainer.style.display = 'none';
    }
}

function updateHidden(isHidden, productId) {
    $.ajax({
        url: contextPath + '/article/updateHidden/' + productId,
        type: 'POST',
        headers: {
             "content-Type": 'application/json; charset=UTF-8'
        },
        data: JSON.stringify({
            hide: isHidden
        }),
        success: function(response) {
            var result = response.result;
            var hidden = response.hidden;

            if(result == 'true') {
                if(hidden == 'show') {
                    alert('숨김 해제했습니다.');
                } else {
                    alert('상품을 숨겼습니다.');
                }
            } else {
                alert('상태 변경에 실패했습니다.');
            }
            location.reload();
        },
        error: function(error) {
            
        }
    });
}

function updateStat(element, status, productId) {
    if(status == 'Active') {
        $.ajax({
            url: contextPath + '/article/updateStat/' + productId,
            type: 'POST',
            headers: {
                "content-Type": 'application/json; charset=UTF-8'
            },
            data: JSON.stringify({
                    status: status,
                    buyerId: ''
            }),
            success: function(response) {
                var result = response.result;
                var buyerId = response.buyerId;

                if(result == 'true') {
                    if(status == 'Active') {
                        alert('판매중으로 변경되었습니다.');
                    }
                } else {
                    alert('상태 변경에 실패했습니다.');
                }
                location.reload();
            },
            error: function(error) {
            
            }
        });
    } else {
        element.href = contextPath + '/article/updateStat/' + productId + '/selectBuyer?status=' + status + '&pre=salesHistory';
        window.location.href = element.href;
    }
}

function deleteArticle(productId) {
    $.ajax({
        url: contextPath + '/article/delete/' + productId,
        type: 'DELETE',
        headers: {
             "content-Type": 'application/json; charset=UTF-8'
        },
        success: function(response) {
            var result = response.result;
            var msg = response.msg;

            if(result == 'true') {
                alert('게시글이 삭제되었습니다.');
            } else {
                if(msg == 'not valid') {
                    alert('잘못된 접근입니다!');
                } else {
                    alert('삭제에 실패했습니다.');
                }
            }
            location.reload();
        },
        error: function(error) {
            
        }
    });
}