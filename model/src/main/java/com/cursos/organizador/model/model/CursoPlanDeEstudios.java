package com.cursos.organizador.model.model;

import com.cursos.organizador.model.model.enums.ETipoCurso;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")

public class CursoPlanDeEstudios {

    @EmbeddedId
    private CursoPlanDeEstudiosKey id;
    @ManyToOne
    @MapsId("curso")
    @JoinColumn(name = "curso")
    private Curso curso;

    @ManyToOne
    @MapsId("planDeEstudios")
    @JoinColumn(name = "planDeEstudios")
    private PlanDeEstudios planDeEstudios;

    private int ciclo;
    private ETipoCurso tipoCurso;

    public CursoPlanDeEstudios() {
        id = new CursoPlanDeEstudiosKey();
    }

    public CursoPlanDeEstudios(Curso curso, PlanDeEstudios planDeEstudios) {
        this.curso = curso;
        this.planDeEstudios = planDeEstudios;
    }

    public CursoPlanDeEstudiosKey getId() {
        return id;
    }

    public void setId(CursoPlanDeEstudiosKey id) {
        this.id = id;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public PlanDeEstudios getPlanDeEstudios() {
        return planDeEstudios;
    }

    public void setPlanDeEstudios(PlanDeEstudios planDeEstudios) {
        this.planDeEstudios = planDeEstudios;
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

    @Override
    public String toString() {
        return curso.toString() +
                "\nCiclo=" + ciclo +
                "\nTipoCurso=" + tipoCurso;
    }

    public int compararTipoCurso(CursoPlanDeEstudios c2){
        // Si son distintos tipos de cursos
        int comp = tipoCurso.compareTo(c2.tipoCurso);
        if(comp!=0)
            return comp;
        // Si no, por ciclo
        comp = Integer.compare(ciclo, c2.ciclo);
        if(comp!=0)
            return comp;

        // Si no, alfabeticamente
        return curso.getNombre().compareTo(c2.getCurso().getNombre());
    }

}
