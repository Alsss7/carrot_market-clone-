$('document').ready(function() {
    articleCount = parseInt(articleCount);
    if(allCount != 0 && allCount <= articleCount && loginId !== '') {
        document.getElementById('moreItemButton').style.display = 'none';
    }
});

if(location.hash) {
    var data = history.state.data;
    if(data) {
        appendItemList(data);
    }
}

if(parseInt(articleCount) > 0 && loginId !== '') {
    document.getElementById('moreItemButton').addEventListener('click', function() {
        $.ajax({
            url: contextPath + '/article/getMoreArticle/' + articleCount + '/' + region,
            type: 'GET',
            success : function(response) {
                appendItemList(response);
                history.replaceState({data: response}, '', contextPath + '/article/fleamarket##');
            },
            error : function(error) {
                console.log(error);
            }
        });
    });
}

function appendItemList(response) {
    response.forEach(function(element) {
        var itemList = document.getElementById('item-list');

        var item = document.createElement('div');
        item.classList.add('item');

        var aElement = document.createElement('a');
        aElement.href = contextPath + '/article/' + element.productId;

        var imgElement = document.createElement('img');
        if(element.filesName.length == 0) {
            imgElement.src = contextPath + '/resources/image/product_image/empty.png';
        } else {
            imgElement.src = contextPath + '/resources/image/product_image/' + element.productId + '/' + element.filesName[0];
        }

        var titleElement = document.createElement('div');
        titleElement.classList.add('article-title');
        titleElement.innerHTML = element.title;

        var statusPriceElement = document.createElement('div');

        var statusElement = document.createElement('span');
        statusElement.classList.add('status');
        if(element.status == 'Booking') {
            statusElement.innerHTML = '예약 중';
            statusElement.style.backgroundColor = 'green';
        } else if(element.status == 'Sold') {
            statusElement.innerHTML = '거래완료';
            statusElement.style.backgroundColor = 'black';
        }

        var priceElement = document.createElement('div');
        priceElement.classList.add('price');
        if(element.price == 0) {
            priceElement.innerHTML = '나눔';
        } else {
            var formatter = new Intl.NumberFormat('ko-KR', { style: 'decimal' });
            var formattedPrice = formatter.format(element.price);
            priceElement.innerHTML = formattedPrice + '원';
        }

        statusPriceElement.appendChild(statusElement);
        statusPriceElement.appendChild(priceElement);

        var regionElement = document.createElement('div');
        regionElement.classList.add('region');
        regionElement.innerHTML = element.region;

        var likeChatElement = document.createElement('div');
        likeChatElement.classList.add('like-and-chat');
        var likeElement = document.createElement('span');
        var chatElement = document.createElement('span');
        likeElement.innerHTML = '관심 ' + element.likeCount;
        chatElement.innerHTML = '채팅 ' + element.chatCount;
        
        likeChatElement.appendChild(likeElement);
        likeChatElement.append(' · ');
        likeChatElement.appendChild(chatElement);

        
        aElement.appendChild(imgElement);
        aElement.appendChild(titleElement);
        aElement.appendChild(statusPriceElement);
        aElement.appendChild(regionElement);
        aElement.appendChild(likeChatElement);
        
        item.appendChild(aElement);

        itemList.append(item);
    });
    
    articleCount = parseInt(articleCount);
    articleCount += 12;
    if(allCount != 0 && allCount <= articleCount && loginId !== '') {
        document.getElementById('moreItemButton').style.display = 'none';
    }
}