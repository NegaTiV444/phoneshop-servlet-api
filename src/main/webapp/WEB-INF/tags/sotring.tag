<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sortBy" required="true" %>

<a href="products?query=${param.query}&sortBy=${sortBy}&order=asc">▲</a>
<a href="products?query=${param.query}&sortBy=${sortBy}&order=desc">▼</a>