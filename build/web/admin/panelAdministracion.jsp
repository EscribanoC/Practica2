<%-- 
    Document   : panelAdministracion
    Created on : 6 mar 2025, 9:49:58
    Author     : usuario
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Administración</title>
        <link rel="stylesheet" href="./styles/styleAdmin.css"/>
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

        <main>
            <div class="contenedorPanelAdministracion">
                <h1>Gestión de Usuarios</h1>
                <div class="contenedorTablaUsuarios">
                    <table id="tablaUsuarios">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Correo</th>
                                <th>Nombre</th>
                                <th>Apellidos</th>
                                <th>Activo</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="usuario" items="${usuarios}">
                                <tr>
                                    <td>${usuario.id}</td>
                                    <td>${usuario.email}</td>
                                    <td>${usuario.nombre}</td>
                                    <td>${usuario.apellidos}</td>
                                    <td class="campoActivo">${usuario.activo?"Si":"No"}</td>
                                    <td class="botonesAdministracion">
                                        <button onclick="editarUsuario(${usuario.id})" class="btnEditar">Editar</button>
                                        <a class="btnActivarUsuario" onclick="activarUsuario('${usuario.email}', '${usuario.id}', ${usuario.activo})">
                                            ${usuario.activo?"Desactivar":"Activar"}
                                        </a>
                                        <button class="btnEliminar" onclick="eliminarUsuario('${usuario.email}', '${usuario.id}')">Eliminar Usuario</button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <!-- Ventana Modal: Editar Usuario-->
                    <div id="modal" class="modal">
                        <div class="modalContenido">
                            <div class="tituloEditarUsuario"> 
                                <h2>Editar Usuario</h2>
                                <span onclick="document.getElementById('modal').style.display = 'none'">
                                    <svg  xmlns="http://www.w3.org/2000/svg"  width="24"  height="24"  viewBox="0 0 24 24"  fill="none"  stroke="currentColor"  stroke-width="2"  stroke-linecap="round"  stroke-linejoin="round"  class="icon icon-tabler icons-tabler-outline icon-tabler-x"><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M18 6l-12 12" /><path d="M6 6l12 12" /></svg>
                                </span>
                            </div>
                            <form action="ControladorEditarUsuario" class="formularioEditarUsuario" method="post">
                                <input type="hidden" name="emailOriginal" id="emailOriginal">
                                <p>Email</p>
                                <input type="email" name="emailUsuario" id="emailUsuario">
                                <p>Contraseña</p>
                                <input type="text" name="passwordUsuario" id="passwordUsuario">
                                <p>Nombre</p>
                                <input type="text" name="nombreUsuario" id="nombreUsuario">
                                <p>Apellidos</p>
                                <input type="text" name="apellidosUsuario" id="apellidosUsuario">

                                <button class="btnGuardarUsuario" type="submit">Guardar cambios</button>
                            </form>
                        </div>
                    </div>
                    <!-- Fin de Ventana Modal: Añadir Actividad-->
                </div>
            </div>
        </main>
        <script>
            /* Comprueba cuando carga el DOm si el campo donde indica si el usuario
             está activo o no, y le asigna un color correspondiente a cada uno.*/
            document.addEventListener("DOMContentLoaded", () => {
                let camposActivo = document.querySelectorAll(".campoActivo");
                camposActivo.forEach((campo) => {
                    campo.style.backgroundColor = campo.textContent === "Si" ? "LightGreen" : "LightCoral";
                });
            });
        </script>
        <script>
            function activarUsuario(usuarioEmail, id, activo) {
                let isActivo = activo ? "desactivar" : "activar";
                if (confirm("Va a " + isActivo + " al usuario " + usuarioEmail + ". ¿Está seguro?")) {
                    window.location.href = "ControladorActivarUsuario?idUsuario=" + id;
                }
            }

        </script>
        <script>
            function eliminarUsuario(usuarioEmail, id) {
                if (confirm("Va a eliminar al usuario " + usuarioEmail + " con todas sus experiencias. ¿Está seguro? Los datos no se podrán recuperar luego.")) {
                    window.location.href = "ControladorEliminarUsuario?idUsuario=" + id;
                }
            }
        </script>
        <script src="./js/editarUsuarioAdmin.js"></script>
    </body>
</html>
