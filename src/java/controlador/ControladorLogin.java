/*
 * Controlador Login
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
import javax.servlet.http.HttpSession;
import modelo.entidades.Usuario;
import modelo.servicio.ServicioUsuario;

/**
 *
 * @author usuario
 */
@WebServlet(name = "ControladorLogin", urlPatterns = {"/Login" , "/ControladorLogin"})
public class ControladorLogin extends HttpServlet {

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
        getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
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
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String error = "";
        
        if(email == null || password == null || email.isEmpty() || password.isEmpty()){
            error = "El e-mail y la contraseña son obligatorias";
        } else{
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
            ServicioUsuario su = new ServicioUsuario(emf);
            Usuario u = su.validarUsuario(email, password);
            emf.close();
            
            
            if(u != null && u.getTipo().equals("usuario") && u.isActivo()){//Si el usuario existe, es de tipo usuario y está activo
                HttpSession sesion = request.getSession();
                sesion.setAttribute("usuario", u);
                response.sendRedirect("usuario/Inicio");
                return;
            } else if(u != null && u.getTipo().equals("admin")){//Si existe el usuario y es admin
                HttpSession sesion = request.getSession();
                sesion.setAttribute("usuario", u);
                response.sendRedirect("admin/PanelAdministracion");
                return;
            } else if(u != null && !u.isActivo()){//Si no está activo
                error = "La cuenta no está activa, tiene que esperar a que un admin se la active.";
            }else{//Si las credenciales son incorrectas
                error = "e-mail o contraseña incorrecta";
            }
        }
        request.setAttribute("error", error);
        getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
