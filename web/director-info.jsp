<%@page import="java.util.List"%>
<%@page import="Model.DirectorInfoBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% DirectorInfoBean bean = (DirectorInfoBean)request.getAttribute("bean"); %>

<!DOCTYPE html>
<html>
    <head>
        <title>CineData - Detalhes do diretor</title>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <link rel="stylesheet" type="text/css" href="style/base.css">
        <link rel="stylesheet" type="text/css" href="style/director-info.css">
        
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
                            <span class="info-caption">ID do Diretor</span><br/>
                            <span class="info-value"><%= bean.getId() %></span>
                        </div>
                        <div class="info">
                            <span class="info-caption">Nome do Diretor</span><br/>
                            <span class="director-name"><%= bean.getName()%></span>
                        </div>
                        <div class="info">
                            <span class="info-caption">Número de Gêneros</span><br/>
                            <span class="info-value"><%= bean.getGenres().size() %></span>
                        </div>
                        <div class="info">
                            <span class="info-caption">Filme(s)</span><br/>
                            <span class="info-value"><%= bean.getNumMovies()%></span>
                        </div>
                        <div class="info">
                            <span class="info-caption">Gêneros</span><br/>
                            <% for (int i = 0; i < bean.getGenres().size(); ++i) { %>
                            <div class="item">
                                <div class="item-value"><%= bean.getGenres().get(i) %></div>
                                <div class="item-desc"><%= bean.getGenreMovies().get(i) %></div>
                            </div>
                            <% } %>                            
                        </div>
                    </section>
                    <section class="right-bar">
                        <div id="director-img" class="director-img"></div>
                        <input class="return" type="button" value="Retornar aos resultados" onclick="window.history.back()"/>
                    </section>
                </div>
            </div>
        </div>
        
        <script>
            $(document).ready(function () {
                getImage("<%= bean.getName() %>", true, 300, function (image) {
                    $("#director-img").css("background-image", "url('" + image + "')");
                });
            });
        </script>
    </body>
</html>