//package com.cursos.organizador.model.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import javax.persistence.*;
//import java.io.Serializable;
//
//@Entity
//@Table(name = "Requisito")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//public class Requisito implements Serializable {
//
//    @EmbeddedId
//    private CursoRequisito id;
//
//    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
//    @MapsId("requiere_Id")
//    @JoinColumn(name = "requiere_Id")
//    @JsonIgnoreProperties({"requisitos","requeridoPor","apuntes"})
//    private Curso requiere;
//
//    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
//    @MapsId("requerido_Id")
//    @JoinColumn(name = "requerido_Id")
//    @JsonIgnoreProperties({"requisitos","requeridoPor","apuntes"})
//    private Curso requerido;
//
//    public Requisito(){}
//
//    public Requisito(Curso lp2,Curso lp1){
//        this.requiere = lp1;
//        this.requerido = lp2;
//    }
//
//    public CursoRequisito getId() {
//        return id;
//    }
//
//    public void setId(CursoRequisito id) {
//        this.id = id;
//    }
//
//    public Curso getRequiere() {
//        return requiere;
//    }
//
//    public void setRequiere(Curso requiere) {
//        this.requiere = requiere;
//    }
//
//    public Curso getRequerido() {
//        return requerido;
//    }
//
//    public void setRequerido(Curso requerido) {
//        this.requerido = requerido;
//    }
//
//
//
//}
