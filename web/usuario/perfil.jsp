<%-- 
    Document   : perfil
    Created on : 3 mar 2025, 11:37:18
    Author     : Carlos
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>${usuarioPerfil}</title>
        <link rel="stylesheet" href="../styles/style.css"/>
        <script src="../js/verExperiencia.js"></script>
    </head>
    <body>
        <header>
            <a href="Inicio"><h1>Inicio</h1></a>
            <div class="cabeceraUsuario">
                <div>
                    <a href="Perfil?idPerfilUsuario=${usuario.id}" ><h3>Usuario: ${usuario}</h3></a>
                    <p>Tipo: ${usuario.tipo}</p>
                    <a href="ControladorCerrarSesion">Cerrar sesión</a>
                </div>
            </div>
        </header>

        <main>
            <h2>Experiencias</h2>
            <c:choose>
                <c:when  test="${experiencias != []}">
                    <c:forEach var="experiencia" items="${experiencias}">
                        <c:if test="${(experiencia.publico && experiencia.usuario.id != usuario.id) || (experiencia.usuario.id == usuario.id)}">
                            <div id="${experiencia.id}" class="experiencia" onclick="verExperiencia(${experiencia.id})">

                                <section class="tituloExperiencia">
                                    <section>
                                        <h2>${experiencia.titulo}
                                            <c:if test="${experiencia.usuario.id == usuario.id}">
                                                <span class="publicoPrivado">${experiencia.publico?"Público":"Privado"}</span>
                                            </c:if>
                                        </h2>
                                        <span> por <a href="Perfil?idPerfilUsuario=${experiencia.usuario.id}">
                                                <c:choose>
                                                    <c:when test="${experiencia.usuario != usuario}">${experiencia.usuario}</c:when>
                                                    <c:otherwise> ti (${experiencia.usuario}) </c:otherwise>
                                                </c:choose> 
                                            </a>

                                        </span>
                                    </section>  
                                </section>
                                <p>${experiencia.descripcion}</p>

                            </div>
                        </c:if>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <section id="containerSinResultados">
                        <p>Sin posts de experiencias. <c:if test="${usuario.id == usuarioPerfil.id}"><a href="Inicio">Crear experiencia</a></c:if></p>
                        </section>
                </c:otherwise>
            </c:choose>

        </main>
    </body>
</html>
