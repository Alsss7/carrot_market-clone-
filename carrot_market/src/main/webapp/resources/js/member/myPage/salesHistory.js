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