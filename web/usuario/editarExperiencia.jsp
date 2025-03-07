<%-- 
    Document   : editarExperiencia
    Created on : 4 mar 2025, 21:32:25
    Author     : Carlos
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Editar Experiencia/${experiencia}</title>
        <link rel="stylesheet" href="../styles/style.css"/>
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
            <div class="container-crearExperiencia container-inicio">
                <h2>Editar Experiencia</h2>
                <form method="post">
                    <section>
                        <input type="hidden" name="idExperiencia" value="${experiencia.id}">
                        <input type="text" name="tituloExperiencia" placeholder="Título" value="${experiencia.titulo}">
                        <input type="date" name="fechaExperiencia" value="${experienciaFecha}">
                    </section>

                    <textarea name="descripcionExperiencia" placeholder="Escribe una descripción para la experiencia..." rows="6">${experiencia.descripcion}</textarea>
                    <h3>Actividades</h3>
                    <ul class="listaActividades">
                        <c:if test="${!experiencia.actividades.isEmpty()}">
                            <c:forEach var="actividad" items="${experiencia.actividades}">
                                <li class="actividad">${actividad}</li>
                                </c:forEach>
                            </c:if>
                        <li><button class="btnAnadirAct">Añadir Actividad</button></li>
                    </ul>
                    
                    <button name="tipoSubmit" value="Guardar Experiencia" type="submit">Guardar Experiencia</button>
                </form>


                <!-- Ventana Modal: Añadir actividad-->
                <div id="modal" class="modal">
                    <div class="modalContenido">
                        <div class="tituloEditarActividad"> 
                            <h2>Añadir Actividad</h2>
                            <span onclick="document.getElementById('modal').style.display = 'none'">
                                <svg  xmlns="http://www.w3.org/2000/svg"  width="24"  height="24"  viewBox="0 0 24 24"  fill="none"  stroke="currentColor"  stroke-width="2"  stroke-linecap="round"  stroke-linejoin="round"  class="icon icon-tabler icons-tabler-outline icon-tabler-x"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M18 6l-12 12" /><path d="M6 6l12 12" /></svg>
                            </span>
                        </div>
                        <form method="post" class="formularioEditarActividad" enctype="multipart/form-data">
                            <section>
                                <input type="hidden" name="idActividad" value="${actividad.id}">
                                <input type="text" name="tituloActividad" placeholder="Título" value="${actividad.titulo}">
                                <input type="date" name="fechaActividad" value="${actividadFecha}">
                            </section>
                            <input type="hidden" name="tipoSubmit" value="Añadir Actividad">
                            <textarea name="descripcionActividad" placeholder="Escribe una descripción para la actividad...">${actividad.descripcion}</textarea>
                            <input type="file" name="imagen">
                            <button type="submit" id="btnGuardarActividad" type="submit" >Añadir Actividad</button>
                        </form>
                    </div>
                </div>
                <!-- Fin de Ventana Modal: Añadir Actividad-->
                <a href="Experiencia?idExperiencia=${experiencia.id}">Volver</a>
                <c:if test="${error != ''}"><p class="error">${error}</p></c:if>
            </div>
        </main>
        <script>
            const btnAnadirAct = document.querySelector(".btnAnadirAct");
            const modal = document.querySelector(".modal");
            btnAnadirAct.addEventListener("click", (e) => {
                e.preventDefault();
                document.getElementById('modal').style.display = 'flex';
            });

            window.onclick = function (event) {
                if (event.target === modal) {
                    modal.style.display = "none";
                }
            };

        </script>
        <!-- SI TENGO TIEMPO
        <script>
            const btnGuardarActividad = document.querySelector(".btnGuardarActividad");

            //Permite codificar la codificación de la respuesta en la petición fetch
            var cabecera = new Headers();
            cabecera.append('Content-Type', 'text/plain; charset=ISO-8859-1');
            cabecera.append('method', 'POST');
            cabecera.append('body',  })
     
            //Evento que guardará una actividad
            btnGuardarActividad.addEventListener("click", (e) => {
                e.preventDefault();
                
                fetch("ControladorFiltroExperiencias", cabecera)
                        .then(function (response) {
                            return response.arrayBuffer();
                        })
                        .then(data => {
                            const decoder = new TextDecoder('ISO-8859-1');
                            data = decoder.decode(data);
                            document.querySelector("#listaExperiencias").innerHTML = data;
                        })
                        .catch((error) => {
                            console.error('Error: ' + error);
                        });
            });
            
        </script>
        -->
    </body>
</html>
