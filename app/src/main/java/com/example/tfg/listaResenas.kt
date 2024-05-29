package com.example.tfg

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Category
import model.Review
import model.User

class listaResenas : AppCompatActivity() {
    var listReviews: ArrayList<Review> = arrayListOf()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_resenas)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        /*Declaración de elementos de uso*/
        val petitions = httPettitions()
        val context: Context = baseContext
        /*Declaracion de elementos visuales*/
        val back: ImageButton = findViewById(R.id.backMainList)
        val list: ListView = findViewById(R.id.listReviews)
        val filter: Button = findViewById(R.id.applyFilter)
        val spinner: Spinner = findViewById(R.id.filtroCategory)
        val nombresCategorias = arrayListOf("ninguna")
        lifecycleScope.launch(Dispatchers.Main) {
            var categories: ArrayList<Category>
            withContext(Dispatchers.IO) {
                categories = petitions.getAllCategories()
            }
            if (categories.isEmpty()) {
                Toast.makeText(this@listaResenas, "Peticion denegada", Toast.LENGTH_SHORT).show()
            } else {
                for(c in categories){
                    nombresCategorias.add(c.nombre)
                }
            }
        }
        val adapterResenas = reviewAdapter(context, listReviews)
        val adapter: ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_spinner_item,nombresCategorias)
        spinner.adapter=adapter
        spinner.setSelection(0)

        list.adapter = adapterResenas

        list.setOnItemClickListener { parent, view, position, id ->
            val review = parent.getItemAtPosition(position) as Review
            val intentDetails = Intent(this, detailsReview::class.java)
            intentDetails.putExtra("review", review.toString())
            startActivity(intentDetails)
        }

        filter.setOnClickListener {
            val categoryN = spinner.selectedItem.toString()
            if(categoryN == "ninguna") {
                lifecycleScope.launch(Dispatchers.Main) {
                    var reviews: ArrayList<Review>
                    withContext(Dispatchers.IO) {
                        reviews = petitions.getAllReviews()!!
                    }
                    if (reviews.isEmpty()) {
                        Toast.makeText(this@listaResenas, "Peticion denegada", Toast.LENGTH_SHORT).show()
                    } else {
                        listReviews.clear()
                        listReviews.addAll(reviews)
                        adapterResenas.notifyDataSetChanged()
                    }
                }
            }else{
                lifecycleScope.launch(Dispatchers.Main) {
                    var newReviews: ArrayList<Review>
                    withContext(Dispatchers.IO) {
                        newReviews = petitions.getReviewsByCategorie(Category(categoryN))!!
                    }
                    if (newReviews.isEmpty()) {
                        Toast.makeText(
                            this@listaResenas,
                            "Ha habido problemas en la peticion",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        listReviews.clear()
                        listReviews.addAll(newReviews)
                        adapterResenas.notifyDataSetChanged()
                    }
                }
            }
        }
            back.setOnClickListener {
                val intentMain = Intent(this, mainMenu::class.java)
                startActivity(intentMain)
            }

    }
}