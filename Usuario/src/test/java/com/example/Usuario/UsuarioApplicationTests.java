package com.example.Usuario;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import com.example.Usuario.controller.UsuarioController;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class UsuarioApplicationTests {
  @Autowired
  private UsuarioController usuarioController; // tu controlador real
  @LocalServerPort
  private int port;
  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  @Order(1)
  void contextLoads() {
     System.out.println("Server running on port: " + port);
    // Verifica que el contexto de Spring arranca correctamente
  }

  @Test
  @Order(2)
  void contextLoads2() throws Exception {
    System.out.println("Testing the context loading. and the controller...");
    assertThat(usuarioController).isNotNull();
  }

  @Test
  @Order(3)
  void getUsuariosReturnsList() {
    String response = this.restTemplate.getForObject(
      "http://localhost:" + port + "/api/v1/usuarios",
      String.class
    );
    assertThat(response).contains("["); // Esperamos un JSON tipo lista :contentReference[oaicite:1]{index=1}
  }





}
