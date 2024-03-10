var region1 = document.getElementById('region1');
var region2 = document.getElementById('region2');


region1.addEventListener("change", function() {
    if(region1.value === "") {
        region2.disabled = true;
    } else {
        region2.disabled = false;
    }
});