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
            <h1><a href="Inicio">myExperience</a></h1>
            <div class="cabeceraUsuario">
                <div>
                    <a href="Perfil?idPerfilUsuario=${usuario.id}" ><h3>Usuario: ${usuario}</h3></a>
                    <p>Tipo: ${usuario.tipo}</p>
                    <a href="ControladorCerrarSesion">Cerrar sesi�n</a>
                </div>
            </div>
        </header>

        <main>
            <div class="contenedorExperienciasPerfil">
                <h2 id="tituloPerfil">${usuario} - Experiencias</h2>
                <c:choose>
                    <c:when  test="${experiencias != []}">
                        <c:forEach var="experiencia" items="${experiencias}">
                            <c:if test="${(experiencia.publico && experiencia.usuario.id != usuario.id) || (experiencia.usuario.id == usuario.id)}">
                                <div class="contenedorExperiencia" onclick="verExperiencia(${experiencia.id})">
                                    <div id="${experiencia.id}" class="experiencia" >

                                        <section class="tituloExperiencia">
                                            <section>
                                                <h3>${experiencia.titulo}
                                                    <c:if test="${experiencia.usuario.id == usuario.id}">
                                                        <span class="publicoPrivado">${experiencia.publico?"P�blico":"Privado"}</span>
                                                    </c:if>
                                                </h3>
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
            </div>
        </main>
    </body>
</html>
