package com.cursos.organizador.model.model;

import com.cursos.organizador.model.model.enums.ETipoPlanEstudios;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "planDeEstudios")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class PlanDeEstudios {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Carrera carrera;

    private String cicloInicioPlan;
    private String cicloFinPlan;
    private int numElectivos;
    private int numPSPs;
    private ETipoPlanEstudios tipoPlanEstudios;
    private boolean tieneTrabajoBachiller = false;

    @OneToMany(mappedBy = "planDeEstudios", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CursoPlanDeEstudios> cursos;

    public PlanDeEstudios() {
        numElectivos=numPSPs=0;
        cicloInicioPlan="2019-1";
        cicloFinPlan="Vigente";
        carrera=new Carrera();
        tipoPlanEstudios= ETipoPlanEstudios.Otro;
        cursos = new ArrayList<>();
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isTieneTrabajoBachiller() {
        return tieneTrabajoBachiller;
    }

    public void setTieneTrabajoBachiller(boolean tieneTrabajoBachiller) {
        this.tieneTrabajoBachiller = tieneTrabajoBachiller;
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

    public List<CursoPlanDeEstudios> getCursos() {
        return cursos;
    }

    public void setCursos(List<CursoPlanDeEstudios> cursos) {
        this.cursos = cursos;
    }

    @Override
    public String toString(){
        StringBuilder c = new StringBuilder();
        c.append("Cursos de ").append(carrera.getNombre());
        c.append("Plan de Estudios ").append(cicloFinPlan);
        List<CursoPlanDeEstudios> _cursos = new ArrayList<>(cursos);
        _cursos.sort(CursoPlanDeEstudios::compararTipoCurso);
        for(CursoPlanDeEstudios cur: _cursos)
        {
            c.append(cur.toString());
        }
        return c.toString();
    }

}
