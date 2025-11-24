package com.example.seminariapp.repository;

import com.example.seminariapp.model.Material;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MaterialRepository extends MongoRepository<Material, String> {

    List<Material> findByEventoId(String eventoId);
}
