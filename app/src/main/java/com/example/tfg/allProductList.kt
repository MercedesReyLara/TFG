package com.example.tfg

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tfg.adapters.ProductAdapter
import com.example.tfg.petitionsAndFunctions.httPettitions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Product
import com.example.tfg.petitionsAndFunctions.SharedPreff

class allProductList : AppCompatActivity() {
    var listProducts: ArrayList<Product> = arrayListOf()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_product_list)

        /*Declaracion de variables y elementos de uso*/
        val petitions = httPettitions()
        val context: Context = baseContext
        val sharedPreff= SharedPreff(context)
        val ip=sharedPreff.getIp(context)
        /*Declaracion de elementos visuales*/
        val back:ImageButton=findViewById(R.id.backMain)
        val spinnerPuntuacion: Spinner =findViewById(R.id.spinnerPuntuacion)
        val listaP:ListView=findViewById(R.id.listProducts)
        val filtro: Button =findViewById(R.id.aplicarPuntuacion)
        val puntuaciones = arrayListOf("Ninguna puntuacion","Puntuacion alta","Puntuacion baja")

        val adapterProduct =
            ProductAdapter(context, listProducts)
        val adapter: ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_spinner_item,puntuaciones)
        spinnerPuntuacion.adapter=adapter
        spinnerPuntuacion.setSelection(0)
        lifecycleScope.launch(Dispatchers.Main) {
            var products: ArrayList<Product>?
            withContext(Dispatchers.IO) {
                products= petitions.getAllProducts(ip)!!
            }
            if(products==null) {
                Toast.makeText(
                    this@allProductList,
                    this@allProductList.getString(R.string.problemas), Toast.LENGTH_SHORT
                ).show()
            }else if(products!!.isEmpty()){
                listProducts.clear()
                Toast.makeText(
                    this@allProductList,
                    this@allProductList.getString(R.string.errorObtencion),
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                listProducts.clear()
                listProducts.addAll(products!!)
                adapterProduct.notifyDataSetChanged()
            }
        }
        listaP.adapter = adapterProduct


        filtro.setOnClickListener {
            val puntuacion = spinnerPuntuacion.selectedItem.toString()
            when (puntuacion) {
                "Ninguna puntuacion" -> {
                    lifecycleScope.launch(Dispatchers.Main) {
                        var products: ArrayList<Product>?
                        withContext(Dispatchers.IO) {
                            products= petitions.getAllProducts(ip)!!
                        }
                        if(products==null) {
                            Toast.makeText(
                                this@allProductList,
                                this@allProductList.getString(R.string.problemas), Toast.LENGTH_SHORT
                            ).show()
                        }else if(products!!.isEmpty()){
                            listProducts.clear()
                            Toast.makeText(
                                this@allProductList,
                                this@allProductList.getString(R.string.errorObtencion),
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            listProducts.clear()
                            listProducts.addAll(products!!)
                            adapterProduct.notifyDataSetChanged()
                        }
                    }
                }
                "Puntuacion alta" -> {
                    lifecycleScope.launch(Dispatchers.Main) {
                        var highProducts: ArrayList<Product>?
                        withContext(Dispatchers.IO) {
                            highProducts = petitions.getProductsHigh(ip)!!
                        }
                        if(highProducts==null) {
                            Toast.makeText(
                                this@allProductList,
                                this@allProductList.getString(R.string.problemas), Toast.LENGTH_SHORT
                            ).show()
                        }else if(highProducts!!.isEmpty()){
                            listProducts.clear()
                            Toast.makeText(
                                this@allProductList,
                                this@allProductList.getString(R.string.errorObtencion),
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            listProducts.clear()
                            listProducts.addAll(highProducts!!)
                            adapterProduct.notifyDataSetChanged()
                        }
                    }
                }
                "Puntuacion baja" -> {
                    lifecycleScope.launch(Dispatchers.Main) {
                        var lowProducts: ArrayList<Product>?
                        withContext(Dispatchers.IO) {
                            lowProducts = petitions.getProductsLow(ip)!!
                        }
                        if(lowProducts==null) {
                            Toast.makeText(
                                this@allProductList,
                                this@allProductList.getString(R.string.problemas), Toast.LENGTH_SHORT
                            ).show()
                        }else if(lowProducts!!.isEmpty()){
                            listProducts.clear()
                            Toast.makeText(
                                this@allProductList,
                                this@allProductList.getString(R.string.noP),
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            listProducts.clear()
                            listProducts.addAll(lowProducts!!)
                            adapterProduct.notifyDataSetChanged()
                        }

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