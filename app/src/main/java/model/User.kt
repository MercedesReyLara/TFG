package model

import java.io.Serializable
import java.time.LocalDateTime

class User :Serializable {
    var nombreU: String=""
    var dni:String=""
    var apellidosU:String=""
    var correo: String=""
    var contrasena: String=""
    var descripcion:String=""
    /*var profileP:ByteArray= "Hola".toByteArray()*/
    constructor()
    constructor(
        nombreU: String,
        dni: String,
        apellidosU: String,
        correo: String,
        contrasena: String,
        descripcion: String,
        /*profileP:ByteArray*/
    ) {
        this.nombreU = nombreU
        this.dni = dni
        this.apellidosU = apellidosU
        this.correo = correo
        this.contrasena = contrasena
        this.descripcion=descripcion
        /*this.profileP=profileP*/
    }

}