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

    <p>To see some of the stuff I've made, check out the Projects menu.</p>

    <p>I'm currently writing my online enclave using Javascript, Backbone.js, and Twitter Bootstrap.
        This site is under a lot of construction, but there will definitely be more content to come.
        In the meantime, please enjoy this picture of Boston in the summer.</p>


    <img src="resources/images/boston-skyline.jpg" class="img-responsive img-rounded">
</div>
<jsp:include page="parts/footer.jsp"/>
</body>
</html>
