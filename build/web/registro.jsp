<%-- 
    Document   : registro
    Created on : 24 feb 2025, 10:15:04
    Author     : usuario
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setBundle var="login" basename="bundle.login"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Registro de Usuario</title>
        <link rel="stylesheet" href="./styles/styleLogin.css"/>
    </head>
    <body>
        <div class="contenedorInicio">
            <h1>myExperience</h1>
            <form method="post">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" placeholder="<fmt:message key="emailEjemplo" bundle="${login}"/>">
                <br>
                <label for="password"><fmt:message key="contrasena" bundle="${login}"/>:</label>
                <input type="password" id="password" name="password">
                <br>
                <label for="nombre"><fmt:message key="nombre" bundle="${login}"/>:</label>
                <input type="text" id="nombre" name="nombre" placeholder="<fmt:message key="nombreEjemplo" bundle="${login}"/>">
                <br>
                <label for="apellidos"><fmt:message key="apellidos" bundle="${login}"/>:</label>
                <input type="text" id="apellidos" name="apellidos" placeholder="<fmt:message key="apellidoEjemplo" bundle="${login}"/>">
                <br>
                <input type="submit" value="<fmt:message key="crearUsuario" bundle="${login}"/>">
            </form>
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>
            <a href="Login"><fmt:message key="yaTengoCuenta" bundle="${login}"/></a>
        </div>
    </body>
</html>
