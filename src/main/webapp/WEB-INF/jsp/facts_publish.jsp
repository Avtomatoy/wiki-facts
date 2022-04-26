<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>Publish</title>
</head>

<body>
<div>
    <form:form method="POST" modelAttribute="factForm">
        <h2>Fact publication</h2>
        <div>
            <h3>Text</h3>
            <form:textarea path="factText" rows="5" columns="80"></form:textarea>
        </div>
        <div>
            <h3>Categories</h3>
            <c:forEach items="${categories}" var="category">
                <div>
                    <form:checkbox path="categories" value="${category}"></form:checkbox>${category.name}
                </div>
            </c:forEach>
        </div>
        <div>
            <h3>Links</h3>
             <form:textarea path="links" rows="3" columns="80"></form:textarea>
        </div>
        <div>

            <button type="submit">Publish</button>
        </div>
    </form:form>
</div>
<div>
    <a href="/facts">Back</a>
</div>
</body>
</html>