package com.cursos.organizador.cursosservice.controller;

import com.cursos.organizador.cursosservice.assembler.CarreraAssembler;
import com.cursos.organizador.cursosservice.dto.CarreraDTO;
import com.cursos.organizador.cursosservice.repository.CarreraRepository;
import com.cursos.organizador.cursosservice.repository.PlanDeEstudiosRepository;
import com.cursos.organizador.model.model.Carrera;
import com.cursos.organizador.model.model.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carreras")
public class CarreraController {

    @Autowired
    public CarreraController(CarreraRepository cr, CarreraAssembler ca, PlanDeEstudiosRepository pde){
        carreraRepository = cr;
        carreraAssembler = ca;
        planDeEstudiosRepository = pde;
    }

    // Repository para sacar de la BD
    private final CarreraRepository carreraRepository;
    private final PlanDeEstudiosRepository planDeEstudiosRepository;
    private final CarreraAssembler carreraAssembler;

    @GetMapping()
    public CollectionModel<EntityModel<CarreraDTO>> list(){
        return carreraAssembler.toCollectionModelDTO(carreraRepository.findAll());
    }

    @GetMapping("/{id}")
    public EntityModel<CarreraDTO> getCarrera(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Carrera c = carreraRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("No se hallo la carrera" + id));
        return carreraAssembler.toModelDTO(c);
    }

}

