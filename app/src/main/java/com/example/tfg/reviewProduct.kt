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
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tfg.petitionsAndFunctions.generalFunctions
import com.example.tfg.petitionsAndFunctions.httPettitions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Product
import model.Review
import com.example.tfg.petitionsAndFunctions.SharedPreff
import model.User

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
        val functions= generalFunctions()
        val sharedPreff= SharedPreff(context)
        val pettitions= httPettitions()
        val ip=sharedPreff.getIp(context)
        val nombresProductos = arrayListOf("-")
        val hintNombre=nombre.hint
        val hintDescripcion=descripcion.hint
        val hintPuntuacion=puntuacion.hint
        /*Obtenemos el DNI del usuario a través de las sharedpreferences*/
        val DNI:String=functions.decrypt(functions.clave,sharedPreff.getUser(context).toString()).toString()
        functions.clearHint(listOf(nombre,descripcion,puntuacion),
            listOf (hintNombre,hintDescripcion,hintPuntuacion)
        )
        lifecycleScope.launch(Dispatchers.Main) {
            var products: ArrayList<Product>?
            withContext(Dispatchers.IO) {
                /*Obtenemos los productos de ese usuario en especifico*/
                products = pettitions.getProductos(User(DNI),ip)!!
            }
            if(products==null){
                Toast.makeText(this@reviewProduct, this@reviewProduct.getString(R.string.problemas)
                    , Toast.LENGTH_SHORT).show()
            } else if (products!!.isEmpty()) {
                Toast.makeText(this@reviewProduct,  this@reviewProduct.getString(R.string.errorObtencion)
                    , Toast.LENGTH_SHORT).show()
            } else {
                for(p in products!!){
                    /*Metemos en la lista de nombres de productos para el spinner los nombres de los productos
                    que tiene el usuario
                     */
                    nombresProductos.add(p.nombreP)
                }
            }
        }
        /*Creamos el adapter para obtener los productos que tiene ese usuario unicamente*/
        val adapter: ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_spinner_item,nombresProductos)
        spinnerProductos.adapter=adapter
        spinnerProductos.setSelection(0)

        publicar.setOnClickListener {
            val nombreTXT=nombre.text.toString()
            val descripcionTXT=descripcion.text.toString()
            val puntuacionTXT=puntuacion.text.toString()

            /*Comprobamos que no haya ningun campo vacio*/
            if(nombreTXT.isEmpty()||descripcionTXT.isEmpty()||puntuacionTXT.isEmpty()){
                Toast.makeText(this@reviewProduct, "Los campos no puden estar vacios", Toast.LENGTH_SHORT).show()
            }else if(!functions.isInt(puntuacionTXT)) {
                Toast.makeText(this@reviewProduct, "El valor puntuacion tiene que ser un número entero", Toast.LENGTH_SHORT).show()
            }else if(puntuacionTXT.toInt()<0 || puntuacionTXT.toInt()>10) {
                Toast.makeText(
                    this@reviewProduct,
                    "El valor puntuacion tiene que ser un número entero entre el 1 y e 10",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                /*Creamos el objeto review*/
                val review= Review(nombreTXT,descripcionTXT,puntuacionTXT.toInt(),DNI,spinnerProductos.selectedItem.toString())
                lifecycleScope.launch(Dispatchers.Main) {
                    var done:Boolean?=false
                    withContext(Dispatchers.IO){
                        /*Hacemos la peticion para publicar la reseña*/
                        done=pettitions.postReview(review,ip)
                    }
                    when (done) {
                        null -> {
                            Toast.makeText(
                                this@reviewProduct,
                                "error en la conexion",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(functions.logOutFun(this@reviewProduct))
                        }

                        true -> {
                            Toast.makeText(this@reviewProduct, "Publicacion realizada", Toast.LENGTH_SHORT).show()
                            functions.manipulateEdits(listOf(nombre,descripcion,puntuacion))
                            functions.clearText(listOf(nombre,descripcion,puntuacion))
                        }

                        else -> {
                            Toast.makeText(this@reviewProduct, "ERROR", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        /*Nos lleva al menú principal*/
        back.setOnClickListener {
            val intentMain= Intent(this,mainMenu::class.java)
            startActivity(intentMain)
        }
    }
}