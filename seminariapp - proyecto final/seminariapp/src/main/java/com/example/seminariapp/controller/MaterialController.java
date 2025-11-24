package com.example.seminariapp.controller;

import com.example.seminariapp.model.Material;
import com.example.seminariapp.service.MaterialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materiales")
public class MaterialController {

    private final MaterialService materialService;

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    // Subir material
    @PostMapping
    public ResponseEntity<Material> subir(@RequestBody Material material) {
        return ResponseEntity.ok(materialService.subirMaterial(material));
    }

    // Listar materiales por evento
    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<Material>> listarPorEvento(@PathVariable String eventoId) {
        return ResponseEntity.ok(materialService.listarPorEvento(eventoId));
    }
}
