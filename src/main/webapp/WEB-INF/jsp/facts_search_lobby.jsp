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
    <h2>Search by:</h2>
    <form:form method="POST" modelAttribute="searchData">
        <form:hidden path="method" value="RANDOM"></form:hidden>
        <button type="submit">Random</button>
    </form:form>
    <form:form method="POST" modelAttribute="searchData">
        <form:textarea path="keywords" rows="1" columns="100"></form:textarea>
        <form:hidden path="method" value="KEYWORDS"></form:hidden>
        <button type="submit">Keywords</button>
    </form:form>
     <form:form method="POST" modelAttribute="searchData">
        <form:hidden path="method" value="RATING"></form:hidden>
        <button type="submit">Rating</button>
     </form:form>
    <a href="/">Back</a>
</div>
</body>
</html>