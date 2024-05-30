package com.example.tfg

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tfg.petitionsAndFunctions.generalFunctions
import com.example.tfg.petitionsAndFunctions.httPettitions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.tfg.petitionsAndFunctions.SharedPreff
import model.User

class perfilUser : AppCompatActivity() {
    private val httPettitions= httPettitions()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details)
        /*Declaramos los elementos visuales*/
        val nombre:TextView=findViewById(R.id.nombreU)
        val apellidos:TextView=findViewById(R.id.puntuacionR)
        val correo:TextView=findViewById(R.id.emailUR)
        val DNI:TextView=findViewById(R.id.productoN)
        val about:TextView=findViewById(R.id.descripcionR)
        var profilePick:ImageView=findViewById(R.id.profilePD)
        val buttonBack:ImageButton=findViewById(R.id.backButton)
        val productList:ImageButton=findViewById(R.id.detailsR)
        val deleteButton:ImageButton=findViewById(R.id.editDelete)
        /*Declaracion de variables*/
        val context=baseContext
        val sharedPreff= SharedPreff(context)
        val functions= generalFunctions()
        val ip=sharedPreff.getIp(context)
        val DNIu=functions.decrypt(functions.clave,sharedPreff.getUser(context).toString()).toString()
        /*val DNIRecuperado:String=functions.decrypt(functions.clave,sharedPreff.getUser(context).toString()).toString()*/
        var userData:User?= User()
        /*Hacemos la peticion*/
        lifecycleScope.launch (Dispatchers.IO){
            /*Buscamos el usuario a través del DNI guardado anteriormente en las preferencias*/
            userData=httPettitions.getUserByDNI(User(DNIu),ip)
            withContext(Dispatchers.Main){
                if(userData!=null){
                    /*Cargamos los datos del usuario obtenido en cada campo correspondiente
                    * Hago la consulta otra vez para que si hemos cambiado los datos anteriormente se pueda actualizar*/
                    nombre.text=userData!!.nombreU
                    apellidos.text=userData!!.apellidosU
                    correo.text=userData!!.correo
                    DNI.text=userData!!.dni
                    about.text=userData!!.descripcion
                    profilePick.setImageBitmap(functions.stringToBitmap(userData!!.profileP))
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

        /*Este boton nos llevara a una pantalla que nos permitirá visualizar el "inventario" del usuario, es decir
        sus productos y las reseñas
         */
        productList.setOnClickListener {
            val intentProductos=Intent(this,productosList::class.java)
            startActivity(intentProductos)
        }

        /*Este boton hará la función tanto de eliminar como de modificar, es decir
        si yo le pulso a la opción eliminar, da de baja al usuario, si le pulso a modificar
        me redirige a la actividad de modificación del usuario
         */
        deleteButton.setOnClickListener {

            /*Nos redirige a la actividad de modificacion del usuario*/
                val intentModify=Intent(this,modificaResena::class.java)
                startActivity(intentModify)
            }

        }
    }
