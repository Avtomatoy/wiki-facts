<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>Sign up</title>
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
  <form:form method="POST" modelAttribute="userForm">
    <h2>Sign up</h2>
    <div>
      <form:input type="text" path="username" placeholder="Username" autofocus="true" required="true"></form:input>
      <form:errors path="username"></form:errors>
        ${usernameError}
    </div>
    <div>
      <form:input type="password" path="password" placeholder="Password" required="true"></form:input>
    </div>
    <div>
      <form:input type="password" path="passwordConfirm" placeholder="Confirm your password" required="true"></form:input>
      <form:errors path="password"></form:errors>
        ${passwordError}
    </div>
    <button type="submit" class="btn btn-primary">Let's go!</button>
  </form:form>
</div>
</body>
</html>
