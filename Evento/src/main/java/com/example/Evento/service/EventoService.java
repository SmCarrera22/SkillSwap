package com.example.Evento.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Evento.model.Evento;
import com.example.Evento.repository.EventoRepository;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public List<Evento> getEventos() {
        return eventoRepository.findAll();
    }

    public Evento addEvento(Evento evento) {
        return eventoRepository.save(evento);
    }

    public Evento getEventoById(int id) {
        return eventoRepository.findById(id).orElse(null);
    }

    public Evento updateEvento(Evento evento) {
        return eventoRepository.save(evento);
    }

    public void deleteEvento(int id) {
        eventoRepository.deleteById(id);
    }

    public Evento getEventoByNombre(String nombre) {
        return eventoRepository.findByNombreEvento(nombre);
    }

    public List<Evento> getEventosPorRangoDeFechas(LocalDate inicio, LocalDate fin) {
        return eventoRepository.findEventosByFecha(inicio, fin);
    }

    public List<Evento> getEventosPorCostoMaximo(int costoMaximo) {
        return eventoRepository.findEventosByCosto(costoMaximo);
    }

    public List<Evento> getEventosPorCapacidadMinima(int capacidadMinima) {
        return eventoRepository.findEventosByCapacidad(capacidadMinima);
    }

}
