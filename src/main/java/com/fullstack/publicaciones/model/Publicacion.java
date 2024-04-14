package com.fullstack.publicaciones.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    @ManyToOne
    @JoinColumn(name = "referencia_id", nullable = true)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Publicacion referencia;

    @Transient
    private List<Publicacion> comentarios;

    @Transient
    private List<Evaluacion> evaluaciones;

    public PublicacionDTO toDto() {
        double prom = evaluaciones == null || evaluaciones.isEmpty() ? 0
                : (evaluaciones.stream().mapToDouble(Evaluacion::getPuntaje)
                        .average().getAsDouble() * 10) / 10;

        return new PublicacionDTO(
                this.id,
                this.autor.getNombre(),
                this.contenido,
                comentarios.size(),
                prom);
    }
}
