<%@ page contentType="text/html;charset=UTF-8" errorPage="/WEB-INF/pages/error.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product Details">
    <div><a href="products">All products</a>/${product.description}</div>
    <h2>${product.description}</h2>
    <section class="productDetails">
        <div class="productImage">
            <img src="${product.imageUrl}">
        </div>
        <div class="productInfo">
            ${product.info}
        </div>
    </section>
    <section class="bigPrice"><h2><fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/></h2></section>
    <section class="buy">
        <button>Buy Now</button>
        <button>Add to cart</button>
    </section>
</tags:master>