var nextDirector = 2;
var searchParams = "";

$(document).ready(function () {
    if ($("#search-form input[type=text]").filter(function () {
        return $(this).val().length != 0;
    }).length > 0)
        searchMovies();
});

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
        "<div class='movie' onclick='location.href=\"movie-info?id=" + movie.id + "\"'>" +
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
    
    if (numPages === 0) {
        $("#pages").append(
            "<input class='page' type='button' value='1'/> "
        );
        return;
    }
        
    var begin = Math.max(1, pageIndex - 4 + Math.min(0, numPages - pageIndex - 5));
    var end = Math.min(numPages, pageIndex + 4 + Math.max(0, 5 - pageIndex));            
    
    if (pageIndex > 5)
        $("#pages").append(
            "<input class='page' type='button' value='...' onclick='openPage(" + (begin - 1) + ")'/> "
        );
    
    for (var i = begin; i <= end; ++i)
        $("#pages").append(
            "<input class='page" + (i == pageIndex ? " current-page" : "") + "' type='button' value='" + i + "' onclick='openPage(" + i + ")'/> "
        );

    if (pageIndex < numPages - 4)
        $("#pages").append(
            "<input class='page' type='button' value='...' onclick='openPage(" + (end + 1) + ")'/> "
        );
}

function openPage(index) {
    if (searchParams.indexOf("page") < 0)
        searchParams += "&page=";
    else
        searchParams = searchParams.substr(0, searchParams.lastIndexOf("=") + 1);
    searchParams += index;
    
    $.getJSON(
        $("#search-form").attr("action"),
        searchParams,
        function (data) {
            $("#results").html("");
            for (var i in data.movies)
                addMovie(data.movies[i]);
            createPages(data.numPages, data.pageIndex);
        }
    );
    
    window.history.pushState({"results": $("#results").html(), "pages": $("#pages").html()}, "", window.location.href.split('?')[0] + "?" + searchParams);
}

function searchMovies() {
    searchParams = $("#search-form").serialize();
    openPage(1);
}