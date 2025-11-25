package com.example.seminariapp.controller;

import com.example.seminariapp.model.Inscripcion;
import com.example.seminariapp.model.enums.TipoEstado;
import com.example.seminariapp.repository.UsuarioRepository;
import com.example.seminariapp.service.InscripcionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;
    private final UsuarioRepository usuarioRepository;

    public InscripcionController(InscripcionService inscripcionService, UsuarioRepository usuarioRepository) {
        this.inscripcionService = inscripcionService;
        this.usuarioRepository = usuarioRepository;
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

    // Listar inscripciones aprobadas de un evento con datos de usuario (para asistencia)
    @GetMapping("/evento/{eventoId}/aprobadas")
    public ResponseEntity<List<InscripcionDetalle>> listarAprobadas(@PathVariable String eventoId) {
        List<Inscripcion> inscripciones = inscripcionService.listarPorEvento(eventoId);
        List<InscripcionDetalle> detalle = inscripciones.stream()
                .filter(ins -> ins.getEstado() == TipoEstado.APROBADO)
                .map(ins -> {
                    var usuario = usuarioRepository.findById(ins.getUsuarioId()).orElse(null);
                    return new InscripcionDetalle(
                            ins.getId(),
                            ins.getUsuarioId(),
                            usuario != null ? usuario.getNombre() : "N/A",
                            usuario != null ? usuario.getEmail() : "N/A",
                            ins.getEstado().name(),
                            ins.getAsistencias()
                    );
                })
                .toList();
        return ResponseEntity.ok(detalle);
    }

    // Consultar asistencias de una inscripción
    @GetMapping("/{id}/asistencias")
    public ResponseEntity<Map<Integer, Boolean>> obtenerAsistencias(@PathVariable String id) {
        return ResponseEntity.ok(inscripcionService.obtenerAsistencias(id));
    }

    // Marcar asistencia
    @PatchMapping("/{id}/asistencia")
    public ResponseEntity<Inscripcion> marcarAsistencia(
            @PathVariable String id,
            @RequestParam int sesion,
            @RequestParam(defaultValue = "true") boolean presente
    ) {
        return ResponseEntity.ok(inscripcionService.marcarAsistencia(id, sesion, presente));
    }

    public record InscripcionDetalle(
            String inscripcionId,
            String usuarioId,
            String nombre,
            String email,
            String estado,
            Map<Integer, Boolean> asistencias
    ) {}
}
