package com.example.tfg.sqlite
import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

class ImageDAO {

    fun crearLibro(galllery: GalleryDbHelper, image: Image): Long {
        val db = galllery.writableDatabase

        val values = ContentValues().apply {
            put(ImageContract.ImageEntry.COLUMN_NAME_CONTENIDO, image.valor)
        }

        return db.insert(ImageContract.ImageEntry.TABLE_NAME, null, values)
    }

    fun visualizarLibros(galllery: GalleryDbHelper): ArrayList<Image> {
        val db = galllery.readableDatabase

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

        val lista = ArrayList<Image>()
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