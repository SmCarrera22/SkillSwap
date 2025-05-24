package com.example.Evento.model;

import java.util.Date;

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
    private String nombre_evento;

    @Column(name = "descripcion_evento", nullable = false, length = 255)
    private String descripcion_evento;

    @Column(name = "fecha_inicio_evento", nullable = false, length = 100)
    private Date fecha_inicio_evento;
    
    @Column(name = "fecha_fin_evento", nullable = false, length = 100)
    private Date fecha_fin_evento;

    @Column(name = "capacidad_maxima_evento", nullable = false, length = 100)
    private int capacidad_maxima_evento;

    @Column(name = "costo_entrada_evento", nullable = false, length = 100)
    private int costo_entrada_evento;

    @Column(name = "fecha_creacion_evento", nullable = false, length = 100)
    private Date fecha_creacion_evento;

}
