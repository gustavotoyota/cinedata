<%@page contentType="application/json" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="Model.MovieSearchBean" %>
<%@page import="Model.MovieResult" %>

<% MovieSearchBean bean = (MovieSearchBean)request.getAttribute("bean"); %>
<% List<MovieResult> movies = bean.getMovies(); %>
{
    "movies": [
        <% for (int i = 0; i < movies.size(); ++i) { %>
        {
            <% MovieResult movie = movies.get(i); %>
            "id": "<%= movie.getId() %>",
            "title": "<%= movie.getTitle() %>",
            "director": "<%= movie.getDirector()%>",
            "languages": "<%= movie.getLanguage()%>"            
        }<% if (i != movies.size() - 1) { %>,<% } %>
        <% } %>
    ],    
    "numPages": <%= bean.getNumPages() %>,
    "pageIndex": <%= bean.getPageIndex()%>
}