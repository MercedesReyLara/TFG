package com.example.tfg

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {

        /*Esta activity la voy a usar tanto como para mostrar productos como reseñas, voy a reutilizar un listview
        Haré 2 botones, cuando pulse no hará la petición de productos y cuando pulse el otro de reseñas
         */
        val functions=generalFunctions()
        val context: Context =baseContext
        val sharedPreff=SharedPreff(context)
        val DNI:String=functions.decrypt(functions.clave,sharedPreff.getUser(context).toString()).toString()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productos_list)
        /*Declaración de elementos visuales*/

        val back: ImageButton =findViewById(R.id.backProfile)

        val lista:ListView=findViewById(R.id.listaProductos)

        val showProducts:ImageButton=findViewById(R.id.listarProductos)
        val showReviews:ImageButton=findViewById(R.id.listarResenas)
        val adapterProductos = ProductAdapter(context,listProductos)
        val adapterResenas = reviewAdapter(context,listReviews)
        val ip=sharedPreff.getIp(context)
        /*En este lo que hacemos es listar los productos del usuario*/
        showProducts.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                var newProducts:ArrayList<Product>?
                withContext(Dispatchers.IO) {
                    newProducts = httPetitions.getProductos(User(DNI),ip)!!
                }
                if(newProducts==null) {
                    Toast.makeText(
                        this@productosList,
                        this@productosList.getString(R.string.problemas), Toast.LENGTH_SHORT
                    ).show()
                }else if(newProducts!!.isEmpty()){
                    Toast.makeText(
                        this@productosList,
                        this@productosList.getString(R.string.errorObtencion),
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    listProductos.clear()
                    listProductos.addAll(newProducts!!)
                    adapterProductos.notifyDataSetChanged()
                }
            }
            lista.adapter = adapterProductos
        }

        /*En este listamos las reseñas del usuario*/
        showReviews.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                var newReviews:ArrayList<Review>?
                withContext(Dispatchers.IO) {
                    newReviews = httPetitions.getReviewsByUser(User(DNI),ip)!!
                }
                if(newReviews==null) {
                    Toast.makeText(
                        this@productosList,
                        this@productosList.getString(R.string.problemas), Toast.LENGTH_SHORT
                    ).show()
                }else if(newReviews!!.isEmpty()){
                    Toast.makeText(
                        this@productosList,
                        this@productosList.getString(R.string.errorObtencion),
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    listReviews.clear()
                    listReviews.addAll(newReviews!!)
                    adapterProductos.notifyDataSetChanged()
                }
            }
            lista.adapter=adapterResenas
        }

        /*En este if else lo que hacemos es mirar que adaptador tiene y adaptar el onItemClick listner
        dependiendo del adaptador que tenga la lista
         */
            lista.setOnItemClickListener { parent, view, position, id ->
                if(lista.adapter==adapterProductos) {
                    /*En este caso iremos a una pantalla para ver los detalles del producto*/
                    val product = parent.getItemAtPosition(position) as Product
                    val intentDetails = Intent(this, DetailsProduct::class.java)
                    intentDetails.putExtra("product", product.toString())
                    startActivity(intentDetails)
                }else {
                    /*En este otro, si pulsamos la opcion 1 eliminaremos esa reseña, y si pulsamos la 2 iremos
                    a un menú de modificación de la reseña
                     */
                    val review=parent.getItemAtPosition(position) as Review
                    var done:Boolean?
                    val builder: AlertDialog.Builder =
                        AlertDialog.Builder(this)/*Creamos el objeto diálogo*/
                    builder.setTitle("¿Que quieres hacer?")/*Establecemos el título, el mensaje principal y las dos opciones*/
                    builder.setMessage("Elige una opción")
                    builder.setPositiveButton("Eliminar reseña") { _, _ ->
                        lifecycleScope.launch(Dispatchers.Main) {
                            withContext(Dispatchers.IO) {
                                done = httPetitions.deleteReview(review.id,ip)!!
                            }
                            if(done==null){
                                Toast.makeText(this@productosList,this@productosList.getString(R.string.problemas),
                                    Toast.LENGTH_SHORT).show()
                            } else if(!done!!){
                                Toast.makeText(this@productosList,this@productosList.getString(R.string.errorObtencion),
                                    Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this@productosList,"Resena eliminada", Toast.LENGTH_SHORT).show()
                                listReviews.remove(review)
                                adapterResenas.notifyDataSetChanged()
                            }
                        }
                    }
                    builder.setNegativeButton("Modificar mi reseña") { _, _ ->
                        val intentModificar=Intent(this,modificaResena::class.java)
                        val resena=parent.getItemAtPosition(position) as Review
                        intentModificar.putExtra("resena",resena.toString())
                        startActivity(intentModificar)
                    }
                    builder.setIcon(R.drawable.imagen_2024_04_22_110039483_removebg_preview)
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