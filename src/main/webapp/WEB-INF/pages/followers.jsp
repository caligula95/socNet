<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Contact</title>

    <spring:url value="/resources/css/style.css" var="styleCss" />
    <spring:url value="/resources/css/bootstrap-theme.min.css" var="bootstrapThemeMinCss" />
    <spring:url value="/resources/css/bootstrap.css" var="bootstrapCss" />
    <spring:url value="/resource/css/navbar.css" var = "navbarCss"/>
    <spring:url value="/resources/html/navbar.html" var = "navbarHtml"/>
    <spring:url value="/resources/js/lib/jquery.js" var = "jqueryJs"/>

    <link rel="stylesheet" type="text/css" href="${styleCss}">
    <link rel="stylesheet" type="text/css" href="${bootstrapThemeMinCss}">
    <link rel="stylesheet" type="text/css" href="${bootstrapCss}">
    <link rel="stylesheet" type="text/css" href="${navbarCss}">

    <script src="${jqueryJs}"></script>
    <script src="<c:url value='/resources/js/friends.js' />"></script>


</head>
<body>

<c:import url = "/resources/html/navbar.jsp"/>

<div class="container">
    <div class="row">
        <div class="col-xs-12 col-sm-offset-3 col-sm-6">
            <div class="panel panel-default">
                <div class="panel-heading c-list">
                    <span class="title">Contacts</span>
                </div>

                <ul class="list-group" id="contact-list">
                    <c:forEach var="user" items="${users}">
                    <li class="list-group-item clearfix">
                        <div class="col-xs-12 col-sm-3">
                            <img src="http://api.randomuser.me/portraits/men/49.jpg" class= "img-responsive img-circle" />
                            </div>
                        <div class="col-xs-12 col-sm-9">
                            <div class="profile-usertitle-name">
                                <a href = "/home/${user.username}">  ${user.firstName}
                                     ${user.lastName} </a>
                                </div>
                            <a>Write message</a>
                            </div>
                        <div class="clearfix"></div>
                        </li>
                    </c:forEach>
                </ul>
            </div>

        </div>




    </div>
</div>

<br>
<br>





</body>
</html>