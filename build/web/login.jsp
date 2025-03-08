<%-- 
    Document   : login
    Created on : 24 feb 2025, 9:40:24
    Author     : usuario
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Inicio de Sesión</title>
        <link rel="stylesheet" href="./styles/styleLogin.css"/>
    </head>
    <body>
        <div class="contenedorInicio">
            <h1>myExperience</h1>
            <form method="post">
                <label>Email</label>
                <input type="email" name="email" required="">
                <br>
                <label>Contraseña</label>
                <input type="password" name="password" required="">
                <br>
                <input type="submit" value="Iniciar Sesión">
            </form>

            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>
            <a href="Registro">¿No tiene cuenta?</a>
        </div>
    </body>
</html>
