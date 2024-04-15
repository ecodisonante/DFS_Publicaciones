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

import com.fullstack.publicaciones.model.Autor;
import com.fullstack.publicaciones.model.ResponseDTO;
import com.fullstack.publicaciones.services.IAutorService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    IAutorService _service;

    @GetMapping
    public ResponseEntity<Object> getAutoresList() {
        List<Autor> autores = _service.getAllAutors();

        if (!autores.isEmpty())
            return ResponseEntity.ok(autores);
        else
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "No hay autores ingresados"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAutor(@PathVariable Long id) {
        try {
            Optional<Autor> autor = _service.getAutorById(id);

            if (!autor.isPresent())
                return ResponseEntity.badRequest().body(
                        new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "No existe el autor con el id " + id));

            return ResponseEntity.ok(autor.get());

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
            return ResponseEntity.ok(_service.createAutor(autor));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> putMethodName(@PathVariable Long id, @RequestBody Autor autor) {
        if (autor.getNombre() == null || autor.getNombre().isBlank()
                || autor.getCorreo() == null || autor.getCorreo().isBlank())
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.BAD_REQUEST.value(), "Debe indicar Nombre y Correo de Autor"));

        try {
            return ResponseEntity.ok(_service.updateAutor(id, autor));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAutor(@PathVariable Long id) {
        try {
            _service.deleteAutor(id);
            return ResponseEntity.ok(new ResponseDTO(HttpStatus.OK.value(), "Autor eliminado."));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDTO(
                    HttpStatus.BAD_REQUEST.value(), e.getMessage()));
        }
    }

}
