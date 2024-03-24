package com.fullstack.publicaciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> getUsuarios() {
        List<UsuarioDTO> usuarios = services.getUsuarios();

        if (!usuarios.isEmpty()) return ResponseEntity.ok(usuarios);
        else return ResponseEntity.ok("Aun no hay usuarios registrados");
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<Object> getUsuario(@PathVariable String id) {
        UsuarioDTO user = services.getById(id);

        if (user != null) return ResponseEntity.ok(user);
        else return ResponseEntity.ok("No existe un usuario con el id " + id);
    }

}
