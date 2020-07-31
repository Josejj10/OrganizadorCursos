package com.cursos.organizador.model.model;

import com.cursos.organizador.model.model.enums.ETipoRequisito;
import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "curso")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Curso implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String code;
    private String nombre;
    private double creditos;
    private String unidadAcademica;
    private String ultimoCicloDictado; // "Vigente"
    private String linkCurso;
    @OneToMany(mappedBy = "curso", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<CursoPlanDeEstudios> planes;

    /*=============================
              Constructores
     *=============================*/

    public Curso() {
        nombre = "Por definir";
        code = "AAA111";
        creditos = 0;
        unidadAcademica = "Por definir";
        ultimoCicloDictado = "Vigente";
        linkCurso = "Por definir";
        planes = new ArrayList<>();
    }
    public Curso(Long id) {
        nombre = "Por definir";
        code = "AAA111";
        creditos = 0;
        unidadAcademica = "Por definir";
        ultimoCicloDictado = "Vigente";
        linkCurso = "Por definir";
        planes = new ArrayList<>();
        this.id = id;
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

    public double getCreditos() {
        return creditos;
    }

    public void setCreditos(double creditos) {
        this.creditos = creditos;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUltimoCicloDictado() {
        return ultimoCicloDictado;
    }

    public void setUltimoCicloDictado(String prof) {
        this.ultimoCicloDictado = prof;
    }

    public String getLinkCurso() {
        return linkCurso;
    }

    public void setLinkCurso(String linkCurso) {
        this.linkCurso = linkCurso;
    }

    public List<CursoPlanDeEstudios> getPlanes() {
        return planes;
    }

    public void setPlanes(List<CursoPlanDeEstudios> planes) {
        this.planes = planes;
    }

    public String getUnidadAcademica() {
        return unidadAcademica;
    }

    public void setUnidadAcademica(String abreviatura) {
        this.unidadAcademica = abreviatura;
    }

    @Override
    public String toString(){
        String c ="";
        c += "==========================================="+"\n";
        c+= code+": "+nombre+"\n";
        c+="Creditos: "+ creditos+"\n";
        return c;
    }
}
