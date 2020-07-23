package com.cursos.organizador.cursosservice.dto;

import com.cursos.organizador.model.model.Curso;
import com.cursos.organizador.model.model.CursoPlanDeEstudios;
import com.cursos.organizador.model.model.CursoPlanDeEstudiosKey;
import com.cursos.organizador.model.model.PlanDeEstudios;
import com.cursos.organizador.model.model.enums.ETipoCurso;

public class CursoPlanDeEstudiosDTO {

    private CursoPlanDeEstudiosKey id;
    private String codeCurso;
    private String nombreCarrera;
    private String cicloFinPlan;
    private int ciclo;
    private ETipoCurso tipoCurso;

    public CursoPlanDeEstudiosDTO(){
    }

    public CursoPlanDeEstudiosDTO(CursoPlanDeEstudios cpe) {
        this.id = cpe.getId();
        this.codeCurso = cpe.getCurso().getCode();
        this.nombreCarrera = cpe.getPlanDeEstudios().getCarrera().getNombre();
        this.cicloFinPlan = cpe.getPlanDeEstudios().getCicloFinPlan();
        this.ciclo = cpe.getCiclo();
        this.tipoCurso = cpe.getTipoCurso();
    }

    public CursoPlanDeEstudiosKey getId() {
        return id;
    }

    public void setId(CursoPlanDeEstudiosKey id) {
        this.id = id;
    }

    public String getCodeCurso() {
        return codeCurso;
    }

    public void setCodeCurso(String codeCurso) {
        this.codeCurso = codeCurso;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public String getCicloFinPlan() {
        return cicloFinPlan;
    }

    public void setCicloFinPlan(String cicloFinPlan) {
        this.cicloFinPlan = cicloFinPlan;
    }

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    public ETipoCurso getTipoCurso() {
        return tipoCurso;
    }

    public void setTipoCurso(ETipoCurso tipoCurso) {
        this.tipoCurso = tipoCurso;
    }
}
