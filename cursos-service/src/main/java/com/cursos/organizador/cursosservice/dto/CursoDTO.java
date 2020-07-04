package com.cursos.organizador.cursosservice.dto;

import com.cursos.organizador.model.model.Curso;
import com.cursos.organizador.model.model.CursoRequisito;
import com.cursos.organizador.model.model.CursoRequisitoKey;

import java.util.ArrayList;
import java.util.List;

public class CursoDTO {

    private Long id;
    // @ElementCollection
    private String nombre;
    private List<String> links;
    private double creditos;
    private int minCreditos;
    private String code;
    private String abreviatura;
    private int ciclo;
    private String prof;
    private String horario;
    //  @ElementCollection
    private List<String> carreras;

    private List<CursoRequisitoDTO> requisitos;
    private List<CursoRequisitoDTO> requeridoPor;

    public CursoDTO() {
    }

    public CursoDTO(Curso c){
        this.id = c.getId();
        this.requeridoPor = new ArrayList<>();
        this.requisitos = new ArrayList<>();
        this.links = c.getLinks()!=null?c.getLinks(): new ArrayList<>();
        this.creditos = c.getCreditos();
        this.minCreditos = c.getMinCreditos();
        this.code = c.getCode();
        this.nombre = c.getNombre();
        this.abreviatura=c.getAbreviatura();
        this.ciclo=c.getCiclo();
        this.prof=c.getProf();
        this.carreras=c.getCarreras();
        this.horario=c.getHorario();
        if(c.getRequisitos() != null) {
            for (CursoRequisito cr : c.getRequisitos()) {
                requisitos.add(new CursoRequisitoDTO(cr));
            }
        }
        if(c.getRequeridoPor() == null) return;
        for(CursoRequisito cr: c.getRequeridoPor())
            requeridoPor.add(new CursoRequisitoDTO(cr));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public double getCreditos() {
        return creditos;
    }

    public void setCreditos(double creditos) {
        this.creditos = creditos;
    }

    public int getMinCreditos() {
        return minCreditos;
    }

    public void setMinCreditos(int minCreditos) {
        this.minCreditos = minCreditos;
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

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public List<String> getCarreras() {
        return carreras;
    }

    public void setCarreras(List<String> carreras) {
        this.carreras = carreras;
    }

    public List<CursoRequisitoDTO> getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(List<CursoRequisitoDTO> requisitos) {
        this.requisitos = requisitos;
    }

    public List<CursoRequisitoDTO> getRequeridoPor() {
        return requeridoPor;
    }

    public void setRequeridoPor(List<CursoRequisitoDTO> requeridoPor) {
        this.requeridoPor = requeridoPor;
    }
}
