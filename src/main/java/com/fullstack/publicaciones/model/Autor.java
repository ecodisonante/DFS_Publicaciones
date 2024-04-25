package com.fullstack.publicaciones.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "autor")
@NoArgsConstructor
@AllArgsConstructor
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "correo", length = 50)
    private String correo;

    @Column(name = "nombre", length = 150)
    private String nombre;

    @Column(name = "publicaciones")
    private int publicaciones;
    
    @Column(name = "evaluaciones")
    private int evaluaciones;
    
}
