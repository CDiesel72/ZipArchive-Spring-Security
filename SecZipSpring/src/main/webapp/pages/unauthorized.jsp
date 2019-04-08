<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>ZIP файлы пользователей</title>
</head>
<body>
<div align="center">
    <h1  style="color: red">Доступ запрещен для ${login}!</h1>

    <c:url value="/logout" var="logoutUrl" />
    <p>Нажмите для выхода: <a href="${logoutUrl}">Выход</a></p>
</div>
</body>
</html>