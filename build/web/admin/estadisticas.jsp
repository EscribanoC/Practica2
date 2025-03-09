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
        <div class="contenedorGraficas">

            <div class="contenedorGraficas-grafica1">
                <h2>Número total de experiencias por Usuario</h2>
                <br>
                <figure class="highcharts-figure">
                    <div id="container1"></div>
                </figure>
            </div>
            <div class="contenedorGraficas-grafica2">
                <h2>Número total de actividades por Experiencia entre dos fechas</h2>
                <br>
                <div>
                    <input type="date" name="fecha1" id="fecha1">
                    <input type="date" name="fecha2" id="fecha2">
                    <button onclick="cargarGraficaExperiencias()" class="btnCargarGrafica">Cargar Experiencias</button>
                </div>
                <figure class="highcharts-figure">
                    <div id="container2"></div>
                </figure>
            </div>
        </div>

        <script src="./js/estadisticaUsuarios.js"></script>
        <script src="./js/estadisticaExperiencias.js"></script>
    </body>
</html>
