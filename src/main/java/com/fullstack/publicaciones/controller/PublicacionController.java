package com.fullstack.publicaciones.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
import com.fullstack.publicaciones.services.interfaces.IAutorService;
import com.fullstack.publicaciones.services.interfaces.IPublicacionService;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/publicaciones")
public class PublicacionController {

    @Autowired
    IPublicacionService publicacionService;
    @Autowired
    IAutorService autorService;

    private static final String ALL_PUBLICACIONES = "lista-publicaciones";

    @GetMapping
    public ResponseEntity<Object> getPublicacionesList() {
        List<Publicacion> publicaciones = publicacionService.getAllPublicacions();

        if (!publicaciones.isEmpty()) {
            var publicacionsResouce = publicaciones.stream()
                    .map(Publicacion::toDto)
                    .map(pub -> EntityModel.of(pub,
                            WebMvcLinkBuilder
                                    .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPublicacion(pub.getId()))
                                    .withSelfRel()))
                    .collect(Collectors.toList());

            WebMvcLinkBuilder linkTo = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPublicacionesList());

            return ResponseEntity.ok(CollectionModel.of(publicacionsResouce, linkTo.withRel("publicaciones")));

        } else {
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "No hay publicaciones ingresadas"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPublicacion(@PathVariable Long id) {
        try {
            Optional<Publicacion> publicacion = publicacionService.getPublicacionById(id);

            if (publicacion.isPresent()) {
                var coms = publicacionService.getComentariosById(id);
                publicacion.get().setComentarios(coms.stream().map(Publicacion::toDto).toList());

                var pubModels = EntityModel.of(publicacion.get(),
                        WebMvcLinkBuilder
                                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPublicacion(id))
                                .withSelfRel(),
                        WebMvcLinkBuilder
                                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPublicacionesList())
                                .withRel(ALL_PUBLICACIONES));

                return ResponseEntity.ok(pubModels);

            } else
                return ResponseEntity.badRequest().body(
                        new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "No existe la publicacion con el id " + id));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ResponseDTO(
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

            pub.setFecha(LocalDateTime.now());

            var createResult = publicacionService.createPublicacion(pub);

            var pubModel = EntityModel.of(createResult,
                    WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPublicacion(createResult.getId()))
                            .withSelfRel(),
                    WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPublicacionesList())
                            .withRel(ALL_PUBLICACIONES));

            // Aumentar cantidad de publicaciones del Autor
            autorService.updateCantPub(pub.getAutor().getId());

            return ResponseEntity.ok(pubModel);

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
            var respuestas = publicacionService.getComentariosById(id);
            if (!respuestas.isEmpty())
                throw new Exception("No puede editar una publicacion que ya tiene comentarios.");

            publicacion.setFecha(LocalDateTime.now());

            var updated = publicacionService.updatePublicacion(id, publicacion);

            var pubModel = EntityModel.of(updated,
                    WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPublicacion(updated.getId()))
                            .withSelfRel(),
                    WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getPublicacionesList())
                            .withRel(ALL_PUBLICACIONES));

            return ResponseEntity.ok(pubModel);
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

        if (pub.getReferencia() != null && pub.getReferencia().getId() > 0
                && publicacionService.getPublicacionById(pub.getReferencia().getId()).isEmpty())
            return new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "No se encontró ID de Referencia.");

        return null;
    }

}
