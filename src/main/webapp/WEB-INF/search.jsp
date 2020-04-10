<%@ page import="ru.itpark.domain.Recipe" %>
<%@ page import="java.util.Collection" %><%--
  сделать красиво карточками рецепты. Сделать
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title><%=request.getAttribute("myrecipes")%></title>
</head>
<body>
<div class="container">
<form action="<%=request.getContextPath()%>/search">
    <input name = "q" placeholder="Поиск по названию" value=<%=request.getAttribute("findByName") == null ? "" : request.getAttribute("findByName")%>>
</form>


    <h4> <%=request.getAttribute("myrecipes")%></h4>
<ul class="list-group">
    <% for (Recipe item: (Collection<Recipe>) request.getAttribute("items")) { %>
<li class="list-group-item">
   <h5> <%=item.getName()%></h5>
    <p></p>
    <h6><%=item.getIngredients()%></h6>
<p></p>
    <%=item.getDescription()%>

</li>
<form  action="<%= request.getContextPath() %>/" method="post">
                <input type="hidden" name="id" value="<%= item.getId() %>">
                <input type="hidden" name="action" value="remove">
                <button>Удалить</button>
            </form>
            <form  action="<%= request.getContextPath() %>/" method="post">
                           <input type="hidden" name="id" value="<%= item.getId() %>">
                           <input type="hidden" name="action" value="edit">
                           <button>Редактировать</button>
                       </form>
    <% } %>
</ul>

    <% Recipe item = (Recipe) request.getAttribute("item"); %>
    <form action="<%= request.getContextPath() %>/" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="<%= item == null ? "" : item.getId() %>">
        <input type="hidden" name="action" value="save">
        <input name="name" placeholder="Название рецепта" value="<%= item == null ? "" : item.getName() %>">
        <input name="ingredients" placeholder="Состав" value="<%= item == null ? "" : item.getIngredients() %>">
        <input name="description" placeholder="Описание" value="<%= item == null ? "" : item.getDescription() %>">
<%--        <input type="file" name="file" accept="image/*">--%>
        <button>Сохранить</button>
    </form>




</div>
</body>
</html>
