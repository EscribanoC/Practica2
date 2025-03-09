/*
 * Clase DTO para transferir la información de la experiencias de forma controlada.
 */
package dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.entidades.Actividad;
import modelo.entidades.ExperienciaViaje;

/**
 *
 * @author Carlos
 */

public class ExperienciaViajeDTO {

    private Long id;
    private String titulo;
    private String descripcion;
    private Date fechaInicio;
    private boolean publico;
    private List<String> actividades;

    //Constructor
    public ExperienciaViajeDTO(ExperienciaViaje experiencia) {
        this.id = experiencia.getId();
        this.titulo = experiencia.getTitulo();
        this.descripcion = experiencia.getDescripcion();
        this.fechaInicio = experiencia.getFechaInicio();
        this.publico = experiencia.isPublico();
        this.actividades = new ArrayList<>();
        for (Actividad actividad : experiencia.getActividades()) {
            this.actividades.add(actividad.getTitulo());
        }
    }

    //Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public boolean isPublico() {
        return publico;
    }

    public void setPublico(boolean publico) {
        this.publico = publico;
    }

    public List<String> getActividades() {
        return actividades;
    }

    public void setActividades(List<String> actividades) {
        this.actividades = actividades;
    }
}
