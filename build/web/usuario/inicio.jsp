<%-- 
    Document   : inicio
    Created on : 25 feb 2025, 20:46:49
    Author     : Carlos
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Inicio</title>
        <link rel="stylesheet" href="../styles/style.css"/>
        <script src="../js/filtrarExperiencias.js"></script>
        <script src="../js/verExperiencia.js"></script>
    </head>
    <body onload="filtrarExperiencias()">
        <header>
            <h1>Inicio</h1>
            <div class="cabeceraUsuario">
                <input type="text" id="filtroBusqueda" oninput="filtrarExperiencias()" placeholder="Buscar experiencia...">
                <div>
                    <a href="Perfil?idPerfilUsuario=${usuario.id}" ><h3>Usuario: ${usuario}</h3></a>
                    <p>Tipo: ${usuario.tipo}</p>
                    <a href="ControladorCerrarSesion">Cerrar sesión</a>
                </div>
            </div>

        </header>

        <main>
            <div class="container-crearExperiencia container-inicio">
                <h2>Crear Experiencia</h2>
                <form method="post" action="ControladorCrearExperiencia">
                    <section>
                        <input type="text" name="titulo" placeholder="Título">
                        <input type="date" name="fecha">
                    </section>

                    <textarea name="descripcion" placeholder="Escribe una descripción para la experiencia..." rows="6"></textarea>
                    <button class="botonFormulario" type="submit">Publicar</button>
                </form>
                <c:if test="${error != null}"><p class="error">${error}</p></c:if>
            </div>
            <div class="listaExperiencias container-inicio" id="listaExperiencias">

            </div>
        </main>
    </body>
</html>
