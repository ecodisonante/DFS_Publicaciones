package com.fullstack.publicaciones.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class UsuarioDTO {
    
    @Getter @Setter String username;
    @Getter @Setter String name;
    @Getter @Setter String email;
    @Getter @Setter int publicaciones;
    @Getter @Setter int comentarios;
    
}
