package com.example.Usuario.model;

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
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUsuario;

    @Column(name = "Nombre", nullable = false)
    private String nombre;

    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @Column(name = "Contraseña", nullable = false)
    private String contraseña;

    @Column(name = "Direccion", nullable = true)
    private String direccion;

    @Column(name = "Telefono", nullable = true, unique = true)
    private int telefono;

}
