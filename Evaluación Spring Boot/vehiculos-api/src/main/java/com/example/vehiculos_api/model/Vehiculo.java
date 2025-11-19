package com.example.vehiculos_api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vehiculos")
public class Vehiculo {
    @Id
    private String id;

    private String marca;
    private String modelo;
    private int anio;
    private String placa;
    private String color;

    // Constructor vacío: Spring y MongoDB lo necesitan
    public Vehiculo(){
    }

    // Constructor con todos los campos
    public Vehiculo(String Id, String marca, String modelo, int anio, String placa, String color){
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.placa = placa;
        this.color = color;
    }

    // GETTERS Y SETTERS
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getMarca(){
        return marca;
    }
    public void setMarca(String marca){
        this.marca = marca;
    }

    public String getModelo(){
        return modelo;
    }
    public void setModelo(String modelo){
        this.modelo = modelo;
    }

    public int getAnio(){
        return anio;
    }
    public void setAnio(int anio){
        this.anio = anio;
    }

    public String getPlaca(){
        return placa;
    }
    public void setPlaca(String placa){
        this.placa = placa;
    }

    public String getColor(){
        return color;
    }
    public void setColor(String color){
        this.color = color;
    }
}
