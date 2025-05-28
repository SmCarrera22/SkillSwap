package com.example.Usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Usuario.model.Usuario;

@Repository 
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    
}
