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
        val pick: ImageView =findViewById(R.id.productPick)
        val name:TextView=findViewById(R.id.nameProduct)
        val price:TextView=findViewById(R.id.pricePorduct)
        val description:TextView=findViewById(R.id.descriptionProduct)
        val category:TextView=findViewById(R.id.categoryProduct)
        val review: ImageButton =findViewById(R.id.makeReview)
        val viewReviews:ImageButton=findViewById(R.id.reviewsP)
        val back:ImageButton=findViewById(R.id.backList)

        /*Declaracion de variables*/

        val functions=generalFunctions()
        /*Recperamos el producto mandado a través del intent y le asignamos a cada campo su valor*/
        val product=intent.getSerializableExtra("producto") as Product?
        name.text=product!!.nombreP
        price.text=product.precio.toString()
        description.text=product.descripcionP
        category.text=product.nombreCategoria


        /*Este boton nos dirige a la lista de reviews de este producto en cuestion*/
        viewReviews.setOnClickListener {
            val intentList= Intent(this,productosList::class.java)
            startActivity(intentList)
        }

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