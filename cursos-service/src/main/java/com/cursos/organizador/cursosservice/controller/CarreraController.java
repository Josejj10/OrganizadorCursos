package com.cursos.organizador.cursosservice.controller;

import com.cursos.organizador.cursosservice.logic.LectorUrls;
import com.cursos.organizador.cursosservice.repository.CarreraRepository;
import com.cursos.organizador.model.model.Carrera;
import com.cursos.organizador.model.model.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/carreras")
public class CarreraController {

    @Autowired
    private CarreraRepository carreraRepository;

    @GetMapping()
    public List<Carrera> listar(){
        return carreraRepository.findAll();
    }

//    @PostMapping
//    public Carrera addCarreraFromUrl(@Valid @RequestBody String url){
//        Carrera c = new Carrera();
//        LectorUrls.leerUrl(url, c);
//        // Agregar los cursos de la carrera
//
//        carreraRepository.save(c);
//        return c;
//    }

}
