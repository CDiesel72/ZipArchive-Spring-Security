<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>ZIP файлы пользователей</title>
</head>
<body>
<div align="center">
    <h1>Секретная страница, только для админов!</h1>

    <p>Нажмите чтобы вернуться: <a href="/">Назад</a></p>

    <c:url value="/logout" var="logoutUrl" />
    <p>Нажмите для выхода: <a href="${logoutUrl}">Выход</a></p>

    <button type="button" id="add_user">Добавить пользователя</button>
    <button type="button" id="delete_user">Удалить пользователей</button>

    <table border="1">
        <c:forEach items="${users}" var="user">
            <tr>
                <td><input type="checkbox" name="toDelete" value="${user.id}" id="check_${user.id}"></td>
                <td><c:out value="${user.login}"/></td>
                <td><c:out value="${user.role}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>

<script src="https://code.jquery.com/jquery-3.2.1.min.js"
        integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
        crossorigin="anonymous">
</script>

<script>
    $('#add_user').click(function(){
        window.location.href = "/register/admin";
    });

    $('#delete_user').click(function(){
        var data = { 'toDelete' : []};
        $(":checked").each(function() {
            data['toDelete'].push($(this).val());
        });
        $.post("/delete_user", data, function(data, status) {
            window.location.reload();
        });
    });
</script>

</body>
</html>
