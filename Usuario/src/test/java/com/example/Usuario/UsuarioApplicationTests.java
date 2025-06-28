package com.example.Usuario;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import com.example.Usuario.controller.UsuarioController;
import com.example.Usuario.model.Usuario;

import net.datafaker.Faker;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class UsuarioApplicationTests {
  @Autowired
  private UsuarioController usuarioController; // tu controlador real
  @LocalServerPort
  private int port;
  @Autowired
  private TestRestTemplate restTemplate;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @BeforeAll
  public void setupDatabase(){
    // Limpiar la tabla de usuarios antes de las pruebas
    Faker faker = new Faker();
    jdbcTemplate.execute("DROP TABLE IF EXISTS usuario");
    jdbcTemplate.execute("""
        Create table if not exists usuario (
            id SERIAL PRIMARY KEY,
            nombre VARCHAR(255) NOT NULL,
            email VARCHAR(255) NOT NULL UNIQUE,
            contraseña VARCHAR(255) NOT NULL,
            fecha_registro DATE NOT NULL,
            direccion VARCHAR(255),
            telefono VARCHAR(20)
            );
        """);
    jdbcTemplate.update("""
        INSERT INTO usuario (nombre, email, contraseña, fecha_registro, direccion, telefono)
        VALUES (?, ?, ?, ?, ?, ?)
        """,
        faker.name().fullName(),
        faker.internet().emailAddress(),
        faker.internet().password(),
        LocalDate.now(),
        faker.address().fullAddress(),
        faker.phoneNumber().phoneNumber()
    );
  }

  // Verifica que el contexto de Spring arranca correctamente
  @Test
  @Order(1)
  void contextLoads() {
     System.out.println("Server running on port: " + port);
  }
  // Verifica que el controlador se carga correctamente
  @Test
  @Order(2)
  void contextLoads2() throws Exception {
    System.out.println("Testing the context loading. and the controller...");
    assertThat(usuarioController).isNotNull();
  }
  
  // Verifica que el endpoint /api/v1/usuarios devuelve una lista de usuarios
  @Test
  @Order(3)
  void getUsuariosReturnsList() {
    String response = this.restTemplate.getForObject(
      "http://localhost:" + port + "/api/v1/usuarios",
      String.class
    );
    assertThat(response).contains("["); // Esperamos un JSON tipo lista :contentReference[oaicite:1]{index=1}
  }

  // Verifica que el endpoint /api/v1/usuarios/1 devuelve un usuario
  @Test
  @Order(4)
  void getUsuarioByIdReturnsUsuario() {
    String response = this.restTemplate.getForObject(
      "http://localhost:" + port + "/api/v1/usuarios/1",
      String.class
    );
    assertThat(response).contains("id"); // Esperamos un JSON con el campo id
  }

  // verifica que algún resultado contiene esta busqueda parcial
  @Test
  @Order(5)
  void buscarUsuariosPorNombreParcial() {
    String nombreBusqueda = "ja"; // búsqueda parcial
    String response = this.restTemplate.getForObject(
        "http://localhost:" + port + "/api/v1/usuarios/buscar?nombre=" + nombreBusqueda,
        String.class
    );
    assertThat(response).contains("ja"); 
}

  // Verifica que el endpoint /api/v1/usuarios/Nombre/{nombre} devuelve un usuario por nombre exacto
  @Test
  @Order(6)
  void getUsuarioByNombreExactoReturnsUsuario() {
    Faker faker = new Faker();

    // Generar un nombre con faker para no tener problemas de duplicidad
    String nombre = faker.name().firstName(); // Ej: "Carlos"

    Usuario usuario = new Usuario();
    usuario.setNombre(nombre);
    usuario.setEmail(faker.internet().emailAddress());
    usuario.setContraseña(faker.internet().password());
    usuario.setFechaRegistro(LocalDate.now());
    usuario.setDireccion(faker.address().fullAddress());
    usuario.setTelefono(faker.phoneNumber().phoneNumber());

    this.restTemplate.postForObject(
        "http://localhost:" + port + "/api/v1/usuarios",
        usuario,
        Usuario.class
    );

    // Llamada directa sin codificación
    String response = this.restTemplate.getForObject(
        "http://localhost:" + port + "/api/v1/usuarios/Nombre/" + nombre,
        String.class
    );

    // Verificar que el nombre está presente
    assertThat(response).contains("\"nombre\":\"" + nombre + "\"");
}




  // Verifica que el endpoint /api/v1/usuarios permite crear un nuevo usuario
  @Test
  @Order(7) 
  void postUsuarioReturns() throws Exception {
       Usuario usuario = new Usuario();
       Faker faker = new Faker();
        usuario.setNombre(faker.name().fullName());
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setContraseña(faker.internet().password());
        usuario.setFechaRegistro(LocalDate.now());
        usuario.setDireccion(faker.address().fullAddress());
        usuario.setTelefono(faker.phoneNumber().phoneNumber());
        assertThat(this.restTemplate.postForObject(
            "http://localhost:" + port + "/api/v1/usuarios",
            usuario, Usuario.class )).isNotNull();
        
    
  }
  // Verifica que el endpoint /api/v1/usuarios/{id} permite actualizar un usuario existente
  @Test
  @Order(8)
  void putUsuarioUpdatesUsuario() {
    Faker faker = new Faker();

    // Obtener usuario existente (ID = 1)
    Usuario usuario = this.restTemplate.getForObject(
        "http://localhost:" + port + "/api/v1/usuarios/1",
        Usuario.class
    );

    // Generar nuevo nombre aleatorio con Faker
    String nuevoNombre; 
      do{
      nuevoNombre = faker.name().firstName();
    } while(!nuevoNombre.contains("ja"));
    usuario.setNombre(nuevoNombre);

    // Enviar PUT (no devuelve respuesta)
    this.restTemplate.put(
        "http://localhost:" + port + "/api/v1/usuarios/1",
        usuario
    );

    // Obtener nuevamente para verificar cambios
    Usuario usuarioActualizado = this.restTemplate.getForObject(
        "http://localhost:" + port + "/api/v1/usuarios/1",
        Usuario.class
    );

    // Verificar que el nombre se actualizó correctamente
    assertThat(usuarioActualizado).isNotNull();
    assertThat(usuarioActualizado.getNombre()).isEqualTo(nuevoNombre);
}





  @Test
  @Order(9)
  void deleteUsuarioDeletesUsuario() {
    // Primero creamos un usuario temporal
    Usuario usuario = new Usuario();
    Faker faker = new Faker();
    usuario.setNombre(faker.name().fullName());
    usuario.setEmail(faker.internet().emailAddress());
    usuario.setContraseña(faker.internet().password());
    usuario.setFechaRegistro(LocalDate.now());
    usuario.setDireccion(faker.address().fullAddress());
    usuario.setTelefono(faker.phoneNumber().phoneNumber());

    Usuario creado = this.restTemplate.postForObject(
        "http://localhost:" + port + "/api/v1/usuarios",
        usuario, Usuario.class);

    // Luego lo eliminamos
    this.restTemplate.delete(
        "http://localhost:" + port + "/api/v1/usuarios/" + creado.getId()
    );

    // Y verificamos que ya no exista
    String response = this.restTemplate.getForObject(
        "http://localhost:" + port + "/api/v1/usuarios",
        String.class
    );

    assertThat(response).doesNotContain("\"id\":" + creado.getId());
}
}