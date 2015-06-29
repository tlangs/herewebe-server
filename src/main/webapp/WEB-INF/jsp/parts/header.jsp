<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: trevyn
  Date: 1/19/15
  Time: 2:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand">Trevyn Langsford</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li <c:if test='${viewName == "home"}'>class="active"</c:if>><a href="${pageContext.request.contextPath}/">Home</a></li>
                <li <c:if test='${viewName == "about"}'>class="active"</c:if>><a href="${pageContext.request.contextPath}/about">About</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Projects <span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="${pageContext.request.contextPath}/soylent">Soylent</a></li>
                        <li><a href="/pokepics">Personal Pokemon Sprites</a></li>
                        <li class="divider"></li>
                        <li><a href="https://github.com/tlangs">GitHub</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>