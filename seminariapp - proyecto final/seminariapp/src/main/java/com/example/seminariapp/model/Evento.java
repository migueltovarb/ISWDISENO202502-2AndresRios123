package com.example.seminariapp.model;

import com.example.seminariapp.model.enums.TipoEvento;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "eventos")
public class Evento {

    @Id
    private String id;

    private String nombre;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private int cupoMaximo;
    private float precio;
    private TipoEvento tipo;

    private String ponenteId;

    public Evento() {}

    public Evento(String id, String nombre, String descripcion, Date fechaInicio, Date fechaFin,
                  int cupoMaximo, float precio, TipoEvento tipo, String ponenteId) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cupoMaximo = cupoMaximo;
        this.precio = precio;
        this.tipo = tipo;
        this.ponenteId = ponenteId;
    }

    // Getters / Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public int getCupoMaximo() { return cupoMaximo; }
    public void setCupoMaximo(int cupoMaximo) { this.cupoMaximo = cupoMaximo; }

    public float getPrecio() { return precio; }
    public void setPrecio(float precio) { this.precio = precio; }

    public TipoEvento getTipo() { return tipo; }
    public void setTipo(TipoEvento tipo) { this.tipo = tipo; }

    public String getPonenteId() { return ponenteId; }
    public void setPonenteId(String ponenteId) { this.ponenteId = ponenteId; }
}
