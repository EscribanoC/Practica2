/*
 * Controlador Visibilidad Experiencia
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
import modelo.entidades.ExperienciaViaje;
import modelo.servicio.ServicioExperienciaViaje;
import modelo.servicio.exceptions.NonexistentEntityException;

/**
 *
 * @author usuario
 */
@WebServlet(name = "ControladorVisibilidadExperiencia", urlPatterns = {"/usuario/ControladorVisibilidadExperiencia"})
public class ControladorVisibilidadExperiencia extends HttpServlet {

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
        if (idExperiencia == null || idExperiencia.isEmpty()) {
            response.sendRedirect("Inicio");
            return;
        }

        try{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
        ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);
        
        
        //Se crean las dos instancias de experiencias, la original y la que cambiará el atributo 'publico'
        ExperienciaViaje experienciaOriginal = sev.findExperienciaViaje(Long.valueOf(idExperiencia));
        ExperienciaViaje experienciaCambiada = new ExperienciaViaje();
        
        
        experienciaCambiada.setId(experienciaOriginal.getId());
        experienciaCambiada.setTitulo(experienciaOriginal.getTitulo());
        experienciaCambiada.setFechaInicio(experienciaOriginal.getFechaInicio());
        experienciaCambiada.setDescripcion(experienciaOriginal.getDescripcion());
        experienciaCambiada.setUsuario(experienciaOriginal.getUsuario());
        experienciaCambiada.setActividades(experienciaOriginal.getActividades());
        experienciaCambiada.setOpiniones(experienciaOriginal.getOpiniones());
        
        experienciaCambiada.setPublico(experienciaOriginal.isPublico()?false:true);
        sev.edit(experienciaCambiada);
        emf.close();
        
        }catch(Exception e){
            e.printStackTrace();
        }
        
        response.sendRedirect("Experiencia?idExperiencia=" + idExperiencia);
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
