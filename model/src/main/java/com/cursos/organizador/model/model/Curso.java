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
    private int minCreditos;
    private String unidadAcademica;
    private String ultimoCicloDictado; // "Vigente"
    private String linkCurso;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<CursoPlanDeEstudios> planes;

    @OneToMany(mappedBy = "requiere", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<CursoRequisito> requisitos;

    // Curso es requisito de
    @OneToMany(mappedBy = "requerido", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<CursoRequisito> requeridoPor;

    /*=============================
       CONSTRUCTORES
     *=============================*/

    public Curso() {
        nombre = "Por definir";
        code = "AAA111";
        creditos = minCreditos = 0;
        unidadAcademica = "Por definir";
        ultimoCicloDictado = "Vigente";
        linkCurso = "Por definir";
        requeridoPor = new ArrayList<>();
        requisitos = new ArrayList<>();
        planes = new ArrayList<>();
    }
    public Curso(Long id) {
        nombre = "Por definir";
        code = "AAA111";
        creditos = minCreditos = 0;
        unidadAcademica = "Por definir";
        ultimoCicloDictado = "Vigente";
        linkCurso = "Por definir";
        requeridoPor = new ArrayList<>();
        requisitos = new ArrayList<>();
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

    public int getMinCreditos() {
        return minCreditos;
    }

    public void setMinCreditos(int minCreditos) {
        this.minCreditos = minCreditos;
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

    public List<CursoRequisito> getRequisitos() {
        return requisitos;
    }

    public List<Curso> getRequisitosCurso(ETipoRequisito tipoRequisito){
        List<Curso> cursos = new ArrayList<>();
        for(CursoRequisito cr : requisitos){
            if(cr.getTipoRequisito() == tipoRequisito)
                cursos.add(cr.getRequerido());
        }
        return cursos;
    }

    public List<Curso> getRequeridoPorCurso(ETipoRequisito tipoRequisito){
        List<Curso> cursos = new ArrayList<>();
        for(CursoRequisito cr : requeridoPor){
            if(cr.getTipoRequisito() == tipoRequisito)
                cursos.add(cr.getRequiere());
        }
        return cursos;
    }

    public List<Curso> getAllRequisitos(){
        List<Curso> cursos = new ArrayList<>();
        for(CursoRequisito cr : requisitos){
                cursos.add(cr.getRequerido());
        }
        return cursos;
    }

    public List<Curso> getAllRequeridoPor(){
        List<Curso> cursos = new ArrayList<>();
        for(CursoRequisito cr : requeridoPor){
                cursos.add(cr.getRequiere());
        }
        return cursos;
    }

    public void setRequisitos(List<CursoRequisito> requisitos) {
        this.requisitos = requisitos;
    }

    public List<CursoRequisito> getRequeridoPor() {
        return requeridoPor;
    }

    public void setRequeridoPor(List<CursoRequisito> requeridoPor) {
        this.requeridoPor = requeridoPor;
    }

    public boolean addRequisito(Curso curso, ETipoRequisito tipoRequisito){
        for(CursoRequisito req : requisitos){
            if(req.getRequerido().getCode().equals(curso.getCode()))
                return false;
        }

        CursoRequisito c = new CursoRequisito(this,curso,tipoRequisito);
        this.requisitos.add(c);
        curso.getRequeridoPor().add(c);
        return true;
    }

    public boolean addRequeridoPor(Curso curso, ETipoRequisito tipoRequisito){
        for(CursoRequisito req : requeridoPor){
            if(req.getRequiere().getCode().equals(curso.getCode()))
                return false;
        }
        CursoRequisito c = new CursoRequisito(curso,this,tipoRequisito);
        this.requeridoPor.add(c);
        curso.getRequisitos().add(c);
        return true;
    }

    public boolean contieneRequisito(Curso curso) {
        Iterator<CursoRequisito> itr = requisitos.iterator();
        CursoRequisito cr;
        while (itr.hasNext()) {
            cr = itr.next();
            if (cr.getRequiere().getId().equals(curso.getId()))
                return true;
        }
        return false;
    }

    public boolean contieneRequeridoPor(Curso curso){
        Iterator<CursoRequisito> itr = requisitos.iterator();
        CursoRequisito cr;
        while (itr.hasNext()) {
            cr = itr.next();
            if (cr.getRequerido().getId().equals(curso.getId()))
                return true;
        }
        return false;
    }

    public void removeRequisito(Curso curso){
        if(!contieneRequisito(curso))
            return;
        Iterator<CursoRequisito> itr = requisitos.iterator();
        CursoRequisito cr;
        while(itr.hasNext()){
            cr = itr.next();
            if(cr.getRequiere().getId().equals(curso.getId())) {
                itr.remove();
                break;
            }
        }
        curso.removeRequeridoPor(this);
    }

    public void removeRequeridoPor(Curso curso){
        if(!contieneRequeridoPor(curso))
            return;
        Iterator<CursoRequisito> itr = requeridoPor.iterator();
        CursoRequisito cr;
        while(itr.hasNext()){
            cr = itr.next();
            if(cr.getRequerido().getId().equals(curso.getId())) {
                itr.remove();
                break;
            }
        }
        curso.removeRequisito(this);
    }

    public ETipoRequisito getTipoReq(Curso curso){
        List<Curso> cursos = new ArrayList<>();
        for(CursoRequisito cr : requisitos){
            if(cr.getRequiere().getId().equals(curso.getId()))
                return cr.getTipoRequisito();
        }
        return ETipoRequisito.Ninguno;
    }

    @Override
    public String toString(){
        String c ="";
        c += "==========================================="+"\n";
        c+= code+": "+nombre+"\n";
        c+="Creditos: "+ creditos+"\n";
        List<Curso> list;
        if((list = getRequisitosCurso(ETipoRequisito.HaberAprobado)).size()>0) {
            c+="Requisitos:"+"\n";
            for(Curso cur : list)
                c+="\t"+cur.getCode() + ": " +cur.getNombre()+"\n";
        }
        if((list =getRequisitosCurso(ETipoRequisito.LlevarSimultaneo)).size()>0) {
            c += "Requerido llevar a la vez: " + "\n";
            for (Curso cur : list)
                c += "\t" + cur.getCode() + ": " + cur.getNombre() + "\n";
        }
        if((list =getRequisitosCurso(ETipoRequisito.NotaMinima08)).size()>0) {
            c += "Requerido tener nota minima 08 en: " + "\n";
            for (Curso cur : list)
                c += "\t" + cur.getCode() + ": " + cur.getNombre() + "\n";
        }
        if(minCreditos != 0)
            c+="Minimo de Creditos: "+minCreditos+"\n";
        return c;
    }
}
