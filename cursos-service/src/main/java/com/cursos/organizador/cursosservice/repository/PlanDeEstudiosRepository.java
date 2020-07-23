package com.cursos.organizador.cursosservice.repository;

import com.cursos.organizador.model.model.Curso;
import com.cursos.organizador.model.model.PlanDeEstudios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanDeEstudiosRepository extends JpaRepository<PlanDeEstudios, Long> {

    @Query("select p from PlanDeEstudios p  where ?1 = p.carrera.nombre")
    List<PlanDeEstudios> findByCarrera(String carrera);
}
