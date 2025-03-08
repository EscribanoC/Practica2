/*
 * Controlador Editar Actividad
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
import javax.servlet.http.Part;
import modelo.entidades.Actividad;
import modelo.entidades.ExperienciaViaje;
import modelo.servicio.ServicioActividad;
import modelo.servicio.ServicioExperienciaViaje;

/**
 *
 * @author Carlos
 */
@WebServlet(name = "ControladorEditarActividad", urlPatterns = {"/usuario/ControladorEditarActividad"})
public class ControladorEditarActividad extends HttpServlet {

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
        String idExperiencia = request.getParameter("idExperiencia");

        if (idExperiencia == null || idExperiencia.isEmpty()) {//Si la idExperiencia no se recibe
            response.sendRedirect("Inicio");
            return;
        }

        //Se recogen los campos del formulario de edición de Actividad
        String idActividad = request.getParameter("idActividad");
        String titulo = request.getParameter("tituloActividad").trim();
        String descripcion = request.getParameter("descripcionActividad").trim();
        String fecha = request.getParameter("fechaActividad").trim();

        // Defino el formato del string para la fecha
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");

        //Si algún de los campos que se reciben(exceptuando la imagen) no se envía o está vacío
        if (idActividad == null || idActividad.isEmpty()
                || titulo == null || titulo.isEmpty()
                || descripcion == null || descripcion.isEmpty()
                || fecha == null || fecha.isEmpty()) {
            response.sendRedirect("EditarExperiencia?idExperiencia=" + idExperiencia);
            return;
        }

        try {
            //Creación de servicio
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
            ServicioActividad sa = new ServicioActividad(emf);
            ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);

            ExperienciaViaje e = sev.findExperienciaViaje(Long.valueOf(idExperiencia));
            
            //Creación nueva instancia de Actividad
            Actividad aOriginal = sa.findActividad(Long.valueOf(idActividad));
            Actividad aNueva = new Actividad();
            aNueva.setId(aOriginal.getId());
            aNueva.setTitulo(titulo);
            aNueva.setDescripcion(descripcion);
            aNueva.setExperiencia(e);
            aNueva.setImagenes(aOriginal.getImagenes());
            
            //Crea un objeto Date que es el que se guardará en la base de datos
            Date fechaFormateada = formatoFecha.parse(fecha);
            aNueva.setFecha(fechaFormateada);

            //Guardar la imagen en la carpeta /usuario/media
            String path = getServletContext().getRealPath("usuario/media");
            Part imagen = request.getPart("imagen");

            if (imagen != null && imagen.getSize() > 0) { //Si no se ha enviado un null y si se ha enviado algo
                System.out.println("illo");
                //Guarda el fichero en el directorio con la ruta absoluta /usuario/media
                String nombreImagen = path + "/" + imagen.getSubmittedFileName();
                InputStream contenido = imagen.getInputStream();
                FileOutputStream ficheroSalida = new FileOutputStream(nombreImagen);
                byte[] buffer = new byte[8192];
                while (contenido.available() > 0) {
                    int bytesLeidos = contenido.read(buffer);
                    ficheroSalida.write(buffer, 0, bytesLeidos);
                }
                ficheroSalida.close();
                contenido.close();

                //Se guarda la ruta de la imagen en la base de datos
                aNueva.setImagenes(imagen.getSubmittedFileName());
            }

            //Se modifica la actividad
            sa.edit(aNueva);

            emf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("EditarExperiencia?idExperiencia=" + idExperiencia);
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
