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
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">
  <!--<link rel="shortcut icon" href="../../assets/ico/favicon.ico">-->

  <title>Trevyn Langsford</title>

  <jsp:include page="parts/resources.jsp"/>
</head>

<body>

<jsp:include page="parts/header.jsp"/>

<div class="container">
  <h1>Welcome to my website!</h1>

  <h3><a href="soylent">Click here to read my account of my week on Soylent!</a></h3>
  <p>I'm currently writing my online enclave using Javascript, Backbone.js, and Twitter Bootstrap.
    This site is under a lot of construction, but there will definitely be more content to come.
    In the meantime, please enjoy this picture of Boston in the summer.</p>

  <img src="resources/images/boston-skyline.jpg" class="img-responsive img-rounded">
</div>



<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script type="text/javascript" src="../../resources/js/libs/bootstrap.min.js"></script>


</body>
</html>
