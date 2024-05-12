package com.TFG.TFG.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="useer")
public class User {

    @Id
    @Column(name = "DNI",nullable = false,unique = true)
    private String DNI;

    @Column(name = "nombreU")
    private String nombreU;

    @Column(name = "apellidosU")
    private String apellidosU;

    @Column(name="correo",unique = true)
    private String correo;

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name="descripcion")
    private String descripcion;

    /*@Column(name="profileP")
    private Blob profileP;*/
    @OneToMany(mappedBy = "user",cascade=CascadeType.REMOVE)
    @JsonIgnore
    private List<Review> resenas=new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "pu",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")}
    )
    @JsonIgnore
    private List<Producto> productsU=new ArrayList<>();
    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getNombreU() {
        return nombreU;
    }

    public void setNombreU(String nombreU) {
        this.nombreU = nombreU;
    }

    public String getApellidosU() {
        return apellidosU;
    }

    public void setApellidosU(String apellidosU) {
        this.apellidosU = apellidosU;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /*public Blob getProfileP() {
        return profileP;
    }

    public void setProfileP(Blob profileP) {
        this.profileP = profileP;
    }*/

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
