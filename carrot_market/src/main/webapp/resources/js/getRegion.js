let hostIndex = location.href.indexOf( location.host ) + location.host.length;
let contextPath = location.href.substring( hostIndex, location.href.indexOf('/', hostIndex + 1) );

var lat;
var lon;

var options = {
	enableHighAccuracy : true,
	timeout : 5000,
	maximumAge : 0
};
	
function regionRequest() {
	$.ajax({
		url : 'https://dapi.kakao.com/v2/local/geo/coord2address.json?x='
				+ lon + '&y=' + lat,
		type : 'GET',
           dataType:"json",
		headers : {
			'Authorization' : 'KakaoAK 6756ca77ed4e62750b5d2bd424b2d90b'
		},
		success : function(data) {
			console.log(data);
			var region = data.documents[0].address;
			var region1 = region.region_1depth_name;
			var region2 = region.region_2depth_name;
			var region3 = region.region_3depth_name;
			console.log(region);
			$('#region1').val(region1 + " " + region2 + " " + region3);
		},
		error : function(e) {
			console.log(e);
		}
	});
};

function getPosition(options) {
    return new Promise((resolve, reject) => 
        navigator.geolocation.getCurrentPosition(resolve, reject, options)
    );
}

async function getRegion() {
	try {
	    const position = await getPosition(options);
	    console.log(position);
	    lat = position.coords.latitude;
	    lon = position.coords.longitude;
	    regionRequest();
	} catch (err) {
		console.warn('ERROR(' + err.code + '): ' + err.message);
		lat = 36.366701;
		lon = 127.344307;
		
		regionRequest();
	};
};

document.getElementById("find_region").onclick = function() {
	getRegion();
}