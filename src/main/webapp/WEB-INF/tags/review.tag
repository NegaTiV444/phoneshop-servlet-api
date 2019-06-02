<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ tag trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="с" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="reviews" type="java.util.ArrayList" scope="request"/>

<c:if test="${not empty reviews}">
    <table>
        <thead>
        <tr>
            <h2>Product reviews</h2>
        </tr>
        </thead>
        <tr>
            <c:forEach var="review" items="${reviews}">
                <hr>
                <p>
                <h3>Name: ${review.name}</h3>
                <span>Rating: ${review.rating}</span>
                <br/>
                <span>Comment: ${review.comment}</span>
                </p>
            </c:forEach>
        </tr>
    </table>
    <hr>
</c:if>
