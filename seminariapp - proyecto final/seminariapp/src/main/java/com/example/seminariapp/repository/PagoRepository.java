package com.example.seminariapp.repository;

import com.example.seminariapp.model.Pago;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PagoRepository extends MongoRepository<Pago, String> {

    Optional<Pago> findByInscripcionId(String inscripcionId);
}
