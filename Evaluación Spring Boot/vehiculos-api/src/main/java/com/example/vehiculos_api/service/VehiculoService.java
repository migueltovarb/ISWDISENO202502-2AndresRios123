package com.example.vehiculos_api.service;

import com.example.vehiculos_api.model.Vehiculo;
import com.example.vehiculos_api.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {
    @Autowired
    private VehiculoRepository vehiculoRepository;

    //CREATE
    public Vehiculo crearVehiculo (Vehiculo vehiculo){
        return vehiculoRepository.save(vehiculo);
    }

    //READ - obtener todos
    public List<Vehiculo> obtenerTodos(){
        return vehiculoRepository.findAll();
    }

    //READ - obbtener por id
    public Optional<Vehiculo> obtenerPorId(String id){
        return vehiculoRepository.findById(id);
    }

    //UPDATE
    public Vehiculo actualizarVehiculo(String id, Vehiculo nuevoVehiculo){
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findById(id);

        if(vehiculoOptional.isPresent()) {
            Vehiculo vehiculoExistente = vehiculoOptional.get();

            vehiculoExistente.setMarca(nuevoVehiculo.getMarca());
            vehiculoExistente.setAnio(nuevoVehiculo.getAnio());
            vehiculoExistente.setColor(nuevoVehiculo.getColor());
            vehiculoExistente.setPlaca(nuevoVehiculo.getPlaca());
            vehiculoExistente.setModelo(nuevoVehiculo.getModelo());

            return vehiculoRepository.save(vehiculoExistente);

        }else{
            return null;
        }
    }

    //DELETE
    public void eliminarVehiculo(String id){
        vehiculoRepository.deleteById(id);
    }
}
