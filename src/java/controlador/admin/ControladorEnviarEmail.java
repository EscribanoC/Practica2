/*
 * Controlador Enviar Email
 */
package controlador.admin;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utilidades.Email;
import utilidades.Utilidades;

/**
 *
 * @author Carlos
 */
@WebServlet(name = "ControladorEnviarEmail", urlPatterns = {"/admin/ControladorEnviarEmail"})
public class ControladorEnviarEmail extends HttpServlet {

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
        String to = request.getParameter("to");
        String from = "escribano.flores.carlos@iescamas.es";
        String password = "practica2";
        String subject = "Activación de cuenta";
        String text = "Buenas, le informamos que su cuenta de myExperience con email " + to + " ha sido activada."
                + "\nYa puede acceder a la apliación cuando lo desee. "
                + "\nMuchas gracias y un saludo.";
        
        if (to != null) {
            Email email = new Email();
            email.setTo(to);
            email.setSubject(subject);
            email.setText(text);
            email.setFrom(from);
            Utilidades u = new Utilidades();
            
            try {
                u.enviarEmail(email, password);
            } catch (Throwable e) {
                System.err.println(e.getMessage());
            }
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
