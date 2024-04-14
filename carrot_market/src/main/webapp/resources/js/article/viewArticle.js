const slidesContainer = document.getElementById('slides');
const slides = document.querySelectorAll('.slide');
const dots = document.querySelectorAll('.dot');

let slideIndex = 0;

function showSlide(index) {
	slides.forEach((slide, i) => {
    	slide.classList.toggle('active', i === index);    
   	});
    dots.forEach((dot, i) => {
        dot.classList.toggle('active', i === index);
    });
}

function nextSlide() {
	slideIndex = (slideIndex + 1) % slides.length;
    showSlide(slideIndex);
}

function prevSlide() {
	slideIndex = (slideIndex - 1 + slides.length) % slides.length;
    showSlide(slideIndex);
}

// 초기 슬라이드 표시
showSlide(slideIndex);


// 업로드 날짜 표시
function formattimeDiff(timeDiff) {
    if (timeDiff < 60000) {
        return Math.floor(timeDiff / 1000) + '초 전';
    } else if (timeDiff < 3600000) {
        return Math.floor(timeDiff / 60000) + '분 전';
    } else if (timeDiff < 86400000) {
        return Math.floor(timeDiff / 3600000) + '시간 전';
    } else if (timeDiff < 2592000000) {
        return Math.floor(timeDiff / 86400000) + '일 전';
    } else if (timeDiff < 31536000000) {
        return Math.floor(timeDiff / 2592000000) + '달 전';
    } else {
        return Math.floor(timeDiff / 31536000000) + '년 전';
    }
}

var formattedtimeDiff = formattimeDiff(timeDiff);
var dateSpan = document.getElementById('created-at');
dateSpan.innerHTML = formattedtimeDiff;


var statusSelectElement = document.getElementById('status-select');
if(statusSelectElement !== null) {
    statusSelectElement.value = productStatus;
}

function updateStatus() {
    var status = statusSelectElement.value;

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
        window.location.href = contextPath + '/article/updateStat/' + productId + '/selectBuyer?status=' + status + '&pre=viewArticle';
    }
}

function openModal(clickedElement) {
    var modalContainer = document.getElementById('modalContainer');

    // 모달 및 모달 컨테이너 표시
    modalContainer.style.display = 'flex';

    // 스크롤 잠금 해제
    document.body.style.overflow = 'hidden';

    // 모달 외부를 클릭하면 모달 닫기
    modalContainer.addEventListener('click', function(event){
    	closeModal(event);
    });
}

function closeModal(event) {
    var modalContainer = document.getElementById('modalContainer');
    // 모달 외부를 클릭한 경우에만 모달 닫기
    if (event.target === modalContainer) {
        // 스크롤 잠금 해제
        document.body.style.overflow = 'auto';
        modalContainer.style.display = 'none';
    }
}

function updateHidden(isHidden) {
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

function deleteArticle() {
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

$('document').ready(function() {
    var userManner = $('#user-manner');
    var progressValue = $('#progressValue');
    if(manner < 21.3) {
        userManner.css('color', '#898989');
        progressValue.css('width', manner + '%');
        progressValue.css('background-color', '#898989');
    } else if(manner < 33.3) {
        userManner.css('color', '#3F77B2');
        progressValue.css('width', manner + '%');
        progressValue.css('background-color', '#3F77B2');
    } else if(manner < 43.5) {
        userManner.css('color', '#5D9BE7');
        progressValue.css('width', manner + '%');
        progressValue.css('background-color', '#5D9BE7');
    } else if(manner < 58) {
        userManner.css('color', '#74C697');
        progressValue.css('width', manner + '%');
        progressValue.css('background-color', '#74C697');
    } else if(manner < 76.8) {
        userManner.css('color', '#EBBC6A');
        progressValue.css('width', manner + '%');
        progressValue.css('background-color', '#EBBC6A');
    } else {
        userManner.css('color', '#E27029');
        progressValue.css('width', manner + '%');
        progressValue.css('background-color', '#E27029');
    }
});