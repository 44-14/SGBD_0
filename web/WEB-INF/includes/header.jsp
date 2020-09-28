<%--
  Created by IntelliJ IDEA.
  User: Code
  Date: 19-08-20
  Time: 02:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>

<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>${requestScope.pageTitle}</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/resources/bootstrap-4.5.2-dist/css/bootstrap.min.css">


    <c:choose>
        <c:when test="${pageContext.request.servletPath.startsWith('/WEB-INF/views/signIn/signIn')
                     || pageContext.request.servletPath.startsWith('/WEB-INF/views/signUp/signUp')}">
              <%--   || pageContext.request.servletPath == '/WEB-INF/views/signIn/signInForm.jsp'
                     || pageContext.request.servletPath == '/WEB-INF/views/signIn/signInConfirmation.jsp'
                     || pageContext.request.servletPath == '/WEB-INF/views/signUp/signUpForm.jsp'
                     || pageContext.request.servletPath == '/WEB-INF/views/signUp/signUpConfirmation.jsp'}">
              --%>

            <link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/public/assets/css/customLandingCSS.css' />
        </c:when>
        
        <c:when test="${!pageContext.request.servletPath.startsWith('/WEB-INF/views/signIn/signIn')
                     &&!pageContext.request.servletPath.startsWith('/WEB-INF/views/signUp/signUp')}">
            <%--
                     && pageContext.request.servletPath != '/WEB-INF/views/signIn/signInForm.jsp'
                     && pageContext.request.servletPath != '/WEB-INF/views/signIn/signInConfirmation.jsp'
                     && pageContext.request.servletPath != '/WEB-INF/views/signUp/signUpForm.jsp'
                     && pageContext.request.servletPath != '/WEB-INF/views/signUp/signUpConfirmation.jsp'}">
             --%>
            <link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/public/assets/css/customCSS.css' />
        </c:when>
    </c:choose>



</head>
<body>
<div class="container">

    <!-- Include de la navbar sauf pour les signIn - signUp views -->
    <c:if test="${!pageContext.request.servletPath.startsWith('/WEB-INF/views/signIn/signIn')
                &&!pageContext.request.servletPath.startsWith('/WEB-INF/views/signUp/signUp')}">
    <%--
                && pageContext.request.servletPath != '/WEB-INF/views/signIn/signInConfirmation.jsp'
                && pageContext.request.servletPath != '/WEB-INF/views/signIn/signInConfirmation.jsp'
                && pageContext.request.servletPath != '/WEB-INF/views/signUp/signUpForm.jsp'
                && pageContext.request.servletPath != '/WEB-INF/views/signUp/signUpConfirmation.jsp'}">
     --%>
        <jsp:include page="/WEB-INF/includes/navbar.jsp"></jsp:include>
    </c:if>


    <c:choose>
        <c:when test="${not empty sessionScope.redirectErrorMessage}">
            <div class="errorMessage">
                <p class="redAlert>"> <c:out value="${sessionScope.redirectErrorMessage}"/></p>

                 <%-- permet de supprimer l'attribut en session et de ne plus afficher le message nulle part --%>
                 <c:remove var="redirectErrorMessage" scope="session" />
            </div>
        </c:when>

        <c:when test="${not empty sessionScope.redirectSuccessMessage}">
            <div class="successMessage">
                <p class="greenAlert"> <c:out value="${sessionScope.redirectSuccessMessage}"/> </p>
                <c:remove var="redirectSuccessMessage" scope="session" />
            </div>
        </c:when>
    </c:choose>


    <%--
    pageContext permet d acceder à la requête reçue par la view.jsp qui est aussi une servlet
    via pageContext.request  => on peut donc accèder aux paramètres de la requête issus de base du formulaire submitted par le client (client-side)
    requestScope permet d acceder aux attributs mis dans la requete (server-side)
    sessionScope permet d acceder aux attributs mis en session (server-side)

     Pour comprendre :  URI = context + servletPath mais ici le servletPath = chemin vers la vue .jsp qui est en fait une servlet aussi
                        Quand on fait un request.getRequestDispatcher(chemin vers une vue .jsp).forward(request,response),
                        en fait on modifie le servletPath dans l'url de la requête pour qu'ellatteigne la .jsp
                        qui va à son tour traiter la request et response

                        Ici, c'est bien le servletPath de la vue invoquée de base recevant la request et response,  à laquelle on va rajouter le header.jsp ensuite
                        Donc le retour dans l'html variera en fonction de la .jsp appelée vu qu'ici on est dans le header.jsp
    <p> contextpath : <c:out value="${pageContext.request.contextPath}"></c:out> <p>  donne   /SGBD_0_war_exploded
    <p> servletpath : <c:out value="${pageContext.request.servletPath}"></c:out> <p>  donne  /WEB-INF/views/signIn/signInForm.jsp
    <p> requestURI : <c:out value="${pageContext.request.requestURI}"></c:out> <p>    donne  /SGBD_0_war_exploded/WEB-INF/views/signIn/signInForm.jsp
    <p> requestURL : <c:out value="${pageContext.request.requestURL}"></c:out> <p>    donne  http://localhost:8080/SGBD_0_war_exploded/WEB-INF/views/signIn/signInForm.jsp
    --%>


    <%-- XL
    <jsp:include page="navbar.jsp"> </jsp:include>
    <jsp:include page="/WEB-INF/includes/error_messages.jsp"> </jsp:include>
    <jsp:include page="/WEB-INF/includes/success_messages.jsp"> </jsp:include>
    --%>



