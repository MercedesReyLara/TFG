package com.example.tfg

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import model.Product

class DetailsProduct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_product)

        /*Declaracion de elementos visuales*/
        val name:TextView=findViewById(R.id.nameProduct)
        val price:TextView=findViewById(R.id.pricePorduct)
        val description:TextView=findViewById(R.id.descriptionProduct)
        val category:TextView=findViewById(R.id.categoryProduct)
        val review: ImageButton =findViewById(R.id.makeReview)
        val back:ImageButton=findViewById(R.id.backList)

        /*Recperamos el producto mandado a través del intent y lo partimos en las distintas partes del prodcuto
        * para poder asignárselos a los distintos campos*/
        val partes=intent.getStringExtra("producto").toString().split("/")
        name.text=partes[0]
        price.text=partes[2]
        description.text=partes[1]
        category.text=partes[3]


        /*Este nos dirige a la pantalla de la lista de productos del usuario en cuestión*/
        back.setOnClickListener {
            val intentList= Intent(this,productosList::class.java)
            startActivity(intentList)
        }

        /*Este botón nos dirige a la pantalla para hacer una review de este producto*/
        review.setOnClickListener {
            val intentReview=Intent(this,reviewProduct::class.java)
            startActivity(intentReview)
        }
    }
}