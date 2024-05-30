package com.example.tfg.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class GalleryDbHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Biblioteca.db"
    }

    override fun onCreate(database: SQLiteDatabase) {
        database.execSQL(ImageContract.ImageEntry.SQL_DELETE_ENTRIES)
        database.execSQL(ImageContract.ImageEntry.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        database.execSQL(ImageContract.ImageEntry.SQL_DELETE_ENTRIES)
        onCreate(database)
    }
}
