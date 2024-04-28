package com.TFG.TFG.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name="mail",unique = true)
    private String mail;

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name="descipcion")
    private String descripcion;

    @OneToMany(mappedBy = "user",cascade=CascadeType.REMOVE)
    @JsonIgnore
    private List<Review> resenas=new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "tener",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @JsonIgnore
    private List<Producto> productsU=new ArrayList<>();
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public List<Review> getResenas() {
        return resenas;
    }

    public void setResenas(List<Review> resenas) {
        this.resenas = resenas;
    }

    public List<Producto> getProductsU() {
        return productsU;
    }

    public void setProductsU(List<Producto> productsU) {
        this.productsU = productsU;
    }
}
