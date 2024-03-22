package com.fullstack.publicaciones.controller;

import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.publicaciones.model.UsuarioDTO;
import com.fullstack.publicaciones.services.UsuarioServices;

@RestController
public class UsuarioController {

    @Autowired
    UsuarioServices services;

    @GetMapping("/usuarios")
    public List<UsuarioDTO> getUsuarios() {
        return services.getUsuarios();
    }

    @GetMapping("/usuario/{id}")
    public UsuarioDTO getUsuario(@PathVariable String id) {
        return services.getById(id);
    }

}
