package com.example.tfg

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class eliminarResena : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eliminar_resena)

        //Búsqueda de elementos visuales
        var eliminar: Button =findViewById(R.id.eliminarRes)
        var resenas: Spinner =findViewById(R.id.spinneResenas)

        /*Declaraion de variables*/
    }
}