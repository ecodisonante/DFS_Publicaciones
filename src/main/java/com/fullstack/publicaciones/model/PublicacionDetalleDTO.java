package com.fullstack.publicaciones.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class PublicacionDetalleDTO {

    @Getter @Setter int nr;
    @Getter @Setter String autor;
    @Getter @Setter String titulo;
    @Getter @Setter String resumen;
    @Getter @Setter int comentarios;
    @Getter @Setter float valoracion;

}
