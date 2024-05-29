package com.TFG.TFG.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name="review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="nombreR")
    private String nombreR;
    @Column(name="opinion")
    private String opinion;
    @Column(name="puntuacion")
    private int puntuacion;
    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="product_id",nullable = false)
    private Producto product;

   public Review() {
    }

    public Review(String nombreR, String opinion,int puntuacion, User user, Producto product) {
        this.nombreR = nombreR;
        this.opinion = opinion;
        this.puntuacion=puntuacion;
        this.user = user;
        this.product = product;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId(){return id;}

    public String getNombreR() {
        return nombreR;
    }

    public void setNombreR(String nombreR) {
        this.nombreR = nombreR;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Producto getProduct() {
        return product;
    }

    public void setProduct(Producto product) {
        this.product = product;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
}
