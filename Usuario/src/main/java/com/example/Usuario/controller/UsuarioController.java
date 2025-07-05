package com.example.Usuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Usuario.service.UsuarioService;
import com.example.Usuario.model.Usuario;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Usuario.model.Usuario;
import com.example.Usuario.service.UsuarioService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


//@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "API para gestionar usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("")
    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve una lista de todos los usuarios registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente"),
        @ApiResponse(responseCode = "400", description = "Error interno del servidor")
    })
    public List<Usuario> getUsuarios() {
        return usuarioService.getUsuarios();
    }

    @PostMapping("")
    public Usuario addUsuario(@RequestBody Usuario usuario) {
        return usuarioService.addUsuario(usuario);
    }

    @GetMapping("{id}")
    public Usuario getUsuario(@PathVariable("id") int id) {
        return usuarioService.getUsuarioById(id);
    }

    @PutMapping("{id}")
    public Usuario putMethodName(@PathVariable("id") int id, @RequestBody Usuario usuario) {
        return usuarioService.updateUsuario(usuario);
    }

    @DeleteMapping("{id}")
    public void deleteUsuario(@PathVariable("id") int id) {
        usuarioService.deleteUsuario(id);
    }

    @GetMapping("/Nombre/{nombre}")
    public Usuario getByNombre(@PathVariable("nombre") String nombre) {
        System.out.println("Nombre: " + nombre);
        return usuarioService.getUsuarioByNombre(nombre);
    }

    @GetMapping("/buscar")
    public List<Usuario> buscarUsuarios(@RequestParam("nombre") String nombre) {
        // Llamamos al servicio para buscar los usuarios con nombre similar
        return usuarioService.buscarUsuariosPorNombre(nombre);
    }
}
