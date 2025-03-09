<%-- 
    Document   : filtroExperiencias
    Created on : 2 mar 2025, 19:40:39
    Author     : Carlos
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:choose>
    <c:when  test="${experiencias != []}">
        <c:forEach var="experiencia" items="${experiencias}">
            <fmt:setLocale value="${pageContext.request.locale}" />
            <c:if test="${experiencia.publico}">
                <div class="contenedorExperiencia" onclick="verExperiencia(${experiencia.id})">
                    <div id="${experiencia.id}" class="experiencia" ">
                        <section>
                            <section>
                                <h2>${experiencia.titulo}</h2>
                                <span> por <a href="Perfil?idPerfilUsuario=${experiencia.usuario.id}">
                                        <c:choose>
                                            <c:when test="${experiencia.usuario != usuario}">${experiencia.usuario}</c:when>
                                            <c:otherwise> ti (${experiencia.usuario}) </c:otherwise>
                                        </c:choose> 
                                    </a>

                                </span>
                            </section>
                        </section>
                        <p> <fmt:formatDate value="${experiencia.fechaInicio}" dateStyle="short"/></p>
                        <p>${experiencia.descripcion}</p>
                    </div>
                    <div>
                        <c:if test="${experiencia.actividades[0].imagenes != null}">
                            <figure class="imagenChica">
                                <img src="./media/${experiencia.actividades[0].imagenes}" alt="imagenActividad"/>
                            </figure>
                        </c:if>
                    </div>
                </div>
            </c:if>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <section id="containerSinResultados">
            <p>Sin resultados</p>
        </section>
    </c:otherwise>
</c:choose>

