package com.example.tfg.sqite

import android.provider.BaseColumns

class Contract {
    ç
    fun BibliotecaContract() {}


    object BibliotecaEntry : BaseColumns {
        const val TABLE_NAME = "biblioteca"
        const val COLUMN_NAME_TITULO = "titulo"
        const val COLUMN_NAME_CODIGO = "codigo"
        const val SQL_CREATE_ENTRIES = ("CREATE TABLE " + TABLE_NAME
                + " (" + BaseColumns._ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME_TITULO + " TEXT,"
                + COLUMN_NAME_CODIGO + " TEXT)")
        const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME
    }

}