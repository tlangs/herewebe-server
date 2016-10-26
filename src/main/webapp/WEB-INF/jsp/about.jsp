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
    <jsp:include page="parts/meta.jsp"/>
    <title>About</title>
    <jsp:include page="parts/resources.jsp"/>
</head>
<body>
<jsp:include page="parts/header.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-sm-12">
            <h1>About Me</h1>

            <p>
                Hi, I'm Trevyn. I spend most of my time studying at Northeastern University, but
                that doesn't stop me from being an adventurous person. I love the outdoors and spend a lot of my free
                time
                biking, running, or camping. When I'm not outside, I'm usually hunkered down in my room for hours on end
                coding whatever project I'm currently working on (most likely to be homework).
            </p>

            <p>
                I love to code and explore new technologies. I have a background in creating full-stack web-based applications, mainly using the Spring framework. I also work in Scala and have some experience using Apache Spark and Hadoop for distributed data processing. I can code using a number of front-end libraries, the most recent one being React/Redux. I am pursuing a Bachelor of Science in Computer Science and a minor in biology. I've used
                my curiosity in both fields to build applications for research and medical purposes, working at
                the Novartis Institute for Biomedical Research and Boston Children's Hospital making applications to
                assist researchers and physicians. I love working in biotech, and I hope to make a career out of it.
            </p>

            <p>
                This site is built using Twitter Bootstrap and Backbone.js, both of which I love for making my website
                more
                than just a pile of spaghetti code. Its a small little application, but its my little web-based home. On the server side, I've made a little Spring application serving up
                JSP
                pages. My server is currently hosted by DigitalOcean, which means if they're having problems, so am I.
            </p>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12">
            <h1>Contact</h1>

            <p>You can <a href="mailto:tlangsford@gmail.com">email me</a> to get in touch.
                I also have a <a href="https://www.linkedin.com/pub/trevyn-langsford/56/810/a80">LinkedIn</a> account,
                and I've been known to <a href="https://twitter.com/tlangsford">tweet</a> occasionally.
				My <a href="https://github.com/tlangs">GitHub</a> account shows a bit of what I've worked on, including the source to this site.
                If you'd like to establish a secure communication with me, you can
                <a href="resources/fiaboutles/public-F433B841.asc">download my public key here</a>.
            </p>
        </div>
    </div>
</div>
<jsp:include page="parts/footer.jsp"/>
</body>
</html>
