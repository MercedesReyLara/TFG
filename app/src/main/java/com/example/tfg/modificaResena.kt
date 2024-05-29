package com.example.tfg

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.circularreveal.CircularRevealWidget.RevealInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Review

class modificaResena : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificar_resena)

        //Búsqueda de elementos visuales
        val modificar: Button =findViewById(R.id.eliminarRes)
        val radioGroup:RadioGroup=findViewById(R.id.radioGroup)
        val text:EditText=findViewById(R.id.textModificacion)
        val back:ImageButton=findViewById(R.id.backToMainD)
        /*Declaraion de variables*/
        val functions=generalFunctions()
        val pettitions=httPettitions()
        var nombre:Boolean=false
        var desc:Boolean=false
        var punt:Boolean=false
        var resenaModificada:Review=Review()
        /*Recuperamos la resena que he mandado a través del intent y hacemos el split
        * para poder coger cada una de las partes y poder crear un objeto reseña con la nueva modificacion*/
        text.isVisible=false
        val resenaParts=intent.getStringExtra("resena").toString().split("/")
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            val selectedOption = radioButton.text
            when(selectedOption){
                "Modificar nome","Modificar nombre" ->{
                    text.hint="Introduce "
                    text.isVisible=true
                    nombre=true
                    desc=false
                    punt=false
                }
               "Modificar descripcion" ->{
                   desc=true
                   nombre=false
                   punt=false
               }
               "Modificar puntuacion" ->{
                   punt=true
                   nombre=false
                   desc=false
               }
            }
        }
        /*Comprobaremos cual de los botones esta on click y formaremos el objeto con ese texto modificado*/
        modificar.setOnClickListener {
            val texto:String=text.text.toString()
            if(nombre){
                if(texto.isEmpty()){
                    Toast.makeText(this,"El texto esta vacio",Toast.LENGTH_SHORT).show()
                }else{
                    resenaModificada= Review(resenaParts[0].toInt(),texto,resenaParts[2],
                        resenaParts[3].toInt(),resenaParts[4],resenaParts[5])
                }
            }else if(desc){
                if(texto.isEmpty()){
                    Toast.makeText(this,"El texto esta vacio",Toast.LENGTH_SHORT).show()
                }else{
                    resenaModificada= Review(resenaParts[0].toInt(),resenaParts[1],texto,
                        resenaParts[3].toInt(),resenaParts[4],resenaParts[5])
                }
            }else if(punt){
                if(texto.isEmpty()){
                    Toast.makeText(this,"El texto esta vacio",Toast.LENGTH_SHORT).show()
                }else if(!functions.isInt(texto)||texto.toInt()<0||texto.toInt()>10){
                    Toast.makeText(this,"El texto tiene que ser un entero entre 1 y 10",Toast.LENGTH_SHORT).show()
                }else{
                    resenaModificada= Review(resenaParts[0].toInt(),resenaParts[1],resenaParts[2],
                        texto.toInt(),resenaParts[4],resenaParts[5])
                }
            }
           lifecycleScope.launch (Dispatchers.Main){
               var done:Boolean?=false
               withContext(Dispatchers.IO){
                  done= pettitions.modifyReview(resenaModificada)
               }
               /*Una vez hecha la peticion con la modificacion, manejamos lo que nos devuelve*/
               when(done){
                   null->{
                       Toast.makeText(this@modificaResena,"Error en la conexion",Toast.LENGTH_SHORT).show()
                   }
                   true->{
                       Toast.makeText(this@modificaResena,"Reseña modificada con exito",Toast.LENGTH_SHORT).show()
                       val intentLista=Intent(this@modificaResena,productosList::class.java)
                       Thread.sleep(1000)
                       startActivity(intentLista)
                   }
                   false->{
                       Toast.makeText(this@modificaResena,"Error durante la modificacion",Toast.LENGTH_SHORT).show()
                   }
               }
           }
        }
        back.setOnClickListener {
            val intentLista= Intent(this,productosList::class.java)
            startActivity(intentLista)
        }
    }
}