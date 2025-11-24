package com.example.seminariapp.controller;

import com.example.seminariapp.model.Evento;
import com.example.seminariapp.service.EventoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    // Crear evento
    @PostMapping
    public ResponseEntity<Evento> crearEvento(@RequestBody Evento evento) {
        return ResponseEntity.ok(eventoService.crearEvento(evento));
    }

    // Actualizar evento
    @PutMapping("/{id}")
    public ResponseEntity<Evento> actualizar(@PathVariable String id, @RequestBody Evento evento) {
        Evento actualizado = eventoService.actualizarEvento(id, evento);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        eventoService.eliminarEvento(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Evento> obtenerPorId(@PathVariable String id) {
        Evento evento = eventoService.obtenerPorId(id);
        return evento != null ? ResponseEntity.ok(evento) : ResponseEntity.notFound().build();
    }

    // Obtener todos
    @GetMapping
    public ResponseEntity<List<Evento>> obtenerTodos() {
        return ResponseEntity.ok(eventoService.obtenerTodos());
    }

    // Asignar ponente
    @PutMapping("/{eventoId}/asignar-ponente/{ponenteId}")
    public ResponseEntity<Evento> asignarPonente(
            @PathVariable String eventoId,
            @PathVariable String ponenteId
    ) {
        return ResponseEntity.ok(eventoService.asignarPonente(eventoId, ponenteId));
    }
}
