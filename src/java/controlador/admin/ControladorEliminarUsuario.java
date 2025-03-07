/*
 * Controlador Eliminar Usuario
 */
package controlador.admin;

import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.entidades.Usuario;
import modelo.servicio.ServicioUsuario;

/**
 *
 * @author Carlos
 */
@WebServlet(name = "ControladorEliminarUsuario", urlPatterns = {"/admin/ControladorEliminarUsuario"})
public class ControladorEliminarUsuario extends HttpServlet {

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
        String idUsuario = request.getParameter("idUsuario");

        if (idUsuario == null || idUsuario.isEmpty()) {
            response.sendRedirect("PanelAdministracion");
            return;
        }

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
        ServicioUsuario su = new ServicioUsuario(emf);

        try {
            Usuario u = su.findUsuario(Long.valueOf(idUsuario));

            if (u == null) {
                response.sendRedirect("PanelAdministracion");
                return;
            }

            su.destroy(Long.valueOf(idUsuario));

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            emf.close();
        }

        response.sendRedirect("PanelAdministracion");
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
