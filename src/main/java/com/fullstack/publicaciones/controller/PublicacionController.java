package com.fullstack.publicaciones.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

import com.fullstack.publicaciones.model.Publicacion;
import com.fullstack.publicaciones.model.ResponseDTO;
import com.fullstack.publicaciones.services.IAutorService;
import com.fullstack.publicaciones.services.IPublicacionService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/publicaciones")
public class PublicacionController {

    @Autowired
    IPublicacionService publicacionService;
    @Autowired
    IAutorService autorService;

    @GetMapping
    public ResponseEntity<Object> getPublicacionesList() {
        List<Publicacion> publicaciones = publicacionService.getAllPublicacions();

        if (!publicaciones.isEmpty())
            return ResponseEntity.ok(publicaciones.stream().map(Publicacion::toDto));
        else
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "No hay publicaciones ingresadas"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPublicacion(@PathVariable Long id) {
        try {
            Optional<Publicacion> publicacion = publicacionService.getPublicacionById(id);

            if (!publicacion.isPresent())
                return ResponseEntity.badRequest().body(
                        new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "No existe la publicacion con el id " + id));

            return ResponseEntity.ok(publicacion.get());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Object> createPublicacion(@RequestBody Publicacion pub) {
        try {
            // Validar publicacion entrante
            var isValidResponse = validarPublicacion(pub);
            if (isValidResponse != null)
                return ResponseEntity.badRequest().body(isValidResponse);

            // Identifica si es respuesta o base
            if (pub.getReferencia() > 0) {
                var ref = publicacionService.getPublicacionById(pub.getReferencia()).get();
                pub.setNivel(ref.getNivel() + 1);

            } else {
                pub.setNivel(1);
            }

            // La valoracion es solo para las respuestas
            if (pub.getNivel() == 1)
                pub.setValoracion(0);

            pub.setFecha(LocalDateTime.now());

            var createResult = publicacionService.createPublicacion(pub);

            // Aumentar cantidad de publicaciones del Autor
            autorService.increasePublicaciones(pub.getAutor().getId());

            // Si es comentario, actualiza publicacion referenciada
            if (pub.getReferencia() > 0)
                publicacionService.updateValoracion(pub.getReferencia());

            return ResponseEntity.ok(createResult);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> putMethodName(@PathVariable Long id, @RequestBody Publicacion publicacion) {
        try {
            // Validar publicacion entrante
            var isValidResponse = validarPublicacion(publicacion);
            if (isValidResponse != null)
                return ResponseEntity.badRequest().body(isValidResponse);

            // Si tiene respuestas, no se puede editar
            var respuestas = publicacionService.getReferencias(id);
            if (respuestas.size() > 0)
                throw new Exception("No puede editar una publicacion que ya tiene comentarios.");

            return ResponseEntity.ok(publicacionService.updatePublicacion(id, publicacion));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePublicacion(@PathVariable Long id) {
        try {
            publicacionService.deletePublicacion(id);
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "Publicacion eliminada."));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

    private ResponseDTO validarPublicacion(Publicacion pub) {

        if (pub.getAutor() == null || pub.getAutor().getId() == null)
            return new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "Debe indicar el ID del Autor.");

        if (autorService.getAutorById(pub.getAutor().getId()).isEmpty())
            return new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "No se encontró el ID del Autor.");

        if (pub.getContenido() == null || pub.getContenido().isEmpty())
            return new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "Debe ingresar el contenido.");

        if (pub.getReferencia() > 0 && publicacionService.getPublicacionById(pub.getReferencia()).isEmpty())
            return new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "No se encontró ID de Referencia.");

        return null;
    }

}
