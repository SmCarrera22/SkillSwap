package com.example.Usuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
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
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.Link;




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

        
    



@PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
public ResponseEntity<?> updateUsuario(
    @PathVariable("id") int id,
    @Valid @RequestBody Usuario usuario) {

    try {
        // 1. Validar y actualizar
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        usuario.setId(id);
        Usuario actualizado = usuarioService.updateUsuario(usuario);

        // 2. Construir enlaces HATEOAS
        Link selfLink = linkTo(methodOn(UsuarioControllerV2.class).updateUsuario(id, usuario)).withSelfRel();
        Link getLink = linkTo(methodOn(UsuarioControllerV2.class).getUsuario(id)).withRel("get");
        Link deleteLink = linkTo(methodOn(UsuarioControllerV2.class).deleteUsuario(id)).withRel("delete");
        Link allUsersLink = linkTo(methodOn(UsuarioControllerV2.class).getUsuarios()).withRel("usuarios");

        // 3. Construir modelo de respuesta
        EntityModel<Usuario> model = EntityModel.of(actualizado);
        model.add(selfLink, getLink, deleteLink, allUsersLink);

        // 4. Retornar respuesta
        return ResponseEntity.ok(model);

    } catch (EntityNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, 
            "Usuario no encontrado con ID: " + id,
            linkTo(methodOn(UsuarioControllerV2.class).getUsuarios()).withRel("usuarios"));
    } catch (IllegalArgumentException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, 
            ex.getMessage(),
            linkTo(methodOn(UsuarioControllerV2.class).updateUsuario(id, usuario)).withSelfRel());
    } catch (Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
            "Error al procesar la solicitud: " + ex.getMessage(),
            linkTo(methodOn(UsuarioControllerV2.class).getApiIndex()).withRel("index"));
    }
}

// Método auxiliar para construir respuestas de error
private ResponseEntity<EntityModel<Map<String, Object>>> buildErrorResponse(
    HttpStatus status, String errorMessage, Link... links) {
    
    Map<String, Object> errorBody = new HashMap<>();
    errorBody.put("error", errorMessage);
    errorBody.put("timestamp", Instant.now().toString());
    
    EntityModel<Map<String, Object>> errorModel = EntityModel.of(errorBody, links);
    return ResponseEntity.status(status).body(errorModel);
}

// Eliminar un usuario por ID
@Operation(
    summary = "Eliminar un usuario",
    description = "Elimina un usuario del sistema según su ID",
    parameters = {
        @Parameter(
            name = "id",
            description = "ID del usuario a eliminar",
            required = true,
            example = "1"
        )
    }
)
@ApiResponses({
    @ApiResponse(
        responseCode = "204",
        description = "Usuario eliminado exitosamente"
    ),
    @ApiResponse(
        responseCode = "404",
        description = "Usuario no encontrado"
    )
})
@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteUsuario(@PathVariable("id") int id) {
    usuarioService.deleteUsuario(id);
    return ResponseEntity.noContent().build();
}


   @Operation(
    summary = "Buscar usuario por nombre",
    description = "Obtiene un usuario específico usando su nombre",
    parameters = {
        @Parameter(
            name = "nombre",
            description = "Nombre del usuario a buscar",
            required = true,
            example = "Pedro"
        )
    }
)
@ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Usuario encontrado",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Usuario.class)
        )
    ),
    @ApiResponse(
        responseCode = "404",
        description = "Usuario no encontrado"
    )
})
@GetMapping("/Nombre/{nombre}")
public ResponseEntity<Usuario> getByNombre(@PathVariable("nombre") String nombre) {
    Usuario usuario = usuarioService.getUsuarioByNombre(nombre);
    if (usuario != null) {
        return ResponseEntity.ok(usuario);
    } else {
        return ResponseEntity.notFound().build();
    }
}


    @Operation(
    summary = "Buscar usuarios por nombre parcial",
    description = "Devuelve una lista de usuarios cuyo nombre contenga el valor proporcionado",
    parameters = {
        @Parameter(
            name = "nombre",
            description = "Nombre (o parte del nombre) a buscar",
            required = true,
            example = "na"
        )
    }
)
@ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Lista de usuarios encontrada",
        content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = Usuario.class))
        )
    ),
    @ApiResponse(responseCode = "400", description = "Parámetro inválido")
})
@GetMapping("/buscar")
public ResponseEntity<List<Usuario>> buscarUsuarios(@RequestParam("nombre") String nombre) {
    List<Usuario> usuarios = usuarioService.buscarUsuariosPorNombre(nombre);
    return ResponseEntity.ok(usuarios);
}


    @Operation(
    summary = "Obtener lista vacía de usuarios",
    description = "Devuelve una lista vacía, útil para pruebas o ejemplos"
)
@ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Lista vacía devuelta exitosamente",
        content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = Usuario.class))
        )
    )
})
@GetMapping("/vaciar")
public ResponseEntity<List<Usuario>> getListaVacia() {
    List<Usuario> listaVacia = Collections.emptyList();
    return ResponseEntity.ok(listaVacia);
}
}
