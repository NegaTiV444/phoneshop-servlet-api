<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="с" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>
<tags:master pageTitle="Order overview">
    <h1>Order overview</h1>
    <table>
        <c:forEach var="item" items="${order.items}" varStatus="status">
            <tr>
                <td>
                        ${status.index + 1}
                </td>
                <td>
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.product.imageUrl}">
                </td>
                <td><a href="<с:url value="/products"/>/${item.product.code}">${item.product.description}</a></td>
                <td>
                    <span>item.quantity</span>
                </td>
                <td class="price">
                    <fmt:formatNumber value="${item.product.price}" type="currency"
                                      currencySymbol="${item.product.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="4"></td>
            <td colspan="1" style="text-align: left">Total: ${order.totalPrice} $</td>
        </tr>
    </table>
    <p>
        <span>First name: ${order.firstName}</span>
    </p>
    <p>
        <span>Last name: ${order.lastName}</span>
    </p>
    <p>
        <span>Phone: ${order.phone}</span>
    </p>
    <p>
        <span>Delivery method: ${order.deliveryMethod.label}</span>
    </p>
    <p>
        <span>Delivery address: ${order.address}</span>
    </p>
    <p>
        <span>Payment method: ${order.paymentMethod.label}</span>
    </p>
</tags:master>