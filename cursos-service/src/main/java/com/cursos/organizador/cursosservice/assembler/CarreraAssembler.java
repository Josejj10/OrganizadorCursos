package com.cursos.organizador.cursosservice.assembler;

import com.cursos.organizador.cursosservice.controller.CarreraController;
import com.cursos.organizador.cursosservice.controller.CursoController;
import com.cursos.organizador.cursosservice.controller.PlanDeEstudiosController;
import com.cursos.organizador.cursosservice.dto.CarreraDTO;
import com.cursos.organizador.cursosservice.dto.CursoDTO;
import com.cursos.organizador.cursosservice.dto.PlanDeEstudiosCarreraDTO;
import com.cursos.organizador.cursosservice.dto.PlanDeEstudiosDTO;
import com.cursos.organizador.model.model.Carrera;
import com.cursos.organizador.model.model.Curso;
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
public class CarreraAssembler implements RepresentationModelAssembler<Carrera,
        EntityModel<Carrera>> {
    @Override
    public EntityModel<Carrera> toModel(Carrera carrera) {
        try {
            return EntityModel.of(carrera,
                    linkTo(methodOn(CarreraController.class).getCarrera(carrera.getId())).withSelfRel(),
                    linkTo(methodOn(CursoController.class).listaPorCarrera(carrera.getNombre())).withRel("cursos"));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public CollectionModel<EntityModel<Carrera>> toCollectionModel(List<Carrera> carreras) {
        return CollectionModel.of(carreras.stream().map(
                this::toModel).collect(Collectors.toList()),
                linkTo(methodOn(CarreraController.class).list()).withSelfRel());
    }

    public EntityModel<CarreraDTO> toModelDTO(Carrera carrera) {
        try {
            return EntityModel.of(new CarreraDTO(carrera),
                    linkTo(methodOn(CarreraController.class).getCarrera(carrera.getId())).withSelfRel(),
                    linkTo(methodOn(CursoController.class).listaPorCarrera(carrera.getNombre())).withRel("cursos"));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public CollectionModel<EntityModel<CarreraDTO>> toCollectionModelDTO(List<Carrera> carreras) {
        return CollectionModel.of(carreras.stream().map(
                this::toModelDTO).collect(Collectors.toList()),
                linkTo(methodOn(CarreraController.class).list()).withSelfRel());
    }

    public EntityModel<PlanDeEstudiosDTO> toModelPde(PlanDeEstudios plan) {
        try {
            return EntityModel.of(new PlanDeEstudiosDTO(plan),
                    linkTo(methodOn(PlanDeEstudiosController.class).getPlanDeEstudios(plan.getId())).withSelfRel(),
                    linkTo(methodOn(PlanDeEstudiosController.class).listaPorCarrera(plan.getCarrera().getNombre())).withRel("planes"));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public CollectionModel<EntityModel<PlanDeEstudiosDTO>> toCollectionModelPdE(List<PlanDeEstudios> planes) {
        return CollectionModel.of(planes.stream().map(this::toModelPde).collect(Collectors.toList()),
                linkTo(methodOn(PlanDeEstudiosController.class).list()).withSelfRel());
    }

    public EntityModel<PlanDeEstudiosCarreraDTO> toModelCarreraPdE(PlanDeEstudios plan) {
        try {
            return EntityModel.of(new PlanDeEstudiosCarreraDTO(plan),
                    linkTo(methodOn(PlanDeEstudiosController.class).getPlanDeEstudios(plan.getId())).withSelfRel(),
                    linkTo(methodOn(PlanDeEstudiosController.class).listaPorCarrera(plan.getCarrera().getNombre())).withRel("planes"));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    public CollectionModel<EntityModel<PlanDeEstudiosCarreraDTO>> toCollectionModelCarreraDTO(List<PlanDeEstudios> planes) {
        return CollectionModel.of(planes.stream().map(this::toModelCarreraPdE).collect(Collectors.toList()),
                linkTo(methodOn(PlanDeEstudiosController.class).list()).withSelfRel());
    }
}
