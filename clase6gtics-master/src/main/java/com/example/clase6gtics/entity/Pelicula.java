package com.example.clase6gtics.entity;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.util.*;
import jakarta.persistence.*;


@Entity
@Table(name = "peliculas")
@Getter
@Setter
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "peliculaid")
    private int peliculaid;

    @Column(nullable = false)
    @Size(min = 3, max = 100,message = "Solo se soportan entre 3 y 100 caracteres.")
    @NotBlank
    private String titulo;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @Future(message ="Debe ser una fecha pasada o presente")
    private Date fechaEstreno;

    @Column(nullable = false)
    @Positive(message = "Debe ser un número positivo.")
    private int duracionMinutos;


    @ManyToMany
    @JoinTable(
            name = "peliculas_directores",
            joinColumns = @JoinColumn(name = "peliculaId"),
            inverseJoinColumns = @JoinColumn(name = "directorId")
    )
    private List<Director> directores;

}
