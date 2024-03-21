package com.fullstack.publicaciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.publicaciones.model.Publicacion;
import com.fullstack.publicaciones.model.PublicacionListDTO;
import com.fullstack.publicaciones.services.PublicacionServices;

@RestController
public class PublicacionController {
    
    @Autowired
    PublicacionServices services;

    @GetMapping("/publicaciones")
    public List<PublicacionListDTO> getPeliculas() {
        return services.getPublicaciones();
    }

    @GetMapping("/publicaciones/{id}")
    public Publicacion getPublicacion(@PathVariable int id) {
        return services.getById(id);
    }

}
