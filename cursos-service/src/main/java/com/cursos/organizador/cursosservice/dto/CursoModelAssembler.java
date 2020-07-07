package com.cursos.organizador.cursosservice.dto;

import com.cursos.organizador.cursosservice.controller.CursoController;
import com.cursos.organizador.model.model.Curso;
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
}
