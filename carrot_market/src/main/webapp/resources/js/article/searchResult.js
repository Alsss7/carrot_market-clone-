$('document').ready(function() {
    articleCount = parseInt(articleCount);
    if(allCount <= articleCount) {
        document.getElementById('moreItemButton').style.display = 'none';
        document.getElementById('resultContainer').style.marginBottom = '50px';
        document.getElementById('resultContainer').style.borderRadius = '10px';
    }
});

if(location.hash) {
    var data = history.state.data;
    if(data) {
        appendItemList(data);
    }
}

if(region != null) {
    document.getElementById('moreItemButton').addEventListener('click', function() {
        $.ajax({
            url: contextPath + '/article/search/getMoreArticle/' + search + '/' + articleCount + '/' + region,
            type: 'GET',
            success : function(response) {
                appendItemList(response);
                history.replaceState({data: response}, '', contextPath + '/article/search/' + search + '##');
            },
            error : function(error) {
                console.log(error);
            }
        });
    });
} else {
    document.getElementById('moreItemButton').addEventListener('click', function() {
        $.ajax({
            url: contextPath + '/article/search/getMoreArticle/' + search + '/'  + articleCount,
            type: 'GET',
            success : function(response) {
                appendItemList(response);
                history.replaceState({data: response}, '', contextPath + '/article/search/' + search + '##');
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

        var regionElement = document.createElement('div');
        regionElement.classList.add('region');
        regionElement.innerHTML = element.region;

        var priceLikeCountElement = document.createElement('div');
        priceLikeCountElement.classList.add('price-likeCount');

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

        var likeCountElement = document.createElement('div');
        var likeIcon = document.createElement('span');
        likeIcon.classList.add('like-icon');
        likeIcon.innerHTML = '♡';
        var likeCount = document.createElement('span');
        likeCount.classList.add('like-count');
        likeCount.innerHTML = element.likeCount;
        likeCountElement.appendChild(likeIcon);
        likeCountElement.appendChild(likeCount);

        priceLikeCountElement.appendChild(statusPriceElement);
        priceLikeCountElement.appendChild(likeCountElement);

        
        aElement.appendChild(imgElement);
        aElement.appendChild(titleElement);
        aElement.appendChild(regionElement);
        aElement.appendChild(priceLikeCountElement);
        
        item.appendChild(aElement);

        itemList.append(item);
    });
    
    articleCount = parseInt(articleCount);
    articleCount += 6;
    if(allCount <= articleCount) {
        document.getElementById('moreItemButton').style.display = 'none';
        document.getElementById('resultContainer').style.marginBottom = '50px';
        document.getElementById('resultContainer').style.borderRadius = '10px';
    }
}