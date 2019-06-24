<%--suppress ALL --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Prog.kiev.ua</title>
</head>
<body>
    <div align="center">
        <c:url value="/j_spring_secrity_check" var="loginUrl" />

        <form action="${loginUrl}" method="POST">
            Login:<br/><input type="text" name="j_login" value=""/><br/>
            Password:<br/><input type="password" name="j_password" value=""/><br/>
            <input type="submit" />

            <p><a href="/register">Register new user</a></p>

            <c:if test="${param.error ne null}">
                <p>Wrong login or password!</p>
            </c:if>

            <c:if test="${param.unauthorized ne null}">
                <p>Chao!</p>
            </c:if>
        </form>
    </div>
</body>
</html>
