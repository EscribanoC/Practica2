<%-- 
    Document   : registro
    Created on : 24 feb 2025, 10:15:04
    Author     : usuario
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Registro de Usuario</title>
        <link rel="stylesheet" href="./styles/styleLogin.css"/>
    </head>
    <body>
        <div class="contenedorInicio">
            <h1>Registro de usuario</h1>
            <form method="post">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" placeholder="email@ejemplo.es">
                <br>
                <label for="password">Contraseña:</label>
                <input type="password" id="password" name="password">
                <br>
                <label for="nombre">Nombre:</label>
                <input type="text" id="nombre" name="nombre" placeholder="Pepe">
                <br>
                <label for="apellidos">Apellidos:</label>
                <input type="text" id="apellidos" name="apellidos" placeholder="Pérez">
                <br>
                <input type="submit" value="Crear Usuario">
            </form>
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>
            <a href="Login">Ya tengo cuenta</a>
        </div>
    </body>
</html>
