package com.cursos.organizador.cursosservice.repository;

import com.cursos.organizador.model.model.Curso;
import com.cursos.organizador.model.model.CursoPlanDeEstudios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Query("select c from Curso c, PlanDeEstudios  p, CursoPlanDeEstudios e " +
            "where ?1 like p.carrera.nombre and e.planDeEstudios = p and e member of c.planes ")
    List<Curso> findByCarrera(String carrera);


    @Query("select c from Curso c where ?1 = c.code ")
    Curso findByCode(String code);


}
