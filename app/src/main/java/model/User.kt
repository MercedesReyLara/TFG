package model

import java.io.Serializable
import java.time.LocalDateTime

class User :Serializable {
    var dni:String=""
    var nombreU: String=""
    var apellidosU:String=""
    var correo: String=""
    var contrasena: String=""
    var descripcion:String=""
    var profileP:String= "Hola"
    var activo:Boolean=false
    constructor()

    constructor(
        dni: String,
        nombreU: String,
        apellidosU: String,
        correo: String,
        contrasena: String,
        descripcion: String,
        profileP:String,
        activo:Boolean
    ) {
        this.dni = dni
        this.nombreU = nombreU
        this.apellidosU = apellidosU
        this.correo = correo
        this.contrasena = contrasena
        this.descripcion=descripcion
        this.profileP=profileP
        this.activo=activo
    }

    constructor(dni: String) {
        this.dni = dni
    }

}