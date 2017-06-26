<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%!
public String getParam(HttpServletRequest request, String name, String defValue) {
    String param = request.getParameter(name);
    if (param == null)
        return defValue;
    return param;
} 
%>

<!DOCTYPE html>
<html>
    <head>
        <title>CineData - Pesquisa de diretores</title>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <link rel="stylesheet" type="text/css" href="style/base.css">
        <link rel="stylesheet" type="text/css" href="style/director-search.css">
        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="script/main.js"></script>
        <script src="script/director-search.js"></script>
    </head>
    <body>
        <div class="wrapper">
            <div class="window">
                <div class="frame">
                    <header class="top-bar" onclick="location.href='index.jsp'"><img src="img/logo.png"/></header>
                    <section class="left-bar">
                        <form id="search-form" action="director-ajax">
                            <div class="input-group">
                                <label>Número de Gêneros</label>
                                <div style="display: flex">
                                    <select name="compop" style="width: 140px">
                                        <option value="<">Menor que</option>
                                        <option value="<=">Menor ou igual a</option>
                                        <option value="=">Igual a</option>
                                        <option value=">=">Maior ou igual a</option>
                                        <option value=">">Maior que</option>
                                    </select>
                                    <input name="compqt" style="margin-left: 29px" type="text"/>
                                </div>
                            </div>
                            <div class="input-group">
                                <label>Gêneros</label>
                                <span>Dentre os gêneros, devem constar:</span><br/>
                                <div id="genres">
                                    <div id="genre1" class="item-group">
                                        <input name="genres" type="text"/>
                                        <input class="del-btn" type="button" value="-" style="display: none" onclick="delGenre(1)"/>
                                    </div>
                                </div>
                                <div id="add-panel" class="item-group">
                                    <input class="add-btn" type="button" value="+" onclick="addGenre()"/>
                                    <span>Adicionar mais um gênero na pesquisa</span>
                                </div>
                            </div>
                            <div class="input-group">
                                <label>Ordenação</label><br/>
                                <input name="order" type="radio" value="asc"/>
                                <span style="font-size: 18px">Crescente</span><br/>
                                <input name="order" type="radio" value="desc"/>
                                <span style="font-size: 18px">Decrescente</span><br/>
                            </div>
                            <div class="bottom-group">
                                <input class="search-btn" type="button" value="Construir ranking" onclick="searchStart()"/>
                            </div>
                            <input type="hidden" name="page" value="1"/>
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
            $("[name=compop]").val("<%= getParam(request, "compop", "<") %>");
            
            $("[name=compqt]").val("<%= getParam(request, "compqt", "") %>");
            
            <% String[] genres = request.getParameterValues("genres"); %>
            <% if (genres != null) { %>
                <% for (int i = 0; i < genres.length; ++i) { %>
                    <% if (i != 0) { %>
                        addGenre();
                    <% } %>
                    $("[name=genres]:last").val("<%= genres[i] %>");
                <% } %>
            <% } %>
                
            $("[name=order][value=<%= getParam(request, "order", "asc") %>]").prop("checked", true);

            if ($("#search-form input[type=text]").filter(function () {
                return $(this).val().length !== 0;
            }).length > 0)
                searchPage("<%= getParam(request, "page", "1") %>");
        </script>
    </body>
</html>