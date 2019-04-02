<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="с" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>

<tags:master pageTitle="Error">
    <h1>Sorry </h1>
    <h2>${requestScope['javax.servlet.error.message']}</h2>
    <span><a href="<с:url value="/products"/>">Home page</a></span>
    <tags:recentlyViewed />
</tags:master>
