package com.example.seminariapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "certificados")
public class Certificado {

    @Id
    private String id;

    private int numero;
    private String qrHash;
    private Date fechaEmision;

    private String inscripcionId;

    public Certificado() {}

    public Certificado(String id, int numero, String qrHash, Date fechaEmision, String inscripcionId) {
        this.id = id;
        this.numero = numero;
        this.qrHash = qrHash;
        this.fechaEmision = fechaEmision;
        this.inscripcionId = inscripcionId;
    }

    // Getters / Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public String getQrHash() { return qrHash; }
    public void setQrHash(String qrHash) { this.qrHash = qrHash; }

    public Date getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(Date fechaEmision) { this.fechaEmision = fechaEmision; }

    public String getInscripcionId() { return inscripcionId; }
    public void setInscripcionId(String inscripcionId) { this.inscripcionId = inscripcionId; }
}
