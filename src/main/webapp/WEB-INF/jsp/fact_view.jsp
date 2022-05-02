<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>Search</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
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
    <c:choose>
        <c:when test="${fact != null}">

            <div class="card">
                <div class="card-header">
                    Author: <a href="/profile/${fact.author.username}">${fact.author.username}</a>
                    <br>
                    ${fact.publicationDate}
                    <br>
                    Rating: ${fact.rating}
                </div>
                <div class="card-body">
                    <p class="card-text">${fact.factText}</p>
                    <c:forEach items="${fact.links}" var="link">
                        <a href="${link}" class="card-link" target="_blank">${link}</a>
                    </c:forEach>
                </div>
                <div class="card-footer">
                    <c:forEach items="${fact.categories}" var="category">
                        <span>${category.name}</span><span> | </span>
                    </c:forEach>
                </div>
            </div>

            <div>
                <button type="submit" class="btn btn-primary" form="previous">Previous</button>
                <button type="submit" class="btn btn-primary" form="next">Next</button>
                <button type="submit" class="btn btn-warning" form="like">${liked ? "Dislike" : "Like"}</button>
                <button type="submit" class="btn btn-secondary" form="edit">Edit</button>
                <button type="submit" class="btn btn-danger" form="delete">Delete</button>
            
                <c:if test="${searchData.offset != 0}">
                    <div>
                       <form:form id="previous" method="GET" modelAttribute="searchData" action="/facts/search">
                           <form:hidden path="method" value="${searchData.method}"></form:hidden>
                           <form:hidden path="keywords" value="${searchData.keywords}"></form:hidden>
                           <form:hidden path="offset" value="${searchData.offset - 1}"></form:hidden>
                       </form:form>
                    </div>
                </c:if>
                <form:form id="next" method="GET" modelAttribute="searchData" action="/facts/search">
                    <form:hidden path="method" value="${searchData.method}"></form:hidden>
                    <form:hidden path="keywords" value="${searchData.keywords}"></form:hidden>
                    <form:hidden path="offset" value="${searchData.offset +1}"></form:hidden>
                </form:form>
                <c:if test="${liked != null}">
                    <form:form id="like" method="POST" action="/facts/search/like" modelAttribute="fact">
                        <form:hidden path="id" value="${fact.id}"></form:hidden>
                    </form:form>
                </c:if>
                <c:if test="${canModerate != null && canModerate == true}">
                    <form:form id="edit" method="GET" action="/facts/edit" modelAttribute="fact">
                        <form:hidden path="id" value="${fact.id}"></form:hidden>
                    </form:form>
                </c:if>
                <c:if test="${canDelete != null && canDelete == true}">
                    <form:form id="delete" method="POST" action="/facts/search/delete" modelAttribute="fact">
                        <form:hidden path="id" value="${fact.id}"></form:hidden>
                    </form:form>
                </c:if>
            </div>
        </c:when>
        <c:otherwise>
            <h2>There are no more facts by your criteria :(</h2>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
