<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>${user.username}</title>
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
    <div>
        <h3>${user.username}</h3>
        role: ${role}
        <c:if test="${canUpgrade}">
            <div class="mt-1">
                <form:form method="POST" action="/profile/${user.username}/upgrade">
                   <button type="submit" class="btn btn-danger">Upgrade to Moderator</button>
                </form:form>
            </div>
        </c:if>
    </div>
    <div class="mt-1">
        <form:form method="GET" modelAttribute="searchData" action="/facts/search">
            <form:hidden path="method" value="OWN"></form:hidden>
            <form:hidden path="username" value="${user.username}"></form:hidden>
            <button type="submit" class="btn btn-warning">Publicised facts</button>
        </form:form>
    </div>
    <div class="mt-1">
        <form:form method="GET" modelAttribute="searchData" action="/facts/search">
            <form:hidden path="method" value="LIKE"></form:hidden>
            <form:hidden path="username" value="${user.username}"></form:hidden>
            <button type="submit" class="btn btn-warning">Liked facts</button>
        </form:form>
    </div>
    <c:if test="${pageContext.request.userPrincipal.name == user.username}">
        <div class="mt-1">
            Preferred facts status: <strong>${user.preferredStatus}</strong>
            <form:form method="POST" action="/profile/${user.username}/prefst">
                <button type="submit" class="btn btn-primary">Change</button>
            </form:form>
        </div>
        <div class="mt-1">
            Preferred facts categories:
            <form:form method="POST" modelAttribute="searchData" action="/profile/${user.username}/prefcat">
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
                <button type="submit" class="btn btn-primary">Save</button>
            </form:form>
        </div>
    </c:if>
</div>
</body>
</html>