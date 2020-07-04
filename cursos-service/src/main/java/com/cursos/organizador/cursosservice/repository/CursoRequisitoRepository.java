package com.cursos.organizador.cursosservice.repository;

import com.cursos.organizador.model.model.CursoRequisito;
import com.cursos.organizador.model.model.CursoRequisitoKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRequisitoRepository extends JpaRepository<CursoRequisito, CursoRequisitoKey> {
}
