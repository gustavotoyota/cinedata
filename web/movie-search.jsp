<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%!
public String getParam(HttpServletRequest request, String name) {
    String param = request.getParameter(name);
    if (param == null)
        return "";
    return param;
} 
%>

<!DOCTYPE html>
<html>
    <head>
        <title>CineData - Pesquisa de filmes</title>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <link rel="stylesheet" type="text/css" href="style/base.css">
        <link rel="stylesheet" type="text/css" href="style/movie-search.css">
        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="script/main.js"></script>
        <script src="script/movie-search.js"></script>
    </head>
    <body>
        <div class="wrapper">
            <div class="window">
                <div class="frame">
                    <header class="top-bar" onclick="location.href='index.jsp'"><img src="img/logo.png"/></header>
                    <section class="left-bar">
                        <form id="search-form" action="movie-ajax">
                            <div class="input-group">
                                <label for="title">Filme</label><br/>
                                <div class="stretch"><input id="title" name="title" type="text" value="<%= getParam(request, "title") %>"/></div>
                            </div>
                            <div class="input-group">
                                <label>Diretor</label><br/>
                                <div id="directors">
                                    <div id="director1" class="item-group">
                                        <input name="directors" type="text"/>
                                        <input class="del-btn" type="button" value="-" style="display: none" onclick="delDirector(1)"/>
                                    </div>
                                </div>
                                <div id="add-panel" class="item-group">
                                    <input class="add-btn" type="button" value="+" onclick="addDirector()"/>
                                    <span>Adicionar mais um diretor à pesquisa</span>
                                </div>
                            </div>
                            <div class="input-group">
                                <label for="language">Idioma</label><br/>
                                <div class="stretch"><input id="language" name="language" type="text" value="<%= getParam(request, "language") %>"/></div>
                            </div>
                            <div class="bottom-group">
                                <input name="exact" type="checkbox" value="on"/><span>A busca deve obedecer a todos os campos acima</span><br/>
                                <input class="search-btn" type="button" value="Pesquisar" onclick="searchMovies()"/>
                            </div>
                        </form>
                    </section>
                    <section class="right-bar">
                        <label>Resultados</label>
                        <div id="results" class="results"></div>
                        <div id="pages" class="pages">
                            <input class="page" type="button" value="1"/>
                        </div>
                    </section>
                </div>
            </div>
        </div>
                            
        <script>
            <% String[] directors = request.getParameterValues("directors"); %>
            <% if (directors != null) { %>
                <% for (int i = 0; i < directors.length; ++i) { %>
                    <% if (i != 0) { %>
                        addDirector();
                    <% } %>
                    $("[name=director]:last").val("<%= directors[i] %>");
                <% } %>
            <% } %>
        </script>
    </body>
</html>