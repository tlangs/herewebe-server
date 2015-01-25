<%--
  Created by IntelliJ IDEA.
  User: trevyn
  Date: 1/19/15
  Time: 2:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>About</title>
    <jsp:include page="parts/resources.jsp"/>
</head>
<body>
<jsp:include page="parts/header.jsp"/>
<div class="container">
    <div>
        <h1>About Me</h1>

        <p>
            Hi, I'm Trevyn. I spend most of my time studying computer science at Northeastern University, but
            that doesn't stop me from being an adventurous person. I love the outdoors and spend a lot of my free time
            biking, running, or camping. When I'm not outside, I'm usually hunkered down in my room for hours on end
            coding whatever project I'm currently working on.
        </p>

        <p>
            I love to code and explore new technologies. Currently, I'm entrenched in Java building web apps using the
            Spring framework. I am pursuing a Bachelor of Science in Computer Science and a minor in biology. I use
            my curiosity in both fields to build applications for research and medical purposes. I've worked at
            the Novartis Institute for Biomedical Research and Boston Children's Hospital making applications to assist
            researchers and doctors.
        </p>

        <p>
            This site is built using Twitter Bootstrap and Backbone.js, both of which I love for making my website more
            than just a pile of spaghetti code. On the server side, I've made a little Spring application serving up JSP
            pages. My server is currently hosted by DigitalOcean, so if they're having problems, so am I.
        </p>
    </div>
    <div>
        <h1>Contact</h1>

        <p>You can <a href="mailto:tlangsford@gmail.com">email me</a> to get in touch.
            I also have a <a href="https://www.linkedin.com/pub/trevyn-langsford/56/810/a80">LinkedIn</a> account,
            and I've been known to <a href="https://twitter.com/tlangsford">tweet</a> occasionally.
            If you'd like to establish a secure communication with me, you can
            <a href="resources/files/public-F433B841.asc">download my public key here</a>.
        </p>
    </div>
</div>

</body>
</html>
