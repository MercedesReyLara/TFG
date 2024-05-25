package model

import java.io.Serializable

class Category:Serializable {
    var id:Int=0
    var nombre:String=""
    var descripcion:String=""

    constructor(nombre:String){
        this.nombre=nombre
    }
    constructor(id: Int, nombre: String, descripcion: String) {
        this.id = id
        this.nombre = nombre
        this.descripcion = descripcion
    }

}