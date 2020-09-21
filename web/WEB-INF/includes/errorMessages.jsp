<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${not empty errorMessages && errorMessages.size() > 0}">
    <div id="errorMessages" class="alert alert-danger" role="alert">
        <ul>
            <c:forEach items="${sessionScope.errorMessages}" var="myItem">
                <li>${myItem.}</li>
            </c:forEach>
        </ul>
    </div>
</c:if>
