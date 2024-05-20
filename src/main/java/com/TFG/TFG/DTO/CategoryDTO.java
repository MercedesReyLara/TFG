package com.TFG.TFG.DTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryDTO {

    @JsonProperty
    private int id;
    @JsonProperty
    private String nombre;
    @JsonProperty
    private String descripcion;

    public CategoryDTO(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion= descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
