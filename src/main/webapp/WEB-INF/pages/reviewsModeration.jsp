<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="с" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:useBean id="reviews" type="java.util.ArrayList" scope="request"/>

<tags:master pageTitle="Moderation">

    <h1>Moderation</h1>

    <c:if test="${not empty reviews}">
        <table>
            <thead>
            <tr>
                <h2>Product reviews</h2>
            </tr>
            </thead>
            <form>
                <c:forEach var="review" items="${reviews}">
                    <tr>
                        <td>
                            <p>
                            <h3><a href="<с:url value="/products"/>/${review.product.code}">${review.product.description}</a></h3>
                            </p>
                            <p>
                            <span>Name: ${review.name}</span>
                            <br/>
                                <span>Rating: ${review.rating}</span>
                                <br/>
                                <span>Comment: ${review.comment}</span>
                            </p>
                            <p>
                                <c:choose>
                                    <c:when test="${review.approved}">
                                        Approved
                                    </c:when>
                                    <c:otherwise>
                                        Awaiting
                                    </c:otherwise>
                                </c:choose>
                            </p>
                        </td>
                        <td>
                            <button formaction="<с:url value="/moderation/"/>${review.uid}" formmethod="post">Approve
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </form>
        </table>
    </c:if>
</tags:master>
