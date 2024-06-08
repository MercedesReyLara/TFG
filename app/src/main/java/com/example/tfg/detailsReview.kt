package com.example.tfg

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tfg.petitionsAndFunctions.SharedPreff
import com.example.tfg.petitionsAndFunctions.httPettitions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Review
import model.User

class detailsReview : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_review)

        /*Declaracion de elementos visuales*/
        val nombre: TextView =findViewById(R.id.nombreReview)
        val puntuacion: TextView =findViewById(R.id.puntuacionReview)
        val user: TextView =findViewById(R.id.userName)
        val producto: TextView =findViewById(R.id.productoNombre)
        val about: TextView =findViewById(R.id.descripcionReview)
        val puntuaciones:TextView=findViewById(R.id.puntuaciones)
        val back: ImageButton =findViewById(R.id.backButton)

        /*Elementos de uso*/
        val context: Context =baseContext
        val peticiones=httPettitions()
        val preferencias=SharedPreff(context)
        val partes=preferencias.getReview(context).split("/")
        nombre.text=partes[1]
        nombre.setTextColor(Color.BLACK)
        puntuacion.text=partes[3]
        puntuacion.setTextColor(Color.BLACK)
        user.text=partes[6]
        user.setTextColor(Color.BLACK)
        producto.text=partes[5]
        producto.setTextColor(Color.BLACK)
        about.text=partes[2]
        about.setTextColor(Color.BLACK)
        puntuaciones.setTextColor(Color.BLACK)
        if(partes[3].toInt()>5)  {
            puntuaciones.text=this.getString(R.string.positiva)
        }else{
           puntuaciones.text=this.getString(R.string.negativa)
        }
        back.setOnClickListener {
           val intentMain= Intent(this,listaResenas::class.java)
            startActivity(intentMain)
        }

        user.setOnClickListener {
            var userR:User?=User()
            lifecycleScope.launch(Dispatchers.Main) {
                withContext(Dispatchers.IO){
                 userR=peticiones.getUserByNombre(User(partes[6],0),preferencias.getIp(context))
                }
                if(userR==null){
                        Toast.makeText(this@detailsReview,this@detailsReview.getString(R.string.problemas)
                            ,Toast.LENGTH_SHORT).show()
                }else{
                    val detailsOtherUser=Intent(this@detailsReview,detailsOtherUser::class.java)
                    detailsOtherUser.putExtra("usuario",userR.toString())
                    detailsOtherUser.putExtra("image",userR!!.profileP)
                    startActivity(detailsOtherUser)
                }
            }
        }
    }
}