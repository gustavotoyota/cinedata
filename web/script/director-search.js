var nextGenre = 2;
var searchParams = "";

window.onpopstate = function(event) {
    if (event.state) {
        $("#results").html(event.state.results);
        $("#pages").html(event.state.pages);
    }
};

function delGenre(id) {
    if ($("[name=genres]").length === 5)
        $("#add-panel").css("display", "flex");

    $("#genre" + id).remove();
    
    if ($("[name=genres]").length === 1)
        $(".del-btn").css("display", "none");
}

function addGenre() {
    if ($("[name=genres]").length === 5)
        return;

    if ($("[name=genres]").length === 1)
        $(".del-btn").css("display", "block");
    
    $("#genres").append(
        "<div id='genre" + nextGenre + "' class='item-group'>" +
            "<input name='genres' type='text'/>" +
            "<input class='del-btn' type='button' value='-' onclick='delGenre(" + nextGenre++ + ")'/>" +
        "</div>"
    );
    
    if ($("[name=genres]").length === 5)
        $("#add-panel").css("display", "none");
}

function addDirector(director) {
    $("#results").append(
        "<div class='director' onclick='location.href=\"director-info?id=" + director.id + "\"'>" +
            "<div class='director-img'></div>" +
            "<div class='director-info'>" +
                "<div class='bold-info'>" + director.rank + "</div>" +
                "<div class='bold-info'>" + director.name + "</div>" +
                "<div class='normal-info'>Num. Filmes: " + director.numMovies + "</div>" +
                "<div class='normal-info'>Num. Gêneros: " + director.numGenres + "</div>" +
                "<div class='normal-info'>" + director.genres + "</div>" +
            "</div>" +
        "</div>"
    );
    
    var directorImg = $(".director-img:last");
    
    getImage(director.name, true, 185, function (image) {
        directorImg.css("background-image", "url('" + image + "')");
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
    
    if (begin > 1)
        $("#pages").append("<input class='page' type='button' value='1' onclick='openPage(1)'/> ");
    if (begin > 2)
        $("#pages").append("<input class='page' type='button' value='...' onclick='openPage(" + (begin - 1) + ")'/> ");
    
    for (var i = begin; i <= end; ++i)
        $("#pages").append("<input class='page" + (i === pageIndex ? " current-page" : "") + "' type='button' value='" + i + "' onclick='openPage(" + i + ")'/> ");

    if (end < numPages - 1)
        $("#pages").append("<input class='page' type='button' value='...' onclick='openPage(" + (end + 1) + ")'/> ");
    if (end < numPages)
        $("#pages").append("<input class='page' type='button' value='" + numPages + "' onclick='openPage(" + numPages + ")'/> ");
}

function openPage(index) {
    if (searchParams.indexOf("page") < 0)
        searchParams += "&page=";
    else
        searchParams = searchParams.substr(0, searchParams.lastIndexOf("=") + 1);
    searchParams += index;
    
    $.ajax({
        dataType: 'json',
        data: searchParams,
        type: 'post',
        url: $("#search-form").attr("action"),
        success: function (data) {
            $("#results").html("");
            for (var i in data.directors)
                addDirector(data.directors[i]);
            createPages(data.numPages, data.pageIndex);
    
            window.history.pushState({
                "results": $("#results").html(),
                "pages": $("#pages").html()},
                "", window.location.href.split('?')[0] + "?" + searchParams
            );
        }
    });
}

function searchPage(page) {
    searchParams = $("#search-form").serialize();
    openPage(page);
}

function searchStart() {
    searchPage(1);
}