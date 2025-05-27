package com.example.Pedido.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.Pedido.model.Pedido;
import com.example.Pedido.repository.PedidoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido getPedidoById(int id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    public Pedido createPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public Pedido updatePedido(int id, Pedido updatedPedido) {
        if (pedidoRepository.existsById(id)) {
            updatedPedido.setIdPedido(id);
            return pedidoRepository.save(updatedPedido);
        }
        return null;
    }

    public void deletePedido(int id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
        }
    }

    public List<Pedido> getPedidosByUsuarioId(int usuarioId) {
        return pedidoRepository.findByUsuarioId(usuarioId);
    }

    public List<Pedido> getPedidosByEventoId(int eventoId) {
        return pedidoRepository.findByEventoId(eventoId);
    }

    public Pedido crearPedidoConUsuarioYEvento(int usuarioId, int eventoId) {
        // Validar existencia del Usuario
        try {
            restTemplate.getForObject("http://localhost:8081/api/v1/usuarios/" + usuarioId, Object.class);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error al consultar Usuario: " + e.getStatusCode());
        }

        // Validar existencia de Evento
        try {
            restTemplate.getForObject("http://localhost:8082/api/v1/eventos/" + eventoId, Object.class);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error al consultar Evento: " + e.getStatusCode());
        }

        Pedido pedido = new Pedido();
        pedido.setUsuarioId(usuarioId);
        pedido.setEventoId(eventoId);
        return pedidoRepository.save(pedido);
    }

}
