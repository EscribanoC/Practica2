/*
 * Controlador Registro de Usuarios
 */
package controlador;

import java.io.IOException;
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
@WebServlet(name = "ControladorRegistro", urlPatterns = {"/Registro", "/ControladorRegistro"})
public class ControladorRegistro extends HttpServlet {

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
        String vista = "/registro.jsp";
        getServletContext().getRequestDispatcher(vista).forward(request, response);
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
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String error = "";
        
        //TODO validaciones de campos?
        if(nombre == null || nombre.isEmpty() ||
           apellidos == null || nombre.isEmpty() ||
           email == null || email.isEmpty() ||
           password == null || password.isEmpty()){
            error = "Los campos del formulario no pueden estar vacíos";
        } else{
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
            ServicioUsuario su = new ServicioUsuario(emf); 
            Usuario u = su.buscarPorEmail(email);
            
            if(u == null){
                u = new Usuario(email, password, nombre, apellidos);
                su.create(u);
                emf.close();
                response.sendRedirect("ControladorLogin");
                return;
            } else{
                error= "El email ya está asociado a otro usuario";
            }
        }
        request.setAttribute("error", error);
        getServletContext().getRequestDispatcher("/registro.jsp").forward(request, response);
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
