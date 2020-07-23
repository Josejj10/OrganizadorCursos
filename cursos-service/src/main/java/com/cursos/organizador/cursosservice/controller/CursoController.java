package com.cursos.organizador.cursosservice.controller;

import com.cursos.organizador.cursosservice.dto.CursoDTO;
import com.cursos.organizador.cursosservice.dto.CursoListaDTO;
import com.cursos.organizador.cursosservice.assembler.CursoModelAssembler;
import com.cursos.organizador.model.model.exception.ResourceNotFoundException;
import com.cursos.organizador.cursosservice.repository.CursoRepository;
import com.cursos.organizador.model.model.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
@RequestMapping("/cursos")
@ComponentScan({"com.cursos.organizador.cursosservice.controller"})
public class CursoController {

    @Autowired
    public CursoController(CursoRepository cr, CursoModelAssembler cma){
        cursoRepository = cr;
        cursoModelAssembler = cma;
    }

    // Repository para sacar los cursos de la BD
    private final CursoRepository cursoRepository;
    private final CursoModelAssembler cursoModelAssembler;

    @GetMapping
    public CollectionModel<EntityModel<CursoListaDTO>> list() {
       return cursoModelAssembler.toCollectionModelLista(cursoRepository.findAll());
    }

    @GetMapping("carrera/{carrera}")
    public CollectionModel<EntityModel<CursoListaDTO>> listaPorCarrera(@PathVariable("carrera") String carrera){
        List<Curso> cursos = cursoRepository.findByCarrera(carrera);
        System.out.println(cursos);
        return cursoModelAssembler.toCollectionModelLista(cursos);
    }

    @GetMapping("/{id}")
    public EntityModel<CursoDTO> getCurso(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Curso c = cursoRepository.findById(id).orElseThrow(()->
        new ResourceNotFoundException("No se hallo el Curso "+id));
        return cursoModelAssembler.toModel(c);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(Exception e) {
        System.out.println(e);
    }


    @PostMapping
    public ResponseEntity<?> crearCurso(@Valid @RequestBody Curso curso){
        EntityModel<CursoDTO> entityModel =
                cursoModelAssembler.toModel(cursoRepository.save(curso));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCurso(@PathVariable(value = "id") Long id,
                                             @Valid @RequestBody Curso curso)
            throws ResourceNotFoundException {
        Curso cur = cursoRepository.findById(id).
                map( c ->{
                    c.setCode(curso.getCode());
                    c.setNombre(curso.getNombre());
                    c.setCreditos(curso.getCreditos());
                    c.setMinCreditos(curso.getMinCreditos());
                    c.setUnidadAcademica(curso.getUnidadAcademica());
                    c.setUltimoCicloDictado(curso.getUltimoCicloDictado());
                    c.setLinkCurso(curso.getLinkCurso());
                    c.setPlanes(curso.getPlanes());
                    c.setRequisitos(curso.getRequisitos());
                    c.setRequeridoPor(curso.getRequeridoPor());
                    return cursoRepository.save(c);
                })
                .orElseThrow(()->
        new ResourceNotFoundException("No se hallo el Curso "+id));
        EntityModel<CursoDTO> entityModel = cursoModelAssembler.toModel(cur);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).
                body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCurso(@PathVariable(value="id") Long id)
            throws ResourceNotFoundException {
        cursoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}/requisitos")
    public CollectionModel<EntityModel<CursoDTO>> getRequisitos(@PathVariable("id") Long id) throws ResourceNotFoundException{
        Curso c = cursoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("No se hallo el Curso "+id));
        return cursoModelAssembler.toCollectionModel(c.getAllRequisitos());
    }

    @GetMapping("/{id}/requeridoPor")
    public CollectionModel<EntityModel<CursoDTO>> getRequeridoPor(@PathVariable("id") Long id) throws ResourceNotFoundException{
        Curso c = cursoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("No se hallo el Curso "+id));
        return cursoModelAssembler.toCollectionModel(c.getAllRequeridoPor());
    }

    //@GetMapping("/actualizarCursos")
    //public List<Carrera> actualizarCursos(){
      //  return lectorUrls.leer();
    //}

}
