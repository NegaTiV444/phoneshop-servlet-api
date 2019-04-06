<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="с" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
<div class="miniCart">
    <a href="<с:url value="/cart"/>">
        <img src="${pageContext.servletContext.contextPath}/images/cart.png">
    </a>
    <span>Products: ${cart.totalProducts}</span>
    <span>Price: ${cart.totalPrice} $</span>
</div>