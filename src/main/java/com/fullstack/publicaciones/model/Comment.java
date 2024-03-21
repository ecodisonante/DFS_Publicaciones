package com.fullstack.publicaciones.model;

import lombok.Getter;
import lombok.Setter;

public class Comment {
    
    @Getter private int id;
    @Getter private Usuario autor;
    @Getter private int publicacionId;

    @Getter @Setter private String contenido;
    @Getter @Setter private int puntaje;
    @Getter @Setter private int order;

    public Comment(int id, int publicacionId, Usuario autor, String contenido, int puntaje) {
        this.id = id;
        this.publicacionId = publicacionId;
        this.autor = autor;
        this.contenido = contenido;
        this.puntaje = puntaje;
    }


    
}
