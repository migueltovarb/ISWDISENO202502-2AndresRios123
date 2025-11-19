package com.example.vehiculos_api.repository;

import com.example.vehiculos_api.model.Vehiculo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepository extends MongoRepository<Vehiculo, String> {
}
