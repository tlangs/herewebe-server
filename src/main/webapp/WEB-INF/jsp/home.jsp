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

  <link href="../../resources/css/bootstrap.css" rel="stylesheet">
  <link href="../../resources/css/starter-template.css" rel="stylesheet">
  <link href="../../resources/css/custom.css" rel="stylesheet">

  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
  <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>

<body>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">Trevyn Langsford</a>
    </div>
    <div class="collapse navbar-collapse">
      <ul class="nav navbar-nav" id="nav-buttons">
        <li id="home-nav"><a href="#">Home</a></li>
        <li id="about-nav"><a href="#about">About</a></li>
        <li id="work-nav"><a href="#work">Work</a></li>
      </ul>
    </div>
  </div>
</div>

<div class="container" id="view-main"></div>



<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script type="text/javascript" src="../../resources/js/libs/bootstrap.min.js"></script>
<script type="text/javascript" src="../../resources/js/libs/underscore/underscore-min.js"></script>
<script type="text/javascript" src="../../resources/js/libs/backbone/backbone-min.js"></script>
<script type="text/javascript" src="../../resources/js/utils/utils.js"></script>


<script type="text/javascript">
  var App = {};

</script>


<script src="../../resources/js/views/AboutView.js"></script>
<script src="../../resources/js/views/WorkView.js"></script>
<script src="../../resources/js/views/HomeView.js"></script>
<script src="../../resources/js/views/SoylentView.js"></script>
<script src="../../resources/js/router.js"></script>


</body>
</html>
