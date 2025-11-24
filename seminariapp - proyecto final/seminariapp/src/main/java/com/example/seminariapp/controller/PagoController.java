package com.example.seminariapp.controller;

import com.example.seminariapp.model.Pago;
import com.example.seminariapp.model.enums.TipoEstado;
import com.example.seminariapp.model.enums.TipoPago;
import com.example.seminariapp.service.PagoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    // Crear pago
    @PostMapping
    public ResponseEntity<Pago> crearPago(
            @RequestParam String inscripcionId,
            @RequestParam float monto,
            @RequestParam TipoPago medio
    ) {
        return ResponseEntity.ok(pagoService.crearPago(inscripcionId, monto, medio));
    }

    // Actualizar estado del pago (APROBADO o RECHAZADO)
    @PutMapping("/{pagoId}/estado")
    public ResponseEntity<Pago> actualizarEstado(
            @PathVariable String pagoId,
            @RequestParam TipoEstado estado
    ) {
        return ResponseEntity.ok(pagoService.actualizarEstado(pagoId, estado));
    }
}
