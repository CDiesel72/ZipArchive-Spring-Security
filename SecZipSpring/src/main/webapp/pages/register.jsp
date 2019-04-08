<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>ZIP файлы пользователей</title>
</head>
<body>
<div align="center">
    <c:url value="/newuser/${adm}" var="regUrl" />

    <c:if test="${pass ne null}">
        <h4  style="color: red">Введенные пароли не совпадают</h4>
    </c:if>

    <c:if test="${exists ne null}">
        <h4  style="color: red">Пользователь с таким логином уже существует!</h4>
    </c:if>

    <form action="${regUrl}" method="POST">
        Введите логин:<br/><input type="text" name="login" value="${login}"><br/>
        Введите пароль:<br/><input type="password" name="password"><br/>
        Повторите пароль:<br/><input type="password" name="pass1"><br/>
        <input type="submit" />

    </form>
</div>
</body>
</html>
