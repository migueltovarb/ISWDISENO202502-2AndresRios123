package com.example.seminariapp.model;

import com.example.seminariapp.model.enums.TipoMaterial;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "materiales")
public class Material {

    @Id
    private String id;

    private String titulo;
    private TipoMaterial tipo;
    private String url;

    private Date fechaSubida;
    private String eventoId;

    public Material() {}

    public Material(String id, String titulo, TipoMaterial tipo, String url,
                    Date fechaSubida, String eventoId) {
        this.id = id;
        this.titulo = titulo;
        this.tipo = tipo;
        this.url = url;
        this.fechaSubida = fechaSubida;
        this.eventoId = eventoId;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public TipoMaterial getTipo() { return tipo; }
    public void setTipo(TipoMaterial tipo) { this.tipo = tipo; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Date getFechaSubida() { return fechaSubida; }
    public void setFechaSubida(Date fechaSubida) { this.fechaSubida = fechaSubida; }

    public String getEventoId() { return eventoId; }
    public void setEventoId(String eventoId) { this.eventoId = eventoId; }
}
