package com.example.seminariapp.repository;

import com.example.seminariapp.model.Evento;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventoRepository extends MongoRepository<Evento, String> {
}
