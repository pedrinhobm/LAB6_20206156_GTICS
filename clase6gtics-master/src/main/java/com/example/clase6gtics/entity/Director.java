package com.example.clase6gtics.entity;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "directores")
@Getter
@Setter
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "directorId")
    private int directorId;

    @Column(nullable = false)
    @Size(min= 2 , max = 100,message = "Solo se soportan entre 2 a 100 caractéres")
    @NotBlank
    private String nombre;

    @Column(nullable = false)
    @Size(max=9, message = "Solo se soportan 9 dígitos")
    private String telefono;

    @Column(nullable = false)
    @Size(max=50, message = "Solo debe seleccionarse de una lista desplegable")
    private String nacionalidad;

    @ManyToMany(mappedBy = "directores")
    private List<Pelicula> pelicula;


}
