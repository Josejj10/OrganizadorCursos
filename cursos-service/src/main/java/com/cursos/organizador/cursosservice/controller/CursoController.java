package com.cursos.organizador.cursosservice.controller;

import com.cursos.organizador.cursosservice.dto.CursoDTO;
import com.cursos.organizador.cursosservice.dto.CursoListaDTO;
import com.cursos.organizador.cursosservice.assembler.CursoModelAssembler;
import com.cursos.organizador.cursosservice.services.LectorCSVService;
import com.cursos.organizador.model.model.exception.ResourceNotFoundException;
import com.cursos.organizador.cursosservice.repository.CursoRepository;
import com.cursos.organizador.model.model.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/cursosBase")
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

    @GetMapping("page/{page}/{size}")
    public CollectionModel<EntityModel<CursoListaDTO>> listPage(
            @PathVariable("page") int page, @PathVariable("size") int size){
        // Page -1 porque comienza desde 0 y el cliente pedira desde pagina 1
        Page<Curso> pg=  cursoRepository.findAll(PageRequest.of(page-1,size));
        return cursoModelAssembler.toCollectionModelLista(pg.getContent());
    }

    @GetMapping("numPages/{size}")
    public int numPages(@PathVariable("size") int size){
        Page<Curso> pg = cursoRepository.findAll(PageRequest.of(0,size));
        return pg.getTotalPages();
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
                    c.setUnidadAcademica(curso.getUnidadAcademica());
                    c.setUltimoCicloDictado(curso.getUltimoCicloDictado());
                    c.setLinkCurso(curso.getLinkCurso());
                    c.setPlanes(curso.getPlanes());
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

}
