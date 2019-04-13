<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="с" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="session"/>
<tags:master pageTitle="Checkout">
    <h1>Checkout</h1>
    <form method="post">
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
            <label for="firstName">First name</label>
            <input type="text" name="firstName" id="firstName" required
                   value="${not empty order.firstName ? order.firstName : ""}">
        </p>
        <p>
            <label for="lastName">Last name</label>
            <input type="text" name="lastName" id="lastName" required
                   value="${not empty order.lastName ? order.lastName : ""}">
        </p>
        <p>
            <label for="phone">Phone</label>
            <input type="tel" name="phone" id="phone" required
                   value="${not empty order.phone ? order.phone : ""}">
        </p>
        <p>
            <label for="deliveryMethod">Delivery method</label>
            <select name="deliveryMethod" id="deliveryMethod"
                    onchange="isUpdate.value = 'true'; form.updateDeliveryMethodBt.click()">
                <option value="COURIER" ${order.deliveryMethod.label eq "Courier" ? "selected" : ""} id="courier">
                    Courier
                </option>
                <option value="STOREPICKUP" ${order.deliveryMethod.label eq "Store pickup" ? "selected" : ""}
                        id="storePickup">StorePickup
                </option>
            </select>
            <button id="updateDeliveryMethodBt" formaction="<с:url value="/checkout"/>" formmethod="post" formnovalidate
                    style="display: none"></button>
            <input type="hidden" name="isUpdate" id="isUpdate" value="false">
        </p>
        <p>
            <label for="address">Delivery address</label>
            <input type="text" name="address" id="address" required
                   value="${not empty order.address ? order.address : ""}">
        </p>
        <p>
            <label for="paymentMethod">Payment method</label>
            <select name="paymentMethod" id="paymentMethod">
                <option value="CASH" ${order.paymentMethod.label  eq 'Cash' ? "selected" : ""}>Cash</option>
                <option value="CREDIT_CARD" ${order.paymentMethod.label  eq 'Credit card' ? "selected" : ""}>Credit
                    card
                </option>
            </select>
        </p>
        <p>
            <button onclick="this.isUpdate.value = 'false'">Place Order</button>
        </p>
    </form>
</tags:master>