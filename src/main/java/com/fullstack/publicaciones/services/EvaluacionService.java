package com.fullstack.publicaciones.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fullstack.publicaciones.model.Evaluacion;
import com.fullstack.publicaciones.repository.EvaluacionRepository;
import com.fullstack.publicaciones.services.interfaces.IEvaluacionService;

@Service
public class EvaluacionService implements IEvaluacionService {

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Override
    public List<Evaluacion> getAllEvaluacions() {
        return evaluacionRepository.findAll();
    }

    @Override
    public Optional<Evaluacion> getEvaluacionById(Long id) {
        return evaluacionRepository.findById(id);
    }

    @Override
    public List<Evaluacion> getByPublicacionId(Long id) {
        return evaluacionRepository.findByPublicacionId(id);
    }

    @Override
    public Evaluacion createEvaluacion(Evaluacion evaluacion) throws Exception {
        var eval = evaluacionRepository.findByAutorIdAndPublicacionId(
                evaluacion.getAutor().getId(),
                evaluacion.getPublicacion().getId());
        if (eval.isEmpty())
            return evaluacionRepository.save(evaluacion);
        else
            throw new Exception("Ya ha evaluado esta publicacion");
    }

    @Override
    public Evaluacion updateEvaluacion(Long id, Evaluacion evaluacion) throws Exception {
        if (evaluacionRepository.existsById(id)) {
            evaluacion.setId(id);
            return evaluacionRepository.save(evaluacion);
        } else {
            throw new Exception("No se encontró ID de Evaluacion.");
        }
    }

    @Override
    public void deleteEvaluacion(Long id) throws Exception {
        if (evaluacionRepository.existsById(id)) {
            evaluacionRepository.deleteById(id);
        } else {
            throw new Exception("No se encontró ID de Evaluacion.");
        }
    }
}
