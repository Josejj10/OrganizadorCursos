package com.cursos.organizador.cursosservice.dto;

import com.cursos.organizador.cursosservice.assembler.CursoModelAssembler;
import com.cursos.organizador.model.model.CursoPlanDeEstudios;
import com.cursos.organizador.model.model.CursoPlanDeEstudiosKey;
import com.cursos.organizador.model.model.CursoRequisito;
import com.cursos.organizador.model.model.PlanDeEstudios;
import com.cursos.organizador.model.model.enums.ETipoCurso;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.ArrayList;
import java.util.List;

public class CursoPlanDeEstudiosDTO {

    //private CursoPlanDeEstudiosKey id;
    private Long id;
    private String nombre;
    private String codigo;
    private double creditos;
    private int ciclo;
    private double minCreditos;
    private ETipoCurso tipoCurso;
    private List<String> carreras;
    private CollectionModel<EntityModel<CursoPlanDeEstudiosListaDTO>> planes;
    private CollectionModel<EntityModel<CursoRequisitoDTO>> requisitos;
    private CollectionModel<EntityModel<CursoRequisitoDTO>> requeridoPor;

    public CursoPlanDeEstudiosDTO(){
    }

    public CursoPlanDeEstudiosDTO(CursoPlanDeEstudios cpe) {
        this.id = cpe.getId();
        this.nombre = cpe.getCurso().getNombre();
        this.codigo = cpe.getCurso().getCode();
        this.creditos = cpe.getCurso().getCreditos();
        this.ciclo = cpe.getCiclo();
        this.minCreditos = cpe.getMinCreditos();
        this.tipoCurso = cpe.getTipoCurso();
        this.carreras = new ArrayList<>();
        CursoModelAssembler cma = new CursoModelAssembler();

        // planes
        for(CursoPlanDeEstudios pde : cpe.getCurso().getPlanes()){
            // Nombre de la carrera
            String c = pde.getPlanDeEstudios().getCarrera().getNombre();
            if(carreras.contains(c))
                c+= " - " + pde.getPlanDeEstudios().getCicloInicioPlan(); // Ing. Informatica - 2019-1
            carreras.add(c);
        }

        if (cpe.getCurso().getPlanes() !=null)
            planes = cma.toCollectionModelCPlanLista(cpe.getCurso().getPlanes());

        // Requisitos
        if (cpe.getRequisitos() != null)
            requisitos = cma.toCollectionModelCReq(cpe.getRequisitos());
        if(cpe.getRequeridoPor() != null)
            requeridoPor = cma.toCollectionModelCReq(cpe.getRequeridoPor());
    }

    public CollectionModel<EntityModel<CursoPlanDeEstudiosListaDTO>> getPlanes() {
        return planes;
    }

    public void setPlanes(CollectionModel<EntityModel<CursoPlanDeEstudiosListaDTO>> planes) {
        this.planes = planes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public double getMinCreditos() {
        return minCreditos;
    }

    public void setMinCreditos(double minCreditos) {
        this.minCreditos = minCreditos;
    }

    public List<String> getCarreras() {
        return carreras;
    }

    public void setCarreras(List<String> carreras) {
        this.carreras = carreras;
    }

    public CollectionModel<EntityModel<CursoRequisitoDTO>> getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(CollectionModel<EntityModel<CursoRequisitoDTO>> requisitos) {
        this.requisitos = requisitos;
    }

    public CollectionModel<EntityModel<CursoRequisitoDTO>> getRequeridoPor() {
        return requeridoPor;
    }

    public void setRequeridoPor(CollectionModel<EntityModel<CursoRequisitoDTO>> requeridoPor) {
        this.requeridoPor = requeridoPor;
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
}
