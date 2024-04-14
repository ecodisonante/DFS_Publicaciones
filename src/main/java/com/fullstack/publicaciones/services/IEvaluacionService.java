package com.fullstack.publicaciones.services;

import java.util.List;
import java.util.Optional;
import com.fullstack.publicaciones.model.Evaluacion;

public interface IEvaluacionService {

    List<Evaluacion> getAllEvaluacions();

    Optional<Evaluacion> getEvaluacionById(Long id);

    List<Evaluacion> getByPublicacionId(Long id);

    Evaluacion createEvaluacion(Evaluacion evaluacion) throws Exception;

    Evaluacion updateEvaluacion(Long id, Evaluacion evaluacion) throws Exception;

    void deleteEvaluacion(Long id) throws Exception;

}
