package com.example.tfg

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Product
import model.SharedPreff
import model.User

class productosList : AppCompatActivity() {
    private val httPetitions=httPettitions()
    private var listProductos:ArrayList<Product> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        val functions=generalFunctions()
        val context: Context =baseContext
        val sharedPreff=SharedPreff(context)
        val DNI:String=functions.decrypt(functions.clave,sharedPreff.getUser(context).toString()).toString()
        val adapter = ProductAdapter(context,listProductos);
        lifecycleScope.launch(Dispatchers.Main) {
            var newProducts:ArrayList<Product>
            withContext(Dispatchers.IO) {
                newProducts = httPetitions.getProductos(User(DNI))!!
            }
            if(newProducts.isEmpty()){
                Toast.makeText(this@productosList,"Peticion denegada", Toast.LENGTH_SHORT).show()
            }else{
                listProductos.clear()
                listProductos.addAll(newProducts)
                adapter.notifyDataSetChanged()
            }
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos_list)
        /*Declaración de elementos visuales*/
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val lista:ListView=findViewById(R.id.listaProductos)
        lista.adapter=adapter
        val back: ImageButton =findViewById(R.id.backProfile)
        lista.setOnItemClickListener { parent, view, position, id ->
            val product=parent.getItemAtPosition(position) as Product
            val intentDetails=Intent(this,DetailsProduct::class.java)
            intentDetails.putExtra("product",product)
            startActivity(intentDetails)
        }

        back.setOnClickListener {
            val intentBack=Intent(this,perfilUser::class.java)
            startActivity(intentBack)
        }
    }
}