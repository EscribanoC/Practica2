/*
 * Controlador Cargar Grafica de experiencias filtrada por dos fechas
 */
package controlador.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.entidades.ExperienciaViaje;
import modelo.servicio.ServicioExperienciaViaje;

/**
 *
 * @author Carlos
 */
@WebServlet(name = "ControladorCargarGraficaExperiencias", urlPatterns = {"/admin/ControladorCargarGraficaExperiencias"})
public class ControladorCargarGraficaExperiencias extends HttpServlet {

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
            throws ServletException, IOException, ParseException {
        //Creación de servicio
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
        ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        String fechaRecibida1 = request.getParameter("fecha1");
        String fechaRecibida2 = request.getParameter("fecha2");
        Date fecha1;
        Date fecha2;
        if (fechaRecibida1 == null || fechaRecibida1.isEmpty()
                || fechaRecibida2 == null || fechaRecibida2.isEmpty()) {
            fecha1 = formato.parse("2025-01-01");
            String hoy = formato.format(new Date());
            fecha2 = formato.parse(hoy);
        } else {
            fecha1 = formato.parse(fechaRecibida1);
            fecha2 = formato.parse(fechaRecibida2);
        }

        Map<String, Integer> experienciaActividades = new HashMap<>();

        try {
            List<ExperienciaViaje> experiencias = sev.findExperienciaViajeEntities();
            for (ExperienciaViaje experiencia : experiencias) {
                if ((experiencia.getFechaInicio().after(fecha1) || experiencia.getFechaInicio().equals(fecha1))
                        && (experiencia.getFechaInicio().before(fecha2) || experiencia.getFechaInicio().equals(fecha2))) {
                    experienciaActividades.put(experiencia.getTitulo(), experiencia.getActividades().size());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            emf.close();
        }

        //El objeto ObjectMapper puede convertir un Map a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(experienciaActividades);

        //Establece el tipo de contenido como JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        //Escribir el JSON en la respuesta
        response.getWriter().write(jsonResponse);

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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ControladorCargarGraficaExperiencias.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ControladorCargarGraficaExperiencias.class.getName()).log(Level.SEVERE, null, ex);
        }
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
