package com.example.seminariapp.service;

import com.example.seminariapp.model.Usuario;
import com.example.seminariapp.model.enums.TipoRol;
import com.example.seminariapp.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario crearUsuario(Usuario usuario) {
        if (usuario.getRol() == TipoRol.ADMIN || usuario.getRol() == TipoRol.COORDINADOR) {
            throw new IllegalArgumentException("No se pueden registrar usuarios con rol ADMIN o COORDINADOR desde el registro p\u00fablico");
        }
        if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            throw new IllegalArgumentException("La contrase\u00f1a es obligatoria");
        }
        // Rol por defecto estudiante si no se envi\u00f3 o si viene vac\u00edo
        if (usuario.getRol() == null) {
            usuario.setRol(TipoRol.ESTUDIANTE);
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public Usuario obtenerPorId(String id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElse(null);
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerPorEmail(String email) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        return usuario.orElse(null);
    }

    public List<Usuario> obtenerPorRol(TipoRol rol) {
        return usuarioRepository.findByRol(rol);
    }

    public Usuario cambiarRol(String id, TipoRol rol) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            return null;
        }
        Usuario usuario = usuarioOpt.get();
        usuario.setRol(rol);
        return usuarioRepository.save(usuario);
    }
}
