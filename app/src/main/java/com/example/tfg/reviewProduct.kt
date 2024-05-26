package com.example.tfg

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Category
import model.Product
import model.Review
import model.SharedPreff

class reviewProduct : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_product)

        /*Declaracion de elementos visuales*/

        val publicar: Button =findViewById(R.id.publicar)
        val nombre:EditText=findViewById(R.id.titulo)
        val descripcion:EditText=findViewById(R.id.descripcion)
        val puntuacion:EditText=findViewById(R.id.puntuacion)
        val spinnerProductos: Spinner =findViewById(R.id.productos)
        val back:ImageButton=findViewById(R.id.backMainR)
        /*Declaracion de variables*/
        val context: Context =baseContext
        val functions=generalFunctions()
        val sharedPreff=SharedPreff(context)
        val pettitions=httPettitions()
        val nombresProductos = arrayListOf("-")
        val hintNombre=nombre.hint
        val hintDescripcion=descripcion.hint
        val hintPuntuacion=puntuacion.hint

        functions.clearHint(listOf(nombre,descripcion,puntuacion),
            listOf (hintNombre,hintDescripcion,hintPuntuacion)
        )
        lifecycleScope.launch(Dispatchers.Main) {
            var products: ArrayList<Product>
            withContext(Dispatchers.IO) {
                products = pettitions.getAllProducts()!!
            }
            if (products.isEmpty()) {
                Toast.makeText(this@reviewProduct, "Peticion denegada", Toast.LENGTH_SHORT).show()
            } else {
                for(p in products){
                    nombresProductos.add(p.nombreP)
                }
            }
        }
        val adapter: ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_spinner_item,nombresProductos)
        spinnerProductos.adapter=adapter
        spinnerProductos.setSelection(0)

        publicar.setOnClickListener {
            val nombreTXT=nombre.text.toString()
            val descripcionTXT=descripcion.text.toString()
            val puntuacionTXT=puntuacion.text.toString()

            if(nombreTXT.isEmpty()||descripcionTXT.isEmpty()||puntuacionTXT.isEmpty()){
                Toast.makeText(this@reviewProduct, "Los campos no puden estar vacios", Toast.LENGTH_SHORT).show()
            }else{
                val review= Review(nombreTXT,descripcionTXT,sharedPreff.getUser(context).toString(),spinnerProductos.selectedItem.toString())
                lifecycleScope.launch(Dispatchers.Main) {
                    var done=false
                    withContext(Dispatchers.IO){
                        done=pettitions.postReview(review)
                    }
                    if(done){
                        Toast.makeText(this@reviewProduct, "Reseña publicada", Toast.LENGTH_SHORT).show()
                        functions.manipulateEdits(listOf(nombre,descripcion,puntuacion))
                    }else{
                        Toast.makeText(this@reviewProduct, "Error en la peticion", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        back.setOnClickListener {
            val intentMain= Intent(this,mainMenu::class.java)
            startActivity(intentMain)
        }
    }
}