package com.example.Pedido.repository;

import com.example.Pedido.model.Pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import org.springframework.data.repository.query.Param;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Query("SELECT p FROM pedidoModel p WHERE p.fechaPedido BETWEEN :startDate AND :endDate")
    List<Pedido> findPedidosByDateRange(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query("SELECT p FROM pedidoModel p WHERE p.metodoPagoPedido = :metodoPago")
    List<Pedido> findPedidosByMetodoPago(@Param("metodoPago") String metodoPago);

    @Query("SELECT p FROM pedidoModel p WHERE p.direccionFacturacionPedido = :direccion")
    List<Pedido> findPedidosByDireccion(@Param("direccion") String direccion);

}
