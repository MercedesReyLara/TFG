package com.example.tfg

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
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
        var userData:User?= User()
        /*Hacemos la peticion*/
        lifecycleScope.launch (Dispatchers.IO){
            /*Buscamos el usuario a través del DNI guardado anteriormente en las preferencias*/
            userData=httPettitions.getUserByDNI(User(DNIu),ip)
            withContext(Dispatchers.Main){
                if(userData!=null){
                    /*Cargamos los datos del usuario obtenido en cada campo correspondiente
                    * Hago la consulta otra vez para que si hemos cambiado los datos anteriormente se pueda actualizar*/
                    nombre.setTextColor(Color.BLACK)
                    nombre.text=userData!!.nombreU
                    apellidos.setTextColor(Color.BLACK)
                    apellidos.text=userData!!.apellidosU
                    correo.setTextColor(Color.BLACK)
                    correo.text=userData!!.correo
                    DNI.setTextColor(Color.BLACK)
                    DNI.text=userData!!.dni
                    about.setTextColor(Color.BLACK)
                    about.text=userData!!.descripcion
                    profilePick.setImageBitmap(functions.byteArrayToBitmap(userData!!.profileP))
                }else{
                    Toast.makeText(this@perfilUser,this@perfilUser.getString(R.string.problemas),Toast.LENGTH_SHORT).show()
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
                val intentModify=Intent(this,modifyUser::class.java)
                intentModify.putExtra("user",userData.toString())
                startActivity(intentModify)
            }

        }
    }
