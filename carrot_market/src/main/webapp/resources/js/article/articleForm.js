function tradeStyleChange(e) {
    var price = document.getElementById("price");
    var won = document.getElementById("won");
    if(e.value == 'sell') {
        price.value = "";
        price.disabled = false;
        won.hidden = false;
    } else if(e.value == 'share') {
        price.value = "나눔";
        price.disabled = true;
        won.hidden = true;
    }
}

function numberFormat(e) {
    e.value = e.value.replace(/[^0-9]/gi, "");

    var number = e.value.replace(/,/g, '');

    var formattedNumber = new Intl.NumberFormat().format(number);

    e.value = formattedNumber;
}

function removeCommas(e) {
    e.value = e.value.replace(/,/g, '');
}

function validateForm() {
    var title = document.getElementById("title").value;
    var category = document.getElementById("category").value;
    var price = document.getElementById("price").value;
    var description = document.getElementById("description").value;
    var place = document.getElementById("place").value;

    if(title == '') {
        alert("제목을 입력하세요");
        return false;
    }

    if(category == '') {
        alert("카테고리를 선택하세요");
        return false;
    }

    if(price == '') {
        alert("가격을 입력하세요");
        return false;
    }

    if(description == '') {
        alert("설명을 입력하세요");
        return false;
    }

    if(place == '') {
        alert("거래 희망 장소를 입력하세요");
        return false;
    }
    
    return true;
}

document.getElementById("image-input").addEventListener("change", function(event) {
    var previewImageDiv = document.getElementById("preview-image");
    previewImageDiv.innerHTML = "";

    var files = event.target.files;
    for(var i = 0; i < files.length; i++) {
        var file = files[i];

        var reader = new FileReader();
        reader.onload = function(e) {
            var imageElement = document.createElement("img");
            imageElement.src = e.target.result;
            previewImageDiv.appendChild(imageElement);
        };
        reader.readAsDataURL(file);
    }
});