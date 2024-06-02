package com.TFG.TFG.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;

public class UserDTO {

    @JsonProperty
    private String dni;
    @JsonProperty
    private String nombreU;
    @JsonProperty
    private String apellidosU;
    @JsonProperty
    private String correo;
    @JsonProperty
    private String contrasena;
    @JsonProperty
    private String descripcion;
    @JsonProperty
    private byte[] profileP;
    @JsonProperty
    private boolean activo;
    // Constructor

    public UserDTO() {
    }

    public UserDTO(String dni, String nombreU, String apellidosU, String correo, String contrasena,
                   String descripcion,byte[]profileP,Boolean activo) {
        this.dni = dni;
        this.nombreU = nombreU;
        this.apellidosU = apellidosU;
        this.correo = correo;
        this.contrasena = contrasena;
        this.descripcion = descripcion;
        this.profileP=profileP;
        this.activo=activo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public byte[] getProfileP() {
        return profileP;
    }

    public void setProfileP(byte[] profileP) {
        this.profileP = profileP;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    public String codearImagen(byte[] profileP){
        return Base64.getEncoder().encodeToString(profileP);
    };
}
