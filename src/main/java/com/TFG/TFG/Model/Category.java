package com.TFG.TFG.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="nombreC",unique = true)
    private String nombreCa;

    @Column(name="descripcionC")
    private String descripcionCa;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Producto> products;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombreCa() {
        return nombreCa;
    }

    public void setNombreCa(String nombreCa) {
        this.nombreCa = nombreCa;
    }

    public String getDescripcionCa() {
        return descripcionCa;
    }

    public void setDescripcionCa(String descripcionCa) {
        this.descripcionCa = descripcionCa;
    }

    public List<Producto> getProducts() {
        return products;
    }

    public void setProducts(List<Producto> products) {
        this.products = products;
    }
}
