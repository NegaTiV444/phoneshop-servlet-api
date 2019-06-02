<%@ page contentType="text/html;charset=UTF-8" errorPage="/WEB-INF/pages/notFound.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="с" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product Details">
    <div><a href="<с:url value="/products"/>">All products</a>/${product.description}</div>
    <h2>${product.description}</h2>
    <section class="productDetails">
        <div class="productImage">
            <img src="${product.imageUrl}">
        </div>
        <div class="productInfo">
                ${product.info}
        </div>
    </section>
    <section class="bigPrice"><h2><fmt:formatNumber value="${product.price}" type="currency"
                                                    currencySymbol="${product.currency.symbol}"/></h2></section>
    <section class="buy">
        <form method="post" action="<с:url value="/cart"/>/add/${product.code}">
            <div class="quantityInput">
                <input id="cartQuantity" name="quantity" value="${not empty param.q ? param.q : 1}">
                <span> Available ${product.stock}</span>
            </div>
            <br/>
            <button>Add to cart</button>
            <c:if test="${not empty param.msg}">
                <c:choose>
                    <c:when test="${param.msg == 'not.a.number.error'}">
                        <span style="color:red">Not a number</span>
                    </c:when>
                    <c:when test="${param.msg == 'out.of.stock.error'}">
                        <span style="color:red">Not enough products in stock</span>
                    </c:when>
                    <c:when test="${param.msg == 'invalid.quantity.error'}">
                        <span style="color:red">Quantity must be greater than 0</span>
                    </c:when>
                    <c:when test="${param.msg == 'added.to.cart'}">
                        <span style="color:green">Added to cart</span>
                    </c:when>
                </c:choose>
            </c:if>
        </form>
    </section>
    <tags:review/>
    <section>
        <h2>Add Review</h2>
        <form method="post">
            <p>
                <span>Name</span>
                <input type="text" name="name" required>
            </p>
            <p>

                <span>Rating</span>
                <input type="range" name="rating" min="1" max="5" step="1" value="5" oninput="amount.value = rating.value">
                <output name="amount" for="rating">5</output>
            </p>
            <p>
                <span>Comment</span>
                <br/>
                <style>
                    textarea {
                        resize: none;
                    }
                </style>
                <textarea name="comment" required rows="6" cols="30"></textarea>
            </p>
            <p>
                <input type="submit">
            </p>
        </form>
        <hr>
    </section>
    <tags:recentlyViewed/>
</tags:master>