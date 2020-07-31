package com.cursos.organizador.cursosservice.controller;

import com.cursos.organizador.cursosservice.assembler.CursoModelAssembler;
import com.cursos.organizador.cursosservice.dto.CursoListaDTO;
import com.cursos.organizador.cursosservice.dto.CursoPlanDeEstudiosDTO;
import com.cursos.organizador.cursosservice.dto.CursoPlanDeEstudiosListaDTO;
import com.cursos.organizador.cursosservice.repository.CursoPlanDeEstudiosRepository;
import com.cursos.organizador.cursosservice.repository.CursoRepository;
import com.cursos.organizador.cursosservice.services.LectorCSVService;
import com.cursos.organizador.model.model.Curso;
import com.cursos.organizador.model.model.CursoPlanDeEstudios;
import com.cursos.organizador.model.model.CursoPlanDeEstudiosKey;
import com.cursos.organizador.model.model.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
@RequestMapping("/cursos")
@ComponentScan({"com.cursos.organizador.cursosservice.controller"})
public class CursoPlanDeEstudiosController {

    @Autowired
    public CursoPlanDeEstudiosController(CursoPlanDeEstudiosRepository cr, CursoModelAssembler cma, LectorCSVService ls){
        cursoRepository = cr;
        cursoModelAssembler = cma;
        lectorCSVService = ls;
    }

    // Repository para sacar los cursos de la BD
    private final CursoPlanDeEstudiosRepository cursoRepository;
    private final CursoModelAssembler cursoModelAssembler;
    private final LectorCSVService lectorCSVService;

    @GetMapping
    public CollectionModel<EntityModel<CursoPlanDeEstudiosListaDTO>> list() {
        return cursoModelAssembler.toCollectionModelCPlanLista(cursoRepository.findAll());
    }


    @GetMapping("page/{page}/{size}")
    public CollectionModel<EntityModel<CursoPlanDeEstudiosListaDTO>> listPage(
            @PathVariable("page") int page, @PathVariable("size") int size){
        // Page -1 porque comienza desde 0 y el cliente pedira desde pagina 1
        Page<CursoPlanDeEstudios> pg=  cursoRepository.findAll(PageRequest.of(page-1,size));
        return cursoModelAssembler.toCollectionModelCPlanLista(pg.getContent());
    }


    @GetMapping("numPages/{size}")
    public int numPages(@PathVariable("size") int size){
        Page<CursoPlanDeEstudios> pg = cursoRepository.findAll(PageRequest.of(0,size));
        return pg.getTotalPages();
    }

    @GetMapping("carrera/{carrera}")
    public CollectionModel<EntityModel<CursoPlanDeEstudiosDTO>> listaPorCarrera(@PathVariable("carrera") String carrera){
        List<CursoPlanDeEstudios> cursos = cursoRepository.findByCarrera(carrera);
        System.out.println(cursos);
        return cursoModelAssembler.toCollectionModelCPlan(cursos);
    }

    @GetMapping("/{idCurso}/{idPde}")
    public EntityModel<CursoPlanDeEstudiosDTO> getCurso(@PathVariable("idCurso") Long idCurso, @PathVariable("idPde") Long idPde)
    {
        CursoPlanDeEstudios c = cursoRepository.findByIdCursoAndIdPlanDeEstudios(idCurso,idPde);
        return cursoModelAssembler.toModelCPlan(c);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(Exception e) {
        System.out.println(e);
    }


    @PostMapping
    public ResponseEntity<?> crearCurso(@Valid @RequestBody CursoPlanDeEstudios curso){
        EntityModel<CursoPlanDeEstudiosDTO> entityModel =
                cursoModelAssembler.toModelCPlan(cursoRepository.save(curso));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateCurso(@PathVariable(value = "id") Long id,
//                                         @Valid @RequestBody CursoPlanDeEstudiosDTO curso)
//            throws ResourceNotFoundException
//    {
//        CursoPlanDeEstudios cur = cursoRepository.findById(id).
//                map( c ->{
//                    c.setCode(curso.getCode());
//                    c.setNombre(curso.getNombre());
//                    c.setCreditos(curso.getCreditos());
//                    c.setUnidadAcademica(curso.getUnidadAcademica());
//                    c.setUltimoCicloDictado(curso.getUltimoCicloDictado());
//                    c.setLinkCurso(curso.getLinkCurso());
//                    c.setPlanes(curso.getPlanes());
//                    return cursoRepository.save(c);
//                })
//                .orElseThrow(()->
//                        new ResourceNotFoundException("No se hallo el Curso "+id));
//        EntityModel<CursoDTO> entityModel = cursoModelAssembler.toModel(cur);
//        return ResponseEntity
//                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).
//                        body(entityModel);
//    }

    @DeleteMapping("/{idCurso}/{idPde}")
    public ResponseEntity<?> deleteCurso(@PathVariable(value="idCurso") Long idCurso, @PathVariable(value="idPde") Long idPde)
            throws ResourceNotFoundException
    {
        cursoRepository.deleteById(new CursoPlanDeEstudiosKey(idCurso,idPde));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/actualizarCursos")
    public void actualizarCursos(){
        lectorCSVService.leerCsvsLocales();
    }

}
