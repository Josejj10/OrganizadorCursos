package com.cursos.organizador.model.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carrera")
public class Carrera {
    @Id
    @GeneratedValue
    private Long id;
    private String nombre;
    private String linkCarrera;
    private String facultad;
    @OneToMany
    private List<PlanDeEstudios> planesDeEstudios;

    public Carrera(){
        planesDeEstudios = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLinkCarrera() {
        return linkCarrera;
    }

    public void setLinkCarrera(String link) {
        this.linkCarrera = link;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public List<PlanDeEstudios> getPlanesDeEstudios() {
        return planesDeEstudios;
    }

    public void setPlanDeEstudios(List<PlanDeEstudios> planesDeEstudios) {
        this.planesDeEstudios = planesDeEstudios;
    }
}