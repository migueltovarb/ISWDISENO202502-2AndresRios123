package com.example.seminariapp.controller;

import com.example.seminariapp.model.Certificado;
import com.example.seminariapp.service.CertificadoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/certificados")
public class CertificadoController {

    private final CertificadoService certificadoService;

    public CertificadoController(CertificadoService certificadoService) {
        this.certificadoService = certificadoService;
    }

    // Generar certificado si el pago está aprobado
    @PostMapping("/generar")
    public ResponseEntity<Certificado> generarCertificado(
            @RequestParam String inscripcionId
    ) {
        return ResponseEntity.ok(certificadoService.generarCertificado(inscripcionId));
    }

    // Buscar certificado por código QR (hash)
    @GetMapping("/validar/{qrHash}")
    public ResponseEntity<Certificado> obtenerPorQr(@PathVariable String qrHash) {
        Certificado certificado = certificadoService.obtenerPorQr(qrHash);
        return certificado != null ? ResponseEntity.ok(certificado) : ResponseEntity.notFound().build();
    }
}
