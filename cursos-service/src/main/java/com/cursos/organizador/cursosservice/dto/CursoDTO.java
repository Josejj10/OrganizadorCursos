package com.cursos.organizador.cursosservice.dto;

import com.cursos.organizador.model.model.Curso;

public class CursoDTO {

    private Long id;
    private String nombre;
    private String codigo;

    public CursoDTO(){}

    public CursoDTO(Curso curso){
        this.id = curso.getId();
        this.nombre = curso.getNombre();
        this.codigo= curso.getCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
