package com.example.seminariapp.model;

import com.example.seminariapp.model.enums.TipoRol;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id;
    private String nombre;
    private String email;
    private int telefono;
    private TipoRol rol;

    public Usuario() {}

    public Usuario(String id, String nombre, String email, int telefono, TipoRol rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.rol = rol;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getTelefono() { return telefono; }
    public void setTelefono(int telefono) { this.telefono = telefono; }

    public TipoRol getRol() { return rol; }
    public void setRol(TipoRol rol) { this.rol = rol; }
}
