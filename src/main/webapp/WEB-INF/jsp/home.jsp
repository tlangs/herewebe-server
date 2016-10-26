<%--
  Created by IntelliJ IDEA.
  User: trevyn
  Date: 1/17/15
  Time: 1:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>

    <jsp:include page="parts/meta.jsp"/>
    <meta name="description" content="The personal website of Trevyn Langsford">

    <title>Trevyn Langsford</title>

    <jsp:include page="parts/resources.jsp"/>
</head>

<body>

<jsp:include page="parts/header.jsp"/>

<div class="container">
    <h1>Welcome to my website!</h1>

    <p>To see some of the stuff I've made, check out the Projects menu. An API that I used recently changed, the menu's a bit barren right now.</p>

    <p>Its starting to get cold again, so here's a picture of Boston in the summer to remind you that warm weather is just one winter away.</p>


    <img src="resources/images/boston-skyline.jpg" class="img-responsive img-rounded">
</div>
<jsp:include page="parts/footer.jsp"/>
</body>
</html>
