package com.fullstack.publicaciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.publicaciones.model.PublicacionDTO;
import com.fullstack.publicaciones.services.PublicacionServices;

@RestController
public class PublicacionController {
    
    @Autowired
    PublicacionServices services;

    @GetMapping("/publicaciones")
    public ResponseEntity<Object> getPeliculas() {
        List<PublicacionDTO> pubs = services.getPublicaciones();

        if (!pubs.isEmpty()) return ResponseEntity.ok(pubs);
        else return ResponseEntity.ok("Aun no hay publicaciones registradas");
    }

    @GetMapping("/publicaciones/{id}")
    public ResponseEntity<Object> getPublicacion(@PathVariable int id) {
        Object pub = services.getById(id);
        
        if (pub != null) return ResponseEntity.ok(pub);
        else return ResponseEntity.ok("No existe la publicacion con el id " + id);
    }

}
