var currentURL = window.location.search;

if(currentURL == "?status=Active") {
    document.getElementById('active').style.fontWeight = 'bold';
    document.getElementById('sold').style.fontWeight = 'normal';
} else if(currentURL == "?status=Sold") {
    document.getElementById('sold').style.fontWeight = 'bold';
    document.getElementById('active').style.fontWeight = 'normal';
}
