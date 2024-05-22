package com.example.tfg

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Product
import model.Review
import model.SharedPreff
import model.User

class productosList : AppCompatActivity() {
    private val httPetitions=httPettitions()
    private var listProductos:ArrayList<Product> = arrayListOf()
    private var listReviews:ArrayList<Review> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {

        /*Esta activity la voy a usar tanto como para mostrar productos como reseñas
        Haré 2 botones, cuando pulse no hará la petición de productos y cuando pulse el otro de reseñas
         */
        val functions=generalFunctions()
        val context: Context =baseContext
        val sharedPreff=SharedPreff(context)
        val DNI:String=/*functions.decrypt(functions.clave,*/sharedPreff.getUser(context).toString()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos_list)
        /*Declaración de elementos visuales*/
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val back: ImageButton =findViewById(R.id.backProfile)

        val lista:ListView=findViewById(R.id.listaProductos)
        val showProducts:ImageButton=findViewById(R.id.listarProductos)
        val showReviews:ImageButton=findViewById(R.id.listarResenas)
        val adapterProductos = ProductAdapter(context,listProductos)
        val adapterResenas = reviewAdapter(context,listReviews)

        /*En este lo que hacemos es listar los productos del usuario*/
        showProducts.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                var newProducts:ArrayList<Product>
                withContext(Dispatchers.IO) {
                    newProducts = httPetitions.getProductos(User(DNI),sharedPreff.ipReal(context).toString())
                }
                if(newProducts.isEmpty()){
                    Toast.makeText(this@productosList,"Peticion denegada", Toast.LENGTH_SHORT).show()
                }else{
                    listProductos.clear()
                    listProductos.addAll(newProducts)
                    adapterProductos.notifyDataSetChanged()
                }
            }
            lista.adapter=adapterProductos
        }

        /*En este listamos las reseñas del usuario*/
        showReviews.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                var newReviews:ArrayList<Review>
                withContext(Dispatchers.IO) {
                    newReviews = httPetitions.getReviewsByUser(User(DNI))
                }
                if(newReviews.isEmpty()){
                    Toast.makeText(this@productosList,"Peticion denegada", Toast.LENGTH_SHORT).show()
                }else{
                    listReviews.clear()
                    listReviews.addAll(newReviews)
                    adapterResenas.notifyDataSetChanged()
                }
            }
            lista.adapter=adapterResenas
        }

        /*En este if else lo que hacemos es mirar que adaptador tiene y adaptar el onItemClick listner
        dependiendo del adaptador que tenga la lista
         */
            lista.setOnItemClickListener { parent, view, position, id ->
                if(lista.adapter==adapterProductos) {
                    val product = parent.getItemAtPosition(position) as Product
                    val intentDetails = Intent(this, DetailsProduct::class.java)
                    intentDetails.putExtra("product", product)
                    startActivity(intentDetails)
                }else {
                    val review=parent.getItemAtPosition(position) as Review
                    var done:Boolean
                    val builder: AlertDialog.Builder =
                        AlertDialog.Builder(this)/*Creamos el objeto diálogo*/
                    builder.setTitle("¿Cerrar sesión?")/*Establecemos el título, el mensaje principal y las dos opciones*/
                    builder.setMessage("¿Seguro que quieres cerrar sesión?")
                    builder.setPositiveButton("Si") { _, _ ->
                        lifecycleScope.launch(Dispatchers.Main) {
                            withContext(Dispatchers.IO) {
                                done = httPetitions.deleteReview(review)
                            }
                            if(!done){
                                Toast.makeText(this@productosList,"Peticion denegada", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this@productosList,"Resena eliminada", Toast.LENGTH_SHORT).show()
                                listReviews.remove(review)
                                adapterResenas.notifyDataSetChanged()
                            }
                        }
                    }
                    builder.setNegativeButton(("No"), { _, _ -> })
                    val dialog = builder.create()/*Lo construímos con las distintas partes*/
                    dialog.show()/*Lo mostramos*/
                }
            }

        back.setOnClickListener {
            val intentBack=Intent(this,perfilUser::class.java)
            startActivity(intentBack)
        }
    }
}