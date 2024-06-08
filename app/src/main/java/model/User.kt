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
    var profileP:ByteArray= byteArrayOf()
    var activo:Boolean=false
    var cantProductos:Int=0
    var cantResenas:Int=0
    constructor()

    constructor(
        dni: String,
        nombreU: String,
        apellidosU: String,
        correo: String,
        contrasena: String,
        descripcion: String,
        profileP:ByteArray,
        activo:Boolean,
        cantProductos:Int,
        cantResenas:Int
    ) {
        this.dni = dni
        this.nombreU = nombreU
        this.apellidosU = apellidosU
        this.correo = correo
        this.contrasena = contrasena
        this.descripcion=descripcion
        this.profileP=profileP
        this.activo=activo
        this.cantProductos=cantProductos
        this.cantResenas=cantResenas
    }

    constructor(dni: String) {
        this.dni = dni
    }
    constructor(correo: String,activo: Boolean){
        this.correo=correo
        this.activo=activo
    }

    constructor(nombre: String,cantProductos:Int) {
        this.nombreU = nombre
    }

    constructor(
        dni: String,
        nombreU: String,
        apellidosU: String,
        correo: String,
        contrasena: String,
        descripcion: String,
        profileP: ByteArray,
        activo: Boolean
    ) {
        this.dni = dni
        this.nombreU = nombreU
        this.apellidosU = apellidosU
        this.correo = correo
        this.contrasena = contrasena
        this.descripcion = descripcion
        this.profileP = profileP
        this.activo = activo
    }


    override fun toString(): String {
        return "$dni/$nombreU/$apellidosU/$correo/$contrasena/$descripcion/$profileP/$activo/$cantResenas/$cantProductos"
    }

}