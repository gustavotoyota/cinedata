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
        "<div class='director' onclick='location.href=\"director-info?id=" + director.id + "\"'>" +
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
            for (var i in data.directors)
                addDirector(data.directors[i]);
            createPages(data.numPages, data.pageIndex);
        }
    );
    
    window.history.pushState({"results": $("#results").html(), "pages": $("#pages").html()}, "", window.location.href.split('?')[0] + "?" + searchParams);
}

function searchDirectors() {
    searchParams = $("#search-form").serialize();
    openPage(1);
}