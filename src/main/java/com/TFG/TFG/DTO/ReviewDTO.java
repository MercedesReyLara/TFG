package com.TFG.TFG.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReviewDTO {

    @JsonProperty
    private int id;
    @JsonProperty
    private String nombre;
    @JsonProperty
    private String opinionU;
    @JsonProperty
    private String user;
    @JsonProperty
    private String userName;
    @JsonProperty
    private int product;
    @JsonProperty
    private String productName;

    // Constructor
    public ReviewDTO(int id, String nombre, String opinionU, String user, String userName, int product, String productName) {
        this.id = id;
        this.nombre = nombre;
        this.opinionU = opinionU;
        this.user = user;
        this.userName = userName;
        this.product = product;
        this.productName = productName;
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

    public String getOpinionU() {
        return opinionU;
    }

    public void setOpinionU(String opinionU) {
        this.opinionU = opinionU;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}

