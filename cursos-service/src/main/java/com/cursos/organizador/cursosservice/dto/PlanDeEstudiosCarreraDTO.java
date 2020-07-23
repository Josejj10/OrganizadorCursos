package com.cursos.organizador.cursosservice.dto;

import com.cursos.organizador.model.model.PlanDeEstudios;
import com.cursos.organizador.model.model.enums.ETipoPlanEstudios;

import java.util.List;

public class PlanDeEstudiosCarreraDTO {
    private Long id;
    private String cicloInicioPlan;
    private String cicloFinPlan;
    private ETipoPlanEstudios tipoPlanEstudios;

    public PlanDeEstudiosCarreraDTO() {
    }

    public PlanDeEstudiosCarreraDTO(PlanDeEstudios plan) {
        id = plan.getId();
        cicloFinPlan = plan.getCicloFinPlan();
        cicloInicioPlan = plan.getCicloInicioPlan();
        tipoPlanEstudios = plan.getTipoPlanEstudios();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ETipoPlanEstudios getTipoPlanEstudios() {
        return tipoPlanEstudios;
    }

    public void setTipoPlanEstudios(ETipoPlanEstudios tipoPlanEstudios) {
        this.tipoPlanEstudios = tipoPlanEstudios;
    }
}
