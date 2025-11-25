package com.example.seminariapp.service;

import com.example.seminariapp.model.Evento;
import com.example.seminariapp.model.Inscripcion;
import com.example.seminariapp.model.enums.TipoEstado;
import com.example.seminariapp.repository.EventoRepository;
import com.example.seminariapp.repository.InscripcionRepository;
import com.example.seminariapp.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    public InscripcionService(
            InscripcionRepository inscripcionRepository,
            UsuarioRepository usuarioRepository,
            EventoRepository eventoRepository
    ) {
        this.inscripcionRepository = inscripcionRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
    }

    // INSCRIBIR
    public Inscripcion inscribir(String usuarioId, String eventoId) {

        // Validar que el evento exista
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("El evento no existe"));

        // Validar que el usuario exista
        usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("El usuario no existe"));

        // Validar cupos
        int inscritos = inscripcionRepository.findByEventoId(eventoId).size();
        if (inscritos >= evento.getCupoMaximo()) {
            throw new RuntimeException("El evento ya alcanzó su cupo máximo");
        }

        // Crear inscripción
        Inscripcion inscripcion = new Inscripcion(
                null,
                new Date(),
                TipoEstado.PENDIENTE,
                usuarioId,
                eventoId,
                null,
                null,
                new HashMap<>()
        );

        return inscripcionRepository.save(inscripcion);
    }

    // CANCELAR
    public Inscripcion cancelar(String inscripcionId) {

        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new RuntimeException("La inscripción no existe"));

        inscripcion.setEstado(TipoEstado.RECHAZADO);

        return inscripcionRepository.save(inscripcion);
    }

    // LISTAR POR USUARIO
    public List<Inscripcion> listarPorUsuario(String usuarioId) {
        return inscripcionRepository.findByUsuarioId(usuarioId);
    }

    // LISTAR POR EVENTO
    public List<Inscripcion> listarPorEvento(String eventoId) {
        return inscripcionRepository.findByEventoId(eventoId);
    }

    public Map<Integer, Boolean> obtenerAsistencias(String inscripcionId) {
        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new RuntimeException("La inscripción no existe"));
        return inscripcion.getAsistencias() != null ? inscripcion.getAsistencias() : new HashMap<>();
    }

    public Inscripcion marcarAsistencia(String inscripcionId, int sesion, boolean presente) {
        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new RuntimeException("La inscripción no existe"));

        if (inscripcion.getEstado() != TipoEstado.APROBADO) {
            throw new RuntimeException("Solo inscripciones aprobadas pueden marcar asistencia");
        }

        Map<Integer, Boolean> asist = inscripcion.getAsistencias();
        if (asist == null) {
            asist = new HashMap<>();
        }
        asist.put(sesion, presente);
        inscripcion.setAsistencias(asist);

        return inscripcionRepository.save(inscripcion);
    }
}
