/*
 * Controlador Perfil
 */
package controlador.usuario;

import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.entidades.ExperienciaViaje;
import modelo.entidades.Usuario;
import modelo.servicio.ServicioUsuario;

/**
 *
 * @author Carlos
 */
@WebServlet(name = "ControladorPerfil", urlPatterns = {"/usuario/Perfil"})
public class ControladorPerfil extends HttpServlet {

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
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
        ServicioUsuario su = new ServicioUsuario(emf);

        //Recoge el id del perfil que se verá en la vista
        String idPerfilUsuario = request.getParameter("idPerfilUsuario");
        
        if (idPerfilUsuario == null || idPerfilUsuario.isEmpty()) {//Si el id es null o está vacío
            response.sendRedirect("Inicio");//Vuelve al inicio
            return;
        }
        
        //Busca al usuario con ese id y su lista de experiencias
        Usuario u = su.findUsuario(Long.valueOf(idPerfilUsuario));
        List <ExperienciaViaje> ev = u.getExperiencias();
        emf.close();
        
        //Guarda ambos elementos en la petición
        request.setAttribute("usuarioPerfil", u);
        request.setAttribute("experiencias", ev);
        
        //Va a la vista de perfil
        getServletContext().getRequestDispatcher("/usuario/perfil.jsp").forward(request, response);
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
