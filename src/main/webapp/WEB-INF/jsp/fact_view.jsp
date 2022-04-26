<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>Search</title>
</head>
<body>
<div>

    <div>
        ${searchData.fact.author.username}
    </div>
    <div>
        ${searchData.fact.factText}
    </div>
    <div>
        ${searchData.fact.rating}
    </div>
    <div>
        <form:form method="POST" modelAttribute="searchData">
            <form:hidden path="method" value="${searchData.method}"></form:hidden>
            <c:forEach items="${searchData.categories}" var="category">
                 <div>
                     <form:hidden path="categories" value="${category}"></form:hidden>
                 </div>
            </c:forEach>
            <form:hidden path="keywords" value="${searchData.keywords}"></form:hidden>
            <form:hidden path="offset" value="${searchData.offset + 1}"></form:hidden>
            <button type="submit">Next</button>
        </form:form>
    </div>
    <div>
        <form:form method="POST" modelAttribute="searchData">
            <form:hidden path="method" value="${searchData.method}"></form:hidden>
            <c:forEach items="${searchData.categories}" var="category">
                 <div>
                     <form:hidden path="categories" value="${category}"></form:hidden>
                 </div>
            </c:forEach>
            <form:hidden path="keywords" value="${searchData.keywords}"></form:hidden>
            <form:hidden path="offset" value="${searchData.offset - 1}"></form:hidden>
            <button type="submit">Previous</button>
        </form:form>
    </div>

    <a href="/">Back</a>
</div>
</body>
</html>
