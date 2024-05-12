package com.TFG.TFG.DTO;

public class UserDTO {
    private String DNI;
    private String nombreU;
    private String apellidosU;
    private String correo;
    private String contrasena;
    private String descripcion;

    // Constructor
    public UserDTO(String DNI, String nombreU, String apellidosU, String correo, String contrasena, String descripcion) {
        this.DNI = DNI;
        this.nombreU = nombreU;
        this.apellidosU = apellidosU;
        this.correo = correo;
        this.contrasena = contrasena;
        this.descripcion = descripcion;
    }
}
