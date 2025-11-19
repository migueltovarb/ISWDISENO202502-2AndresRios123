package com.example.vehiculos_api.controller;

import com.example.vehiculos_api.model.Vehiculo;
import com.example.vehiculos_api.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    //CREATE
    @PostMapping
    public ResponseEntity<Vehiculo> crearVehiculo(@RequestBody Vehiculo vehiculo){
        Vehiculo nuevo = vehiculoService.crearVehiculo(vehiculo);
        return ResponseEntity.ok(nuevo);
    }

    //READ - obtener todos
    @GetMapping
    public ResponseEntity<List<Vehiculo>> obtenerTodos(){
        List<Vehiculo> lista = vehiculoService.obtenerTodos();
        return ResponseEntity.ok(lista);
    }

    // READ - obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> obtenerPorId(@PathVariable String id) {
        Optional<Vehiculo> vehiculo = vehiculoService.obtenerPorId(id);

        if (vehiculo.isPresent()) {
            return ResponseEntity.ok(vehiculo.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> actualizarVehiculo(
            @PathVariable String id,
            @RequestBody Vehiculo nuevoVehiculo) {

        Vehiculo actualizado = vehiculoService.actualizarVehiculo(id, nuevoVehiculo);

        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVehiculo(@PathVariable String id) {
        vehiculoService.eliminarVehiculo(id);
        return ResponseEntity.noContent().build();
    }

}
