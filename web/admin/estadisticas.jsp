<%-- 
    Document   : estadisticas
    Created on : 7 mar 2025, 16:19:35
    Author     : Carlos
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Estadísticas</title>
        <link rel="stylesheet" href="./styles/styleAdmin.css"/>
        <link rel="stylesheet" href="./styles/estadisticas.css"/>
        <script src="https://code.highcharts.com/highcharts.js"></script>
        <script src="https://code.highcharts.com/modules/exporting.js"></script>
        <script src="https://code.highcharts.com/modules/export-data.js"></script>
        <script src="https://code.highcharts.com/modules/accessibility.js"></script>
    </head>
    <body>
        <header>
            <h1>Panel de Administración</h1>
            <nav>
                <ul id="listaUsuarios">
                    <li><a href="PanelAdministracion">Gestión de Usuarios</a></li>
                    <li><a href="Estadisticas">Estadísticas</a></li>
                </ul>
            </nav>
            <div class="cabeceraUsuario">
                <div>
                    <h3>Usuario: ${usuario}</h3>
                    <p>Tipo: ${usuario.tipo}</p>
                    <a href="../usuario/ControladorCerrarSesion">Cerrar sesión</a>
                </div>
            </div>
        </header>
        <figure class="highcharts-figure">
            <div id="container"></div>
            <p class="highcharts-description">
                Gráfica que muestra los usuarios con el número total de sus experiencias publicadas.
            </p>
        </figure>

        <script src="./js/estadisticas.js"></script>
    </body>
</html>
