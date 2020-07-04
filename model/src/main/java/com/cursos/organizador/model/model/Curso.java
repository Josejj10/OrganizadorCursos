package com.cursos.organizador.model.model;

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
    private String nombre;
    private double creditos;
    private int minCreditos;
    private String code;
    private String abreviatura;
    private int ciclo;
    private String prof;
    private String horario;
    @ElementCollection
    private List<String> carreras;
    @ElementCollection
    private List<String> links;
    //@OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    //private Formula formula;
    // Requisitos para llevar el curso
    // Curso requiere
    @OneToMany(mappedBy = "requiere", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("apuntes")
    private List<CursoRequisito> requisitos;

    // Curso es requisito de
    @OneToMany(mappedBy = "requerido", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("apuntes")
    private List<CursoRequisito> requeridoPor;

    /*=============================
       CONSTRUCTORES
     *=============================*/

    public Curso() {
        links = new ArrayList<>();
//        apuntes = new ArrayList<>();
        requeridoPor = new ArrayList<>();
        requisitos = new ArrayList<>();
//        formula = new Formula();
        carreras = new ArrayList<>();
    }
    public Curso(Long id) {
        links = new ArrayList<>();
//        apuntes = new ArrayList<>();
        requeridoPor = new ArrayList<>();
        requisitos = new ArrayList<>();
//        formula = new Formula();
        carreras = new ArrayList<>();
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

//    public void addApunte(Apunte a) {
//        apuntes.add(a);
//        a.setCurso(this);
//    }
//
//    public void eliminarApunte(Apunte a) {
//        if (apuntes.remove(a))
//            a.setCurso(null);
//    }

//    public List<Apunte> getApuntes() {
//        return apuntes;
//    }
//
//    public void setApuntes(List<Apunte> apuntes) {
//        this.apuntes = apuntes;
//    }
//
//    public Formula getFormula() {
//        return formula;
//    }
//
//    public void setFormula(Formula formula) {
//        this.formula = formula;
//    }

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

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    public int getMinCreditos() {
        return minCreditos;
    }

    public void setMinCreditos(int minCreditos) {
        this.minCreditos = minCreditos;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public List<String> getCarreras() {
        return carreras;
    }

    public void setCarreras(List<String> carreras) {
        this.carreras = carreras;
    }

    public void addCarrera(String carrera){
        carreras.add(carrera);
    }

    public void removeCarrera(String carrera){
        carreras.remove(carrera);
    }

    public List<CursoRequisito> getRequisitos() {
        return requisitos;
    }

    public List<Curso> getRequisitosCurso(int tipoRequisito){
        List<Curso> cursos = new ArrayList<>();
        for(CursoRequisito cr : requisitos){
            if(cr.getTipoRequisito() == tipoRequisito)
                cursos.add(cr.getRequerido());
        }
        return cursos;
    }

    public List<Curso> getRequeridoPorCurso(int tipoRequisito){
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

    public boolean addRequisito(Curso curso, int tipoRequisito){
        for(CursoRequisito req : requisitos){
            if(req.getRequiere().getCode().equals(curso.getCode()))
                return false;
        }

        CursoRequisito c = new CursoRequisito(this,curso,tipoRequisito);
        this.requisitos.add(c);
        curso.getRequeridoPor().add(c);
        return true;
    }

    public boolean addRequeridoPor(Curso curso, int tipoRequisito){
        for(CursoRequisito req : requeridoPor){
            if(req.getRequerido().getCode().equals(curso.getCode()))
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

    public int getTipoReq(Curso curso){
        List<Curso> cursos = new ArrayList<>();
        for(CursoRequisito cr : requisitos){
            if(cr.getRequiere().getId() == curso.getId())
                return cr.getTipoRequisito();
        }
        return -1;
    }

    @Override
    public String toString(){
        String c ="";
        c += "==========================================="+"\n";
        c+= code+": "+nombre+"\n";
        c+="Ciclo " + ciclo+"\n";
        c+="Creditos: "+ creditos+"\n";
        List<Curso> list;
        if((list = getRequisitosCurso(0)).size()>0) {
            c+="Requisitos:"+"\n";
            for(Curso cur : list)
                c+="\t"+cur.getCode() + ": " +cur.getNombre()+"\n";
        }
        if((list =getRequisitosCurso(1)).size()>0) {
            c += "Requisitos a la vez: " + "\n";
            for (Curso cur : list)
                c += "\t" + cur.getCode() + ": " + cur.getNombre() + "\n";
        }
        if((list =getRequisitosCurso(2)).size()>0) {
            c += "Requisitos nota minima 08: " + "\n";
            for (Curso cur : list)
                c += "\t" + cur.getCode() + ": " + cur.getNombre() + "\n";
        }
        c+="Minimo de Creditos: "+minCreditos+"\n";
        return c;
    }

}
