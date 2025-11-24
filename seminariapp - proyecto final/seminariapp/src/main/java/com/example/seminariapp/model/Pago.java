package com.example.seminariapp.model;

import com.example.seminariapp.model.enums.TipoEstado;
import com.example.seminariapp.model.enums.TipoPago;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "pagos")
public class Pago {

    @Id
    private String id;

    private float monto;
    private TipoPago medio;
    private TipoEstado estado;

    private String referencia;
    private Date fechaPago;

    private String inscripcionId;

    public Pago() {}

    public Pago(String id, float monto, TipoPago medio, TipoEstado estado, String referencia,
                Date fechaPago, String inscripcionId) {
        this.id = id;
        this.monto = monto;
        this.medio = medio;
        this.estado = estado;
        this.referencia = referencia;
        this.fechaPago = fechaPago;
        this.inscripcionId = inscripcionId;
    }

    // Getters y Setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public float getMonto() { return monto; }
    public void setMonto(float monto) { this.monto = monto; }

    public TipoPago getMedio() { return medio; }
    public void setMedio(TipoPago medio) { this.medio = medio; }

    public TipoEstado getEstado() { return estado; }
    public void setEstado(TipoEstado estado) { this.estado = estado; }

    public String getReferencia() { return referencia; }
    public void setReferencia(String referencia) { this.referencia = referencia; }

    public Date getFechaPago() { return fechaPago; }
    public void setFechaPago(Date fechaPago) { this.fechaPago = fechaPago; }

    public String getInscripcionId() { return inscripcionId; }
    public void setInscripcionId(String inscripcionId) { this.inscripcionId = inscripcionId; }
}
