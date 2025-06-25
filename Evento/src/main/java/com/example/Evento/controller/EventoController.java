package com.example.Evento.controller;

import java.time.LocalDate;
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
        System.out.println("Evento creado con éxito: " + evento);
        return eventoService.addEvento(evento);
    }

    @GetMapping("/{id}")
    public Evento getEvento(@PathVariable("id") int id) {
        return eventoService.getEventoById(id);
    }

    @PutMapping("/{id}")
    public Evento updateEvento(@PathVariable("id") int id, @RequestBody Evento evento) {
        evento.setId_evento(id);
        return eventoService.updateEvento(evento);
    }

    @DeleteMapping("/{id}")
    public void deleteEvento(@PathVariable("id") int id) {
        eventoService.deleteEvento(id);
    }

    @GetMapping("/nombre/{nombre}")
    public Evento getByNombre(@PathVariable("nombre") String nombre) {
        return eventoService.getEventoByNombre(nombre);
    }

    @GetMapping("/fecha")
    public List<Evento> getByFechas(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam("fin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return eventoService.getEventosPorRangoDeFechas(inicio, fin);
    }

    @GetMapping("/costo")
    public List<Evento> getByCosto(@RequestParam("costoMaximo") int costoMaximo) {
        return eventoService.getEventosPorCostoMaximo(costoMaximo);
    }

    @GetMapping("/capacidad")
    public List<Evento> getByCapacidad(@RequestParam("capacidadMinima") int capacidadMinima) {
        return eventoService.getEventosPorCapacidadMinima(capacidadMinima);
    }

}
