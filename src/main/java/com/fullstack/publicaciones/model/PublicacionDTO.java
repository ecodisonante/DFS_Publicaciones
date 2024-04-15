package com.fullstack.publicaciones.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PublicacionDTO {

    long id;
    String autor;
    String conetnido;
    int cantComentarios;
    double valoracion;

}
