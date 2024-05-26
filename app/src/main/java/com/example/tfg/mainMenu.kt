package com.example.tfg

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import model.SharedPreff

class mainMenu : AppCompatActivity() {
    private lateinit var context:Context
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context=baseContext
        setContentView(R.layout.activity_pantalla_principal)

        //Búsqueda de elementos visuales
        val eliminar: Button =findViewById(R.id.eliminarResena)
        val listar:Button=findViewById(R.id.listaResenasGlobal)
        val subir:Button=findViewById(R.id.subirResena)
        val perfilU:ImageButton=findViewById(R.id.botonPerfilU)
        val cerrarSesion:ImageButton=findViewById(R.id.pechar)
        val ajustesButton:ImageButton=findViewById(R.id.ajustes)

        //Declaración de variables que vamos a utilizar


         ajustesButton.setOnClickListener {
             val intentAjustes=Intent(this,ajustes::class.java)
             startActivity(intentAjustes)
         }
        cerrarSesion.setOnClickListener {
            val builder: AlertDialog.Builder =
                AlertDialog.Builder(this)/*Creamos el objeto diálogo*/
            builder.setTitle("¿Cerrar sesión?")/*Establecemos el título, el mensaje principal y las dos opciones*/
            builder.setMessage("¿Seguro que quieres cerrar sesión?")
            builder.setPositiveButton("Si") { _, _ ->
                logOutFun()
            }
            builder.setNegativeButton(("No"), { _, _ -> })
            val dialog = builder.create()/*Lo construímos con las distintas partes*/
            dialog.show()/*Lo mostramos*/
        }

        perfilU.setOnClickListener {
            val intentPerfil=Intent(this,perfilUser::class.java)
            startActivity(intentPerfil)
        }

        listar.setOnClickListener {
            val intentResenas=Intent(this,listaResenas::class.java)
            startActivity(intentResenas)
        }

        eliminar.setOnClickListener {
            val intentEliminar=Intent(this,listaResenas::class.java)
            startActivity(intentEliminar)
        }

        subir.setOnClickListener {
            val intentResenar=Intent(this,reviewProduct::class.java)
            startActivity(intentResenar)
        }
    }
    fun logOutFun() {
        /*Esta función nos redirige a la pantalla principal
         Si le damos al botón establece el login como false y nos pide introducir nuestros datos de nuevo*/
        val sharedPreff = SharedPreff(context)
        sharedPreff.saveLogin(context, false)
        /*sharedPreff.savePressed(context,false)*/
        val intent = Intent(this, logIn::class.java)
        startActivity(intent)
    }
}