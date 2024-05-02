package com.fullstack.publicaciones.controller;

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

import com.fullstack.publicaciones.model.Autor;
import com.fullstack.publicaciones.model.ResponseDTO;
import com.fullstack.publicaciones.services.interfaces.IAutorService;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    IAutorService service;

    private static final String ALL_AUTORES = "lista-autores";
    
    @GetMapping
    public ResponseEntity<Object> getAutoresList() {
        List<Autor> autores = service.getAllAutors();

        if (!autores.isEmpty()) {
            var autoresResouce = autores.stream()
                    .map(autor -> EntityModel.of(autor,
                            WebMvcLinkBuilder
                                    .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAutor(autor.getId()))
                                    .withSelfRel()))
                    .collect(Collectors.toList());

            WebMvcLinkBuilder linkTo = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAutoresList());

            return ResponseEntity.ok(CollectionModel.of(autoresResouce, linkTo.withRel("publicaciones")));

        }
        else
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "No hay autores ingresados"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAutor(@PathVariable Long id) {
        try {
            Optional<Autor> autor = service.getAutorById(id);

            if (autor.isPresent()) {
                var autorModel = EntityModel.of(autor.get(),
                        WebMvcLinkBuilder
                                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAutor(id))
                                .withSelfRel(),
                        WebMvcLinkBuilder
                                .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAutoresList())
                                .withRel(ALL_AUTORES));

                return ResponseEntity.ok(autorModel);

            } else
                return ResponseEntity.badRequest().body(
                        new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "No existe el autor con el id " + id));


        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Object> createAutor(@RequestBody Autor autor) {
        if (autor.getNombre() == null || autor.getNombre().isBlank()
                || autor.getCorreo() == null || autor.getCorreo().isBlank())
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.BAD_REQUEST.value(), "Debe indicar Nombre y Correo de Autor"));

        try {
            var createResult = service.createAutor(autor);

            var autorModel = EntityModel.of(createResult,
                    WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAutor(createResult.getId()))
                            .withSelfRel(),
                    WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAutoresList())
                            .withRel(ALL_AUTORES));

            return ResponseEntity.ok(autorModel);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAutor(@PathVariable Long id, @RequestBody Autor autor) {
        if (autor.getNombre() == null || autor.getNombre().isBlank()
                || autor.getCorreo() == null || autor.getCorreo().isBlank())
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.BAD_REQUEST.value(), "Debe indicar Nombre y Correo de Autor"));

        try {
            var updated = service.updateAutor(id, autor);

            var autorModel = EntityModel.of(updated,
                    WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAutor(updated.getId()))
                            .withSelfRel(),
                    WebMvcLinkBuilder
                            .linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAutoresList())
                            .withRel(ALL_AUTORES));

            return ResponseEntity.ok(autorModel);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAutor(@PathVariable Long id) {
        try {
            service.deleteAutor(id);
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "Autor eliminado."));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

}
