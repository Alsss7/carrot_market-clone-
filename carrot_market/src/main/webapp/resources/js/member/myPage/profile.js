document.getElementById('previewImage').addEventListener('click', function() {
    document.getElementById('fileInput').click();
});

document.getElementById('fileInput').addEventListener('change', function(event) {
    var file = event.target.files[0];
    var reader = new FileReader();
    
    reader.onload = function(e) {
        var img = document.getElementById('previewImage');
        img.src = e.target.result;
        document.getElementById('deleteButton').style.display = 'flex';
    };
    var originImage = document.getElementById('originImage');
    if(originImage) {
        console.log('remove');
        originImage.parentNode.removeChild(originImage);
    }
    
    reader.readAsDataURL(file);
});

function removeImage(event) {
    event.stopPropagation();
    var originImage = document.getElementById('originImage');
    if(originImage) {
        console.log('remove');
        originImage.parentNode.removeChild(originImage);
    }

    var profileImage = document.getElementById('previewImage');
    profileImage.src = 'https://d1unjqcospf8gs.cloudfront.net/assets/users/default_profile_80-c649f052a34ebc4eee35048815d8e4f73061bf74552558bb70e07133f25524f9.png';
    document.getElementById('fileInput').value = '';
    document.getElementById('deleteButton').style.display = 'none';
}