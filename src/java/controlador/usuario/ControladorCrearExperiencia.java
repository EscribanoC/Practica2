/*
 * Controlador Crear Experiencia
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
import modelo.entidades.ExperienciaViaje;
import modelo.entidades.Usuario;
import modelo.servicio.ServicioExperienciaViaje;

/**
 *
 * @author Carlos
 */
@WebServlet(name = "ControladorCrearExperiencia", urlPatterns = {"/usuario/ControladorCrearExperiencia"})
public class ControladorCrearExperiencia extends HttpServlet {

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
        getServletContext().getRequestDispatcher("Inicio").forward(request, response);
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
        HttpSession sesion = request.getSession();
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");
        String titulo = request.getParameter("titulo");
        String fecha = request.getParameter("fecha");
        String descripcion = request.getParameter("descripcion");
        //String error = "";
        if (titulo.isEmpty() || titulo == null
                || fecha.isEmpty() || fecha == null
                || descripcion.isEmpty() || descripcion == null) {//Si los campos están vacíos
            //error = "Los campos no pueden estar vacío";
            response.sendRedirect("Inicio");
            return;
        }

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
        ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);

        //Crea la experiencia
        ExperienciaViaje ev = new ExperienciaViaje();
        ev.setTitulo(titulo);
        ev.setDescripcion(descripcion);
        ev.setUsuario(usuario);
        // Definir el formato del string
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Convertir el String a Date
            Date fechaInicio = formato.parse(fecha);
            ev.setFechaInicio(fechaInicio);
            //La guarda en la base de datos
            sev.create(ev);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        emf.close();//Se cierra el entity manager

        //Redirige al controlador
        response.sendRedirect("Inicio");
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
