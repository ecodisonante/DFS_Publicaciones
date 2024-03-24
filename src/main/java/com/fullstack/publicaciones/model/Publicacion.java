package com.fullstack.publicaciones.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Publicacion {

    @Getter @Setter private int id;
    @Getter @Setter private Usuario autor;
    @Getter @Setter private String titulo;
    @Getter @Setter private String resumen;
    @Getter @Setter private String contenido;
    @Getter @Setter private List<Comment> comentarios;

    public Publicacion(int id, String titulo, String resumen, String contenido, Usuario autor) {
        this.id = id;
        this.titulo = titulo;
        this.resumen = resumen;
        this.contenido = contenido;
        this.autor = autor;
        this.comentarios = new ArrayList<>();
    }

    public float getPuntaje() {
        int totalComments = comentarios.size();
        if (totalComments == 0)
            return 0;

        int totalScore = 0;
        for (Comment comment : comentarios) {
            totalScore += comment.getPuntaje();
        }
        
        return totalScore / totalComments;
    }

    public String getnombreAutor(){
        return this.autor.getName();
    }

    public int getCantidadComentarios(){
        return this.comentarios.size();
    }

    public void addComment(Comment newComment) {
        comentarios.add(newComment);
    }

}
