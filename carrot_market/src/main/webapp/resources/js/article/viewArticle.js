const slidesContainer = document.getElementById('slides');
const slides = document.querySelectorAll('.slide');
const prevBtn = document.getElementById('prevBtn');
const nextBtn = document.getElementById('nextBtn');
let slideIndex = 0;

function showSlide(index) {
	slides.forEach((slide, i) => {
    	slide.classList.toggle('active', i === index);    
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


var statusFormElement = document.getElementById('statusForm');
var statusSelectElement = document.getElementById('status-select');
statusSelectElement.value = productStatus;

function submitForm() {
    console.log(statusFormElement.action);
    statusFormElement.submit();
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