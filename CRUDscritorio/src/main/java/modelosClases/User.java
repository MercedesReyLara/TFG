/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelosClases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;


@Entity
@Table(name="useer")
public class User implements Serializable{

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

    @Lob
    @Column(name="profileP",columnDefinition = "LONGBLOB")
    private String profileP;

    @Column(name="activo")
    private Boolean activo;
    @OneToMany(mappedBy = "user",cascade=CascadeType.REMOVE)
    private List<Review> resenas=new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(
            name = "pu",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")}
    )
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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getProfileP() {
        return profileP;
    }

    public void setProfileP(String profileP) {
        this.profileP = profileP;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
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

