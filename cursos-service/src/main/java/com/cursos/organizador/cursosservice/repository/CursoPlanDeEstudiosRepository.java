package com.cursos.organizador.cursosservice.repository;

import com.cursos.organizador.model.model.Curso;
import com.cursos.organizador.model.model.CursoPlanDeEstudios;
import com.cursos.organizador.model.model.CursoPlanDeEstudiosKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoPlanDeEstudiosRepository extends JpaRepository<CursoPlanDeEstudios, CursoPlanDeEstudiosKey> {

    @Query("select e from Curso c, PlanDeEstudios  p, CursoPlanDeEstudios e " +
            "where ?1 like p.carrera.nombre and e.planDeEstudios = p and e member of c.planes ")
    List<CursoPlanDeEstudios> findByCarrera(String carrera);

    @Query("select e from CursoPlanDeEstudios e " +
            "where ?1 = e.curso.id and ?2 = e.planDeEstudios.id")
    CursoPlanDeEstudios findByIdCursoAndIdPlanDeEstudios(Long id_curso, Long id_planDeEstudios);
}
