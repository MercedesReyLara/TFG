package com.example.tfg

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Review
import model.User

class listaResenas : AppCompatActivity() {
    var listReviews:ArrayList<Review> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_resenas)

        /*Declaración de elementos de uso*/
        val petitions=httPettitions()
        val context:Context=baseContext
        /*Declaracion de elementos visuales*/
        val back:ImageButton=findViewById(R.id.backMainList)
        val list:ListView=findViewById(R.id.listReviews)

        val adapterResenas=reviewAdapter(context,listReviews)
        lifecycleScope.launch(Dispatchers.Main) {
            var newReviews:ArrayList<Review>
            withContext(Dispatchers.IO) {
                newReviews = petitions.getAllReviews()!!
            }
            if(newReviews.isEmpty()){
                Toast.makeText(this@listaResenas,"Peticion denegada", Toast.LENGTH_SHORT).show()
            }else{
                listReviews.clear()
                listReviews.addAll(newReviews)
                adapterResenas.notifyDataSetChanged()
            }
        }

        list.adapter=adapterResenas

        list.setOnItemClickListener{parent, view, position, id ->
            val review=parent.getItemAtPosition(position) as Review
            val intentDetails=Intent(this,detailsReview::class.java)
            intentDetails.putExtra("review",review)
            startActivity(intentDetails)
        }
        back.setOnClickListener {
            val intentMain=Intent(this,mainMenu::class.java)
            startActivity(intentMain)
        }
    }
}