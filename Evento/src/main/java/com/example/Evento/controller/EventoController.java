package com.example.Evento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.Evento.model.Evento;

import jakarta.persistence.EntityManager;

public class EventoController {

    @Autowired
    private EntityManager entityManager;


    //buscar todos los eventos
    public List<Evento> findAllEventos() {
        String query = "SELECT e FROM Evento e";
        return entityManager.createQuery(query, Evento.class).getResultList();
    }

    //buscar evento por id
    public Evento findEventoById(int id) {
        return entityManager.find(Evento.class, id);
    }

    //guardar evento
    public void saveEvento(Evento evento) {
        entityManager.persist(evento);
    }

    //actualizar evento
    public void updateEvento(Evento evento) {
        entityManager.merge(evento);
    }

    //eliminar evento
    public void deleteEvento(int id) {
        Evento evento = findEventoById(id);
        if (evento != null) {
            entityManager.remove(evento);
        }
    }

}
