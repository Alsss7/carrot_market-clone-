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

var fileCount = 1;

function addFile() {
    var imageContainer = document.getElementById('input-file-preview');
    var inputFile = document.createElement('input');
    inputFile.type = 'file';
    inputFile.name = 'files';
    inputFile.id = 'fileInput' + fileCount;
    inputFile.style.display = 'none';
    fileCount++;

    var inputLabel = document.createElement('label');
    inputLabel.htmlFor = inputFile.id;
    inputLabel.classList.add('input-image');
    inputLabel.textContent = '선택';

    var inputAndPreview =  document.createElement('div');
    inputAndPreview.classList.add('input-and-preview');
    inputAndPreview.appendChild(inputFile);
    inputAndPreview.appendChild(inputLabel);

    inputFile.addEventListener('change', function(event) {
        handleFileSelect(event, inputFile, inputLabel, inputAndPreview);
    });

    imageContainer.appendChild(inputAndPreview);
}

function handleFileSelect(event, inputFile, inputLabel, inputAndPreview) {
    var file = event.target.files[0];
    var reader = new FileReader();
    reader.onload = function(e) {
        var previewImage = document.createElement('div');
        previewImage.classList.add('preview-image');

        var imageElement = document.createElement('img');
        imageElement.src = e.target.result;
        
        var deleteButton = document.createElement('a');
        deleteButton.classList.add('delete-button');
        deleteButton.innerHTML = 'X';

        deleteButton.addEventListener('click', function() {
            inputFile.value = null;
            var parentNode = previewImage.parentNode;
            if (parentNode) {
                parentNode.removeChild(previewImage);
            }
            // inputAndPreview에서 inputFile을 삭제합니다.
            inputAndPreview.removeChild(inputFile);
            inputAndPreview.removeChild(inputLabel);
        });

        previewImage.appendChild(imageElement);
        previewImage.appendChild(deleteButton);

        inputAndPreview.insertBefore(previewImage, inputAndPreview.firstChild);
    }
    reader.readAsDataURL(file);
}