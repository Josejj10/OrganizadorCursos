package com.cursos.organizador.cursosservice.controller;

import com.cursos.organizador.cursosservice.dto.CursoDTO;
import com.cursos.organizador.cursosservice.dto.CursoModelAssembler;
import com.cursos.organizador.cursosservice.services.LectorUrlsService;
import com.cursos.organizador.model.model.Carrera;
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
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.CollectionModel;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/cursos")
@ComponentScan({"com.cursos.organizador.cursosservice.controller"})
public class CursoController {

    @Autowired
    public CursoController(CursoRepository cr, LectorUrlsService ls, CursoModelAssembler cma){
        cursoRepository = cr;
        lectorUrls = ls;
        cursoModelAssembler = cma;
    }

    // Repository para sacar los cursos de la BD
    private final CursoRepository cursoRepository;
    private final LectorUrlsService lectorUrls;
    private final CursoModelAssembler cursoModelAssembler;

    @GetMapping
    public CollectionModel<EntityModel<CursoDTO>> list() {
       return cursoModelAssembler.toCollectionModel(cursoRepository.findAll());
    }

    //@GetMapping
//    public List<CursoDTO> list(){
//        List<CursoDTO> cursos = new ArrayList<>();
//        for(Curso c :cursoRepository.findAll())
//            cursos.add(new CursoDTO(c));
//        return cursos;
//    }

    @GetMapping("/{id}")
    public EntityModel<CursoDTO> getCurso(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Curso c = cursoRepository.findById(id).orElseThrow(()->
        new ResourceNotFoundException("No se hallo el Curso "+id));
        return cursoModelAssembler.toModel(c);
    }
//
//    @GetMapping("/{id}")
//    public CursoDTO getCurso(@PathVariable("id") Long id) throws ResourceNotFoundException {
//        Curso c = cursoRepository.findById(id).orElseThrow(()->
//        new ResourceNotFoundException("No se hallo el Curso "+id));
//        return new CursoDTO(c);
//    }

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

//    @PostMapping
//    public Curso crearCurso(@Valid @RequestBody Curso curso){
//        return cursoRepository.save(curso);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCurso(@PathVariable(value = "id") Long id,
                                             @Valid @RequestBody Curso curso)
            throws ResourceNotFoundException {

        Curso cur = cursoRepository.findById(id).
                map( c ->{
                    c.setNombre(curso.getNombre());
                    c.setCode(curso.getCode());
                    c.setCreditos(curso.getCreditos());
                    c.setHorario(curso.getHorario());
                    c.setProf(curso.getProf());
                    c.setLinks(curso.getLinks());
                    c.setRequisitos(curso.getRequisitos());
                    c.setRequeridoPor(curso.getRequeridoPor());
                    c.setCiclo(curso.getCiclo());
                    c.setMinCreditos(curso.getMinCreditos());
                    c.setAbreviatura(curso.getAbreviatura());
                    return cursoRepository.save(c);
                })
                .orElseThrow(()->
        new ResourceNotFoundException("No se hallo el Curso "+id));
        EntityModel<CursoDTO> entityModel = cursoModelAssembler.toModel(cur);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).
                body(entityModel);
    }

//    @PutMapping("/{id}")
//    public Curso updateCurso(@PathVariable(value = "id") Long id,
//                                             @Valid @RequestBody Curso curso)
//            throws ResourceNotFoundException {
//
//        Curso c = cursoRepository.findById(id).orElseThrow(()->
//        new ResourceNotFoundException("No se hallo el Curso "+id));
//        c.setNombre(curso.getNombre());
//        c.setCode(curso.getCode());
//        c.setCreditos(curso.getCreditos());
//        c.setHorario(curso.getHorario());
//        c.setProf(curso.getProf());
//        c.setLinks(curso.getLinks());
//        c.setRequisitos(curso.getRequisitos());
//        c.setRequeridoPor(curso.getRequeridoPor());
//        c.setCiclo(curso.getCiclo());
//        c.setMinCreditos(curso.getMinCreditos());
//        c.setAbreviatura(curso.getAbreviatura());
//        return cursoRepository.save(c);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCurso(@PathVariable(value="id") Long id)
            throws ResourceNotFoundException {
        cursoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}/requisitos")
    public List<Curso> getRequisitos(@PathVariable("id") Long id) throws ResourceNotFoundException{
        Curso c = cursoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("No se hallo el Curso "+id));
        return c.getAllRequisitos();
    }

    @GetMapping("/{id}/requeridoPor")
    public List<Curso> getRequeridoPor(@PathVariable("id") Long id) throws ResourceNotFoundException{
        Curso c = cursoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("No se hallo el Curso "+id));
        return c.getAllRequeridoPor();
    }

    @GetMapping("/{id}/tieneReq")
    public boolean contieneRequisito(@PathVariable("id") Long id,@Valid @RequestBody Long idReq) throws ResourceNotFoundException{
        Curso c = cursoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("No se hallo el curso" +id));
        for(Curso cr : c.getAllRequisitos())
            if(cr.getId().equals(idReq))
                return true;
        return false;
    }

    @GetMapping("/{id}/reqPor")
    public boolean esRequeridoPor(@PathVariable("id") Long id,@Valid @RequestBody Long idReq) throws ResourceNotFoundException{
        Curso c = cursoRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("No se hallo el curso" +id));
        for(Curso cr : c.getAllRequeridoPor())
            if(cr.getId().equals(idReq))
                return true;
        return false;
    }

    @GetMapping("/actualizarCursos")
    public List<Carrera> actualizarCursos(){
        return lectorUrls.leer();
    }

}
