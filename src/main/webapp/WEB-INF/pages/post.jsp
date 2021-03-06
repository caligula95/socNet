<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Post page</title>

    <spring:url value="/resources/css/style.css" var="styleCss"/>
    <spring:url value="/resources/css/bootstrap-theme.min.css" var="bootstrapThemeMinCss"/>
    <spring:url value="/resources/css/bootstrap.css" var="bootstrapCss"/>
    <spring:url value="/resources/css/navbar.css" var="navbarCss"/>
    <spring:url value="/resources/html/navbar.jsp" var="navbarHtml"/>
    <spring:url value="/resources/js/lib/jquery.js" var = "jqueryJs"/>

    <link rel="stylesheet" type="text/css" href="${styleCss}">
    <link rel="stylesheet" type="text/css" href="${bootstrapThemeMinCss}">
    <link rel="stylesheet" type="text/css" href="${bootstrapCss}">
    <link rel="stylesheet" type="text/css" href="${navbarCss}">

    <script src="${jqueryJs}"></script>

</head>
<body>

<!-- Model values used by Javascript -->
<input type="hidden" id="post-id" value="${post.id}"/>
<input type="hidden" id="user-id" value="${user.id}"/>
<input type="hidden" id="user-first-name" value="${user.firstName}"/>
<input type="hidden" id="user-last-name" value="${user.lastName}"/>
<input type="hidden" id="user-username" value="${user.username}"/>

<c:import url = "/resources/html/navbar.jsp"/>

<div class="container">
    <div class="row profile">

        <div class="col-md-3">
            <div class="profile-sidebar">
                <div class="profile-author">
                    Author
                </div>
                <!-- SIDEBAR USERPIC -->
                <div class="profile-userpic">
                    <img src="/resources/usersImages/${author.id}.png" class="img-responsive" alt="">
                </div>
                <!-- END SIDEBAR USERPIC -->
                <!-- SIDEBAR USER TITLE -->
                <div class="profile-usertitle">
                    <div class="profile-usertitle-name">
                        <c:out value="${author.firstName} ${author.lastName}"/>
                    </div>
                </div>
                <!-- END SIDEBAR USER TITLE -->
                <!-- SIDEBAR BUTTONS -->
                <div class="profile-userbuttons">
                    <button type="button" class="btn btn-success btn-sm">Messages</button>
                    <button type="button" class="btn btn-danger btn-sm">Followers</button>
                </div>
                <!-- END SIDEBAR BUTTONS -->
                <!-- SIDEBAR MENU -->
                <div class="profile-usermenu">
                    <ul class="nav">
                        <li class="active">
                            <a href="#">
                                <i class="glyphicon glyphicon-home"></i>
                                Overview </a>
                        </li>

                    </ul>
                </div>
                <!-- END MENU -->
            </div>
        </div>

        <div class="col-md-9">
            <div class="profile-content">
                <div>
                    <h2>${post.title}</h2>
                    <br>
                    <p>${post.text}</p>
                    <br>
                    <p>Published: <i>${post.postingDate}</i></p>
                </div>
                <hr/>
                <div class="row">
                    <div class="col-sm-12">
                        <h3>Comments</h3>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-12">
                        <div class="widget-area no-padding blank">

                            <div class="status-upload">
                                <form id="comments-form">
                                    <textarea id="form-comment-text" placeholder="Add comment here"></textarea>
                                    <button type="submit" class="btn btn-success green"><i class="fa fa-share"></i>
                                        Comment
                                    </button>
                                </form>
                            </div>

                        </div>
                    </div>
                </div>

                <br/>

                <div id="comments">

                </div>

            </div>

        </div>

    </div>
</div>

<br>
<br>

<c:import url="/resources/html/comment.template.html"/>

<script type="text/javascript" src="<c:url value='/resources/js/comment.js' />"></script>

</body>
</html>
