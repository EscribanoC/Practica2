/*
 * Controlador Eliminar Experiencia
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
import javax.servlet.http.HttpSession;
import modelo.entidades.ExperienciaViaje;
import modelo.entidades.Usuario;
import modelo.servicio.ServicioExperienciaViaje;
import modelo.servicio.exceptions.NonexistentEntityException;

/**
 *
 * @author usuario
 */
@WebServlet(name = "ControladorEliminarExperiencia", urlPatterns = {"/usuario/ControladorEliminarExperiencia"})
public class ControladorEliminarExperiencia extends HttpServlet {

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
        String idExperiencia = request.getParameter("idExperiencia");
        
        if(idExperiencia == null || idExperiencia.isEmpty()){//Comprueba que el id existe y no está vacío
            response.sendRedirect("Inicio");//Redirige el cliente a la vista inicio
            return;
        }
        
        //Creación de servicio y sesión
        HttpSession sesion = request.getSession();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
        ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);
        
        //Recoge la experiencia a eliminar
        ExperienciaViaje e = sev.findExperienciaViaje(Long.valueOf(idExperiencia));
        
        
        //Comprueba que el usuario al que pertence la experiencia es el mimso que el de la sesión
        if(!e.getUsuario().equals((Usuario)sesion.getAttribute("usuario"))){
            emf.close();
            response.sendRedirect("Inicio");//Redirige el cliente a la vista inicio
            return;
        }
        
        try {//Elimina la experiencia con todos los elementos que dependan de él
            sev.destroy(e.getId());
            
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladorEliminarExperiencia.class.getName()).log(Level.SEVERE, null, ex);
        }
        emf.close();
        
        response.sendRedirect("Inicio");//Redirige a la pantalla de inicio
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
