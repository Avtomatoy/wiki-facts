<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>Log in with your account</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>

<body>
<div>
  <table border="1">
    <thead>
    <th>ID</th>
    <th>UserName</th>
    <th>Password</th>
    <th>Roles</th>
    <th>Fact IDs</th>
    </thead>
    <c:forEach items="${userDtos}" var="userDto">
      <tr>
        <td>${userDto.user.id}</td>
        <td>${userDto.user.username}</td>
        <td>${userDto.user.password}</td>
        <td>
          <c:forEach items="${userDto.user.roles}" var="role">${role.name}; </c:forEach>
        </td>

        <td>
            <c:forEach items="${userDto.facts}" var="fact">${fact.id}<br></c:forEach>
        </td>
        <td>
            <form action="${pageContext.request.contextPath}/admin" method="post">
                <input type="hidden" name="userId" value="${userDto.user.id}"/>
                <input type="hidden" name="action" value="delete"/>
                <button type="submit">Delete</button>
            </form>
        </td>
      </tr>
    </c:forEach>
  </table>
  <a href="/">Главная</a>
</div>
</body>
</html>
