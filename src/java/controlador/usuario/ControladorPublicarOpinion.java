/*
 * Controlador Publica Opinión
 */
package controlador.usuario;

import java.io.IOException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.entidades.ExperienciaViaje;
import modelo.entidades.Opinion;
import modelo.entidades.Usuario;
import modelo.servicio.ServicioExperienciaViaje;
import modelo.servicio.ServicioOpinion;

/**
 *
 * @author Carlos
 */
@WebServlet(name = "ControladorPublicarOpinion", urlPatterns = {"/usuario/ControladorPublicarOpinion"})
public class ControladorPublicarOpinion extends HttpServlet {

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
        response.sendRedirect("Experiencia?idExperiencia=" + idExperiencia);
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
        //Recoge los campos de la opinion a publicar y el id de la experiencia
        String opinion = request.getParameter("nuevaOpinion");
        String idExperiencia = request.getParameter("idExperiencia");

        if (opinion == null || opinion.isEmpty()) {//Si la opinión está vacía o no existe
            response.sendRedirect("Experiencia?idExperiencia=" + idExperiencia);//Redirige a la página de la experiencia
            return;
        }

        //Creación de los servicios y de la sesión
        HttpSession sesion = request.getSession();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
        ServicioOpinion so = new ServicioOpinion(emf);
        ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);

        try {
            //Recoge la experiencia
            ExperienciaViaje ev = sev.findExperienciaViaje(Long.valueOf(idExperiencia));
            //Crea una opinión
            Opinion o = new Opinion();
            o.setContenido(opinion);
            o.setExperiencia(ev);
            o.setUsuario((Usuario) sesion.getAttribute("usuario"));
            so.create(o);//Se crea en la base de datos
        } catch (Exception e) {
            e.printStackTrace();
        }
        emf.close();

        response.sendRedirect("Experiencia?idExperiencia=" + idExperiencia);
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
