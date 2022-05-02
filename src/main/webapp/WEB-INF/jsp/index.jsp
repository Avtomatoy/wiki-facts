<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<html>
<head>
  <title>Wiki-facts</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
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
</body>
</html>