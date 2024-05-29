package model

import java.io.Serializable

class Product:Serializable {
    var id:Int=0
    var nombreP:String=""
    var descripcionP:String=""
    var precio:Double=0.0
    var nombreCategoria:String=""
    constructor()
    constructor(
        id: Int,
        nombreP: String,
        descripcionP: String,
        precio: Double,
        nombreCategoria: String
    ) {
        this.id = id
        this.nombreP = nombreP
        this.descripcionP = descripcionP
        this.precio = precio
        this.nombreCategoria = nombreCategoria
    }

    override fun toString(): String {
        return "$nombreP/$descripcionP/$precio/$nombreCategoria"
    }


}