package com.cursos.organizador.model.model;

import com.cursos.organizador.model.model.enums.ETipoRequisito;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class CursoRequisito implements Serializable {

    @EmbeddedId
    private CursoRequisitoKey id;

    @ManyToOne
    @MapsId("requiere")
    @JoinColumn(name = "requiere")
    private Curso requiere;

    @ManyToOne
    @MapsId("requerido")
    @JoinColumn(name = "requerido")
    private Curso requerido;

    private ETipoRequisito tipoRequisito; // "Haber pasado", "A la vez", "Nota Minima 08",  0 1 y 2

    public CursoRequisito() {
        id = new CursoRequisitoKey();
    }

    public CursoRequisito(Curso requiere, Curso requerido, ETipoRequisito tr) {
        id = new CursoRequisitoKey();
        this.requiere = requiere;
        this.requerido = requerido;
        this.tipoRequisito = tr;
    }

    public CursoRequisitoKey getId() {
        return id;
    }

    public void setId(CursoRequisitoKey id) {
        this.id = id;
    }

    public Curso getRequiere() {
        return requiere;
    }

    public void setRequiere(Curso requiere) {
        this.requiere = requiere;
    }

    public Curso getRequerido() {
        return requerido;
    }

    public void setRequerido(Curso requerido) {
        this.requerido = requerido;
    }

    public ETipoRequisito getTipoRequisito() {
        return tipoRequisito;
    }

    public void setTipoRequisito(ETipoRequisito tipoRequisito) {
        this.tipoRequisito = tipoRequisito;
    }

    @Override
    public String toString(){
        return "Requiere: "+requiere.getNombre() +" - " +
                requiere.getCode() + "\nRequerido: " + requerido.getNombre()+
                " - " +requerido.getCode();
    }

}
