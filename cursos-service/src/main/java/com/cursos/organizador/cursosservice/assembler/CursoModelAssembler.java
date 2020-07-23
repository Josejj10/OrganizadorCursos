package com.cursos.organizador.cursosservice.assembler;

import com.cursos.organizador.cursosservice.controller.CarreraController;
import com.cursos.organizador.cursosservice.controller.CursoController;
import com.cursos.organizador.cursosservice.controller.PlanDeEstudiosController;
import com.cursos.organizador.cursosservice.dto.CursoDTO;
import com.cursos.organizador.cursosservice.dto.CursoListaDTO;
import com.cursos.organizador.cursosservice.dto.CursoPlanDeEstudiosDTO;
import com.cursos.organizador.cursosservice.dto.CursoRequisitoDTO;
import com.cursos.organizador.model.model.Curso;
import com.cursos.organizador.model.model.CursoPlanDeEstudios;
import com.cursos.organizador.model.model.CursoRequisito;
import com.cursos.organizador.model.model.PlanDeEstudios;
import com.cursos.organizador.model.model.exception.ResourceNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CursoModelAssembler implements RepresentationModelAssembler<Curso,
        EntityModel<CursoDTO>> {
    @Override
    public EntityModel<CursoDTO> toModel(Curso curso){
        try {
            return EntityModel.of(new CursoDTO(curso),
                    linkTo(methodOn(CursoController.class).getCurso(curso.getId())).withSelfRel(),
                    linkTo(methodOn(CursoController.class).list()).withRel("cursos"));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    public EntityModel<CursoRequisitoDTO> toModelCR(CursoRequisito cr){
        try{
            return EntityModel.of(new CursoRequisitoDTO(cr),
            linkTo(methodOn(CursoController.class).getCurso(cr.getId().getRequiere())).withRel("requiere"),
            linkTo(methodOn(CursoController.class).getCurso(cr.getId().getRequerido())).withRel("requeridoPor"));
        }catch(ResourceNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }

    public EntityModel<CursoPlanDeEstudiosDTO> toModelCPlan(CursoPlanDeEstudios cpe){
        try{
            return EntityModel.of(new CursoPlanDeEstudiosDTO(cpe),
                    linkTo(methodOn(CursoController.class).getCurso(cpe.getId().getCurso())).withRel("curso"), // Link Curso
                    linkTo(methodOn(PlanDeEstudiosController.class).getPlanDeEstudios(cpe.getId().getPlanDeEstudios())).withRel("planEstudios")); // Link Plan esutdios
        }catch(ResourceNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }

    public EntityModel<CursoListaDTO> toModelList(Curso curso){
        try {
            return EntityModel.of(new CursoListaDTO(curso),
                    linkTo(methodOn(CursoController.class).getCurso(curso.getId())).withSelfRel(),
                    linkTo(methodOn(CursoController.class).list()).withRel("cursos"));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public CollectionModel<EntityModel<CursoDTO>> toCollectionModel(List<Curso> cursos) {
       return CollectionModel.of(cursos.stream().map(
                this::toModel).collect(Collectors.toList()),
                linkTo(methodOn(CursoController.class).list()).withSelfRel());
    }

    public CollectionModel<EntityModel<CursoListaDTO>> toCollectionModelLista(List<Curso> cursos) {
       return CollectionModel.of(cursos.stream().map(
                this::toModelList).collect(Collectors.toList()),
                linkTo(methodOn(CursoController.class).list()).withSelfRel());
    }

    public CollectionModel<EntityModel<CursoRequisitoDTO>> toCollectionModelCReq(List<CursoRequisito> reqs) {
       return CollectionModel.of(reqs.stream().map(
                this::toModelCR).collect(Collectors.toList()));
    }

    public CollectionModel<EntityModel<CursoPlanDeEstudiosDTO>> toCollectionModelCPlan(List<CursoPlanDeEstudios> cpes) {
        return CollectionModel.of(cpes.stream().map(
                this::toModelCPlan).collect(Collectors.toList()));
    }

}
