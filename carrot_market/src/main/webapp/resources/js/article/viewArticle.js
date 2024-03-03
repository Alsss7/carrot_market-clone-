
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