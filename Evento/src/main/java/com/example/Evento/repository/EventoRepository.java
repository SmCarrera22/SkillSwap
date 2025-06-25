package com.example.Evento.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Evento.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Integer> {

    Evento findByNombreEvento(String nombre_evento);

    @Query("SELECT e FROM Evento e WHERE e.fechaInicioEvento >= :fechaInicio AND e.fechaFinEvento <= :fechaFin")
    List<Evento> findEventosByFecha(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);


    @Query("SELECT e FROM Evento e WHERE e.costoEntradaEvento <= :costoMaximo")
    List<Evento> findEventosByCosto(
        @Param("costoMaximo") int costoMaximo
    );

    @Query("SELECT e FROM Evento e WHERE CAST(e.capacidadMaximaEvento AS int) >= :capacidadMinima")
    List<Evento> findEventosByCapacidad(
        @Param("capacidadMinima") int capacidadMinima
    );
}
