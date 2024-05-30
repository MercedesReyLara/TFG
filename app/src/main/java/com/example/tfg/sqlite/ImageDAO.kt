package com.example.tfg.sqlite
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class ImageDAO {

    fun crearImagen(galllery: GalleryDbHelper, list: List<Image>): Boolean {
        val db :SQLiteDatabase= galllery.writableDatabase
        galllery.onCreate(db)
            for(image in list){
                val values = ContentValues().apply {
                put(ImageContract.ImageEntry.COLUMN_NAME_CONTENIDO, image.valor)
            }
                db.insert(ImageContract.ImageEntry.TABLE_NAME, null, values)
        }

        return true
    }

    fun visualizarImagenes(gallery: GalleryDbHelper): ArrayList<Image> {
        val db = gallery.readableDatabase

        val projection = arrayOf(
            BaseColumns._ID,
            ImageContract.ImageEntry.COLUMN_NAME_CONTENIDO
        )

        val cursor: Cursor = db.query(
            ImageContract.ImageEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val lista:ArrayList<Image> = arrayListOf()
        while (cursor.moveToNext()) {
            val image = Image(
                cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID)),
                cursor.getBlob(cursor.getColumnIndexOrThrow(ImageContract.ImageEntry.COLUMN_NAME_CONTENIDO))
            )
            lista.add(image)
        }
        cursor.close()
        return lista
    }


}