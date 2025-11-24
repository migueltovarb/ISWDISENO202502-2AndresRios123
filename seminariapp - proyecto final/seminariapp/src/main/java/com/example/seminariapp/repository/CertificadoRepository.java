package com.example.seminariapp.repository;

import com.example.seminariapp.model.Certificado;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CertificadoRepository extends MongoRepository<Certificado, String> {

    Optional<Certificado> findByInscripcionId(String inscripcionId);

    Optional<Certificado> findByQrHash(String qrHash);
}
