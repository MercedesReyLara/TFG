package com.TFG.TFG.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReviewDTO {

    @JsonProperty
    private int id;
    @JsonProperty
    private String nombre;
    @JsonProperty
    private String descripcion;
    @JsonProperty
    private String id_user;
    @JsonProperty
    private String userName;
    @JsonProperty
    private int product;
    @JsonProperty
    private String nombreProducto;

    // Constructor
    public ReviewDTO(int id, String nombre, String descripcion, String id_user, String userName, int product, String nombreProducto) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id_user = id_user;
        this.userName = userName;
        this.product = product;
        this.nombreProducto = nombreProducto;
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

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
}

