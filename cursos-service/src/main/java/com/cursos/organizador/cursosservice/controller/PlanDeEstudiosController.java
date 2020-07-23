package com.cursos.organizador.cursosservice.controller;

import com.cursos.organizador.cursosservice.assembler.CarreraAssembler;
import com.cursos.organizador.cursosservice.dto.PlanDeEstudiosDTO;
import com.cursos.organizador.cursosservice.repository.PlanDeEstudiosRepository;
import com.cursos.organizador.model.model.PlanDeEstudios;
import com.cursos.organizador.model.model.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
@RequestMapping("/planesEstudio")
@ComponentScan({"com.cursos.organizador.cursosservice.controller"})
public class PlanDeEstudiosController {

    @Autowired
    public PlanDeEstudiosController(PlanDeEstudiosRepository pde, CarreraAssembler ca){
        planDeEstudiosRepository = pde;
        carreraAssembler = ca;
    }

    // Repository para sacar los cursos de la BD
    private final PlanDeEstudiosRepository planDeEstudiosRepository;
    private final CarreraAssembler carreraAssembler;

    @GetMapping
    public CollectionModel<EntityModel<PlanDeEstudiosDTO>> list() {
        return carreraAssembler.toCollectionModelPdE(planDeEstudiosRepository.findAll());
    }

    @GetMapping("/{id}")
    public EntityModel<PlanDeEstudiosDTO> getPlanDeEstudios(@PathVariable("id") Long id) throws ResourceNotFoundException {
        PlanDeEstudios plan = planDeEstudiosRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("No se hallo el Plan de Estudios " + id));
        return carreraAssembler.toModelPde(plan);
    }


    @GetMapping("/porCarrera/{carrera}")
    public CollectionModel<EntityModel<PlanDeEstudiosDTO>> listaPorCarrera(@PathVariable("carrera") String carrera){
        List<PlanDeEstudios> planes = planDeEstudiosRepository.findByCarrera(carrera);
        return carreraAssembler.toCollectionModelPdE(planes);
    }
}
