<%--
  Created by IntelliJ IDEA.
  User: trevyn
  Date: 1/19/15
  Time: 9:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>A Journey in Soylent</title>
    <jsp:include page="parts/resources.jsp"/>
</head>
<body>
<jsp:include page="parts/header.jsp"/>
<div class="container" id="view-main">
    <div id="soylentPostContainer">

    </div>
    <div class="text-center">
        <ul class="pagination" id="soylentPaginator" style="cursor:pointer;">
            <li><a data-page="previous">&laquo;</a></li>
            <li><a data-page="0">0</a></li>
            <li><a data-page="1">1</a></li>
            <li><a data-page="2">2</a></li>
            <li><a data-page="3">3</a></li>
            <li><a data-page="4">4</a></li>
            <li><a data-page="5">5</a></li>
            <li><a data-page="6">6</a></li>
            <li><a data-page="next">&raquo;</a></li>
        </ul>
    </div>
</div>
</body>

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

</html>
