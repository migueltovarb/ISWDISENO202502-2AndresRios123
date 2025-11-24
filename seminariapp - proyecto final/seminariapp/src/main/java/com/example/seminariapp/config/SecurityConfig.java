package com.example.seminariapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        // Registro de usuarios abierto
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                        // Consultas de usuarios restringidas
                        .requestMatchers("/usuarios/**").hasAnyRole("ADMIN", "COORDINADOR")
                        // Eventos: lectura libre; cambios solo coordinador/admin
                        .requestMatchers(HttpMethod.GET, "/eventos/**").permitAll()
                        .requestMatchers("/eventos/**").hasAnyRole("COORDINADOR", "ADMIN")
                        .requestMatchers("/materiales/**").hasAnyRole("PONENTE", "COORDINADOR", "ADMIN")
                        .requestMatchers("/inscripciones/**").hasAnyRole("ESTUDIANTE", "COORDINADOR", "ADMIN")
                        .requestMatchers("/pagos/**").hasAnyRole("ESTUDIANTE", "COORDINADOR", "ADMIN")
                        .requestMatchers("/certificados/**").hasAnyRole("COORDINADOR", "ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("admin")
                .password("admin")
                .roles("ADMIN")
                .build();

        UserDetails coordinador = User.withUsername("coordinador")
                .password("coordinador")
                .roles("COORDINADOR")
                .build();

        UserDetails ponente = User.withUsername("ponente")
                .password("ponente")
                .roles("PONENTE")
                .build();

        UserDetails estudiante = User.withUsername("estudiante")
                .password("estudiante")
                .roles("ESTUDIANTE")
                .build();

        return new InMemoryUserDetailsManager(admin, coordinador, ponente, estudiante);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Para demo: no encriptado. No usar en producción.
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(List.of("*"));
            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
            config.setAllowedHeaders(List.of("*"));
            config.setAllowCredentials(false);
            return config;
        };
    }
}
