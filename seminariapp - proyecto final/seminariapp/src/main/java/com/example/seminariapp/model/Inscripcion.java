package com.example.seminariapp.model;

import com.example.seminariapp.model.enums.TipoEstado;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Document(collection = "inscripciones")
public class Inscripcion {

    @Id
    private String id;

    private Date fechaCreacion;
    private TipoEstado estado;

    private String usuarioId;
    private String eventoId;

    private String pagoId;
    private String certificadoId;
    private Map<Integer, Boolean> asistencias = new HashMap<>();

    public Inscripcion() {}

    public Inscripcion(String id, Date fechaCreacion, TipoEstado estado, String usuarioId, String eventoId,
                       String pagoId, String certificadoId, Map<Integer, Boolean> asistencias) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.usuarioId = usuarioId;
        this.eventoId = eventoId;
        this.pagoId = pagoId;
        this.certificadoId = certificadoId;
        if (asistencias != null) {
            this.asistencias = asistencias;
        }
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public TipoEstado getEstado() { return estado; }
    public void setEstado(TipoEstado estado) { this.estado = estado; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public String getEventoId() { return eventoId; }
    public void setEventoId(String eventoId) { this.eventoId = eventoId; }

    public String getPagoId() { return pagoId; }
    public void setPagoId(String pagoId) { this.pagoId = pagoId; }

    public String getCertificadoId() { return certificadoId; }
    public void setCertificadoId(String certificadoId) { this.certificadoId = certificadoId; }

    public Map<Integer, Boolean> getAsistencias() { return asistencias; }
    public void setAsistencias(Map<Integer, Boolean> asistencias) { this.asistencias = asistencias; }
}
