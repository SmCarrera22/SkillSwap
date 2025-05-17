package com.example.Usuario.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Usuario.Services.UsuarioService;
import com.example.Usuario.Model.Usuario;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("")
    public List<Usuario> getUsuarios()
    {
        return usuarioService.getUsuarios();
    }

    @PostMapping("")
    public Usuario addUsuario(@RequestBody Usuario usuario)
    {
        return usuarioService.addUsuario(usuario);
    }

    @GetMapping("{id}")
    public Usuario getUsuario(@PathVariable int id)
    {
        return usuarioService.getUsuarioById(id);
    }

    @PutMapping("{id}")
    public Usuario putMethodName(@PathVariable int id, @RequestBody Usuario usuario)
    {
        return usuarioService.updateUsuario(usuario);
    }

    @DeleteMapping("{id}")
    public void deleteUsuario(@PathVariable int id)
    {
        usuarioService.deleteUsuario(id);
    }

    @GetMapping("/Nombre/{Nombre}")
    public Usuario getByNombre(@PathVariable String Nombre)
    {
        System.out.println("Nombre: " + Nombre);
        return usuarioService.getUsuarioByNombre(Nombre);
    }
    
}
