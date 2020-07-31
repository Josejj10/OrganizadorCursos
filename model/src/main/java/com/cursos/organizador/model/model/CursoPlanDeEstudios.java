package com.cursos.organizador.model.model;

import com.cursos.organizador.model.model.enums.ETipoCurso;
import com.cursos.organizador.model.model.enums.ETipoRequisito;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")

public class CursoPlanDeEstudios {

    //@EmbeddedId
    //private CursoPlanDeEstudiosKey id;

    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne(cascade = CascadeType.PERSIST)
    //@MapsId("curso")
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @ManyToOne
    //@MapsId("planDeEstudios")
    @JoinColumn(name = "planDeEstudios_id")
    private PlanDeEstudios planDeEstudios;

    private int ciclo;
    private ETipoCurso tipoCurso;

    private Double minCreditos;

    @OneToMany(mappedBy = "requiere", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<CursoRequisito> requisitos;

    // De que cursos es requisito
    @OneToMany(mappedBy = "requerido", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<CursoRequisito> requeridoPor;


    // region Constructores

    public CursoPlanDeEstudios() {
        //id = new CursoPlanDeEstudiosKey();
        requisitos = new ArrayList<>();
        requeridoPor = new ArrayList<>();
        minCreditos = 0.0;
        curso = new Curso();
        tipoCurso = ETipoCurso.Otro;
    }

    public CursoPlanDeEstudios(Curso curso, PlanDeEstudios planDeEstudios) {
        //id = new CursoPlanDeEstudiosKey();
        this.curso = curso;
        this.planDeEstudios = planDeEstudios;
        requisitos = new ArrayList<>();
        requeridoPor = new ArrayList<>();
        minCreditos = 0.0;
        tipoCurso = ETipoCurso.Otro;
    }

    // endregion

    // region Getters y Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public PlanDeEstudios getPlanDeEstudios() {
        return planDeEstudios;
    }

    public void setPlanDeEstudios(PlanDeEstudios planDeEstudios) {
        this.planDeEstudios = planDeEstudios;
    }

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    public ETipoCurso getTipoCurso() {
        return tipoCurso;
    }

    public void setTipoCurso(ETipoCurso tipoCurso) {
        this.tipoCurso = tipoCurso;
    }

    public Double getMinCreditos() {
        return minCreditos;
    }

    public void setMinCreditos(Double minCreditos) {
        this.minCreditos = minCreditos;
    }

    // endregion

    public int compararTipoCurso(CursoPlanDeEstudios c2){
        // Si son distintos tipos de cursos
        int comp = tipoCurso.compareTo(c2.tipoCurso);
        if(comp!=0)
            return comp;
        // Si no, por ciclo
        comp = Integer.compare(ciclo, c2.ciclo);
        if(comp!=0)
            return comp;

        // Si no, alfabeticamente
        return curso.getNombre().compareTo(c2.getCurso().getNombre());
    }

    public List<CursoRequisito> getRequisitos() {
        return requisitos;
    }

    public List<CursoPlanDeEstudios> getRequisitosCurso(ETipoRequisito tipoRequisito){
        List<CursoPlanDeEstudios> cursos = new ArrayList<>();
        for(CursoRequisito cr : requisitos){
            if(cr.getTipoRequisito() == tipoRequisito)
                cursos.add(cr.getRequerido());
        }
        return cursos;
    }

    public List<CursoPlanDeEstudios> getAllRequisitos(){
        List<CursoPlanDeEstudios> cursos = new ArrayList<>();
        for(CursoRequisito cr : requisitos){
            cursos.add(cr.getRequerido());
        }
        return cursos;
    }

    public void setRequisitos(List<CursoRequisito> requisitos) {
        this.requisitos = requisitos;
    }

    public boolean contieneRequisito(CursoPlanDeEstudios curso) {
        Iterator<CursoRequisito> itr = requisitos.iterator();
        CursoRequisito cr;
        while (itr.hasNext()) {
            cr = itr.next();
            if (cr.getRequiere().getId().equals(curso.getId()))
                return true;
        }
        return false;
    }

    public boolean contieneRequeridoPor(CursoPlanDeEstudios curso){
        Iterator<CursoRequisito> itr = requisitos.iterator();
        CursoRequisito cr;
        while (itr.hasNext()) {
            cr = itr.next();
            if (cr.getRequerido().getId().equals(curso.getId()))
                return true;
        }
        return false;
    }

    public void removeRequisito(CursoPlanDeEstudios curso){
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

    public List<CursoPlanDeEstudios> getRequeridoPorCurso(ETipoRequisito tipoRequisito){
        List<CursoPlanDeEstudios> cursos = new ArrayList<>();
        for(CursoRequisito cr : requeridoPor){
            if(cr.getTipoRequisito() == tipoRequisito)
                cursos.add(cr.getRequiere());
        }
        return cursos;
    }

    public List<CursoPlanDeEstudios> getAllRequeridoPor(){
        List<CursoPlanDeEstudios> cursos = new ArrayList<>();
        for(CursoRequisito cr : requeridoPor){
            cursos.add(cr.getRequiere());
        }
        return cursos;
    }
    public List<CursoRequisito> getRequeridoPor() {
        return requeridoPor;
    }

    public void setRequeridoPor(List<CursoRequisito> requeridoPor) {
        this.requeridoPor = requeridoPor;
    }

    public boolean addRequisito(CursoPlanDeEstudios curso, ETipoRequisito tipoRequisito){
        for(CursoRequisito req : requisitos){
            if(req.getRequerido().getCurso().getCode().equals(curso.getCurso().getCode()))
                return false;
        }

        CursoRequisito c = new CursoRequisito(this,curso,tipoRequisito);
        this.requisitos.add(c);
        curso.getRequeridoPor().add(c);
        return true;
    }

    public void removeRequeridoPor(CursoPlanDeEstudios c){
        if(!contieneRequeridoPor(c))
            return;
        Iterator<CursoRequisito> itr = getRequeridoPor().iterator();
        CursoRequisito cr;
        while(itr.hasNext()){
            cr = itr.next();
            if(cr.getRequerido().getId().equals(c.getId())) {
                itr.remove();
                break;
            }
        }
        c.removeRequisito(this);
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
        c += this.curso.toString();
        c+= "Ciclo: " + ciclo + "\nTipo Curso: " + tipoCurso+"\n";
        //c+= id.toString()+'\n';
        List<CursoPlanDeEstudios> list;
        if((list = getRequisitosCurso(ETipoRequisito.HaberAprobado)).size()>0) {
            c+="Requisitos:"+"\n";
            for(CursoPlanDeEstudios cur : list)
                c+="\t"+cur.getCurso().getCode() + ": " +cur.getCurso().getNombre()+"\n";
        }
        if((list =getRequisitosCurso(ETipoRequisito.LlevarSimultaneo)).size()>0) {
            c += "Requerido llevar a la vez: " + "\n";
            for (CursoPlanDeEstudios cur : list)
                c += "\t" + cur.getCurso().getCode() + ": " + cur.getCurso().getNombre() + "\n";
        }
        if((list =getRequisitosCurso(ETipoRequisito.NotaMinima08)).size()>0) {
            c += "Requerido tener nota minima 08 en: " + "\n";
            for (CursoPlanDeEstudios cur : list)
                c += "\t" + cur.getCurso().getCode() + ": " + cur.getCurso().getNombre() + "\n";
        }
        if((list =getAllRequeridoPor()).size()>0) {
            c += "Requerido por: " + "\n";
            for (CursoPlanDeEstudios cur : list) {
                c += "\t" + cur.getCurso().getCode() + ": " + cur.getCurso().getNombre() + "\n";
                c+= '\t'+ cur.getId().toString()+"\n";
            }
        }
        if(minCreditos != 0.0)
            c+="Minimo de Creditos: "+minCreditos+"\n";
        return c;
    }


}
