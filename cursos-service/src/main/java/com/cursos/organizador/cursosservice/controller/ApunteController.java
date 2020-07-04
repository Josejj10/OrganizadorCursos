package com.cursos.organizador.cursosservice.controller;

import com.cursos.organizador.cursosservice.repository.ApunteRepository;
import com.cursos.organizador.model.model.Apunte;
import com.cursos.organizador.model.model.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/apuntes")
public class ApunteController {

    @Autowired
    private ApunteRepository apunteRepository;

    @GetMapping
    public List<Apunte> list(){
        //return mockup();
        return apunteRepository.findAll();
    }


    @GetMapping("/{id}")
    public Apunte getApunte(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return apunteRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("No se hallo el apunte "+id));
    }

    @PostMapping
    public Apunte crearApunte(@RequestBody Apunte apunte){
        return apunteRepository.save(apunte);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(Exception e) {
        System.out.println(e);
    }
    

    @PutMapping("/{id}")
    public Apunte updateApunte(@PathVariable(value = "apunte") Long id,
                             @Valid @RequestBody Apunte apunte)
            throws ResourceNotFoundException {
        Apunte a = apunteRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("No se hallo el apunte"+id));
        a.setCurso(apunte.getCurso());
        a.setTags(apunte.getTags());
        a.setFechaUltimaModificacion(Date.from(Instant.now()));
        a.setTexto(apunte.getTexto());
        a.setTitulo(apunte.getTitulo());
        return apunteRepository.save(a);
    }


    @DeleteMapping("/{id}")
    public Map<String,Boolean> deleteApunte(@PathVariable(value="id") Long id)
            throws ResourceNotFoundException {
        Apunte a = apunteRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("No se hallo el apunte "+id));
        apunteRepository.delete(a);
        Map<String,Boolean> response = new HashMap<>();
        response.put("Eliminado",Boolean.TRUE);
        return response;
    }

    private List<Apunte> mockup(){
        List<Apunte> ap = new ArrayList<>();
        ap.add(new Apunte(1l));
        ap.add(new Apunte(2l));
        ap.add(new Apunte(3l));
        return ap;
    }

}
