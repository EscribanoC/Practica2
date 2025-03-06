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
        String opinion = request.getParameter("nuevaOpinion");
        String idExperiencia = request.getParameter("idExperiencia");

        if (opinion == null || opinion.isEmpty()) {
            response.sendRedirect("Experiencia?idExperiencia=" + idExperiencia);
            return;
        }

        HttpSession sesion = request.getSession();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
        ServicioOpinion so = new ServicioOpinion(emf);
        ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);

        try {
            ExperienciaViaje ev = sev.findExperienciaViaje(Long.valueOf(idExperiencia));
            Opinion o = new Opinion();
            o.setContenido(opinion);
            o.setExperiencia(ev);
            o.setUsuario((Usuario) sesion.getAttribute("usuario"));
            so.create(o);
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
