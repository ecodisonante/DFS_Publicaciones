package com.fullstack.publicaciones.model;

import lombok.Getter;
import lombok.Setter;

public class Usuario {
    
    @Getter private String id;
    @Getter private String email;
    
    @Setter private String passwd;
    @Getter @Setter private String name;
    
    public Usuario(String id, String name, String email, String passwd) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwd = passwd;
    }

}
