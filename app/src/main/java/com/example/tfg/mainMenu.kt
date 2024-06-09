package com.example.tfg

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.tfg.petitionsAndFunctions.httPettitions
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.tfg.petitionsAndFunctions.SharedPreff
import com.example.tfg.petitionsAndFunctions.generalFunctions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Product
import model.Review
import model.User

class mainMenu : AppCompatActivity() {
    private val notificactionID=1
    private val codigoPermisos = 111
    private lateinit var context:Context
    private var permisos=false
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context=baseContext
        setContentView(R.layout.activity_pantalla_principal)
        //Búsqueda de elementos visuales
        val productos: Button =findViewById(R.id.listarProductosR)
        val listar:Button=findViewById(R.id.listaResenasGlobal)
        val subir:Button=findViewById(R.id.subirResena)
        val perfilU:ImageButton=findViewById(R.id.botonPerfilU)
        val cerrarSesion:ImageButton=findViewById(R.id.pechar)
        val ajustesButton:ImageButton=findViewById(R.id.ajustes)
        val pettitions=httPettitions()
        val context:Context=baseContext
        val sharedPreff=SharedPreff(context)
        sharedPreff.savePermisos(context,false)
        val functions= generalFunctions()
        val cantidadResenas=sharedPreff.getNumResenas(context)
        val permisosPedidos=sharedPreff.getPermisos(context)
        val DNIu=functions.decrypt(functions.clave,sharedPreff.getUser(context).toString()).toString()
        val ip=sharedPreff.getIp(context)
        lifecycleScope.launch (Dispatchers.IO){
            /*Buscamos el usuario a través del DNI guardado anteriormente en las preferencias*/
            var reviews:ArrayList<Review>? = arrayListOf()
            reviews=pettitions.getAllReviews(ip)
            withContext(Dispatchers.Main){
                if(reviews==null){
                    Toast.makeText(this@mainMenu,this@mainMenu.getString(R.string.problemas),
                        Toast.LENGTH_SHORT).show()
                }else if(reviews.isEmpty()){
                    subir.isVisible=false
                }else{
                    sharedPreff.saveNumResenas(context,reviews.size)
                }
            }
        }
        //Declaración de variables que vamos a utilizar
        /*Establecemos el idioma porque al salir de la app se resetea
        entonces buscamos en las preferencias el codigo y aplicamos el idioma
        Se hace tanto en el log in como en el main por si cerramos sesión y cerramos la app
        o bien cerramos la app sin cerrar sesion*/
        functions.setLanguage(context)

        /*Este boton nos lleva a los ajustes para cambiar la ip*/
         ajustesButton.setOnClickListener {
             val intentAjustes=Intent(this,ajustes::class.java)
             startActivity(intentAjustes)
         }

        /*Este boton ejecuta una función que cierra sesión, lo que hace que te lleve al log in
        y que tengas que volver a intoducir las credenciales*/
        cerrarSesion.setOnClickListener {
            val builder: AlertDialog.Builder =
                AlertDialog.Builder(this)/*Creamos el objeto diálogo*/
            builder.setTitle(this.getString(R.string.cerrarSesion))/*Establecemos el título, el mensaje principal y las dos opciones*/
            builder.setMessage(this.getString(R.string.cerrarSesionD))
            builder.setPositiveButton("Si") { _, _ ->
                startActivity(functions.logOutFun(this@mainMenu))
            }
            builder.setNegativeButton(("No"), { _, _ -> })
            val dialog = builder.create()/*Lo construímos con las distintas partes*/
            dialog.show()/*Lo mostramos*/
        }

        /*Va hacia el perfil del usuario*/
        perfilU.setOnClickListener {
            val intentPerfil=Intent(this,perfilUser::class.java)
            startActivity(intentPerfil)
        }

        /*Va hacia la lista de TODAS las reseñas*/
        listar.setOnClickListener {
            val intentResenas=Intent(this,listaResenas::class.java)
            startActivity(intentResenas)
        }

        /*Va hacia la lista de los productos*/
        productos.setOnClickListener {
            val intentProductsList=Intent(this,allProductList::class.java)
            startActivity(intentProductsList)
        }

        /*Va al menú de subida de una reseña*/
        subir.setOnClickListener {
            val intentResenar=Intent(this,reviewProduct::class.java)
            startActivity(intentResenar)
        }
    }
}