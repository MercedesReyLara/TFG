package com.TFG.TFG.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    // Constructor
    public UserDTO(String dni, String nombreU, String apellidosU, String correo, String contrasena, String descripcion) {
        this.dni = dni;
        this.nombreU = nombreU;
        this.apellidosU = apellidosU;
        this.correo = correo;
        this.contrasena = contrasena;
        this.descripcion = descripcion;
    }
}
