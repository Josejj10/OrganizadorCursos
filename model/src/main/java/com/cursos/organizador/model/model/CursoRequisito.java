package com.cursos.organizador.model.model;

import com.cursos.organizador.model.model.enums.ETipoRequisito;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class CursoRequisito implements Serializable {

    @EmbeddedId
    private CursoRequisitoKey id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @MapsId("requiere")
//    @JoinColumns({
//            @JoinColumn(name = "requiere_curso", referencedColumnName = "curso"),
//            @JoinColumn(name = "requiere_plan", referencedColumnName = "planDeEstudios")
//    })
    private CursoPlanDeEstudios requiere;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @MapsId("requerido")
//    @JoinColumns({
//            @JoinColumn(name = "requerido_curso", referencedColumnName = "curso"),
//            @JoinColumn(name = "requerido_plan", referencedColumnName = "planDeEstudios")
//    })
    private CursoPlanDeEstudios requerido;

    private ETipoRequisito tipoRequisito; // "Haber pasado", "A la vez", "Nota Minima 08",  0 1 y 2

    public CursoRequisito() {
        id = new CursoRequisitoKey();
    }

    public CursoRequisito(CursoPlanDeEstudios requiere, CursoPlanDeEstudios requerido, ETipoRequisito tr) {
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

    public CursoPlanDeEstudios getRequiere() {
        return requiere;
    }

    public void setRequiere(CursoPlanDeEstudios requiere) {
        this.requiere = requiere;
    }

    public CursoPlanDeEstudios getRequerido() {
        return requerido;
    }

    public void setRequerido(CursoPlanDeEstudios requerido) {
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
        return "Requiere: "+requiere.getCurso().getNombre() +" - " +
                requiere.getCurso().getCode() + "\nRequerido: " +
                requerido.getCurso().getNombre()+
                " - " +requerido.getCurso().getCode();
    }

}
