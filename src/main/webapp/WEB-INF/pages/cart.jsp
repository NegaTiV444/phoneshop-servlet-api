<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="с" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="session"/>
<tags:master pageTitle="Cart">
    <h1>CART</h1>
    <form method="post">
        <table>
            <c:forEach var="item" items="${cart.items}" varStatus="status">
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
                        <input type="text" name="newQuantity"
                               value="${not empty requestScope.q[status.index] ? requestScope.q[status.index] : item.quantity}">
                        <c:if test="${not empty requestScope.msg}">
                            <c:choose>
                                <c:when test="${requestScope.msg[status.index] == 'not.a.number.error'}">
                                    <p style="color:red">Not a number</p>
                                </c:when>
                                <c:when test="${requestScope.msg[status.index] == 'out.of.stock.error'}">
                                    <p style="color:red">Not enough products in stock</p>
                                </c:when>
                                <c:when test="${requestScope.msg[status.index] == 'invalid.quantity.error'}">
                                    <p style="color:red">Quantity must be greater than 0</p>
                                </c:when>
                            </c:choose>
                        </c:if>
                    </td>
                    <td class="price">
                        <fmt:formatNumber value="${item.product.price}" type="currency"
                                          currencySymbol="${item.product.currency.symbol}"/>
                    </td>
                    <td>
                        <button name="delete" formaction="<с:url value="/cart/deleteItem/"/>${item.product.code}">
                            Delete
                        </button>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="4">
                    <button style="float: right">Update</button>
                </td>
                <td colspan="2" style="text-align: left">Total: ${cart.totalPrice} $</td>

            </tr>
        </table>
    </form>
</tags:master>