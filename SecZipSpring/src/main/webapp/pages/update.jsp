<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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

    <c:if test="${oldpass ne null}">
        <h4 style="color: red">Не правильный пароль пользователя</h4>
    </c:if>

    <c:if test="${pass ne null}">
        <h4  style="color: red">Введенные пароли не совпадают</h4>
    </c:if>

    <c:url value="/update" var="updateUrl"/>
    <form action="${updateUrl}" method="POST">
        Старый пароль:<br/><input type="password" name="old_pass"/><br/>
        Новый пароль:<br/><input type="password" name="password"/><br/>
        Повторите пароль:<br/><input type="password" name="pass1"><br/>
        <input type="submit" value="Изменить"/>
    </form>

    <p>Нажмите чтобы вернуться: <a href="/">Назад</a></p>

    <c:url value="/logout" var="logoutUrl"/>
    <p>Нажмите для выхода: <a href="${logoutUrl}">Выход</a></p>
</div>
</body>
</html>
