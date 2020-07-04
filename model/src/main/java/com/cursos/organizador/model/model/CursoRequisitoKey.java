package com.cursos.organizador.model.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CursoRequisitoKey implements Serializable {
    @Column(name = "requiere")
    private Long requiere;

    @Column(name = "requerido")
    private Long requerido;

    public CursoRequisitoKey() {
    }

    public CursoRequisitoKey(Long requiere, Long requerido) {
        this.requiere = requiere;
        this.requerido = requerido;
    }



    public Long getRequiere() {
        return requiere;
    }

    public void setRequiere(Long requiere) {
        this.requiere = requiere;
    }

    public Long getRequerido() {
        return requerido;
    }

    public void setRequerido(Long requerido) {
        this.requerido = requerido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CursoRequisitoKey that = (CursoRequisitoKey) o;
        return Objects.equals(requiere, that.requiere) &&
                Objects.equals(requerido, that.requerido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requiere, requerido);
    }
}
