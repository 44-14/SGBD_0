<%--
  Created by IntelliJ IDEA.
  User: Code
  Date: 21-09-20
  Time: 04:14
  To change this template use File | Settings | File Templates.
--%>

<!-- Source OP https://www.informit.com/articles/article.aspx?p=30946&seqNum=7 -->


<%-- Show all request parameters and their values --%>
<p><font size='5'>
    All Request Parameters:
</font><p>

<%-- For every String[] item of paramValues... --%>
<c:forEach var='parameter' items='${paramValues}'>
    <ul>
            <%-- Show the key, which is the request parameter
                 name --%>
        <li><b><c:out value='${parameter.key}'/></b>:</li>

            <%-- Iterate over the values -- a String[] --
                 associated with this request parameter --%>
        <c:forEach var='value' items='${parameter.value}'>
            <%-- Show the String value --%>
            <c:out value='${value}'/>
        </c:forEach>
    </ul>
</c:forEach>




<%-- AFFICHER TOUS LES ATTRIBUTS =>  KEY OBJET + VALUE  => foncitonne bien pour les attributs monovaleurs mais pas pour genre un objet contenant des champs--%>
<%-- Remplacer sessionScope par pageScope ou requestScope ou applicationScope fonctionne

<%-- Loop over the JSTL implicit object (= pageScope ou requestScope etc qui sont des objets implicites)
, stored in the page-scoped attribute named scope that was set above.
   That implicit object is a map --%>
<c:forEach items='${sessionScope}' var='p'>
    <ul>
            <%-- Display the key of the current item, which
                 represents the parameter name --%>
        <li>Parameter Name: <c:out value='${p.key}'/></li>

            <%-- Display the value of the current item, which
                 represents the parameter value --%>
        <li>Parameter Value: <c:out value='${p.value}'/></li>
    </ul>
</c:forEach>




<!-- PEPITE -->
<!-- Display all declared fields and their values -->
<%-- Remplacer your_object_here par ${sessionScope.signedUser} par exemple
listera tous les champs et leurs valeurs pour l'objet signedUser donc lastname firstname etc --%>
<c:set var="object" value="${your_object_here}" />
<c:if test="${not empty object['class'].declaredFields}">
    <h2>Declared fields <em>&dollar;{object.name}</em></h2>
    <ul>
        <c:forEach var="field" items="${object['class'].declaredFields}">
            <c:catch><li><span style="font-weight: bold">
                ${field.name}: </span>${object[field.name]}</li>
            </c:catch>
        </c:forEach>
    </ul>
</c:if>




<!-- PEPITE -->
<!-- Display all declared methods. -->
<%-- Remplacer your_object_here par ${sessionScope.signedUser} par exemple --%>
<c:set var="object" value="${your_object_here}" />
<c:if test="${not empty object['class'].declaredMethods}">
    <h2>Declared methods<em>&lt;% object.getName() %&gt;</em></h2>
    <ul>
        <c:forEach var="method" items="${object['class'].declaredMethods}">
            <c:catch><li>${method.name}</li></c:catch>
        </c:forEach>
    </ul>
</c:if>


<!--  Reprend les deux pépites au dessus -->
<!-- source : https://stackoverflow.com/questions/16302554/view-all-fields-properties-of-bean-in-jsp-jstl -->

<p class="TODO <your name> PRINT OBJECT PROPERTIES">
    <c:set var="object" value="${your_object_here}" />
<h2><b>Object:&nbsp; ${object['class']} </b></h2>
<h3><b>Declared fields</b></h3>
<c:if test="${!empty object['class'].declaredFields}">
    <ul>
        <c:forEach var="attr" items="${object['class'].declaredFields}">
            <c:catch><li><b>${attr.name}</b>:&nbsp; ${object[attr.name]}</li></c:catch>
        </c:forEach>
    </ul>
</c:if>
<c:if test="${empty object['class'].declaredFields}">No declared fields</c:if>
<h3><b>Declared methods</b></h3>
<c:if test="${!empty object['class'].declaredMethods}">
    <ul>
        <c:forEach var="attr" items="${object['class'].declaredMethods}">
            <c:catch><li><b>${attr.name}</b>(...)</li></c:catch>
        </c:forEach>
    </ul>
</c:if>
<c:if test="${empty object['class'].declaredMethods}">No declared methods</c:if>
</p>
