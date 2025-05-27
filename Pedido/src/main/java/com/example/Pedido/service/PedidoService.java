package com.example.Pedido.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Pedido.model.Pedido;
import com.example.Pedido.repository.PedidoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

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

    public Pedido crearPedidoConUsuarioYEvento(int usuarioId, int eventoId) {
    Pedido pedido = new Pedido();
    pedido.setUsuarioId(usuarioId);
    pedido.setEventoId(eventoId);
    return pedidoRepository.save(pedido);
    }

    public List<Pedido> getPedidosByUsuarioId(int usuarioId) {
        return pedidoRepository.findByUsuarioId(usuarioId);
    }

    public List<Pedido> getPedidosByEventoId(int eventoId) {
        return pedidoRepository.findByEventoId(eventoId);
    }



}
