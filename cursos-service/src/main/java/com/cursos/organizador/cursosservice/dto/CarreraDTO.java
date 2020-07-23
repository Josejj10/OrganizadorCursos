package com.cursos.organizador.cursosservice.dto;

import com.cursos.organizador.cursosservice.assembler.CarreraAssembler;
import com.cursos.organizador.cursosservice.assembler.CursoModelAssembler;
import com.cursos.organizador.model.model.Carrera;
import com.cursos.organizador.model.model.PlanDeEstudios;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.ArrayList;
import java.util.List;

public class CarreraDTO {
    private Long id;
    private String nombre;
    private String linkCarrera;
    private String facultad;
    private CollectionModel<EntityModel<PlanDeEstudiosCarreraDTO>> planesDeEstudios;

    public CarreraDTO() {
    }

    public CarreraDTO(Carrera carrera) {
        id = carrera.getId();
        nombre = carrera.getNombre();
        linkCarrera = carrera.getLinkCarrera();
        facultad = carrera.getFacultad();
        CarreraAssembler ca = new CarreraAssembler();
        if(carrera.getPlanesDeEstudios() != null)
            planesDeEstudios =  ca.toCollectionModelCarreraDTO(carrera.getPlanesDeEstudios());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLinkCarrera() {
        return linkCarrera;
    }

    public void setLinkCarrera(String linkCarrera) {
        this.linkCarrera = linkCarrera;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public CollectionModel<EntityModel<PlanDeEstudiosCarreraDTO>> getPlanesDeEstudios() {
        return planesDeEstudios;
    }

    public void setPlanesDeEstudios(CollectionModel<EntityModel<PlanDeEstudiosCarreraDTO>> planesDeEstudios) {
        this.planesDeEstudios = planesDeEstudios;
    }
}
