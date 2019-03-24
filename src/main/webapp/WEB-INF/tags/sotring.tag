<%@tag pageEncoding="UTF-8"%>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sortBy" required="true" %>

<a href="products?query=${param.query}&sortBy=${sortBy}&order=ascending">▲</a>
<a href="products?query=${param.query}&sortBy=${sortBy}&order=descending">▼</a>