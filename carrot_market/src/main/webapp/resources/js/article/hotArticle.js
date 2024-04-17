var region1 = document.getElementById('region1');
var region2 = document.getElementById('region2');

var pathname = window.location.pathname;
var path = pathname.split('/');

$('document').ready(function() {
    var districts = ["서울", "인천", "대전", "광주", "대구", 
                    "울산", "부산", "경기", "강원특별자치도", "충북", "충남", "전북특별자치도", 
                    "전남", "경북", "경남", "제주특별자치도"];
    for(var i = 0; i < districts.length; i++) {
        var option = document.createElement('option');
        option.text = districts[i];
        option.value = districts[i];
        region1.add(option);
    }

    if(path[4] !== '' && path.length == 5) {
        region1.value = decodeURIComponent(path[4]);
        region2.disabled = false;
        setRegion2();
    } else if(path.length == 6) {
        region1.value = decodeURIComponent(path[4]);
        setRegion2();
        region2.value = decodeURIComponent(path[5]);
        region2.disabled = false;
    }
});

region1.addEventListener("change", function() {
    setRegion2();
    location.href = contextPath + '/article/hotArticle/' + region1.value;
});

region2.addEventListener("change", function() {
    location.href = contextPath + '/article/hotArticle/' + region1.value + '/' + region2.value;
});

function setRegion2() {
    if(region1.value === "") {
        region2.value = "";
        region2.disabled = true;
    } else if(region1.value == "서울") {
        var seoulDistricts = ["강남구", "강동구", "강북구", "강서구", "관악구", "광진구", 
                            "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", 
                            "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구",
                            "중구", "중랑구"];
        for(var i = 0; i < seoulDistricts.length; i++) {
            var option = document.createElement('option');
            option.text = seoulDistricts[i];
            option.value = seoulDistricts[i];
            region2.add(option);
        }
    } else if(region1.value == "인천") {
        var inchoenDistricts = ["계양구", "남구", "남동구", "동구", "부평구", "서구", 
                            "연수구", "중구", "강화군", "옹진군"];
        for(var i = 0; i < inchoenDistricts.length; i++) {
            var option = document.createElement('option');
            option.text = inchoenDistricts[i];
            option.value = inchoenDistricts[i];
            region2.add(option);
        }
    } else if(region1.value == "대전") {
        var deajeonDistricts = ["대덕구", "동구", "서구", "유성구", "중구"];
        for(var i = 0; i < deajeonDistricts.length; i++) {
            var option = document.createElement('option');
            option.text = deajeonDistricts[i];
            option.value = deajeonDistricts[i];
            region2.add(option);
        }
    } else if(region1.value == "광주") {
        var gwangjuDistricts = ["광산구", "남구", "동구", "북구", "서구"];
        for(var i = 0; i < gwangjuDistricts.length; i++) {
            var option = document.createElement('option');
            option.text = gwangjuDistricts[i];
            option.value = gwangjuDistricts[i];
            region2.add(option);
        }
    } else if(region1.value == "대구") {
        var deaguDistricts = ["남구", "달서구", "동구", "북구", "서구", "수성구", "중구", "달성군"];
        for(var i = 0; i < deaguDistricts.length; i++) {
            var option = document.createElement('option');
            option.text = deaguDistricts[i];
            option.value = deaguDistricts[i];
            region2.add(option);
        }
    } else if(region1.value == "울산") {
        var ulsanDistricts = ["남구", "동구", "북구", "중구", "울주군"];
        for(var i = 0; i < ulsanDistricts.length; i++) {
            var option = document.createElement('option');
            option.text = ulsanDistricts[i];
            option.value = ulsanDistricts[i];
            region2.add(option);
        }
    } else if(region1.value == "부산") {
        var busanDistricts = ["강서구", "금정구", "남구", "동구", "동래구", "부산진구", "북구", "사상구", 
                            "사하구", "서구", "수영구", "연제구", "영도구", "중구", "해운대구", "기장군"];
        for(var i = 0; i < busanDistricts.length; i++) {
            var option = document.createElement('option');
            option.text = busanDistricts[i];
            option.value = busanDistricts[i];
            region2.add(option);
        }
    } else if(region1.value == "경기") {
        var gyeongiDistricts = ["고양시", "과천시", "광명시", "광주시", "구리시", "군포시", "김포시", "남양주시", 
                            "동두천시", "부천시", "성남시", "수원시", "시흥시", "안산시", "안성시", "안양시", 
                            "양주시", "오산시", "용인시", "의왕시", "의정부시", "이천시", "파주시", "평택시", 
                            "포천시", "하남시", "화성시", "가평군", "양평군", "여주군", "연천군"];
        for(var i = 0; i < gyeongiDistricts.length; i++) {
            var option = document.createElement('option');
            option.text = gyeongiDistricts[i];
            option.value = gyeongiDistricts[i];
            region2.add(option);
        }
    } else if(region1.value == "강원특별자치도") {
        var gangwonDistricts = ["강릉시", "동해시", "삼척시", "속초시", "원주시", "춘천시", "태백시", "고성군", 
                            "양구군", "양양군", "영월군", "인제군", "정선군", "철원군", "평창군", "홍천군", 
                            "화천군", "횡성군"];
        for(var i = 0; i < gangwonDistricts.length; i++) {
            var option = document.createElement('option');
            option.text = gangwonDistricts[i];
            option.value = gangwonDistricts[i];
            region2.add(option);
        }
    } else if(region1.value == "충북") {
        var chungbukDistricts = ["제천시", "청주시", "충주시", "괴산군", "단양군", "보은군", "영동군", "옥천군", 
                            "음성군", "증평군", "진천군", "청원군"];
        for(var i = 0; i < chungbukDistricts.length; i++) {
            var option = document.createElement('option');
            option.text = chungbukDistricts[i];
            option.value = chungbukDistricts[i];
            region2.add(option);
        }
    } else if(region1.value == "충남") {
        var chungnamDistricts = ["계룡시", "공주시", "논산시", "보령시", "서산시", "아산시", "천안시", "금산군", 
                                "당진군", "부여군", "서천군", "연기군", "예산군", "청양군", "태안군", "홍성군"];
        for(var i = 0; i < chungnamDistricts.length; i++) {
            var option = document.createElement('option');
            option.text = chungnamDistricts[i];
            option.value = chungnamDistricts[i];
            region2.add(option);
        }
    } else if(region1.value == "전북특별자치도") {
        var jeonbukDistricts = ["군산시", "김제시", "남원시", "익산시", "전주시", "정읍시", "고창군", "무주군", 
                                "부안군", "순창군", "완주군", "임실군", "장수군", "진안군"];
        for(var i = 0; i < jeonbukDistricts.length; i++) {
            var option = document.createElement('option');
            option.text = jeonbukDistricts[i];
            option.value = jeonbukDistricts[i];
            region2.add(option);
        }
    } else if(region1.value == "전남") {
        var jeonnamDistricts = ["광양시", "나주시", "목포시", "순천시", "여수시", "강진군", "고흥군", "곡성군", 
                            "구례군", "담양군", "무안군", "보성군", "신안군", "영광군", "영암군", "완도군", "장성군", 
                            "장흥군", "진도군", "함평군", "해남군", "화순군"];
        for(var i = 0; i < jeonnamDistricts.length; i++) {
            var option = document.createElement('option');
            option.text = jeonnamDistricts[i];
            option.value = jeonnamDistricts[i];
            region2.add(option);
        }
    } else if(region1.value == "경북") {
        var gyeongbukDistricts = ["경산시", "경주시", "구미시", "김천시", "문경시", "상주시", "안동시", "영주시", 
                                "영천시", "포항시", "고령군", "군위군", "봉화군", "성주군", "영덕군", "영양군", 
                                "예천군", "울릉군", "울진군", "의성군", "청도군", "청송군", "칠곡군"];
        for(var i = 0; i < gyeongbukDistricts.length; i++) {
            var option = document.createElement('option');
            option.text = gyeongbukDistricts[i];
            option.value = gyeongbukDistricts[i];
            region2.add(option);
        }
    } else if(region1.value == "경남") {
        var gyeongnamDistricts =  ["거제시", "김해시", "마산시", "밀양시", "사천시", "양산시", "진주시", "진해시", 
                                "창원시", "통영시", "거창군", "고성군", "남해군", "산청군", "의령군", "창녕군", 
                                "하동군", "함안군", "함양군", "합천군"];
        for(var i = 0; i < gyeongnamDistricts.length; i++) {
            var option = document.createElement('option');
            option.text = gyeongnamDistricts[i];
            option.value = gyeongnamDistricts[i];
            region2.add(option);
        }
    } else if(region1.value == "제주특별자치도") {
        var jejuDistricts =  ["서귀포시", "제주시", "남제주군", "북제주군"];
        for(var i = 0; i < jejuDistricts.length; i++) {
            var option = document.createElement('option');
            option.text = jejuDistricts[i];
            option.value = jejuDistricts[i];
            region2.add(option);
        }
    }
}