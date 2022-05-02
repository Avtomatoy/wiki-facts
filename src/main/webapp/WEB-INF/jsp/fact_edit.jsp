<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>Edit</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
<div class="m-1">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
          <a class="navbar-brand" href="/">Wiki-facts</a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
              <a class="nav-link" href="/facts">Facts</a>
              <sec:authorize access="isAuthenticated()">
                <a class="nav-link" href="/profile/${pageContext.request.userPrincipal.name}">${pageContext.request.userPrincipal.name}</a>
                <a class="nav-link" href="/logout">Logout</a>
              </sec:authorize>
              <sec:authorize access="!isAuthenticated()">
                 <a class="nav-link" href="/login">Sign in</a>
                 <a class="nav-link" href="/registration">Sign up</a>
              </sec:authorize>
            </div>
          </div>
        </div>
    </nav>

    <h2>Fact editing</h2>
    <form:form method="POST" modelAttribute="fact" action="/facts/edit">
        <div class="input-group">
            <span class="input-group-text">Text</span>
            <form:textarea class="form-control" aria-label="Text" path="factText" rows="5" columns="10" required="true" value="${fact.factText}"></form:textarea>
        </div>
        <label>Categories</label>
        <c:forEach items="${categoryPairs}" var="categoryPair">
            <div>
                <c:choose>
                    <c:when test="${categoryPair.checked}">
                        <form:checkbox path="categories" value="${categoryPair.category}" checked="true"></form:checkbox>
                    </c:when>
                    <c:otherwise>
                        <form:checkbox path="categories" value="${categoryPair.category}"></form:checkbox>
                    </c:otherwise>
                </c:choose>
                ${categoryPair.category.name}
            </div>
        </c:forEach>
        <div class="input-group">
            <span class="input-group-text">Links</span>
            <form:textarea class="form-control" aria-label="Links" path="links" rows="5" columns="10" value="${links}"></form:textarea>
        </div>
        <form:hidden path="id" value="${fact.id}"></form:hidden>
        <button type="submit" class="btn btn-success">Ready</button>
    </form:form>

</div>
</body>
</html>
