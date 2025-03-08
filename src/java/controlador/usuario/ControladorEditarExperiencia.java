/*
 * Controlador Editar Experiencia
 */
package controlador.usuario;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import modelo.entidades.Actividad;
import modelo.entidades.ExperienciaViaje;
import modelo.entidades.Usuario;
import modelo.servicio.ServicioActividad;
import modelo.servicio.ServicioExperienciaViaje;

/**
 *
 * @author Carlos
 */
@WebServlet(name = "ControladorEditarExperiencia", urlPatterns = {"/usuario/EditarExperiencia"})
public class ControladorEditarExperiencia extends HttpServlet {

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

        String idExperiencia = request.getParameter("idExperiencia");

        if (idExperiencia == null || idExperiencia.isEmpty()) {//Si el id de la experiencia es null o está vacía 
            response.sendRedirect("Inicio");//Al cliente se le envía al inicio
            return;
        }

        HttpSession sesion = request.getSession();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
        ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);

        ExperienciaViaje ev = sev.findExperienciaViaje(Long.valueOf(idExperiencia));//Busca la experiencia
        emf.close();

        if (ev == null) {//Si la experiencia no existe
            response.sendRedirect("Inicio");//Al cliente se le envía al inicio
            return;
        }

        Usuario usuarioExperiencia = ev.getUsuario();
        Usuario usuarioSesion = (Usuario) sesion.getAttribute("usuario");
        if (usuarioExperiencia.getId().compareTo(usuarioSesion.getId()) != 0) {//Si la experiencia que se intenta editar no pertenece al cliente de la sesión
            response.sendRedirect("Inicio");//Al cliente se le envía al inicio
            return;
        }

        request.setAttribute("experiencia", ev);//Se envía la experiencia a la vista
        //Para recuperar la fecha de inico de la experiencia en la vista, se tiene que parsear al formato 'yyyy-MM-dd
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        request.setAttribute("experienciaFecha", formato.format(ev.getFechaInicio()));
        getServletContext().getRequestDispatcher("/usuario/editarExperiencia.jsp").forward(request, response);
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
        //Recoge la id de la experiencia actual y el tipo de submit
        String idExperiencia = request.getParameter("idExperiencia");
        String tipoSubmit = request.getParameter("tipoSubmit");
        String error = "";

        //Creación de servicios
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
        ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);
        ServicioActividad sa = new ServicioActividad(emf);
        //Guarda la experiencia original
        ExperienciaViaje experienciaOriginal = sev.findExperienciaViaje(Long.valueOf(idExperiencia));

        // Defino el formato del string para la fecha
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        request.setAttribute("experiencia", experienciaOriginal);
        //Para recuperar la fecha de inico de la experiencia en la vista, se tiene que parsear al formato 'yyyy-MM-dd
        request.setAttribute("experienciaFecha", formato.format(experienciaOriginal.getFechaInicio()));

        //Si se añade una actividad
        if (tipoSubmit.equals("Añadir Actividad")) {
            //Recoge los parámetros de la actividad a añadir
            String tituloActividad = request.getParameter("tituloActividad");
            String fechaActividad = request.getParameter("fechaActividad");
            String descripcionActividad = request.getParameter("descripcionActividad");

            if (tituloActividad == null || tituloActividad.isEmpty()
                    || fechaActividad == null || fechaActividad.isEmpty()
                    || descripcionActividad == null || descripcionActividad.isEmpty()) {//Si los campos de la actividad est?n vac?os
                error = "Los campos de la actividad no pueden estar vacíos";
                request.setAttribute("error", error);
                getServletContext().getRequestDispatcher("/usuario/editarExperiencia.jsp").forward(request, response);
                return;
            }

            try {
                //Crea la actividad
                Actividad a = new Actividad();
                a.setTitulo(tituloActividad);
                a.setDescripcion(descripcionActividad);
                a.setExperiencia(experienciaOriginal);
                // Convertir el String a Date
                Date fechaNueva = formato.parse(fechaActividad);
                a.setFecha(fechaNueva);
                
                //Guardar la imagen en la carpeta /usuario/media
                String path = getServletContext().getRealPath("usuario/media");
                Part fichero = request.getPart("imagen");
                
                if (fichero != null && fichero.getSize() > 0) { //Si no se ha enviado un null y si se ha enviado algo

                    String nombreImagen = path + "/" + fichero.getSubmittedFileName();
                    InputStream contenido = fichero.getInputStream();
                    FileOutputStream ficheroSalida = new FileOutputStream(nombreImagen);
                    byte[] buffer = new byte[8192];
                    while (contenido.available() > 0) {
                        int bytesLeidos = contenido.read(buffer);
                        ficheroSalida.write(buffer, 0, bytesLeidos);
                    }
                    ficheroSalida.close();
                    contenido.close();
                    //Se guarda la ruta de la imagen en la base de datos
                    a.setImagenes(fichero.getSubmittedFileName());
                }

                sa.create(a);
                emf.close();
            } catch (Exception e) {
                e.printStackTrace();
                error = "Error: " + e.getMessage();
                emf.close();
            }
        }

        //Si se quiere editar la experiencia
        if (tipoSubmit.equals("Guardar Experiencia")) {

            //Recoge los parámetros de la experiencia
            String tituloExperiencia = request.getParameter("tituloExperiencia");
            String fechaExperiencia = request.getParameter("fechaExperiencia");
            String descripcionExperiencia = request.getParameter("descripcionExperiencia");

            if (tituloExperiencia == null || tituloExperiencia.isEmpty()
                    || fechaExperiencia == null || fechaExperiencia.isEmpty()
                    || descripcionExperiencia == null || descripcionExperiencia.isEmpty()) {//Si los campos de la experiencia están vacíos
                error = "Los campos de la experiencia no pueden estar vacíos";
                request.setAttribute("error", error);
                getServletContext().getRequestDispatcher("/usuario/editarExperiencia.jsp").forward(request, response);
                return;
            }

            try {
                //Crea la experiencia
                ExperienciaViaje experienciaCambiada = new ExperienciaViaje();
                experienciaCambiada.setId(experienciaOriginal.getId());
                experienciaCambiada.setTitulo(tituloExperiencia);
                experienciaCambiada.setDescripcion(descripcionExperiencia);
                experienciaCambiada.setUsuario(experienciaOriginal.getUsuario());
                experienciaCambiada.setActividades(experienciaOriginal.getActividades());
                experienciaCambiada.setOpiniones(experienciaOriginal.getOpiniones());

                // Convertir el String a Date
                Date fechaInicioNueva = formato.parse(fechaExperiencia);
                experienciaCambiada.setFechaInicio(fechaInicioNueva);

                //Se edita en la base de datos
                sev.edit(experienciaCambiada);

                emf.close();
                response.sendRedirect("Experiencia?idExperiencia=" + idExperiencia);
                return;
            } catch (Exception e) {
                error = "Error: " + e.getMessage();
                emf.close();
            }
        }

        request.setAttribute("error", error);
        // Redirige para evitar duplicados
        response.sendRedirect(request.getContextPath() + "/usuario/EditarExperiencia?idExperiencia=" + idExperiencia);
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
