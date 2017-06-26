function getImage(text, person, size, callback) {
    var textSize = text.indexOf(" {");
    if (textSize < 0)
        textSize = text.length;
    text = text.substr(0, textSize)
    
    var url = "https://api.themoviedb.org/3/search/" + (person ? "person" : "multi");
    var data = "api_key=0e97fc75d33aba58ae213d9218ab0499&query=" +
        text;
    
    $.getJSON(url, data,
        function (data) {
            if (data.total_results === 0)
                return;

            var file = data.results[0].poster_path || data.results[0].profile_path;
            callback("https://image.tmdb.org/t/p/w" + size + file);
        }
    );
}