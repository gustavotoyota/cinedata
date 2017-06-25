$(document).ready(function () {
    getImage("Guardiões da Galáxia Vol. 2", false, 300, function (image) {
        $("#movie-img").css("background-image", "url('" + image + "')");
    });
});