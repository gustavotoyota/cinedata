$(document).ready(function () {
    getImage("James Gunn", true, 300, function (image) {
        $("#director-img").css("background-image", "url('" + image + "')");
    });
});