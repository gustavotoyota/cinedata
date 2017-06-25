var nextGenre = 2;
var searchParams = "";

$(document).ready(function () {
    if ($("#search-form input[type=text]").filter(function () {
        return $(this).val().length != 0;
    }).length > 0)
        searchDirectors();
});

window.onpopstate = function(event) {
    if (event.state) {
        $("#results").html(event.state.results);
        $("#pages").html(event.state.pages);
    }
};

function delGenre(id) {
    if ($("[name=genres]").length == 5)
        $("#add-panel").css("display", "flex");

    $("#genre" + id).remove();
    
    if ($("[name=genres]").length == 1)
        $(".del-btn").css("display", "none");
}

function addGenre() {
    if ($("[name=genres]").length == 5)
        return;

    if ($("[name=genres]").length == 1)
        $(".del-btn").css("display", "block");
    
    $("#genres").append(
        "<div id='genre" + nextGenre + "' class='item-group'>" +
            "<input name='genres' type='text'/>" +
            "<input class='del-btn' type='button' value='-' onclick='delGenre(" + nextGenre++ + ")'/>" +
        "</div>"
    );
    
    if ($("[name=genres]").length == 5)
        $("#add-panel").css("display", "none");
}

function addDirector(director) {
    $("#results").append(
        "<div class='director'>" +
            "<div class='director-img'></div>" +
            "<div class='director-info'>" +
                "<span class='bold-info'>" + director.rank + "</span><br/>" +
                "<span class='bold-info'>" + director.name + "</span><br/>" +
                "<span class='normal-info'>Num. Filmes: " + director.numMovies + "</span><br/>" +
                "<span class='normal-info'>Num. Gêneros: " + director.numGenres + "</span><br/>" +
                "<span class='normal-info'>" + director.genres + "</span>" +
            "</div>" +
        "</div>"
    );
    
    var directorImg = $(".director-img:last");
    
    getImage(director.name, true, 185, function (image) {
        directorImg.css("background-image", "url('" + image + "')")
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
            for (var director in data.directors)
                addDirector(director);
            createPages(data.numPages, data.pageIndex);
        }
    );
    
    window.history.pushState({"results": $("#results").html(), "pages": $("#pages").html()}, "", window.location.href.split('?')[0] + searchParams);
}

function searchDirectors() {
    searchParams = $("#search-form").serialize();
    openPage(1);
}