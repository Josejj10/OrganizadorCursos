package com.cursos.organizador.cursosservice.repository;

import com.cursos.organizador.model.model.Apunte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApunteRepository extends JpaRepository<Apunte, Long> {
}
