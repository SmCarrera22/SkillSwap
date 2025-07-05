package com.example.Usuario.assemblers;


import com.example.Usuario.model.Usuario;
import com.example.Usuario.controller.UsuarioControllerV2;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UsuarioModelAssembler implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

   @Override
   public EntityModel<Usuario> toModel(Usuario usuario) {
       return EntityModel.of(usuario,
           linkTo(methodOn(UsuarioControllerV2.class).getUsuario(usuario.getId())).withSelfRel(),
           linkTo(methodOn(UsuarioControllerV2.class).getUsuarios()).withRel("usuarios")
       );
   }
}
