package com.fullstack.publicaciones.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "publicacion")
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Lob
    @Column(name = "contenido")
    private String contenido;

    @Column(name = "valoracion")
    private double valoracion;

    @Column(name = "nivel")
    private int nivel;

    @Column(name = "referencia", nullable = true)
    private long referencia;

    @Transient
    private List<Publicacion> refList;

    public PublicacionDTO toDto() {
        return new PublicacionDTO(
                this.id,
                this.autor.getNombre(),
                this.contenido,
                refList.size(),
                Math.round(refList.stream().mapToDouble(Publicacion::getValoracion).average().getAsDouble() * 10) / 10);
    }
}
