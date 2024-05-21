package com.example.tfg.sqlite

class Image {
    var id:Int=0
    var valor:ByteArray= byteArrayOf()

    constructor()
    constructor(id: Int, valor: ByteArray) {
        this.id = id
        this.valor = valor
    }


}