/*
 * Controlador Crear Actividad
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
import modelo.entidades.Actividad;
import modelo.entidades.ExperienciaViaje;
import modelo.servicio.ServicioActividad;
import modelo.servicio.ServicioExperienciaViaje;

/**
 *
 * @author Carlos
 */
@WebServlet(name = "ControladorCrearActividad", urlPatterns = {"/usuario/ControladorCrearActividad"})
public class ControladorCrearActividad extends HttpServlet {

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

        //Creación de servicios
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
        ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);
        ServicioActividad sa = new ServicioActividad(emf);
        ExperienciaViaje experienciaOriginal = sev.findExperienciaViaje(Long.valueOf(idExperiencia));

        // Definir el formato del string
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        request.setAttribute("experiencia", experienciaOriginal);
        //Para recuperar la fecha de inico de la experiencia en la vista, se tiene que parsear al formato 'yyyy-MM-dd
        request.setAttribute("experienciaFecha", formato.format(experienciaOriginal.getFechaInicio()));

        //Recoge los parámetros de la actividad a añadir
        String tituloActividad = request.getParameter("tituloActividad");
        String fechaActividad = request.getParameter("fechaActividad");
        String descripcionActividad = request.getParameter("descripcionActividad");

        if (tituloActividad == null || tituloActividad.isEmpty()
                || fechaActividad == null || fechaActividad.isEmpty()
                || descripcionActividad == null || descripcionActividad.isEmpty()) {//Si los campos de la actividad están vacíos
            error = "Los campos de la actividad no pueden estar vacíos";
            request.setAttribute("error", error);
            getServletContext().getRequestDispatcher("/usuario/editarExperiencia.jsp").forward(request, response);
            return;
        }

        //TODO Añadir imágenes
        try {
            //Crea la actividad
            Actividad a = new Actividad();
            a.setTitulo(tituloActividad);
            a.setDescripcion(descripcionActividad);
            a.setExperiencia(experienciaOriginal);

            // Convertir el String a Date
            Date fechaNueva = formato.parse(fechaActividad);
            a.setFecha(fechaNueva);
            sa.create(a);

        } catch (Exception e) {
            error = "Error: " + e.getMessage();
        } finally {
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
