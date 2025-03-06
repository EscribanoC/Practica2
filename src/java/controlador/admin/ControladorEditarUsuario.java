/*
 * Controlador Editar Usuario desde Admin
 */
package controlador.admin;

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
@WebServlet(name = "ControladorEditarUsuario", urlPatterns = {"/admin/ControladorEditarUsuario"})
public class ControladorEditarUsuario extends HttpServlet {

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
        String idUsuario = request.getParameter("idUsuario");

        if (idUsuario == null || idUsuario.isEmpty()) {
            response.sendRedirect("PanelAdministracion");
            return;
        }

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
        ServicioUsuario su = new ServicioUsuario(emf);

        try {
            Usuario u = su.findUsuario(Long.valueOf(idUsuario));

            if (u != null) {//Si el usuario existe
                // Crea la respuesta en formato JSON
                String jsonResponse = String.format(
                        "{\"id\": \"%s\", \"email\": \"%s\", \"nombre\": \"%s\", \"apellidos\": \"%s\"}",
                        u.getId(),
                        u.getEmail(),
                        u.getNombre(),
                        u.getApellidos()
                );

                // Establece el tipo de contenido como JSON, el tipo de encode
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonResponse);//Devuelve la respuesta

            } else {//Si el usuario no existe
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Usuario no encontrado\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            emf.close();
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
        String emailOriginal = request.getParameter("emailOriginal");
        String email = request.getParameter("emailUsuario");
        String password = request.getParameter("passwordUsuario");
        String nombre = request.getParameter("nombreUsuario");
        String apellidos = request.getParameter("apellidosUsuario");
        
        if (emailOriginal == null || emailOriginal.isEmpty()
                || email == null || email.isEmpty()
                || nombre == null || nombre.isEmpty()
                || apellidos == null || apellidos.isEmpty()) { //Si algún campo está vacío
            response.sendRedirect("PanelAdministracion");
            return;
        }

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
        ServicioUsuario su = new ServicioUsuario(emf);
        Usuario uOriginal = su.buscarPorEmail(emailOriginal);
        try {

            //Crea una instancia con el nuevo usuario
            Usuario uCambiado = new Usuario();
            uCambiado.setId(uOriginal.getId());
            uCambiado.setEmail(email);
            if (password != null && !password.isEmpty()) {
                uCambiado.setPassword(password);
            } else{
                uCambiado.setPassword(uOriginal.getPassword());
            }
            uCambiado.setNombre(nombre);
            uCambiado.setApellidos(apellidos);
            uCambiado.setTipo(uOriginal.getTipo());
            uCambiado.setExperiencias(uOriginal.getExperiencias());
            uCambiado.setActivo(uOriginal.isActivo());

            //Se edita en la base de datos
            su.edit(uCambiado);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            emf.close();
        }

        response.sendRedirect("PanelAdministracion");
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
