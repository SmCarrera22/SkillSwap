package com.example.Evento.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Evento")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_evento;

    @Column(name = "nombre_evento", nullable = false, length = 100, unique = true)
    private String nombreEvento;

    @Column(name = "descripcion_evento", nullable = false, length = 255)
    private String descripcionEvento;

    @Column(name = "fecha_inicio_evento", nullable = false, length = 100)
    private LocalDate fechaInicioEvento;
    
    @Column(name = "fecha_fin_evento", nullable = false, length = 100)
    private LocalDate fechaFinEvento;

    @Column(name = "capacidad_maxima_evento", nullable = false, length = 100)
    private int capacidadMaximaEvento;

    @Column(name = "costo_entrada_evento", nullable = false, length = 100)
    private int costoEntradaEvento;

    @Column(name = "fecha_creacion_evento", nullable = false, length = 100)
    private LocalDate fechaCreacionEvento;

}
