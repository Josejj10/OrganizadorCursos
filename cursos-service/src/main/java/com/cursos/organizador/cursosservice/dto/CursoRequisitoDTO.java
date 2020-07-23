package com.cursos.organizador.cursosservice.dto;

import com.cursos.organizador.model.model.Curso;
import com.cursos.organizador.model.model.CursoRequisito;
import com.cursos.organizador.model.model.CursoRequisitoKey;
import com.cursos.organizador.model.model.enums.ETipoRequisito;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

public class CursoRequisitoDTO {
    private CursoRequisitoKey id;
    private String codeRequiere;
    private String nomRequiere;
    private String codeRequerido;
    private String nomRequerido;
    private ETipoRequisito tipoRequisito;


    public CursoRequisitoDTO() {
    }

    public CursoRequisitoDTO(CursoRequisito cr){
        this.id = cr.getId();
        this.codeRequerido = cr.getRequerido().getCode();
        this.codeRequiere = cr.getRequiere().getCode();
        this.nomRequerido = cr.getRequerido().getNombre();
        this.nomRequiere = cr.getRequiere().getNombre();
        this.tipoRequisito = cr.getTipoRequisito();

    }


    public CursoRequisitoKey getId() {
        return id;
    }

    public void setId(CursoRequisitoKey id) {
        this.id = id;
    }

    public String getCodeRequiere() {
        return codeRequiere;
    }

    public void setCodeRequiere(String codeRequiere) {
        this.codeRequiere = codeRequiere;
    }

    public String getCodeRequerido() {
        return codeRequerido;
    }

    public void setCodeRequerido(String codeRequerido) {
        this.codeRequerido = codeRequerido;
    }

    public ETipoRequisito getTipoRequisito() {
        return tipoRequisito;
    }

    public void setTipoRequisito(ETipoRequisito tipoRequisito) {
        this.tipoRequisito = tipoRequisito;
    }

    public String getNomRequiere() {
        return nomRequiere;
    }

    public void setNomRequiere(String nomRequiere) {
        this.nomRequiere = nomRequiere;
    }

    public String getNomRequerido() {
        return nomRequerido;
    }

    public void setNomRequerido(String nomRequerido) {
        this.nomRequerido = nomRequerido;
    }
}
