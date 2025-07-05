package com.example.Usuario.controller;

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
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;

import java.util.Map;
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
                        "buscar-usuarios": { 
                            "href": "/api/v1/usuarios/buscar?nombre={nombre}",
                            "templated": true
                        },
                        "buscar-por-nombre-exacto": { 
                            "href": "/api/v1/usuarios/Nombre/{nombre}",
                            "templated": true
                        },
                        "eliminar-usuario": { 
                            "href": "/api/v1/usuarios/{id}",
                            "templated": true,
                            "type": "DELETE"
                        },
                        "crear-usuario": {
                            "href": "/api/v1/usuarios",
                            "type": "POST"
                        },
                        "lista-vacia": { "href": "/api/v1/usuarios/lista-vacia" }
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
    
    return EntityModel.of(
        content,
        linkTo(methodOn(UsuarioControllerV2.class).getBasicApiIndex()).withSelfRel(),
        linkTo(methodOn(UsuarioControllerV2.class).getUsuarios()).withRel("obtener-usuarios"),
        linkTo(methodOn(UsuarioControllerV2.class).buscarUsuarios("")).withRel("buscar-usuarios")
            .withName("nombre")
            .withTitle("Buscar usuarios por nombre parcial"),
        linkTo(methodOn(UsuarioControllerV2.class).getByNombre("{nombre}")).withRel("buscar-por-nombre-exacto")
            .withName("nombre")
            .withTitle("Buscar usuario por nombre exacto"),
        linkTo(methodOn(UsuarioControllerV2.class).deleteUsuario(0)).withRel("eliminar-usuario")
            .withType("DELETE")
            .withTitle("Eliminar usuario por ID"),
        linkTo(methodOn(UsuarioControllerV2.class).addUsuario(null)).withRel("crear-usuario")
            .withType("POST")
            .withTitle("Crear nuevo usuario"),
        linkTo(methodOn(UsuarioControllerV2.class).getListaVacia()).withRel("lista-vacia")
            .withTitle("Lista vacía de usuarios")
    );
}


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

        
    



@Operation(
    summary = "Obtener usuario por ID",
    description = "Busca y devuelve un usuario según su identificador único"
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

@GetMapping("/{id}")
public ResponseEntity<Usuario> getUsuario(
    @Parameter(description = "ID del usuario a buscar", required = true)
    @PathVariable("id") int id
) {
    Usuario usuario = usuarioService.getUsuarioById(id);
    if (usuario != null) {
        return ResponseEntity.ok(usuario);
    } else {
        return ResponseEntity.notFound().build();
    }
}

    
    
  @Operation(
    summary = "Actualizar un usuario existente",
    description = "Actualiza los datos de un usuario según su ID",
    parameters = {
        @Parameter(
            name = "id",
            description = "ID del usuario a actualizar",
            required = true,
            example = "1"
        )
    },
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Datos actualizados del usuario",
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Usuario.class),
            examples = @ExampleObject(
                name = "EjemploUsuarioActualizado",
                value = "{ \"nombre\": \"Pedro Actualizado\", \"correo\": \"pedro.actualizado@mail.com\", \"contrasena\": \"nueva123\" }"
            )
        )
    )
)
@ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Usuario actualizado correctamente",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Usuario.class)
        )
    ),
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
})
@PutMapping("/{id}")
public ResponseEntity<Usuario> updateUsuario(@PathVariable("id") int id, @RequestBody Usuario usuario) {
    Usuario actualizado = usuarioService.updateUsuario(usuario);
    if (actualizado != null) {
        return ResponseEntity.ok(actualizado);
    } else {
        return ResponseEntity.notFound().build();
    }
}

// Eliminar un usuario por ID
@Operation(
    summary = "Eliminar un usuario",
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
    
    Usuario usuario = usuarioService.getUsuarioByNombre(nombre);
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
}

// Obtener lista vacía de usuarios
@Operation(
    summary = "Obtener lista vacía de usuarios",
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
        )
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
