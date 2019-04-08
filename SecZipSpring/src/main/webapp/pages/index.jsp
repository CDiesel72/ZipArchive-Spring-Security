<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<html>
<head>
    <title>ZIP файлы пользователей</title>
</head>
<body>
<div align="center">
    <h1>Вы вошли как: ${login}. Ваш статус:</h1>
    <c:forEach var="s" items="${roles}">
        <h3><c:out value="${s}"/></h3>
    </c:forEach>
    <br/>

    <c:if test="${admin}">
        <c:url value="/admin" var="adminUrl"/>
        <p><a href="${adminUrl}">Нажмите</a> для перехода на страницу админа</p>
    </c:if>
    <br/>
    <button type="button" id="change_pass">Изменить пароль</button>
    <br/><br/>


    <table border="1">
        <caption style="font-size: larger; font-weight: bold">Список ZIP-архивов</caption>

        <tr>
            <th></th>
            <th>ID</th>
            <th>Имя ZIP-архива</th>
            <c:if test="${admin}">
                <th>Владелец</th>
            </c:if>
        </tr>

        <c:forEach items="${files}" var="file">
            <tr>
                <td>
                    <input type="checkbox" value="${file.getId()}"/>
                </td>
                <td>
                    <c:out value="${file.getId()}"/>
                </td>
                <td>
                    <a href="/file_get/${file.getId()}" download="${file.getFile().getName()}">
                        <c:out value="${file.getFile().getName()}"/>
                    </a>
                </td>
                <c:if test="${admin}">
                    <td>
                        <c:out value="${file.getCustomUser().getLogin()}"/>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
    <br/>

    <button type="button" id="delete_zip">Удалить архивы</button>
    <br/><br/>
    <h4>Добавить файлы в Zip-архив</h4>
    <form action="/add_file" enctype="multipart/form-data" method="POST">
        Ввeдите имя Zip-архива:<input type="text" name="name_z"><br/><br/>
        Добавить файл: <input type="file" name="file_d" enctype="multipart/form-data" multiple>
        <input type="submit"/>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.2.1.min.js"
        integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
        crossorigin="anonymous">
</script>

<script>
    $('#change_pass').click(function () {
        window.location.href = "/page_update";
    });

    $('#delete_zip').click(function () {
        var data = {'toDelete': []};
        $(":checked").each(function () {
            data['toDelete'].push($(this).val());
        });
        $.post("/delete_file", data, function (data, status) {
            window.location.reload();
        });
    });
</script>


</body>
</html>
