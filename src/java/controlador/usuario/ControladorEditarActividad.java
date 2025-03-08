/*
 * Controlador Editar Actividad
 */
package controlador.usuario;

import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.entidades.Actividad;
import modelo.servicio.ServicioActividad;

/**
 *
 * @author Carlos
 */
@WebServlet(name = "ControladorEditarActividad", urlPatterns = {"/usuario/ControladorEditarActividad"})
public class ControladorEditarActividad extends HttpServlet {

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

        if (idActividad == null || idActividad.isEmpty()) {//Si el id de la actividad es null o está vacía 
            response.sendRedirect("Inicio");//Al cliente se le envía al inicio
            return;
        }

        Actividad a = null;

        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
            ServicioActividad sa = new ServicioActividad(emf);

            //Busca la actividad
            a = sa.findActividad(Long.valueOf(idActividad));
            emf.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (a == null) {//Si la actividad no existe
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"error\": \"Usuario no encontrado\"}");

        } else {
            // Defino el formato del string para la fecha
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = formato.format(a.getFecha());

            String jsonResponse = String.format(
                    "{\"id\": \"%s\", \"titulo\": \"%s\", \"fecha\": \"%s\", \"descripcion\": \"%s\", \"imagen\": \"%s\"}",
                    a.getId(),
                    a.getTitulo(),
                    fecha,
                    a.getDescripcion(),
                    a.getImagenes()
            );

            // Establece el tipo de contenido como JSON, el tipo de encode
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);//Devuelve la respuesta
        }
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
