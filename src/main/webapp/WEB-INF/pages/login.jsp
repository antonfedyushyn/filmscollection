<%--suppress JspAbsolutePathInspection --%>
<%--suppress HtmlFormInputWithoutLabel --%>
<%--suppress XmlPathReference --%>
<%--suppress HtmlUnknownTarget --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Кинотеатр онлайн</title>
    <link rel="shortcut icon" href="<c:url value="resources/img/favicon.ico" />" type="image/x-icon">
</head>
<body>
    <div align="center">
        <c:url value="/j_spring_secrity_check" var="loginUrl" />

        <form action="${loginUrl}" method="POST">
            Login:<br/><input type="text" name="j_login" value="" title="login: "/><br/>
            Password:<br/><input type="password" name="j_password" value="" title="login: "/><br/>
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
