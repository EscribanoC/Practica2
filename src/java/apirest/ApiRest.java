/*
 * API REST
 */
package apirest;

/**
 *
 * @author Carlos
 */
import dto.ExperienciaViajeDTO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import modelo.entidades.ExperienciaViaje;
import modelo.servicio.ServicioExperienciaViaje;

@Path("/experiencias")
public class ApiRest {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ExperienciaViajeDTO> listarExperiencias() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Practica2PU");
        ServicioExperienciaViaje sev = new ServicioExperienciaViaje(emf);

        List<ExperienciaViaje> experiencias = sev.findExperienciaViajeEntities();
        emf.close();

        //Convierte las entidades de la lista experiencias en una lista de ExperienciaViajeDTO
        List<ExperienciaViajeDTO> experienciaDTOs = new ArrayList<>();
        for (ExperienciaViaje experiencia : experiencias) {
            experienciaDTOs.add(new ExperienciaViajeDTO(experiencia));
        }

        return experienciaDTOs;
    }
}
