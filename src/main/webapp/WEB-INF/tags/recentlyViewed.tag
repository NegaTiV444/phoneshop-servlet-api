<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="с" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="history" type="com.es.phoneshop.model.history.History" scope="session"/>

<c:if test="${not empty history}">
    <table>
        <thead>
        <tr>
            Recently viewed products
        </tr>
        </thead>
        <tr>
            <c:forEach var="product" items="${history.recentProducts}">
                <td>
                    <a href="<с:url value="/products"/>/${product.code}">
                        <img class="product-tile"
                             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                    </a>
                </td>
            </c:forEach>
        </tr>
    </table>
</c:if>

