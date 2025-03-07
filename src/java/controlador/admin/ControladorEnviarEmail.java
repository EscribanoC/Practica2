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
        String from = "escribano.flores.carlos@iescamas.com";
        String password = "practica2";
        String subject = "Activación de cuenta";
        String text = "Buenas, le informamos que su cuenta de myExperience con email " + to + " ha sido activada."
                + "\n Ya puede acceder a la apliación cuando lo desee. "
                + "\n Muchas gracias y un saludo.";
        
        if (to != null) {
            Email email = new Email();
            email.setTo(to);
            email.setSubject(subject);
            email.setText(text);
            email.setFrom(from);
            Utilidades u = new Utilidades();
            
            try {
                enviarEmail(email, password);
            } catch (Throwable e) {
                System.err.println(e.getMessage());
            }
        }
        response.sendRedirect("PanelAdministracion");

    }

    public void enviarEmail(Email email, String password) throws MessagingException {
        
        Properties p = new Properties();
        // Servidor smtp de correo
        p.setProperty("mail.smtp.host", "smtp.gmail.com");
        // Usar TLS
        p.setProperty("mail.smtp.starttls.enable", "true");
        // puerto del servidor smtp
        p.setProperty("mail.smtp.port", "587");
        // Usuario smtp
        p.setProperty("mail.smtp.user", email.getFrom());
        // Autenticación requerida
        p.setProperty("mail.smtp.auth", "true");
        // Obtenemos la sesión
        Session sesion = Session.getDefaultInstance(p);
        sesion.setDebug(false);
        // Creamos el mensaje
        MimeMessage mensaje = new MimeMessage(sesion);
        // Y establecemos sus propiedades
        
        //try {
            
            mensaje.setFrom(new InternetAddress(email.getFrom()));
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getTo()));
            mensaje.setSubject(email.getSubject());
            mensaje.setText(email.getText());
            // Enviamos el mensaje
            Transport t = sesion.getTransport("smtp");
            // Para conectarnos usamos usuario y password
            t.connect(email.getFrom(), password);
            t.sendMessage(mensaje, mensaje.getAllRecipients());
        //} catch (MessagingException e) {
        //    System.err.println(e.getMessage());
        //} catch (Exception ex){
        //    System.out.println("ERROR: " + ex.getMessage());
        //}
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
