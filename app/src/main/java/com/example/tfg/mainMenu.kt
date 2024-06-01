package com.example.tfg

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tfg.petitionsAndFunctions.generalFunctions

class mainMenu : AppCompatActivity() {
    private lateinit var context:Context
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

        //Declaración de variables que vamos a utilizar
        val functions= generalFunctions()
        val context:Context=baseContext
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