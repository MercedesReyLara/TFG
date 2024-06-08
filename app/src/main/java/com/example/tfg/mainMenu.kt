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
        val cantidadProductos=0
        val permisosPedidos=sharedPreff.getPermisos(context)
        val DNIu=functions.decrypt(functions.clave,sharedPreff.getUser(context).toString()).toString()
        val ip=sharedPreff.getIp(context)
        lifecycleScope.launch (Dispatchers.IO){
            /*Buscamos el usuario a través del DNI guardado anteriormente en las preferencias*/
            var products:ArrayList<Product>? = arrayListOf()
            products=pettitions.getProductos(User(DNIu),ip)
            withContext(Dispatchers.Main){
                if(products==null){
                    Toast.makeText(this@mainMenu,this@mainMenu.getString(R.string.problemas),
                        Toast.LENGTH_SHORT).show()
                }else if(products.isEmpty()){
                    subir.isVisible=false
                }else{
                    sharedPreff.saveNumProductos(context,products.size)
                }
            }
        }
        if(cantidadProductos<sharedPreff.getNumProductos(context)){
            if(!permisosPedidos){
                requestAllPermissions()
                channel()
                if(permisos){
                    notification()
                }
            }else{
                channel()
                if(permisos){
                    notification()
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

    @SuppressLint("MissingPermission")
    private fun notification() {
        var builderN = NotificationCompat.Builder(this, "channel")
            .setSmallIcon(R.drawable.imagen_2024_04_22_110039483_removebg_preview)
            .setContentTitle("RESEÑE SU NUEVO PRODUCTO!!")
            .setStyle(NotificationCompat.BigTextStyle().bigText(("Gracias por comprar un producto. Por favor, " +
                    "deje su reseña en nuestro foro para ayudarnos a mejorar")))
            .setAutoCancel(true)
        /*val intent=Intent(this,workButton::class.java)
        val pendingIntent= PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_UPDATE_CURRENT)
        builderN.setContentIntent(pendingIntent)*/
        /*Nos sirve para volver a la app una vez que clickamos(tendría que ver como conservar el estado de la app al salirte
        */
        with(NotificationManagerCompat.from(this)) {
            notify(notificactionID, builderN.build())
        }
    }
    fun channel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chanel = NotificationChannel(
                "channel",
                "My Channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "A"
            }
            val notificationManager: NotificationManager =getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
            notificationManager.createNotificationChannel(chanel)
        }

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestAllPermissions() {
            val permissionsNeeded = arrayOf(
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.VIBRATE
            )

            val permissionsToRequest = permissionsNeeded.filter {
                ContextCompat.checkSelfPermission(
                    this,
                    "Acepte los permisos"
                ) != PackageManager.PERMISSION_GRANTED
            }

            if (permissionsToRequest.isNotEmpty()) {
                ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toTypedArray(),
                    codigoPermisos
                )
            }
        }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == codigoPermisos) {
            val permissionsGranted = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
            if (permissionsGranted) {
                permisos=true
            } else {
                permisos=false
            }
        }
    }
}