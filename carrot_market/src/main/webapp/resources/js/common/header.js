document.getElementById('search').addEventListener('keypress', search);

function search(e) {
    const code = e.code;
    var input = document.getElementById("search");
    var form = document.getElementById("")
    if (code == 'Enter') {
        // 검색어 처리 로직
        console.log(input.value);
        location.href = contextPath + '/article/search/' + input.value;
    }
}