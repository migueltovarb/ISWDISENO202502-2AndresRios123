package com.example.seminariapp.service;

import com.example.seminariapp.model.Certificado;
import com.example.seminariapp.model.Inscripcion;
import com.example.seminariapp.model.Pago;
import com.example.seminariapp.model.enums.TipoEstado;
import com.example.seminariapp.repository.CertificadoRepository;
import com.example.seminariapp.repository.InscripcionRepository;
import com.example.seminariapp.repository.PagoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    // Generar certificado: si hay pago debe estar aprobado; si no hay pago, basta con inscripción aprobada
    public Certificado generarCertificado(String inscripcionId) {

        // 1. Validar inscripción
        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "La inscripción no existe"));

        // 2. Validar pago asociado si existe
        Optional<Pago> pagoOpt = pagoRepository.findByInscripcionId(inscripcionId);
        if (pagoOpt.isPresent() && pagoOpt.get().getEstado() != TipoEstado.APROBADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El pago no está aprobado. No se puede generar certificado.");
        }
        if (pagoOpt.isEmpty() && inscripcion.getEstado() != TipoEstado.APROBADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La inscripción no está aprobada. No se puede generar certificado.");
        }

        // 2.5. Si ya existe certificado, retornarlo
        Optional<Certificado> existente = certificadoRepository.findByInscripcionId(inscripcionId);
        if (existente.isPresent()) {
            return existente.get();
        }

        // 3. Validar asistencia completa (si falta o hay alguna en falso, no genera)
        var asistencias = inscripcion.getAsistencias();
        boolean asistenciaCompleta = asistencias != null && !asistencias.isEmpty() && asistencias.values().stream().allMatch(Boolean::booleanValue);
        if (!asistenciaCompleta) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La asistencia no está completa. No se puede generar certificado.");
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

    // Buscar certificado por inscripción
    public Certificado obtenerPorInscripcion(String inscripcionId) {
        return certificadoRepository.findByInscripcionId(inscripcionId).orElse(null);
    }
}
