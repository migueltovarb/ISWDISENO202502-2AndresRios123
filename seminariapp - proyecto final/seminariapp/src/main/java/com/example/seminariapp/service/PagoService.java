package com.example.seminariapp.service;

import com.example.seminariapp.model.Inscripcion;
import com.example.seminariapp.model.Pago;
import com.example.seminariapp.model.enums.TipoEstado;
import com.example.seminariapp.model.enums.TipoPago;
import com.example.seminariapp.repository.InscripcionRepository;
import com.example.seminariapp.repository.PagoRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final InscripcionRepository inscripcionRepository;

    public PagoService(PagoRepository pagoRepository, InscripcionRepository inscripcionRepository) {
        this.pagoRepository = pagoRepository;
        this.inscripcionRepository = inscripcionRepository;
    }

    // Crear pago
    public Pago crearPago(String inscripcionId, float monto, TipoPago medio) {

        // Validar inscripción
        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new RuntimeException("La inscripción no existe"));

        // Crear pago en estado PENDIENTE
        Pago pago = new Pago(
                null,
                monto,
                medio,
                TipoEstado.PENDIENTE,
                UUID.randomUUID().toString(),   // referencia única
                new Date(),
                inscripcionId
        );

        pago = pagoRepository.save(pago);

        // Asociar pago a la inscripción
        inscripcion.setPagoId(pago.getId());
        inscripcionRepository.save(inscripcion);

        return pago;
    }

    // Actualizar estado de pago
    public Pago actualizarEstado(String pagoId, TipoEstado estado) {

        Optional<Pago> pagoOpt = pagoRepository.findById(pagoId);
        if (pagoOpt.isEmpty()) {
            throw new RuntimeException("El pago no existe");
        }

        Pago pago = pagoOpt.get();
        pago.setEstado(estado);
        pagoRepository.save(pago);

        // Obtener inscripción asociada
        Inscripcion inscripcion = inscripcionRepository.findById(pago.getInscripcionId())
                .orElseThrow(() -> new RuntimeException("La inscripción asociada no existe"));

        // Actualizar estado de inscripción
        if (estado == TipoEstado.APROBADO) {
            inscripcion.setEstado(TipoEstado.APROBADO);
        } else if (estado == TipoEstado.RECHAZADO) {
            inscripcion.setEstado(TipoEstado.RECHAZADO);
        }

        inscripcionRepository.save(inscripcion);

        return pago;
    }
}
