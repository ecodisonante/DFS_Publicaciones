package com.fullstack.publicaciones.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fullstack.publicaciones.model.Evaluacion;

public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {

    List<Evaluacion> findByPublicacionId(long id);

    Optional<Evaluacion> findByAutorIdAndPublicacionId(long autorId, long publicacionId);
    
}
