<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<html>
<head>
    <title>${pageTitle}</title>
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body class="master-tag-body">
<header>
    <a href="<c:url value="/"/>">
        <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
        PhoneShop
    </a>
    <div style="float: right; font-size: 15px; margin-right: 30px">
        <jsp:include page="/cart/miniCart"/>
    </div>

</header>
<main>
    <jsp:doBody/>
</main>
<footer>
    <style>
        hr {
            border: none;
            background-color: aquamarine;
            height: 3px;
        }
    </style>
    <hr>
    <a href="<c:url value="/moderation"/>"><h2>Moderation</h2></a>
</footer>
</body>
</html>