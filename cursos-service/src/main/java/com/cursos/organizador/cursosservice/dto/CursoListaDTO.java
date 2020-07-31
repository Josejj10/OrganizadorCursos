package com.cursos.organizador.cursosservice.dto;

import com.cursos.organizador.model.model.Curso;
import com.cursos.organizador.model.model.CursoRequisito;
import com.cursos.organizador.model.model.CursoRequisitoKey;

import java.util.ArrayList;
import java.util.List;

public class CursoListaDTO {

    private Long id;
    private String nombre;
    private String code;
    private double creditos;
    public CursoListaDTO() {
    }

    public CursoListaDTO(Curso c){
        this.id = c.getId();
        this.code = c.getCode();
        this.nombre = c.getNombre();
        this.creditos = c.getCreditos();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCreditos() {
        return creditos;
    }

    public void setCreditos(double creditos) {
        this.creditos = creditos;
    }
}
