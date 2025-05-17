package com.example.Usuario.Repository;

import com.example.Usuario.Model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    
}
