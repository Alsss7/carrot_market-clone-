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
var dateSpan = document.getElementById('sent-at');
dateSpan.innerHTML = formattedtimeDiff;

function submit() {
    document.getElementById('chatting').submit();
}