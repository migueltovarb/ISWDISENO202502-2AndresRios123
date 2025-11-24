package com.example.seminariapp.controller;

import com.example.seminariapp.model.Inscripcion;
import com.example.seminariapp.service.InscripcionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;

    public InscripcionController(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    // Inscribir usuario a evento
    @PostMapping
    public ResponseEntity<Inscripcion> inscribir(
            @RequestParam String usuarioId,
            @RequestParam String eventoId
    ) {
        return ResponseEntity.ok(inscripcionService.inscribir(usuarioId, eventoId));
    }

    // Cancelar inscripción
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Inscripcion> cancelar(@PathVariable String id) {
        return ResponseEntity.ok(inscripcionService.cancelar(id));
    }

    // Listar inscripciones por usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Inscripcion>> listarPorUsuario(@PathVariable String usuarioId) {
        return ResponseEntity.ok(inscripcionService.listarPorUsuario(usuarioId));
    }

    // Listar inscripciones por evento
    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<Inscripcion>> listarPorEvento(@PathVariable String eventoId) {
        return ResponseEntity.ok(inscripcionService.listarPorEvento(eventoId));
    }
}
