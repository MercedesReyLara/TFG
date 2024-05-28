package com.example.tfg

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
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
        val deleteButton:ImageButton=findViewById(R.id.editDelete)
        /*Declaracion de variables*/
        val context=baseContext
        val sharedPreff=SharedPreff(context)
        val functions=generalFunctions()
        val DNIu=sharedPreff.getUser(context).toString()
        /*val DNIRecuperado:String=functions.decrypt(functions.clave,sharedPreff.getUser(context).toString()).toString()*/
        var userData:User?= User()
        /*Hacemos la peticion*/
        lifecycleScope.launch (Dispatchers.IO){
            /*Buscamos el usuario a través del DNI guardado anteriormente en las preferencias*/
            userData=httPettitions.getUserByDNI(User(DNIu))
            withContext(Dispatchers.Main){
                if(userData!=null){
                    /*Cargamos los datos del usuario obtenido en cada campo correspondiente
                    * Hago la consulta otra vez para que si hemos cambiado los datos anteriormente se pueda actualizar*/
                    nombre.text=userData!!.nombreU
                    apellidos.text=userData!!.apellidosU
                    correo.text=userData!!.correo
                    DNI.text=userData!!.dni
                    about.text=userData!!.descripcion
                    /*val valorImagen=functions.byteArrayToBitmap(userData!!.profileP.toString().toByteArray())
                    Log.i("xd",valorImagen.toString())
                    profilePick.setImageBitmap(valorImagen)*/
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
            val builderOpciones: AlertDialog.Builder =
                AlertDialog.Builder(this)/*Creamos el objeto diálogo*/
            builderOpciones.setTitle("¿Que deseas hacer?")/*Establecemos el título, el mensaje principal y las dos opciones*/
            builderOpciones.setPositiveButton("Eliminar") { _, _ ->
                /*Nos abre un dialog para saber si queremos eliminar de verdad el usuario*/
                val builderEliminar: AlertDialog.Builder =
                    AlertDialog.Builder(this)/*Creamos el objeto diálogo*/
                builderEliminar.setTitle("¿Desactivar cuenta?")/*Establecemos el título, el mensaje principal y las dos opciones*/
                builderEliminar.setMessage("Si eliminas tu usuario no podrás volver a acceder a tu cuenta hasta volver a reactivarla")
                builderEliminar.setPositiveButton("Desactivar") { _, _ ->
                    /*Si le damos a desactivar se lanzará la petición que hará que nuestro usuario
                    se desactive y por lo tanto cerrará sesión
                     */
                    lifecycleScope.launch(Dispatchers.Main) {
                        var done:Boolean?=false
                       withContext(Dispatchers.IO){
                            done=httPettitions.deleteUser(DNIu)
                       }
                        when (done) {
                            null -> {
                                Toast.makeText(this@perfilUser,"error en la conexion",Toast.LENGTH_SHORT).show()
                                startActivity(functions.logOutFun(this@perfilUser))
                            }
                            true -> {
                                Toast.makeText(this@perfilUser,"afasd+",Toast.LENGTH_SHORT).show()
                                startActivity(functions.logOutFun(this@perfilUser))
                            }
                            else -> {
                                Toast.makeText(this@perfilUser,"ERROR",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                /*Si cancelamos no hace nada*/
                builderEliminar.setNegativeButton(("Cancelar"), { _, _ -> })
                val dialog = builderEliminar.create()/*Lo construímos con las distintas partes*/
                dialog.show()/*Lo mostramos*/
            }
            /*Nos redirige a la actividad de modificacion del usuario*/
            builderOpciones.setNegativeButton("Modificar") { _, _ ->
                val intentModify=Intent(this,modificaResena::class.java)
                startActivity(intentModify)
            }
            val dialog = builderOpciones.create()/*Lo construímos con las distintas partes*/
            dialog.show()/*Lo mostramos*/
        }
    }
}