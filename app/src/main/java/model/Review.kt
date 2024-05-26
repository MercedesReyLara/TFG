package model

import java.io.Serializable

class Review:Serializable {
    var id:Int=0
    var nombre:String=""
    var descripcion:String=""
    var id_User:String=""
    var nombre_User:String=""
    var id_Producto:Int=0
    var nombrePorducto:String=""

    constructor()
    constructor(
        id: Int,
        nombre: String,
        descripcion: String,
        id_User: String,
        nombre_User: String,
        id_Producto: Int,
        nombrePorducto: String
    ) {
        this.id = id
        this.nombre = nombre
        this.descripcion = descripcion
        this.id_User = id_User
        this.nombre_User = nombre_User
        this.id_Producto = id_Producto
        this.nombrePorducto = nombrePorducto
    }

    constructor(nombre: String, descripcion: String, id_User: String, nombrePorducto: String) {
        this.nombre = nombre
        this.descripcion = descripcion
        this.id_User = id_User
        this.nombrePorducto=nombrePorducto
    }


}