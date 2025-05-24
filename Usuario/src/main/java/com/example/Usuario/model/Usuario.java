package com.example.Usuario.model;

import java.time.LocalDate;

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
@Table(name = "Usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Nombre", length = 100, nullable = false)
    private String Nombre;

    @Column(name = "Email", unique = true, length = 100, nullable = false)
    private String Email;

    @Column(name = "Contraseña", unique = true, length = 100, nullable = false)
    private String Contraseña;

    @Column(name = "Fecha_Registro", nullable = false)
    private LocalDate Fecha_Registro;

    @Column(name = "Direccion", unique = true, length = 200, nullable = false)
    private String Direccion;

    @Column(name = "Telefono", unique = true, length = 20, nullable = false)
    private String Telefono;

}
