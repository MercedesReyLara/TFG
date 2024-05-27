package com.example.tfg.sqlite

import android.provider.BaseColumns
object ImageContract {

    object ImageEntry : BaseColumns {
        const val TABLE_NAME = "biblioteca"
        const val COLUMN_NAME_CONTENIDO = "codigo"

        const val SQL_CREATE_ENTRIES =
            "CREATE TABLE $TABLE_NAME (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "$COLUMN_NAME_CONTENIDO BLOB)"

        const val SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}

