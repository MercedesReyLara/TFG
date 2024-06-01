package com.example.tfg

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class detailsReview : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_review)

        val nombre: TextView =findViewById(R.id.nombreReview)
        val puntuacion: TextView =findViewById(R.id.puntuacionReview)
        val user: TextView =findViewById(R.id.userName)
        val producto: TextView =findViewById(R.id.productoNombre)
        val about: TextView =findViewById(R.id.descripcionReview)
        val puntuaciones:TextView=findViewById(R.id.puntuaciones)
        val back: ImageButton =findViewById(R.id.backButton)

        val partes=intent.getStringExtra("review").toString().split("/")
        nombre.text=partes[1]
        nombre.setTextColor(Color.BLACK)
        puntuacion.text=partes[3]
        puntuacion.setTextColor(Color.BLACK)
        user.text=partes[6]
        user.setTextColor(Color.BLACK)
        producto.text=partes[5]
        producto.setTextColor(Color.BLACK)
        about.text=partes[2]
        about.setTextColor(Color.BLACK)
        puntuaciones.setTextColor(Color.BLACK)
        if(partes[3].toInt()>5)  {
            puntuaciones.text=this.getString(R.string.positiva)
        }else{
           puntuaciones.text=this.getString(R.string.negativa)
        }
        back.setOnClickListener {
           val intentMain= Intent(this,listaResenas::class.java)
            startActivity(intentMain)
        }
    }
}