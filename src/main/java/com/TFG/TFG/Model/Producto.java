package com.TFG.TFG.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="product")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombreP",unique = true)
    private String nombreP;

    @Column(name = "descripcionP")
    private String descripcionP;

    @Column(name = "precio")
    private Double precio;

    @OneToMany(mappedBy = "product",cascade=CascadeType.REMOVE)
    @JsonIgnore
    private List<Review> resenas=new ArrayList<>();

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;

    @ManyToMany(mappedBy = "productsU")
    @JsonIgnore
    private List<User> users=new ArrayList<>();

    /*Column(name="imagen",columnDefinition = "BLOB")
    private byte[] profilePicture;*/
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

   /* public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }*/
}
