package com.example.Usuario.controller;


import java.time.Instant;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Usuario.assemblers.UsuarioModelAssembler;
import com.example.Usuario.model.Usuario;
import com.example.Usuario.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;
import org.springframework.hateoas.Link;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpHeaders;



@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "API para gestionar usuarios")
public class UsuarioControllerV2 {

@Autowired
private UsuarioService usuarioService;

@Autowired
    private UsuarioModelAssembler assembler;


// Obtener índice de la API con enlaces HATEOAS
@Operation(
    summary = "Obtener índice de la API",
    description = "Devuelve información básica de la API con enlaces HATEOAS a todos los endpoints principales"
)
@ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Índice de la API obtenido exitosamente",
        content = @Content(
            mediaType = MediaTypes.HAL_JSON_VALUE,
            examples = @ExampleObject(
                value = """
                {
                    "description": "API de Gestión de Usuarios v1.0",
                    "version": "1.0",
                    "_links": {
                        "self": { "href": "/api/v1/usuarios/index" },
                        "obtener-usuarios": { "href": "/api/v1/usuarios" },
                        "obtener-usuario": {
                            "href": "/api/v1/usuarios/{id}",
                            "templated": true,
                            "type": "GET",
                            "title": "Obtener usuario por ID"
                        },
                        "buscar-usuarios": { 
                            "href": "/api/v1/usuarios/buscar?nombre={nombre}",
                            "templated": true,
                            "title": "Buscar usuarios por nombre parcial"
                        },
                        "buscar-por-nombre-exacto": { 
                            "href": "/api/v1/usuarios/Nombre/{nombre}",
                            "templated": true,
                            "title": "Buscar usuario por nombre exacto"
                        },
                        "crear-usuario": {
                            "href": "/api/v1/usuarios",
                            "type": "POST",
                            "title": "Crear nuevo usuario"
                        },
                        "actualizar-usuario": {
                            "href": "/api/v1/usuarios/{id}",
                            "templated": true,
                            "type": "PUT",
                            "title": "Actualizar usuario existente"
                        },
                        "eliminar-usuario": { 
                            "href": "/api/v1/usuarios/{id}",
                            "templated": true,
                            "type": "DELETE",
                            "title": "Eliminar usuario por ID"
                        },
                        "lista-vacia": { 
                            "href": "/api/v1/usuarios/lista-vacia",
                            "title": "Lista vacía de usuarios"
                        }
                    }
                }"""
            )
        )
    )
})
@GetMapping("/index")
public EntityModel<Map<String, String>> getBasicApiIndex() {
    Map<String, String> content = new HashMap<>();
    content.put("description", "API de Gestión de Usuarios v1.0");
    content.put("version", "1.0");
=======

@GetMapping("/index")
public EntityModel<Map<String, String>> getBasicApiIndex() {
    Map<String, String> content = new HashMap<>();
<<<<<<< HEAD
    content.put("description", "API de Usuarios - Endpoints disponibles");
>>>>>>> 7ea354e (Agregar endpoint para obtener índice básico de la API de Usuarios)
=======
    content.put("description", "API de Gestión de Usuarios v1.0");
    content.put("version", "1.0");
>>>>>>> dfbb093 (Actualizar documentación de la API y mejorar el índice de endpoints)
    
    return EntityModel.of(
        content,
        linkTo(methodOn(UsuarioControllerV2.class).getBasicApiIndex()).withSelfRel(),
        linkTo(methodOn(UsuarioControllerV2.class).getUsuarios()).withRel("obtener-usuarios"),
        linkTo(methodOn(UsuarioControllerV2.class).getUsuario(0)).withRel("obtener-usuario")
            .withName("id")
            .withTitle("Obtener usuario por ID")
            .withType("GET"),
        linkTo(methodOn(UsuarioControllerV2.class).buscarUsuarios("")).withRel("buscar-usuarios")
            .withName("nombre")
            .withTitle("Buscar usuarios por nombre parcial"),
        linkTo(methodOn(UsuarioControllerV2.class).getByNombre("{nombre}")).withRel("buscar-por-nombre-exacto")
            .withName("nombre")
            .withTitle("Buscar usuario por nombre exacto"),
        linkTo(methodOn(UsuarioControllerV2.class).addUsuario(null)).withRel("crear-usuario")
            .withType("POST")
            .withTitle("Crear nuevo usuario"),
        linkTo(methodOn(UsuarioControllerV2.class).updateUsuario(0, null)).withRel("actualizar-usuario")
            .withType("PUT")
            .withTitle("Actualizar usuario existente"),
        linkTo(methodOn(UsuarioControllerV2.class).deleteUsuario(0)).withRel("eliminar-usuario")
            .withType("DELETE")
            .withTitle("Eliminar usuario por ID"),
        linkTo(methodOn(UsuarioControllerV2.class).getListaVacia()).withRel("lista-vacia")
            .withTitle("Lista vacía de usuarios")
    );
}

// Obtener todos los usuarios con enlaces HATEOAS
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



    @GetMapping
    public CollectionModel<EntityModel<Usuario>> getUsuarios() {
        List<EntityModel<Usuario>> usuarios = usuarioService.getUsuarios().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

    return CollectionModel.of(
        usuarios,
        linkTo(methodOn(UsuarioControllerV2.class).getUsuarios()).withSelfRel()
    );
}

// Agregar un nuevo usuario con enlaces HATEOAS
// Agregar un nuevo usuario con enlaces HATEOAS
@Operation(
    summary = "Agregar un nuevo usuario",
    description = "Crea un nuevo usuario en el sistema y devuelve el recurso con enlaces HATEOAS",
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Datos del usuario a registrar",
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Usuario.class),
            examples = @ExampleObject(
                name = "EjemploUsuario",
                value = """
                {
                    "nombre": "Pedro",
                    "email": "pedro@mail.com",
                    "contraseña": "12345",
                    "fechaRegistro": "2025-07-05",
                    "direccion": "Calle Falsa 123",
                    "telefono": "123456789"
                }"""
            )
        )
    )
)
@ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "Usuario creado exitosamente",
        content = @Content(
            mediaType = MediaTypes.HAL_JSON_VALUE,
            schema = @Schema(implementation = Usuario.class),
            examples = @ExampleObject(
                value = """
                {
                    "id": 1,
                    "nombre": "Pedro",
                    "email": "pedro@mail.com",
                    "_links": {
                        "self": { 
                            "href": "/api/v1/usuarios/1",
                            "type": "GET"
                        },
                        "todos-usuarios": { 
                            "href": "/api/v1/usuarios",
                            "type": "GET"
                        }
                    }
                }"""
            )
        )
    ),
    @ApiResponse(responseCode = "400", description = "Datos inválidos"),
    @ApiResponse(responseCode = "409", description = "El email ya está registrado")
})
@PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
public ResponseEntity<EntityModel<Usuario>> addUsuario(@Valid @RequestBody Usuario usuario) {
    Usuario nuevoUsuario = usuarioService.addUsuario(usuario);
    
    // Construcción del modelo HATEOAS con el assembler + enlaces adicionales
    EntityModel<Usuario> model = assembler.toModel(nuevoUsuario)
        .add(linkTo(methodOn(UsuarioControllerV2.class).getUsuarios()).withRel("todos-usuarios"));
    
    return ResponseEntity
        .created(linkTo(methodOn(UsuarioControllerV2.class).getUsuario(nuevoUsuario.getId())).toUri())
        .body(model);
}

        
    


// Obtener un usuario por ID con enlaces HATEOAS
// Obtener un usuario por ID con enlaces HATEOAS
@Operation(
    summary = "Obtener usuario por ID",
    description = "Busca y devuelve un usuario según su identificador único. La respuesta incluye enlaces HATEOAS para acciones relacionadas."
)
@ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Usuario encontrado",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Usuario.class),
            examples = @ExampleObject(
                value = "{\"id\": 1, \"nombre\": \"Ejemplo\", \"email\": \"ejemplo@mail.com\", \"_links\": {\"self\": {\"href\": \"/api/usuarios/1\"}, \"todos\": {\"href\": \"/api/usuarios\"}}}"
            )
        ),
        headers = @Header(name = "Link", description = "Enlaces HATEOAS para acciones disponibles", schema = @Schema(type = "string"))
    ),
    @ApiResponse(
        responseCode = "404",
        description = "Usuario no encontrado",
        content = @Content
    )
})

@GetMapping("/{id}")
public ResponseEntity<EntityModel<Usuario>> getUsuario(
    @PathVariable("id") int id
) {
    Usuario usuario = usuarioService.getUsuarioById(id);
    
    if (usuario != null) {
        EntityModel<Usuario> model = EntityModel.of(usuario);
        
        // Enlace self (GET)
        model.add(linkTo(methodOn(UsuarioControllerV2.class)
            .getUsuario(id))
            .withSelfRel()
            .withType("GET"));
        
        // Enlace a la lista de usuarios (usando getUsuarios() que sí existe)
        model.add(linkTo(methodOn(UsuarioControllerV2.class)
            .getUsuarios())
            .withRel("todos-usuarios")
            .withType("GET"));
        
        return ResponseEntity.ok(model);
    }
    return ResponseEntity.notFound().build();
}

// Actualizar un usuario por ID
@Operation(
    summary = "Actualizar usuario",
    description = "Actualiza completamente un usuario existente",
    parameters = {
        @Parameter(
            name = "id",
            description = "ID del usuario a actualizar",
            required = true,
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "integer")
            in = ParameterIn.PATH,
            example = "1",
            schema = @Schema(type = "integer")
        )
    },
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Datos actualizados del usuario",
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Usuario.class),
            examples = {
                @ExampleObject(
                    name = "Actualización básica",
                    value = """
                    {
                        "nombre": "Juan Pérez",
                        "email": "juan.perez@example.com",
                        "contrasena": "nuevaContraseña123"
                    }"""
                )
            }
            examples = {
                @ExampleObject(
                    name = "Actualización básica",
                    value = """
                    {
                        "nombre": "Juan Pérez",
                        "email": "juan.perez@example.com",
                        "contrasena": "nuevaContraseña123"
                    }"""
                )
            }
        )
    )
)
@ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Usuario actualizado exitosamente",
        content = @Content(
            mediaType = "application/hal+json",
            schema = @Schema(implementation = Usuario.class),
            examples = @ExampleObject(
                value = """
                {
                    "id": 1,
                    "nombre": "Juan Pérez",
                    "email": "juan.perez@example.com",
                    "_links": {
                        "self": {
                            "href": "/api/v1/usuarios/1",
                            "type": "PUT"
                        },
                        "get": {
                            "href": "/api/v1/usuarios/1",
                            "type": "GET"
                        },
                        "delete": {
                            "href": "/api/v1/usuarios/1",
                            "type": "DELETE"
                        },
                        "usuarios": {
                            "href": "/api/v1/usuarios",
                            "type": "GET"
                        }
                    }
                }"""
            )
        )
    ),
    @ApiResponse(
        responseCode = "400",
        description = "Datos de entrada inválidos",
        description = "Usuario actualizado exitosamente",
        content = @Content(
            mediaType = "application/hal+json",
            schema = @Schema(implementation = Usuario.class),
            examples = @ExampleObject(
                value = """
                {
                    "id": 1,
                    "nombre": "Juan Pérez",
                    "email": "juan.perez@example.com",
                    "_links": {
                        "self": {
                            "href": "/api/v1/usuarios/1",
                            "type": "PUT"
                        },
                        "get": {
                            "href": "/api/v1/usuarios/1",
                            "type": "GET"
                        },
                        "delete": {
                            "href": "/api/v1/usuarios/1",
                            "type": "DELETE"
                        },
                        "usuarios": {
                            "href": "/api/v1/usuarios",
                            "type": "GET"
                        }
                    }
                }"""
            )
        )
    ),
    @ApiResponse(
        responseCode = "400",
        description = "Datos de entrada inválidos",
        content = @Content(
            mediaType = "application/hal+json",
            examples = @ExampleObject(
                value = """
                {
                    "error": "El email proporcionado no es válido",
                    "timestamp": "2023-08-15T10:30:00Z",
                    "status": 400,
                    "_links": {
                        "self": {
                            "href": "/api/v1/usuarios/1",
                            "type": "PUT"
                        },
                        "documentacion": {
                            "href": "/api/v1/usuarios",
                            "type": "GET"
                        }
                    }
                }"""
            )
            mediaType = "application/hal+json",
            examples = @ExampleObject(
                value = """
                {
                    "error": "El email proporcionado no es válido",
                    "timestamp": "2023-08-15T10:30:00Z",
                    "status": 400,
                    "_links": {
                        "self": {
                            "href": "/api/v1/usuarios/1",
                            "type": "PUT"
                        },
                        "documentacion": {
                            "href": "/api/v1/usuarios",
                            "type": "GET"
                        }
                    }
                }"""
            )
        )
    ),
    @ApiResponse(
        responseCode = "404",
        description = "Usuario no encontrado",
        content = @Content(
            mediaType = "application/hal+json",
            examples = @ExampleObject(
                value = """
                {
                    "error": "Usuario no encontrado con ID: 99",
                    "timestamp": "2023-08-15T10:30:00Z",
                    "status": 404,
                    "_links": {
                        "usuarios": {
                            "href": "/api/v1/usuarios",
                            "type": "GET"
                        },
                        "crear": {
                            "href": "/api/v1/usuarios",
                            "type": "POST"
                        }
                    }
                }"""
            )
        )
    )
    @ApiResponse(
        responseCode = "404",
        description = "Usuario no encontrado",
        content = @Content(
            mediaType = "application/hal+json",
            examples = @ExampleObject(
                value = """
                {
                    "error": "Usuario no encontrado con ID: 99",
                    "timestamp": "2023-08-15T10:30:00Z",
                    "status": 404,
                    "_links": {
                        "usuarios": {
                            "href": "/api/v1/usuarios",
                            "type": "GET"
                        },
                        "crear": {
                            "href": "/api/v1/usuarios",
                            "type": "POST"
                        }
                    }
                }"""
            )
        )
    )
})
@PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
public ResponseEntity<?> updateUsuario(
        @PathVariable("id") int id,
        @Valid @RequestBody Usuario usuario) {

    try {
        // 1. Validación y actualización
        usuario.setId(id);
        Usuario actualizado = usuarioService.updateUsuario(usuario);

        // 2. Construcción de enlaces HATEOAS
        Link selfLink = linkTo(methodOn(UsuarioControllerV2.class)
                .updateUsuario(id, usuario)).withSelfRel()
                .withType("PUT");

        Link getLink = linkTo(methodOn(UsuarioControllerV2.class)
                .getUsuario(id)).withRel("get")
                .withType("GET");

        Link deleteLink = linkTo(methodOn(UsuarioControllerV2.class)
                .deleteUsuario(id)).withRel("delete")
                .withType("DELETE");

        Link allUsersLink = linkTo(methodOn(UsuarioControllerV2.class)
                .getUsuarios()).withRel("usuarios")
                .withType("GET");

        // 3. Construcción del modelo de respuesta
        EntityModel<Usuario> model = EntityModel.of(actualizado);
        model.add(selfLink, getLink, deleteLink, allUsersLink);

        // 4. Retorno de respuesta
        return ResponseEntity.ok(model);

    } catch (EntityNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND,
                "Usuario no encontrado con ID: " + id,
                linkTo(methodOn(UsuarioControllerV2.class).getUsuarios()).withRel("usuarios"),
                linkTo(methodOn(UsuarioControllerV2.class).addUsuario(null)).withRel("crear"));
    } catch (IllegalArgumentException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                linkTo(methodOn(UsuarioControllerV2.class).updateUsuario(id, usuario)).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).getUsuarios()).withRel("documentacion")); // Cambio clave aquí
    } catch (Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "Error interno del servidor",
                linkTo(methodOn(UsuarioControllerV2.class).updateUsuario(id, usuario)).withSelfRel());
    }
}

private ResponseEntity<EntityModel<Map<String, Object>>> buildErrorResponse(
        HttpStatus status, String errorMessage, Link... links) {

    Map<String, Object> errorBody = new HashMap<>();
    errorBody.put("error", errorMessage);
    errorBody.put("timestamp", Instant.now().toString());
    errorBody.put("status", status.value());

    EntityModel<Map<String, Object>> errorModel = EntityModel.of(errorBody, links);
    return ResponseEntity.status(status).body(errorModel);
}




// Eliminar un usuario por ID
@Operation(
    summary = "Eliminar un usuario",
    description = "Elimina un usuario del sistema según su ID y devuelve enlaces HATEOAS relacionados",
    description = "Elimina un usuario del sistema según su ID y devuelve enlaces HATEOAS relacionados",
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
        description = "Usuario eliminado exitosamente",
        content = @Content(
            mediaType = MediaTypes.HAL_JSON_VALUE,
            examples = @ExampleObject(
                value = """
                {
                    "_links": {
                        "all-users": { "href": "/api/v1/usuarios" },
                        "create-user": { 
                            "href": "/api/v1/usuarios",
                            "type": "POST"
                        }
                    }
                }"""
            )
        )
        description = "Usuario eliminado exitosamente",
        content = @Content(
            mediaType = MediaTypes.HAL_JSON_VALUE,
            examples = @ExampleObject(
                value = """
                {
                    "_links": {
                        "all-users": { "href": "/api/v1/usuarios" },
                        "create-user": { 
                            "href": "/api/v1/usuarios",
                            "type": "POST"
                        }
                    }
                }"""
            )
        )
    ),
    @ApiResponse(
        responseCode = "404",
        description = "Usuario no encontrado",
        content = @Content(
            mediaType = MediaTypes.HAL_JSON_VALUE,
            examples = @ExampleObject(
                value = """
                {
                    "_links": {
                        "all-users": { "href": "/api/v1/usuarios" }
                    },
                    "error": "Usuario no encontrado con ID: 999"
                }"""
            )
        )
        description = "Usuario no encontrado",
        content = @Content(
            mediaType = MediaTypes.HAL_JSON_VALUE,
            examples = @ExampleObject(
                value = """
                {
                    "_links": {
                        "all-users": { "href": "/api/v1/usuarios" }
                    },
                    "error": "Usuario no encontrado con ID: 999"
                }"""
            )
        )
    )
})
@DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
public ResponseEntity<?> deleteUsuario(@PathVariable("id") int id) {
    try {
        usuarioService.deleteUsuario(id);
        
        // Crear modelo con enlaces HATEOAS
        CollectionModel<?> model = CollectionModel.empty()
            .add(linkTo(methodOn(UsuarioControllerV2.class).getUsuarios()).withRel("all-users"))
            .add(linkTo(methodOn(UsuarioControllerV2.class).addUsuario(null)).withRel("create-user")
                .withType("POST"));
        
        return ResponseEntity.noContent()
                .header(HttpHeaders.LINK, model.getLinks().toString())
                .build();
                
    } catch (EntityNotFoundException ex) {
        // Crear modelo de error con HATEOAS (CORRECCIÓN AQUÍ)
        EntityModel<String> errorModel = EntityModel.of(
            "Usuario no encontrado con ID: " + id,
            linkTo(methodOn(UsuarioControllerV2.class).getUsuarios()).withRel("all-users") // Se movió withRel()
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorModel);
    }
}

// Buscar usuario por nombre exacto
@Operation(
    summary = "Buscar usuario por nombre exacto",
    description = "Devuelve un usuario específico con enlaces HATEOAS usando su nombre exacto"
@DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
public ResponseEntity<?> deleteUsuario(@PathVariable("id") int id) {
    try {
        usuarioService.deleteUsuario(id);
        
        // Crear modelo con enlaces HATEOAS
        CollectionModel<?> model = CollectionModel.empty()
            .add(linkTo(methodOn(UsuarioControllerV2.class).getUsuarios()).withRel("all-users"))
            .add(linkTo(methodOn(UsuarioControllerV2.class).addUsuario(null)).withRel("create-user")
                .withType("POST"));
        
        return ResponseEntity.noContent()
                .header(HttpHeaders.LINK, model.getLinks().toString())
                .build();
                
    } catch (EntityNotFoundException ex) {
        // Crear modelo de error con HATEOAS (CORRECCIÓN AQUÍ)
        EntityModel<String> errorModel = EntityModel.of(
            "Usuario no encontrado con ID: " + id,
            linkTo(methodOn(UsuarioControllerV2.class).getUsuarios()).withRel("all-users") // Se movió withRel()
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorModel);
    }
}

// Buscar usuario por nombre exacto
@Operation(
    summary = "Buscar usuario por nombre exacto",
    description = "Devuelve un usuario específico con enlaces HATEOAS usando su nombre exacto"
)
@ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Usuario encontrado",
        content = @Content(
            mediaType = MediaTypes.HAL_JSON_VALUE,
            schema = @Schema(implementation = Usuario.class),
            examples = @ExampleObject(
                value = """
                {
                    "id": 1,
                    "nombre": "EzekielMitchell",
                    "email": "ejemplo@mail.com",
                    "_links": {
                        "self": { "href": "/api/v1/usuarios/Nombre/EzekielMitchell" },
                        "usuario": { "href": "/api/v1/usuarios/1" },
                        "todos-usuarios": { "href": "/api/v1/usuarios" }
                    }
                }"""
            )
            mediaType = MediaTypes.HAL_JSON_VALUE,
            schema = @Schema(implementation = Usuario.class),
            examples = @ExampleObject(
                value = """
                {
                    "id": 1,
                    "nombre": "EzekielMitchell",
                    "email": "ejemplo@mail.com",
                    "_links": {
                        "self": { "href": "/api/v1/usuarios/Nombre/EzekielMitchell" },
                        "usuario": { "href": "/api/v1/usuarios/1" },
                        "todos-usuarios": { "href": "/api/v1/usuarios" }
                    }
                }"""
            )
        )
    ),
    @ApiResponse(
        responseCode = "404",
        description = "Usuario no encontrado"
    )
})
@GetMapping(value = "/Nombre/{nombre}", produces = MediaTypes.HAL_JSON_VALUE)
public ResponseEntity<EntityModel<Usuario>> getByNombre(
    @Parameter(description = "Nombre exacto del usuario", required = true, example = "EzekielMitchell")
    @PathVariable("nombre") String nombre) {
    
@GetMapping(value = "/Nombre/{nombre}", produces = MediaTypes.HAL_JSON_VALUE)
public ResponseEntity<EntityModel<Usuario>> getByNombre(
    @Parameter(description = "Nombre exacto del usuario", required = true, example = "EzekielMitchell")
    @PathVariable("nombre") String nombre) {
    
    Usuario usuario = usuarioService.getUsuarioByNombre(nombre);
    if (usuario == null) {
    if (usuario == null) {
        return ResponseEntity.notFound().build();
    }

    EntityModel<Usuario> model = EntityModel.of(usuario,
        linkTo(methodOn(UsuarioControllerV2.class).getByNombre(nombre)).withSelfRel(),
        linkTo(methodOn(UsuarioControllerV2.class).getUsuario(usuario.getId())).withRel("usuario"),
        linkTo(methodOn(UsuarioControllerV2.class).getUsuarios()).withRel("todos-usuarios")
    );

    return ResponseEntity.ok(model);
}


// Buscar usuarios por nombre parcial

    EntityModel<Usuario> model = EntityModel.of(usuario,
        linkTo(methodOn(UsuarioControllerV2.class).getByNombre(nombre)).withSelfRel(),
        linkTo(methodOn(UsuarioControllerV2.class).getUsuario(usuario.getId())).withRel("usuario"),
        linkTo(methodOn(UsuarioControllerV2.class).getUsuarios()).withRel("todos-usuarios")
    );

    return ResponseEntity.ok(model);
}


// Buscar usuarios por nombre parcial
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

@GetMapping(value = "/buscar", produces = MediaTypes.HAL_JSON_VALUE)
public CollectionModel<EntityModel<Usuario>> buscarUsuarios(
    @RequestParam("nombre") String nombre) {
    
    List<EntityModel<Usuario>> usuarios = usuarioService.buscarUsuariosPorNombre(nombre)
        .stream()
        .map(usuario -> EntityModel.of(usuario,
            linkTo(methodOn(UsuarioControllerV2.class).getUsuario(usuario.getId())).withSelfRel()
        ))
        .collect(Collectors.toList());

    return CollectionModel.of(usuarios,
        linkTo(methodOn(UsuarioControllerV2.class).buscarUsuarios(nombre)).withSelfRel()
            .andAffordance(afford(methodOn(UsuarioControllerV2.class).buscarUsuarios(""))), // Para parámetros dinámicos
        linkTo(methodOn(UsuarioControllerV2.class).getUsuarios()).withRel("todos-usuarios")
    );

@GetMapping(value = "/buscar", produces = MediaTypes.HAL_JSON_VALUE)
public CollectionModel<EntityModel<Usuario>> buscarUsuarios(
    @RequestParam("nombre") String nombre) {
    
    List<EntityModel<Usuario>> usuarios = usuarioService.buscarUsuariosPorNombre(nombre)
        .stream()
        .map(usuario -> EntityModel.of(usuario,
            linkTo(methodOn(UsuarioControllerV2.class).getUsuario(usuario.getId())).withSelfRel()
        ))
        .collect(Collectors.toList());

    return CollectionModel.of(usuarios,
        linkTo(methodOn(UsuarioControllerV2.class).buscarUsuarios(nombre)).withSelfRel()
            .andAffordance(afford(methodOn(UsuarioControllerV2.class).buscarUsuarios(""))), // Para parámetros dinámicos
        linkTo(methodOn(UsuarioControllerV2.class).getUsuarios()).withRel("todos-usuarios")
    );
}

// Obtener lista vacía de usuarios
@Operation(
// Obtener lista vacía de usuarios
@Operation(
    summary = "Obtener lista vacía de usuarios",
    description = "Devuelve una lista vacía con enlaces HATEOAS, útil para pruebas o ejemplos",
    tags = {"Usuarios"}
    description = "Devuelve una lista vacía con enlaces HATEOAS, útil para pruebas o ejemplos",
    tags = {"Usuarios"}
)
@ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Lista vacía devuelta exitosamente",
        content = @Content(
            mediaType = MediaTypes.HAL_JSON_VALUE,
            schema = @Schema(implementation = Usuario.class),
            examples = @ExampleObject(
                value = """
                {
                    "_embedded": {
                        "usuarioList": []
                    },
                    "_links": {
                        "self": {
                            "href": "/api/v1/usuarios/vaciar"
                        },
                        "todos-usuarios": {
                            "href": "/api/v1/usuarios"
                        }
                    }
                }"""
            )
            mediaType = MediaTypes.HAL_JSON_VALUE,
            schema = @Schema(implementation = Usuario.class),
            examples = @ExampleObject(
                value = """
                {
                    "_embedded": {
                        "usuarioList": []
                    },
                    "_links": {
                        "self": {
                            "href": "/api/v1/usuarios/vaciar"
                        },
                        "todos-usuarios": {
                            "href": "/api/v1/usuarios"
                        }
                    }
                }"""
            )
        )
    ),
    @ApiResponse(
        responseCode = "404",
        description = "Endpoint no encontrado",
        content = @Content(schema = @Schema(hidden = true))
    ),
    @ApiResponse(
        responseCode = "404",
        description = "Endpoint no encontrado",
        content = @Content(schema = @Schema(hidden = true))
    )
})

@GetMapping(value = "/vaciar", produces = MediaTypes.HAL_JSON_VALUE)
public CollectionModel<EntityModel<Usuario>> getListaVacia() {
    List<EntityModel<Usuario>> listaVacia = Collections.emptyList();
    
    return CollectionModel.of(
        listaVacia,
        linkTo(methodOn(UsuarioControllerV2.class).getListaVacia()).withSelfRel(),
        linkTo(methodOn(UsuarioControllerV2.class).getUsuarios()).withRel("todos-usuarios")
    );
}
}
