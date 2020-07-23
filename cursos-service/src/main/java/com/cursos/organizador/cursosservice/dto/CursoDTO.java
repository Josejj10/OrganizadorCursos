package com.cursos.organizador.cursosservice.dto;

import com.cursos.organizador.cursosservice.assembler.CarreraAssembler;
import com.cursos.organizador.cursosservice.assembler.CursoModelAssembler;
import com.cursos.organizador.model.model.Curso;
import com.cursos.organizador.model.model.PlanDeEstudios;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.ArrayList;
import java.util.List;

public class CursoDTO {

    private Long id;
    private String code;
    private String nombre;
    private double creditos;
    private int minCreditos;
    private String unidadAcademica;
    private String ultimoCicloDictado; // "Vigente"
    private String linkCurso;
    private CollectionModel<EntityModel<PlanDeEstudiosDTO>> planes;
    private CollectionModel<EntityModel<CursoRequisitoDTO>> requisitos;
    private CollectionModel<EntityModel<CursoRequisitoDTO>> requeridoPor;

    public CursoDTO() {
    }

    public CursoDTO(Curso c){
        this.id = c.getId();
        this.creditos = c.getCreditos();
        this.minCreditos = c.getMinCreditos();
        this.code = c.getCode();
        this.nombre = c.getNombre();
        CursoModelAssembler cma = new CursoModelAssembler();
        CarreraAssembler ca = new CarreraAssembler();
        if(c.getPlanes() != null) {
            List<PlanDeEstudios> _planes = new ArrayList<>();
            c.getPlanes().stream().forEach(cpde ->
                    _planes.add(cpde.getPlanDeEstudios()));
            planes = ca.toCollectionModelPdE(_planes);
        }
        if(c.getRequisitos() != null)
            requisitos = cma.toCollectionModelCReq(c.getRequisitos());
        if(c.getRequeridoPor() != null)
            requeridoPor = cma.toCollectionModelCReq(c.getRequeridoPor());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getCreditos() {
        return creditos;
    }

    public void setCreditos(double creditos) {
        this.creditos = creditos;
    }

    public int getMinCreditos() {
        return minCreditos;
    }

    public void setMinCreditos(int minCreditos) {
        this.minCreditos = minCreditos;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public CollectionModel<EntityModel<CursoRequisitoDTO>> getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(CollectionModel<EntityModel<CursoRequisitoDTO>> requisitos) {
        this.requisitos = requisitos;
    }

    public CollectionModel<EntityModel<CursoRequisitoDTO>> getRequeridoPor() {
        return requeridoPor;
    }

    public void setRequeridoPor(CollectionModel<EntityModel<CursoRequisitoDTO>> requeridoPor) {
        this.requeridoPor = requeridoPor;
    }

    public String getUnidadAcademica() {
        return unidadAcademica;
    }

    public void setUnidadAcademica(String unidadAcademica) {
        this.unidadAcademica = unidadAcademica;
    }

    public String getUltimoCicloDictado() {
        return ultimoCicloDictado;
    }

    public void setUltimoCicloDictado(String ultimoCicloDictado) {
        this.ultimoCicloDictado = ultimoCicloDictado;
    }

    public String getLinkCurso() {
        return linkCurso;
    }

    public void setLinkCurso(String linkCurso) {
        this.linkCurso = linkCurso;
    }

    public CollectionModel<EntityModel<PlanDeEstudiosDTO>> getPlanes() {
        return planes;
    }

    public void setPlanes(CollectionModel<EntityModel<PlanDeEstudiosDTO>> planes) {
        this.planes = planes;
    }
}
