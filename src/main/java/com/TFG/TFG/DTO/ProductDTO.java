package com.TFG.TFG.DTO;

public class ProductDTO {
    private int id;
    private String nombreP;
    private String descripcionP;
    private Double precio;
    /*private String imagen;*/
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
}
