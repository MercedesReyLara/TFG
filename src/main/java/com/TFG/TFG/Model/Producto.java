package com.TFG.TFG.Model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "product")
    private List<Review> resenas=new ArrayList<>();

    @ManyToOne/*MIRAR(cascade = CascadeType.DETACH)*/
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;
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

    public List<Review> getResenas() {
        return resenas;
    }

    public void setResenas(List<Review> resenas) {
        this.resenas = resenas;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
