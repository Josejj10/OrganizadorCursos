package com.cursos.organizador.model.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CursoPlanDeEstudiosKey implements Serializable {

    @Column(name = "curso")
    private Long curso;

    @Column(name = "planDeEstudios")
    private Long planDeEstudios;

    public CursoPlanDeEstudiosKey() {
    }

    public CursoPlanDeEstudiosKey(Long curso, Long planDeEstudios) {
        this.curso = curso;
        this.planDeEstudios = planDeEstudios;
    }

    public Long getCurso() {
        return curso;
    }

    public void setCurso(Long curso) {
        this.curso = curso;
    }

    public Long getPlanDeEstudios() {
        return planDeEstudios;
    }

    public void setPlanDeEstudios(Long planDeEstudios) {
        this.planDeEstudios = planDeEstudios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CursoPlanDeEstudiosKey that = (CursoPlanDeEstudiosKey) o;
        return Objects.equals(curso, that.curso) &&
                Objects.equals(planDeEstudios, that.planDeEstudios);
    }

    @Override
    public int hashCode() {
        return Objects.hash(curso, planDeEstudios);
    }

    @Override
    public String toString() {
        return "CursoPlanDeEstudiosKey{" +
                "curso=" + curso +
                ", planDeEstudios=" + planDeEstudios +
                '}';
    }
}
