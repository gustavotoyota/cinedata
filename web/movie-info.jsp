<%@page import="java.util.List"%>
<%@page import="Model.MovieInfoBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% MovieInfoBean bean = (MovieInfoBean)request.getAttribute("bean"); %>

<!DOCTYPE html>
<html>
    <head>
        <title>CineData - Detalhes do filme</title>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <link rel="stylesheet" type="text/css" href="style/base.css">
        <link rel="stylesheet" type="text/css" href="style/movie-info.css">
        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="script/main.js"></script>
    </head>
    <body>
        <div class="wrapper">
            <div class="window">
                <div class="frame">
                    <header class="top-bar" onclick="location.href='index.jsp'"><img src="img/logo.png"/></header>
                    <section class="left-bar" style="overflow: auto">
                        <div class="info">
                            <span class="info-caption">ID do filme</span><br/>
                            <span class="info-value"><%= bean.getId() %></span>
                        </div>
                        <div class="info">
                            <span class="info-caption">Título</span><br/>
                            <span class="movie-title"><%= bean.getTitle()%></span>
                        </div>
                        <div class="info" style="float: left; margin-right: 32px">
                            <span class="info-caption">Ano</span><br/>
                            <span class="info-value"><%= bean.getYear()%></span>
                        </div>
                        <div class="info">
                            <span class="info-caption">Idioma(s)</span><br/>
                            <span class="info-value"><%= bean.getLanguages()%></span>
                        </div>
                        <div class="info" style="clear: both">
                            <span class="info-caption">Gênero(s)</span><br/>
                            <span class="info-value"><%= bean.getGenres()%></span>
                        </div>
                        <div class="info">
                            <span class="info-caption">Diretores</span><br/>
                            <% for (int i = 0; i < bean.getDirectorNames().size(); ++i) { %>
                            <div class="item">
                                <div class="item-value"><%= bean.getDirectorNames().get(i) %></div>
                                <div class="item-desc"><%= bean.getDirectorDescs().get(i) %></div>
                            </div>
                            <% } %>
                        </div>
                        <div class="info">
                            <span class="info-caption">Atores</span><br/>
                            <% for (int i = 0; i < bean.getActorNames().size(); ++i) { %>
                            <div class="item">                                                                
                                <div class="item-value"><%= bean.getActorNames().get(i) %></div>
                                <div class="item-desc"><%= bean.getActorDescs().get(i) %></div>
                            </div>                            
                            <% } %>
                        </div>
                    </section>
                    <section class="right-bar">
                        <div id="movie-img" class="movie-img"></div>
                        <input class="return" type="button" value="Retornar aos resultados" onclick="window.history.back()"/>
                    </section>
                </div>
            </div>
        </div>
    
        <script>
            $(document).ready(function () {
                getImage("<%= bean.getTitle() %>", false, 300, function (image) {
                    $("#movie-img").css("background-image", "url('" + image + "')");
                });
            });
        </script>
    </body>
</html>