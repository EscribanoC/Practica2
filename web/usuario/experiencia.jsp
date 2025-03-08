<%-- 
    Document   : experiencia
    Created on : 3 mar 2025, 17:41:44
    Author     : Carlos
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>${experiencia}</title>
        <link rel="stylesheet" href="../styles/style.css"/>
        <c:if test="${experiencia.usuario.id == usuario.id}">
            <script>
                /* Cuando se clicka en el menú de 'más opciones' se abrirá o cerrará el dropdown */
                function desplegarDropdown() {
                    document.getElementById("myDropdown").classList.toggle("mostrar");
                }

                // Cierra el dropdown después de usarlo
                window.onclick = function (event) {
                    if (!event.target.matches('.dropbtn') && !event.target.matches('.svgMasOpciones')) {
                        var dropdowns = document.getElementsByClassName("dropdown-content");
                        var i;
                        for (i = 0; i < dropdowns.length; i++) {
                            var openDropdown = dropdowns[i];
                            if (openDropdown.classList.contains('mostrar')) {
                                openDropdown.classList.remove('mostrar');
                            }
                        }
                    }
                };
            </script>
        </c:if>
    </head>
    <body>
        <header>
            <h1><a href="Inicio">myExperience</a></h1>
            <div class="cabeceraUsuario">
                <div>
                    <a href="Perfil?idPerfilUsuario=${usuario.id}" ><h3>Usuario: ${usuario}</h3></a>
                    <p>Tipo: ${usuario.tipo}</p>
                    <a href="ControladorCerrarSesion">Cerrar sesión</a>
                </div>
            </div>
        </header>
        <main>
            <div class="experienciaDesglosada">
                <h1 class="experienciaDesglosada-tituloExperiencia">Experiencia</h1>
                <c:choose>
                    <c:when test="${experiencia != null}">
                        <div class="experienciaDesglosada-Contenido">
                            <div class="experienciaDesglosada-Contenido-Titulo">
                                <h2>${experiencia}
                                    <c:if test="${experiencia.usuario.id == usuario.id}">
                                        <span class="publicoPrivado">${experiencia.publico?"Público":"Privado"}</span>
                                    </c:if>
                                </h2>
                                <c:if test="${experiencia.usuario.id == usuario.id}">
                                    <div class="dropdown">
                                        <button onclick="desplegarDropdown()" class="dropbtn">
                                            <svg class="svgMasOpciones" xmlns="http://www.w3.org/2000/svg"  width="24"  height="24"  viewBox="0 0 24 24"  fill="none"  stroke="currentColor"  stroke-width="2"  stroke-linecap="round"  stroke-linejoin="round"  class="icon icon-tabler icons-tabler-outline icon-tabler-dots"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M5 12m-1 0a1 1 0 1 0 2 0a1 1 0 1 0 -2 0" /><path d="M12 12m-1 0a1 1 0 1 0 2 0a1 1 0 1 0 -2 0" /><path d="M19 12m-1 0a1 1 0 1 0 2 0a1 1 0 1 0 -2 0" /></svg>
                                        </button>
                                        <div id="myDropdown" class="dropdown-content">
                                            <a href="EditarExperiencia?idExperiencia=${experiencia.id}">Editar</a>
                                            <a href="ControladorVisibilidadExperiencia?idExperiencia=${experiencia.id}">Cambiar a ${experiencia.publico?"Privado":"Público"}</a>
                                            <a style="cursor: pointer;" onclick="eliminarExperiencia(${experiencia.id})">Eliminar experiencia</a>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                            <p>${experiencia.descripcion}</p>
                            <c:if test="${experiencia.actividades != []}">
                                <h3>Actividades</h3>
                                <div>
                                    <ul class="listaActividades">
                                        <c:forEach var="actividad" items="${experiencia.actividades}">
                                            <li class="actividad">${actividad}</li>
                                            </c:forEach>
                                    </ul>
                                </div>
                            </c:if>

                            <div class="contenedorImagenes">
                                <c:forEach var="actividad" items="${experiencia.actividades}">
                                    <c:if test="${actividad.imagenes != null}">
                                        <figure class="imagenEnMiniatura">
                                            <img src="./media/${actividad.imagenes}" id="imagenActividad" alt="imagenActividad"/>
                                        </figure>
                                    </c:if>
                                </c:forEach>
                            </div>


                        </div>
                        <c:if test="${usuario != null}">
                            <hr>
                            <form  method="post" action="ControladorPublicarOpinion" class="experienciaDesglosada-ContainerOpinar">
                                <input type="hidden" name="idExperiencia" value="${experiencia.id}">
                                <textarea id="nuevaOpinion" name="nuevaOpinion" placeholder="Escribe tu opinión..."></textarea>
                                <button type="submit">Publicar</button>
                            </form>
                        </c:if>
                        <div class="experienciaDesglosada-Opiniones">
                            <c:forEach var="opinion" items="${experiencia.opiniones}">
                                <div class="opinion">
                                    <h4><a href="Perfil?idPerfilUsuario=${opinion.usuario.id}">${opinion.usuario}</a></h4>
                                    <p>${opinion.contenido}</p>
                                </div>
                            </c:forEach>
                        </div>

                    </c:when>
                    <c:otherwise>
                        <p id="containerSinResultados">
                            La experiencia buscada no existe.
                        </p>
                    </c:otherwise>
                </c:choose>
            </div>
        </main>
        <script>
            function eliminarExperiencia(id) {
                if (confirm("¿Estás seguro de eliminar la experiencia? Perderás toda la información: actividades e imágenes.")) {
                    window.location.href = "ControladorEliminarExperiencia?idExperiencia=" + id;
                }
            }
            ;
        </script>
    </body>
</html>
