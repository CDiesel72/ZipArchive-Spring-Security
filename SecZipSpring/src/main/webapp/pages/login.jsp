<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>ZIP файлы пользователей</title>
</head>
<body>
    <div align="center">
        <c:url value="/j_spring_security_check" var="loginUrl" />

        <c:if test="${param.error ne null}">
            <h4 style="color: red">Не правильный логин или пароль!</h4>
        </c:if>

        <form action="${loginUrl}" method="POST">
            Ваш логин:<br/><input type="text" name="j_login"><br/>
            Пароль:<br/><input type="password" name="j_password"><br/>
            <input type="submit" />

            <p><a href="/register/no">Зарегистрировать нового пользователя</a></p>

            <c:if test="${param.error ne null}">
                <p>Не правильный логин или пароль!</p>
            </c:if>

            <c:if test="${param.logout ne null}">
                <p>До встречи!</p>
            </c:if>
        </form>
    </div>
</body>
</html>
