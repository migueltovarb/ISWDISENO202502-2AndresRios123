package com.example.seminariapp.service;

import com.example.seminariapp.model.Material;
import com.example.seminariapp.repository.MaterialRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    // Subir material
    public Material subirMaterial(Material material) {
        return materialRepository.save(material);
    }

    // Listar todos los materiales de un evento
    public List<Material> listarPorEvento(String eventoId) {
        return materialRepository.findByEventoId(eventoId);
    }
}
