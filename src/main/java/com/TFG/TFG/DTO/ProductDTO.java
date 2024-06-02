package com.TFG.TFG.DTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDTO {

    @JsonProperty
    private int id;
    @JsonProperty
    private String nombreP;
    @JsonProperty
    private String descripcionP;
    @JsonProperty
    private Double precio;
    @JsonProperty
    private String nombreCategoria;

    // Constructor
    public ProductDTO(int id, String nombreP, String descripcionP, Double precio/*, String imagen*/,String nombreC) {
        this.id = id;
        this.nombreP = nombreP;
        this.descripcionP = descripcionP;
        this.precio = precio;
        /*this.imagen = imagen;*/
        this.nombreCategoria=nombreC;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreP() {
        return nombreP;
    }

    public void setNombreP(String nombreP) {
        this.nombreP = nombreP;
    }

    public String getDescripcionP() {
        return descripcionP;
    }

    public void setDescripcionP(String descripcionP) {
        this.descripcionP = descripcionP;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
}
