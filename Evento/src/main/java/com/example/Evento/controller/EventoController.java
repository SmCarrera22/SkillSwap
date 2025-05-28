package com.example.Evento.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.example.Evento.model.Evento;
import com.example.Evento.service.EventoService;

@RestController
@RequestMapping("/api/v1/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping("")
    public List<Evento> getEventos() {
        return eventoService.getEventos();
    }

    @PostMapping("")
    public Evento addEvento(@RequestBody Evento evento) {
        return eventoService.addEvento(evento);
    }

    @GetMapping("/{id}")
    public Evento getEvento(@PathVariable("id") int id) {
        return eventoService.getEventoById(id);
    }

    @PutMapping("/{id}")
    public Evento updateEvento(@PathVariable int id, @RequestBody Evento evento) {
        evento.setId_evento(id);
        return eventoService.updateEvento(evento);
    }

    @DeleteMapping("/{id}")
    public void deleteEvento(@PathVariable int id) {
        eventoService.deleteEvento(id);
    }

    @GetMapping("/nombre/{nombre}")
    public Evento getByNombre(@PathVariable String nombre) {
        return eventoService.getEventoByNombre(nombre);
    }

    @GetMapping("/fecha")
    public List<Evento> getByFechas(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date inicio,
            @RequestParam("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fin) {
        return eventoService.getEventosPorRangoDeFechas(inicio, fin);
    }

    @GetMapping("/costo")
    public List<Evento> getByCosto(@RequestParam("max") int costoMaximo) {
        return eventoService.getEventosPorCostoMaximo(costoMaximo);
    }

    @GetMapping("/capacidad")
    public List<Evento> getByCapacidad(@RequestParam("min") int capacidadMinima) {
        return eventoService.getEventosPorCapacidadMinima(capacidadMinima);
    }

}
