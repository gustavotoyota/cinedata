var nextDirector = 2;
var searchParams = "";

window.onpopstate = function(event) {
    if (event.state) {
        $("#results").html(event.state.results);
        $("#pages").html(event.state.pages);
    }
};

function delDirector(id) {
    if ($("[name=directors]").length == 5)
        $("#add-panel").css("display", "flex");

    $("#director" + id).remove();
    
    if ($("[name=directors]").length == 1)
        $(".del-btn").css("display", "none");
}

function addDirector() {
    if ($("[name=directors]").length == 5)
        return;

    if ($("[name=directors]").length == 1)
        $(".del-btn").css("display", "block");
    
    $("#directors").append(
        "<div id='director" + nextDirector + "' class='item-group'>" +
            "<input name='directors' type='text'/>" +
            "<input class='del-btn' type='button' value='-' onclick='delDirector(" + nextDirector++ + ")'/>" +
        "</div>"
    );
    
    if ($("[name=directors]").length == 5)
        $("#add-panel").css("display", "none");
}

function addMovie(movie) {
    $("#results").append(
        "<div class='movie'>" +
            "<div class='movie-img'></div>" +
            "<div class='movie-info'>" +
                "<span class='bold-info'>" + movie.title + "</span><br/>" +
                "<span class='normal-info'>" + movie.director + "</span><br/>" +
                "<span class='normal-info'>" + movie.languages + "</span>" +
            "</div>" +
        "</div>"
    );
    
    var movieImg = $(".movie-img:last");
    
    getImage(movie.title, false, 92, function (image) {
        movieImg.css("background-image", "url('" + image + "')")
    });
}

function createPages(numPages, pageIndex) {
    $("#pages").html("");
    for (var i = 1; i <= numPages; ++i)
        $("#pages").append(
            "<input class='page" + (i == pageIndex ? " current-page" : "") + "' type='button' value='" + i + "' onclick='openPage(" + i + ")'/> "
        );
}

function openPage(index) {
    $.getJSON(
        $("#search-form").attr("action"),
        searchParams,
        function (data) {
            $("#results").html("");
            for (var movie in data.movies)
                addMovie(movie);
            createPages(data.numPages, data.pageIndex);
        }
    );
    
    window.history.pushState({"results": $("#results").html(), "pages": $("#pages").html()}, "", window.location.href.split('?')[0] + searchParams);
}

function searchMovies() {
    searchParams = $("#search-form").serialize();
    openPage(1);
}