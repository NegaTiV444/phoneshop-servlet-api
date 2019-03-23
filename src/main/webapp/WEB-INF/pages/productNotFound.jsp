<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Not Found">
    <h1>Sorry </h1>
    <h2>Product ${requestScope.code} not found</h2>
    <span><a href="products">Back to all products</a></span>
</tags:master>