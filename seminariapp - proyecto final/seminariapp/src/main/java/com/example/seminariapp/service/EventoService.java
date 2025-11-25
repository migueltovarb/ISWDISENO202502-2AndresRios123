package com.example.seminariapp.service;

import com.example.seminariapp.model.Evento;
import com.example.seminariapp.model.Usuario;
import com.example.seminariapp.model.enums.TipoRol;
import com.example.seminariapp.repository.EventoRepository;
import com.example.seminariapp.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;

    public EventoService(EventoRepository eventoRepository,
                         UsuarioRepository usuarioRepository) {
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // CREATE
    public Evento crearEvento(Evento evento) {
        return eventoRepository.save(evento);
    }

    // UPDATE
    public Evento actualizarEvento(String id, Evento eventoActualizado) {
        Optional<Evento> eventoOpt = eventoRepository.findById(id);

        if (eventoOpt.isEmpty()) return null;

        Evento evento = eventoOpt.get();
        evento.setNombre(eventoActualizado.getNombre());
        evento.setDescripcion(eventoActualizado.getDescripcion());
        evento.setFechaInicio(eventoActualizado.getFechaInicio());
        evento.setFechaFin(eventoActualizado.getFechaFin());
        evento.setCupoMaximo(eventoActualizado.getCupoMaximo());
        evento.setPrecio(eventoActualizado.getPrecio());
        evento.setTipo(eventoActualizado.getTipo());
        // Actualizar ponente si viene en la petición
        if (eventoActualizado.getPonenteId() != null && !eventoActualizado.getPonenteId().isBlank()) {
            usuarioRepository.findById(eventoActualizado.getPonenteId())
                    .filter(u -> u.getRol() == TipoRol.PONENTE)
                    .orElseThrow(() -> new RuntimeException("El ponente no existe o no tiene rol PONENTE"));
            evento.setPonenteId(eventoActualizado.getPonenteId());
        }

        return eventoRepository.save(evento);
    }

    // DELETE
    public void eliminarEvento(String id) {
        eventoRepository.deleteById(id);
    }

    // READ by ID
    public Evento obtenerPorId(String id) {
        return eventoRepository.findById(id).orElse(null);
    }

    // READ all
    public List<Evento> obtenerTodos() {
        return eventoRepository.findAll();
    }

    // Asignar ponente (validando rol)
    public Evento asignarPonente(String eventoId, String ponenteId) {

        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento no existe"));

        Usuario ponente = usuarioRepository.findById(ponenteId)
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));

        if (ponente.getRol() != TipoRol.PONENTE) {
            throw new RuntimeException("Solo usuarios con rol PONENTE pueden ser asignados");
        }

        evento.setPonenteId(ponenteId);
        return eventoRepository.save(evento);
    }
}
