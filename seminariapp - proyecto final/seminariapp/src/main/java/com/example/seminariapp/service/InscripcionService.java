package com.example.seminariapp.service;

import com.example.seminariapp.model.Evento;
import com.example.seminariapp.model.Inscripcion;
import com.example.seminariapp.model.Usuario;
import com.example.seminariapp.model.enums.TipoEstado;
import com.example.seminariapp.repository.EventoRepository;
import com.example.seminariapp.repository.InscripcionRepository;
import com.example.seminariapp.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
                null
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
}
