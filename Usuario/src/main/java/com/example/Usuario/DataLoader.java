package com.example.Usuario;

import com.example.Usuario.model.Usuario;
import com.example.Usuario.repository.UsuarioRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        for (int i = 0; i < 5; i++) {
            Usuario usuario = new Usuario();
            usuario.setNombre(faker.name().fullName());
            usuario.setEmail(faker.internet().emailAddress());
            usuario.setContraseña(faker.internet().password());
            usuario.setFechaRegistro(LocalDate.now());
            usuario.setDireccion(faker.address().fullAddress());
            usuario.setTelefono(faker.phoneNumber().phoneNumber());
            usuarioRepository.save(usuario);
        }
    }

}
