package com.example.Evento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.Evento.model.Evento;
import com.example.Evento.repository.EventoRepository;

public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public List<Evento> getLibros() {
        // return libroRepository.getLibros();
        return eventoRepository.findAll();
    }

}
