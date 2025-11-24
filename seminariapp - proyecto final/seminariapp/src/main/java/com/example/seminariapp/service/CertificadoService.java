package com.example.seminariapp.service;

import com.example.seminariapp.model.Certificado;
import com.example.seminariapp.model.Inscripcion;
import com.example.seminariapp.model.Pago;
import com.example.seminariapp.model.enums.TipoEstado;
import com.example.seminariapp.repository.CertificadoRepository;
import com.example.seminariapp.repository.InscripcionRepository;
import com.example.seminariapp.repository.PagoRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class CertificadoService {

    private final CertificadoRepository certificadoRepository;
    private final InscripcionRepository inscripcionRepository;
    private final PagoRepository pagoRepository;

    public CertificadoService(
            CertificadoRepository certificadoRepository,
            InscripcionRepository inscripcionRepository,
            PagoRepository pagoRepository
    ) {
        this.certificadoRepository = certificadoRepository;
        this.inscripcionRepository = inscripcionRepository;
        this.pagoRepository = pagoRepository;
    }

    // Generar certificado si el pago está aprobado
    public Certificado generarCertificado(String inscripcionId) {

        // 1. Validar inscripción
        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new RuntimeException("La inscripción no existe"));

        // 2. Validar pago asociado
        Pago pago = pagoRepository.findByInscripcionId(inscripcionId)
                .orElseThrow(() -> new RuntimeException("La inscripción no tiene pago asociado"));

        // 3. Validar estado del pago
        if (pago.getEstado() != TipoEstado.APROBADO) {
            throw new RuntimeException("El pago no está aprobado. No se puede generar certificado.");
        }

        // 4. Crear certificado
        Certificado certificado = new Certificado(
                null,
                new Random().nextInt(999999),   // número aleatorio
                UUID.randomUUID().toString(),    // hash QR
                new Date(),
                inscripcionId
        );

        certificado = certificadoRepository.save(certificado);

        // 5. Asociar certificado a la inscripción
        inscripcion.setCertificadoId(certificado.getId());
        inscripcionRepository.save(inscripcion);

        return certificado;
    }

    // Buscar por código QR
    public Certificado obtenerPorQr(String qrHash) {
        Optional<Certificado> cert = certificadoRepository.findByQrHash(qrHash);
        return cert.orElse(null);
    }
}
