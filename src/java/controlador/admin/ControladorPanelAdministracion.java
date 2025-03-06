/*
 * Controlador Panel de Administación
 */
package controlador.admin;

import java.io.IOException;
import java.util.List;
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
 * @author usuario
 */
@WebServlet(name = "ControladorPanelAdministracion", urlPatterns = {"/admin/PanelAdministracion","/admin/ControladorPanelAdministracion"})
public class ControladorPanelAdministracion extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code."></code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
        ServicioUsuario su = new ServicioUsuario(emf);
        
        //Guarda la lista completa de usuarios
        List<Usuario> usuarios = su.findUsuarioEntities();
        //El método removeIf() filtra en una lista eliminando los elementos que cumplan la condición
        usuarios.removeIf(u -> u.getTipo().equals("admin"));
        
        request.setAttribute("usuarios", usuarios);
        getServletContext().getRequestDispatcher("/admin/panelAdministracion.jsp").forward(request, response);
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
