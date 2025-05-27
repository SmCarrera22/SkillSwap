package com.example.Pedido.model;

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
@Table(name = "pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPedido;

    @Column(name = "usuario_id")
    private int usuarioId;
    
    @Column(name = "evento_id")
    private int eventoId;

    @Column(name = "fecha_pedido")
    private Date fechaPedido;

    @Column(name = "total_pedido")
    private int totalPedido;

    @Column(name = "metodo_pago_pedido")
    private String metodoPagoPedido;

    @Column(name = "direccion_facturacion_pedido")
    private String direccionFacturacionPedido;

}
