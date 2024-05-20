package com.example.tfg

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.SharedPreff
import model.User

class perfilUser : AppCompatActivity() {
    private val httPettitions=httPettitions()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details)
        /*Declaramos los elementos visuales*/
        val nombre:TextView=findViewById(R.id.nombreU)
        val apellidos:TextView=findViewById(R.id.apellidos)
        val correo:TextView=findViewById(R.id.emailUser)
        val DNI:TextView=findViewById(R.id.DNIDetalles)
        val about:TextView=findViewById(R.id.descripcionU)
        var profilePick:ImageView=findViewById(R.id.profilePD)
        val buttonBack:ImageButton=findViewById(R.id.backButton)
        val productList:ImageButton=findViewById(R.id.productosList)

        /*Declaracion de variables*/
        val context=baseContext
        val sharedPreff=SharedPreff(context)
        val functions=generalFunctions()
        val DNIRecuperado:String=functions.decrypt(functions.clave,sharedPreff.getUser(context).toString()).toString()
        var userData:User?= User()
        /*Hacemos la peticion*/
        lifecycleScope.launch (Dispatchers.IO){
            userData=httPettitions.getUserByDNI(DNIRecuperado)
            withContext(Dispatchers.Main){
                if(userData!=null){
                    nombre.text=userData!!.nombreU
                    apellidos.text=userData!!.apellidosU
                    correo.text=userData!!.correo
                    DNI.text=userData!!.dni
                    about.text=userData!!.descripcion
                }else{
                    Toast.makeText(this@perfilUser,"Error en la obtencion de datos",Toast.LENGTH_SHORT).show()
                }

            }
        }
        /*Boton para volver al menu principal*/
        buttonBack.setOnClickListener {
            val intentMain= Intent(this,mainMenu::class.java)
            startActivity(intentMain)
        }

        productList.setOnClickListener {
            val intentProductos=Intent(this,productosList::class.java)
            startActivity(intentProductos)
        }
    }
}