/*
 * Controlador Filtro Experiencias
 */
package controlador.usuario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.entidades.ExperienciaViaje;
import modelo.servicio.ServicioExperienciaViaje;

/**
 *
 * @author Carlos
 */
@WebServlet(name = "ControladorFiltroExperiencias", urlPatterns = {"/usuario/ControladorFiltroExperiencias"})
public class ControladorFiltroExperiencias extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        //HttpSession sesion = request.getSession();
        //Recoge el campo de filtro
        String filtro = request.getParameter("filtro");
        
        if (filtro == null) {//Si el campo es nulo
            filtro = "";
        }
        //Creación de servicio
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
        ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);
        //Recoge la lista total de experiencias
        List<ExperienciaViaje> experiencias = sev.findExperienciaViajeEntities();
        emf.close();
        //Edita las letras del filtro a minúsculas para poder comparar
        filtro = filtro.toLowerCase().trim();
        //Creación de nueva lista con las experiencias filtradas
        List<ExperienciaViaje> filtradas = new ArrayList();
        
        for (ExperienciaViaje ev: experiencias) {//Por cada experiencia
            if (ev.getTitulo().toLowerCase().contains(filtro) || 
                    ev.getDescripcion().toLowerCase().contains(filtro)) {//Si el título o la descripción contiene el campo del filtro
                filtradas.add(ev);//Se añade a la nueva lista de experiencias filtradas
            }
        }
        //Se añade la nueva lista al apetición como atributo
        request.setAttribute("experiencias", filtradas);
        getServletContext().getRequestDispatcher("/usuario/filtroExperiencias.jsp").forward(request, response);
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
