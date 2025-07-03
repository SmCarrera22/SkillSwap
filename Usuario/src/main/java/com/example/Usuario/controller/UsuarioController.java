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


import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;


//@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "API para gestionar usuarios")
public class UsuarioController {

@Autowired
private UsuarioService usuarioService;

@Operation(
    summary = "Obtener todos los usuarios",
    description = "Devuelve una lista de todos los usuarios registrados"
)
@ApiResponses(value = {
    @ApiResponse(
        responseCode = "200",
        description = "Lista de usuarios obtenida correctamente",
        content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = Usuario.class))
        )
    ),
    @ApiResponse(responseCode = "400", description = "Error interno del servidor")
})

@GetMapping("")
public ResponseEntity<List<Usuario>> getUsuarios() {
    List<Usuario> usuarios = usuarioService.getUsuarios();
    return ResponseEntity.ok(usuarios);
}


@Operation(
    summary = "Agregar un nuevo usuario",
    description = "Crea un nuevo usuario en el sistema",
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Datos del usuario a registrar",
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Usuario.class),
            examples = @ExampleObject(
                name = "EjemploUsuario",
                value = "{ \"nombre\": \"Pedro\", \"correo\": \"pedro@mail.com\", \"contrasena\": \"12345\" }"
            )
        )
    )
)
@ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "Usuario creado exitosamente",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Usuario.class)
        )
    ),
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
})
@PostMapping("")
public ResponseEntity<Usuario> addUsuario(@RequestBody Usuario usuario) {
    Usuario nuevoUsuario = usuarioService.addUsuario(usuario);
    return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
}

        
    



@GetMapping("/{id}")
public ResponseEntity<Usuario> getUsuario(@PathVariable("id") int id) {
    Usuario usuario = usuarioService.getUsuarioById(id);
    if (usuario != null) {
        return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable("id") int id, @RequestBody Usuario usuario) {
        Usuario actualizado = usuarioService.updateUsuario(usuario);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
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
