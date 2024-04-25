package com.TFG.TFG.Model;


import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name="producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nombreP")
    private String nombreP;

    @Column(name = "descripcionP")
    private String descripcionP;

    @Column(name = "precio")
    private Double precio;

    @ManyToMany(mappedBy = "productos")
    private ArrayList<User> users=new ArrayList<>();

    /*@Column(name="")*/

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
}
