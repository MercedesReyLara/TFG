package model

import java.io.Serializable

class Review:Serializable {
    var id:Int=0
    var nombre:String=""
    var descripcion:String=""
    var puntuacion:Int=0
    var id_user:String=""
    var user_name:String=""
    var id_product:Int=0
    var nombreProducto:String=""

    constructor()
    constructor(
        id: Int,
        nombre: String,
        descripcion: String,
        puntuacion:Int,
        id_user: String,
        user_name: String,
        id_product: Int,
        nombreProducto: String
    ) {
        this.id = id
        this.nombre = nombre
        this.descripcion = descripcion
        this.puntuacion=puntuacion
        this.id_user = id_user
        this.user_name = user_name
        this.id_product= id_product
        this.nombreProducto = nombreProducto
    }

    constructor(nombre: String, descripcion: String,puntuacion:Int,
                id_user: String, nombreProducto: String) {
        this.nombre = nombre
        this.descripcion = descripcion
        this.puntuacion=puntuacion
        this.id_user = id_user
        this.nombreProducto=nombreProducto
    }

    constructor(
        id: Int,
        nombre: String,
        descripcion: String,
        puntuacion: Int,
        id_user: String,
        nombreProducto: String
    ) {
        this.id = id
        this.nombre = nombre
        this.descripcion = descripcion
        this.puntuacion = puntuacion
        this.id_user = id_user
        this.nombreProducto=nombreProducto
    }


    override fun toString(): String {
        return "$id/$nombre/$descripcion/$puntuacion/$id_user/$nombreProducto/$user_name"
    }


}