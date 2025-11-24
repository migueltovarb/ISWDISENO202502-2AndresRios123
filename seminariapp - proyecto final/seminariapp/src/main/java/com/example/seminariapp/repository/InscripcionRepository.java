package com.example.seminariapp.repository;

import com.example.seminariapp.model.Inscripcion;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InscripcionRepository extends MongoRepository<Inscripcion, String> {

    List<Inscripcion> findByUsuarioId(String usuarioId);

    List<Inscripcion> findByEventoId(String eventoId);
}
