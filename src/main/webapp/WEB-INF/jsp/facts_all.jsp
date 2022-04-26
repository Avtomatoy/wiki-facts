<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>All facts</title>
  <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
</head>

<body>
<div>
  <table border="1">
    <thead>
    <th>ID</th>
    <th>Author</th>
    <th>Publication date</th>
    <th>Status</th>
    <th>Text</th>
    <th>Categories</th>
    <th>Links</th>
    <th>Rating</th>
    </thead>
    <c:forEach items="${allFacts}" var="fact">
      <tr>
        <td>${fact.id}</td>
        <td>${fact.author.username}</td>
        <td>${fact.publicationDate}</td>
        <td>${fact.status}</td>
        <td>${fact.factText}</td>
        <td>
          <c:forEach items="${fact.categories}" var="category">${category.name}<br></c:forEach>
        </td>
        <td>
            <c:forEach items="${fact.links}" var="link">${link}<br></c:forEach>
        </td>
        <td>${fact.rating}</td>
      </tr>
    </c:forEach>
  </table>
  <a href="/">Главная</a>
</div>
</body>
</html>