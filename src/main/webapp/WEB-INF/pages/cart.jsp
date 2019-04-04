<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="с" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
<tags:master pageTitle="Cart">
    <%--<form>--%>
        <%--<input type="text" name="query" value="${param.query}">--%>
        <%--<button>Search</button>--%>
    <%--</form>--%>
    <%--<table>--%>
        <%--<thead>--%>
        <%--<tr>--%>
            <%--<td>Image</td>--%>
            <%--<td>Description--%>
                <%--<tags:sotring sortBy="description"/>--%>
            <%--</td>--%>
            <%--<td class="price">--%>
                <%--Price--%>
                <%--<tags:sotring sortBy="price"/>--%>
            <%--</td>--%>
        <%--</tr>--%>
        <%--</thead>--%>
        <%--<c:forEach var="item" items="${cart.items}" varStatus="status">--%>
            <%--<tr>--%>
                <%--<td>--%>
                    <%--${status.index}--%>
                <%--</td>--%>
                <%--<td>--%>
                    <%--<img class="product-tile"--%>
                         <%--src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.product.imageUrl}">--%>
                <%--</td>--%>
                <%--<td><a href="<с:url value="/products"/>/${item.product.code}">${item.product.description}</a></td>--%>
                <%--<td class="price">--%>
                    <%--<fmt:formatNumber value="${item.product.price}" type="currency"--%>
                                      <%--currencySymbol="${item.product.currency.symbol}"/>--%>
                <%--</td>--%>
            <%--</tr>--%>
        <%--</c:forEach>--%>
    <%--</table>--%>
    <%--<tags:recentlyViewed/>--%>
</tags:master>