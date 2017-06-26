<%@page contentType="application/json" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="Model.DirectorResult"%>
<%@page import="Model.DirectorSearchBean"%>

<% DirectorSearchBean bean = (DirectorSearchBean)request.getAttribute("bean"); %>
<% List<DirectorResult> directors = bean.getDirectors(); %>
{
    "directors": [
        <% for (int i = 0; i < directors.size(); ++i) { %>
        {
            <% DirectorResult director = directors.get(i); %>
            "rank": "<%= director.getRank() %>",
            "id": "<%= director.getId() %>",
            "name": "<%= director.getName()%>",
            "numMovies": "<%= director.getNumMovies()%>",
            "numGenres": "<%= director.getNumGenres()%>",
            "genres": "<%= director.getGenres()%>"
        }<% if (i != directors.size() - 1) { %>,<% } %>
        <% } %>
    ],    
    "numPages": <%= bean.getNumPages() %>,
    "pageIndex": <%= bean.getPageIndex()%>
}