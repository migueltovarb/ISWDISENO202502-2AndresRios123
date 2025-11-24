package com.example.seminariapp.model;

import com.example.seminariapp.model.enums.TipoRol;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id;
    private String nombre;
    private String email;
    private long telefono;
    private TipoRol rol;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public Usuario() {}

    public Usuario(String id, String nombre, String email, long telefono, TipoRol rol, String password) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.rol = rol;
        this.password = password;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public long getTelefono() { return telefono; }
    public void setTelefono(long telefono) { this.telefono = telefono; }

    public TipoRol getRol() { return rol; }
    public void setRol(TipoRol rol) { this.rol = rol; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
