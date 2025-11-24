package com.example.seminariapp.config;

import com.example.seminariapp.model.Usuario;
import com.example.seminariapp.model.enums.TipoRol;
import com.example.seminariapp.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminDataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminDataLoader(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        usuarioRepository.findByEmail("admin@local").ifPresentOrElse(
                u -> {},
                () -> {
                    Usuario admin = new Usuario(
                            null,
                            "Administrador",
                            "admin@local",
                            0,
                            TipoRol.ADMIN,
                            passwordEncoder.encode("admin123")
                    );
                    usuarioRepository.save(admin);
                    System.out.println("Usuario admin@local creado con clave admin123 (solo para pruebas).");
                }
        );
    }
}
