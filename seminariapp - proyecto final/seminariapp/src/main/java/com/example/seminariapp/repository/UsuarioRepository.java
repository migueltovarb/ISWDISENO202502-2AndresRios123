package com.example.seminariapp.repository;

import com.example.seminariapp.model.Usuario;
import com.example.seminariapp.model.enums.TipoRol;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByRol(TipoRol rol);
}
