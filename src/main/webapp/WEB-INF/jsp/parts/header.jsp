<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: trevyn
  Date: 1/19/15
  Time: 2:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="/">Trevyn Langsford</a>
    </div>
    <div class="collapse navbar-collapse">
      <ul class="nav navbar-nav" id="nav-buttons">
        <li <c:if test='${viewName == "home"}'>class="active"</c:if>><a href="/">Home</a></li>
        <li <c:if test='${viewName == "about"}'>class="active"</c:if>><a href="/about">About</a></li>
        <li <c:if test='${viewName == "work"}'>class="active"</c:if>><a href="/work">Work</a></li>
      </ul>
    </div>
  </div>
</div>