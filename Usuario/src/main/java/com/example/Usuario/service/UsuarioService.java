package com.example.Usuario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Usuario.model.Usuario;
import com.example.Usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario addUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
        return usuario;
    }

    public Usuario getUsuarioById(int id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario updateUsuario(Usuario updatedUsuario) {
        return usuarioRepository.save(updatedUsuario);
    }

    public void deleteUsuario(int id) {
            usuarioRepository.deleteById(id);
    }

    public Usuario getUsuarioByNombre(String nombre) {
        for (Usuario us : usuarioRepository.findAll()) {
            System.out.println("usuarioRepository nombre: " + nombre);
            if (us.getNombre().equals(nombre)) {
                return us;
            }
        }
        return null;
    }
    
    public List<Usuario> buscarUsuariosPorNombre(String nombre) {

    return usuarioRepository.buscarPorNombre(nombre);

  }
}