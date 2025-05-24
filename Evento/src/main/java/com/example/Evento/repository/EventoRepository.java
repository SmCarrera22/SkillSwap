package com.example.Evento.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Evento.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Integer> {

    Evento findByNombreEvento(String nombre_evento);

    @Query("SELECT e FROM Evento e WHERE e.fecha_inicio_evento >= :fechaInicio AND e.fecha_fin_evento <= :fechaFin")
    List<Evento> findEventosByFecha(
        @Param("fechaInicio") Date fechaInicio, 
        @Param("fechaFin") Date fechaFin
    );

    @Query("SELECT e FROM Evento e WHERE e.costo_entrada_evento <= :costoMaximo")
    List<Evento> findEventosByCosto(
        @Param("costoMaximo") int costoMaximo
    );

    @Query("SELECT e FROM Evento e WHERE CAST(e.capacidad_maxima_evento AS int) >= :capacidadMinima")
    List<Evento> findEventosByCapacidad(
        @Param("capacidadMinima") int capacidadMinima
    );
}
