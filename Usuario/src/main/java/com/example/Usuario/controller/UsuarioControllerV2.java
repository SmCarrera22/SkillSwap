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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "API para gestionar usuarios")
public class UsuarioControllerV2 {

@Autowired
private UsuarioService usuarioService;

@Autowired
    private UsuarioModelAssembler assembler;


// Índice de Endpoints
@Operation(
    summary = "Índice de Endpoints",
    description = "Muestra todos los endpoints disponibles en la API de Usuarios"
)

@ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Directorio de endpoints",
        content = @Content(
            mediaType = MediaTypes.HAL_JSON_VALUE,
            examples = @ExampleObject(
                value = """
                {
                    "description": "API de Gestión de Usuarios v1.0",
                    "_links": {
                        "self": { "href": "http://localhost:8081/api/v1/usuarios/index" },
                        "obtener-usuarios": { "href": "http://localhost:8081/api/v1/usuarios" },
                        "lista-vacia": { "href": "http://localhost:8081/api/v1/usuarios/vaciar" }
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
        linkTo(methodOn(UsuarioControllerV2.class).getListaVacia()).withRel("lista-vacia")
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
