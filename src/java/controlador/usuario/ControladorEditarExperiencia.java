/*
 * Controlador Editar Experiencia
 */
package controlador.usuario;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.entidades.Actividad;
import modelo.entidades.ExperienciaViaje;
import modelo.entidades.Usuario;
import modelo.servicio.ServicioActividad;
import modelo.servicio.ServicioExperienciaViaje;

/**
 *
 * @author Carlos
 */
@WebServlet(name = "ControladorEditarExperiencia", urlPatterns = {"/usuario/EditarExperiencia"})
public class ControladorEditarExperiencia extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idExperiencia = request.getParameter("idExperiencia");

        if (idExperiencia == null || idExperiencia.isEmpty()) {//Si el id de la experiencia es null o est� vac�a 
            response.sendRedirect("Inicio");//Al cliente se le env�a al inicio
            return;
        }

        HttpSession sesion = request.getSession();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
        ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);

        ExperienciaViaje ev = sev.findExperienciaViaje(Long.valueOf(idExperiencia));//Busca la experiencia
        emf.close();

        if (ev == null) {//Si la experiencia no existe
            response.sendRedirect("Inicio");//Al cliente se le env�a al inicio
            return;
        }

        Usuario usuarioExperiencia = ev.getUsuario();
        Usuario usuarioSesion = (Usuario) sesion.getAttribute("usuario");
        if (usuarioExperiencia.getId().compareTo(usuarioSesion.getId()) != 0) {//Si la experiencia que se intenta editar no pertenece al cliente de la sesi�n
            System.out.println("illo");
            response.sendRedirect("Inicio");//Al cliente se le env�a al inicio
            return;
        }

        request.setAttribute("experiencia", ev);//Se env�a la experiencia a la vista
        //Para recuperar la fecha de inico de la experiencia en la vista, se tiene que parsear al formato 'yyyy-MM-dd
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        request.setAttribute("experienciaFecha", formato.format(ev.getFechaInicio()));
        getServletContext().getRequestDispatcher("/usuario/editarExperiencia.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Recoge la id de la experiencia actual y el tipo de submit
        String idExperiencia = request.getParameter("idExperiencia");
        String error = "";

        //Creaci�n de servicios
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
        ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);
        ServicioActividad sa = new ServicioActividad(emf);
        ExperienciaViaje experienciaOriginal = sev.findExperienciaViaje(Long.valueOf(idExperiencia));

        // Definir el formato del string
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        request.setAttribute("experiencia", experienciaOriginal);
        //Para recuperar la fecha de inico de la experiencia en la vista, se tiene que parsear al formato 'yyyy-MM-dd
        request.setAttribute("experienciaFecha", formato.format(experienciaOriginal.getFechaInicio()));

        //System.out.println("Tipo submit" + tipoSubmit);
        //System.out.println("Entra En guardar experiencia");
        //Recoge los par�metros de la experiencia
        String tituloExperiencia = request.getParameter("tituloExperiencia");
        String fechaExperiencia = request.getParameter("fechaExperiencia");
        String descripcionExperiencia = request.getParameter("descripcionExperiencia");

        if (tituloExperiencia == null || tituloExperiencia.isEmpty()
                || fechaExperiencia == null || fechaExperiencia.isEmpty()
                || descripcionExperiencia == null || descripcionExperiencia.isEmpty()) {//Si los campos de la experiencia est�n vac�os
            error = "Los campos de la experiencia no pueden estar vac�os";
            request.setAttribute("error", error);
            getServletContext().getRequestDispatcher("/usuario/editarExperiencia.jsp").forward(request, response);
            return;
        }

        try {
            //Crea la experiencia
            ExperienciaViaje experienciaCambiada = new ExperienciaViaje();
            experienciaCambiada.setId(experienciaOriginal.getId());
            experienciaCambiada.setTitulo(tituloExperiencia);
            experienciaCambiada.setDescripcion(descripcionExperiencia);
            experienciaCambiada.setUsuario(experienciaOriginal.getUsuario());
            experienciaCambiada.setActividades(experienciaOriginal.getActividades());
            experienciaCambiada.setOpiniones(experienciaOriginal.getOpiniones());

            // Convertir el String a Date
            Date fechaInicioNueva = formato.parse(fechaExperiencia);
            experienciaCambiada.setFechaInicio(fechaInicioNueva);

            //Se edita en la base de datos
            sev.edit(experienciaCambiada);

            emf.close();
            response.sendRedirect("Experiencia?idExperiencia=" + idExperiencia);
            return;
        } catch (Exception e) {
            error = "Error: " + e.getMessage();
            emf.close();
        }

        request.setAttribute("error", error);
        // Redirige para evitar duplicados
        response.sendRedirect(request.getContextPath() + "/usuario/EditarExperiencia?idExperiencia=" + idExperiencia);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
