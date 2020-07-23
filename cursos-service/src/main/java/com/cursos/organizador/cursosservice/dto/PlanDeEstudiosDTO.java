package com.cursos.organizador.cursosservice.dto;

import com.cursos.organizador.cursosservice.assembler.CursoModelAssembler;
import com.cursos.organizador.model.model.Curso;
import com.cursos.organizador.model.model.CursoPlanDeEstudios;
import com.cursos.organizador.model.model.PlanDeEstudios;
import com.cursos.organizador.model.model.enums.ETipoPlanEstudios;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.ArrayList;
import java.util.List;

public class PlanDeEstudiosDTO {
    private Long id;
    private Long idCarrera;
    private String nombreCarrera;
    private String cicloInicioPlan;
    private String cicloFinPlan;
    private int numElectivos;
    private int numPSPs;
    private ETipoPlanEstudios tipoPlanEstudios;
    private boolean tieneTrabajoBachiller;
    private CollectionModel<EntityModel<CursoListaDTO>> cursos;

    public PlanDeEstudiosDTO() {
    }

    public PlanDeEstudiosDTO(PlanDeEstudios plan) {
        id = plan.getId();
        nombreCarrera = plan.getCarrera().getNombre();
        idCarrera = plan.getCarrera().getId();
        cicloInicioPlan = plan.getCicloInicioPlan();
        cicloFinPlan = plan.getCicloFinPlan();
        numElectivos = plan.getNumElectivos();
        numPSPs = plan.getNumPSPs();
        tipoPlanEstudios = plan.getTipoPlanEstudios();
        tieneTrabajoBachiller = plan.isTieneTrabajoBachiller();
        if(plan.getCursos() != null) {
            CursoModelAssembler cma = new CursoModelAssembler();
            List<Curso> _cursos = new ArrayList<>();
            plan.getCursos().stream().forEach(cpde ->
                    _cursos.add(cpde.getCurso()));
            cursos = cma.toCollectionModelLista(_cursos);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(Long idCarrera) {
        this.idCarrera = idCarrera;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public String getCicloInicioPlan() {
        return cicloInicioPlan;
    }

    public void setCicloInicioPlan(String cicloInicioPlan) {
        this.cicloInicioPlan = cicloInicioPlan;
    }

    public String getCicloFinPlan() {
        return cicloFinPlan;
    }

    public void setCicloFinPlan(String cicloFinPlan) {
        this.cicloFinPlan = cicloFinPlan;
    }

    public int getNumElectivos() {
        return numElectivos;
    }

    public void setNumElectivos(int numElectivos) {
        this.numElectivos = numElectivos;
    }

    public int getNumPSPs() {
        return numPSPs;
    }

    public void setNumPSPs(int numPSPs) {
        this.numPSPs = numPSPs;
    }

    public ETipoPlanEstudios getTipoPlanEstudios() {
        return tipoPlanEstudios;
    }

    public void setTipoPlanEstudios(ETipoPlanEstudios tipoPlanEstudios) {
        this.tipoPlanEstudios = tipoPlanEstudios;
    }

    public boolean isTieneTrabajoBachiller() {
        return tieneTrabajoBachiller;
    }

    public void setTieneTrabajoBachiller(boolean tieneTrabajoBachiller) {
        this.tieneTrabajoBachiller = tieneTrabajoBachiller;
    }

    public CollectionModel<EntityModel<CursoListaDTO>> getCursos() {
        return cursos;
    }

    public void setCursos(CollectionModel<EntityModel<CursoListaDTO>> cursos) {
        this.cursos = cursos;
    }
}
