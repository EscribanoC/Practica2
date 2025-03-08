/*
 * Controlador Eliminar Actividad
 */
package controlador.usuario;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.entidades.Actividad;
import modelo.servicio.ServicioActividad;
import modelo.servicio.exceptions.NonexistentEntityException;

/**
 *
 * @author Carlos
 */
@WebServlet(name = "ControladorEliminarActividad", urlPatterns = {"/usuario/ControladorEliminarActividad"})
public class ControladorEliminarActividad extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idActividad = request.getParameter("idActividad");

        if (idActividad == null || idActividad.isEmpty()) {//Comprueba que el id existe y no está vacío
            response.sendRedirect("Inicio");//Redirige el cliente a la vista inicio
            return;
        }
        //Creación de servicio
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
        ServicioActividad sa = new ServicioActividad(emf);
        //Recoge la actividad
        Actividad a = sa.findActividad(Long.valueOf(idActividad));

        if (a != null) {//Si la actividad existe
            try {
                sa.destroy(Long.valueOf(idActividad));//Elimina la actividad
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ControladorEliminarExperiencia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        emf.close();
        response.sendRedirect("EditarExperiencia?idExperiencia=" + a.getExperiencia().getId());//Redirige a la vista de la edición
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
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
        processRequest(request, response);
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
