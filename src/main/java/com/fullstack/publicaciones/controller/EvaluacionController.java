package com.fullstack.publicaciones.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.publicaciones.model.Evaluacion;
import com.fullstack.publicaciones.model.ResponseDTO;
import com.fullstack.publicaciones.services.IAutorService;
import com.fullstack.publicaciones.services.IEvaluacionService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/evaluaciones")
public class EvaluacionController {

    @Autowired
    IEvaluacionService evaluacionService;
    @Autowired
    IAutorService autorService;

    @GetMapping
    public ResponseEntity<Object> getEvaluacionesList() {
        List<Evaluacion> evaluaciones = evaluacionService.getAllEvaluacions();

        if (!evaluaciones.isEmpty())
            return ResponseEntity.ok(evaluaciones);
        else
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "No hay evaluaciones ingresadas"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEvaluacion(@PathVariable Long id) {
        try {
            Optional<Evaluacion> evaluacion = evaluacionService.getEvaluacionById(id);

            if (!evaluacion.isPresent())
                return ResponseEntity.badRequest().body(
                        new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "No existe evaluacion con el id " + id));

            return ResponseEntity.ok(evaluacion.get());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Object> createEvaluacion(@RequestBody Evaluacion evaluacion) {
        try {
            // Validar publicacion entrante
            var isValidResponse = validarEvaluacion(evaluacion);
            if (isValidResponse != null)
                return ResponseEntity.badRequest().body(isValidResponse);

                var eval = evaluacionService.createEvaluacion(evaluacion);
                autorService.updateCantEval(evaluacion.getAutor().getId());
                
            return ResponseEntity.ok(eval);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PutMapping("path/{id}")
    public ResponseEntity<Object> putMethodName(@PathVariable Long id, @RequestBody Evaluacion evaluacion) {
        try {
            // Validar publicacion entrante
            var isValidResponse = validarEvaluacion(evaluacion);
            if (isValidResponse != null)
                return ResponseEntity.badRequest().body(isValidResponse);

            return ResponseEntity.ok(evaluacionService.updateEvaluacion(id, evaluacion));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEvaluacion(@PathVariable Long id) {
        try {
            evaluacionService.deleteEvaluacion(id);
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "Evaluacion eliminado."));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    private ResponseDTO validarEvaluacion(Evaluacion eva) {

        if (eva.getAutor() == null || eva.getAutor().getId() == 0)
            return new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "Debe indicar el ID del Autor.");

        if (eva.getPublicacion() == null || eva.getPublicacion().getId() == 0)
            return new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "Debe indicar el ID de Publicacion.");

        if (autorService.getAutorById(eva.getAutor().getId()).isEmpty())
            return new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "No se encontró el ID del Autor.");

        if (eva.getPuntaje() < 1 || eva.getPuntaje() > 5)
            return new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "Puntaje debe ser entre 1 y 5");

        return null;
    }

}
