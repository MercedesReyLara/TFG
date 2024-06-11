package com.example.tfg

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.tfg.petitionsAndFunctions.generalFunctions
import com.example.tfg.petitionsAndFunctions.httPettitions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Review
import com.example.tfg.petitionsAndFunctions.SharedPreff

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
        val functions= generalFunctions()
        val pettitions= httPettitions()
        val context: Context =baseContext
        val sharedPreff= SharedPreff(context)
        var nombre:Boolean=false
        var desc:Boolean=false
        var punt:Boolean=false
        var resenaModificada:Review=Review()
        val ip=sharedPreff.getIp(context)
        /*Recuperamos la resena que he mandado a través del intent y hacemos el split
        * para poder coger cada una de las partes y poder crear un objeto reseña con la nueva modificacion*/
        text.isVisible=false
        val resenaParts=intent.getStringExtra("resena").toString().split("/")
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            val selectedOption = radioButton.text
            when(selectedOption){
                "Modificar nome","Modificar nombre" ->{
                    text.hint=this.getString(R.string.nN)
                    text.isVisible=true
                    nombre=true
                    desc=false
                    punt=false
                }
               "Modificar descrición","Modificar descripción"->{
                   text.hint=this.getString(R.string.nD)
                   text.isVisible=true
                   desc=true
                   nombre=false
                   punt=false
               }
               "Modificar puntuación" ->{
                   text.hint=this.getString(R.string.nP)
                   text.isVisible=true
                   punt=true
                   nombre=false
                   desc=false
               }
            }
        }
        functions.clearHint(listOf(text), listOf(text.hint))
        /*Comprobaremos cual de los botones esta on click y formaremos el objeto con ese texto modificado*/
        modificar.setOnClickListener {
            val texto:String=text.text.toString()
            if(nombre){
                if(texto.isEmpty()){
                    Toast.makeText(this,this.getString(R.string.errorVacios),Toast.LENGTH_SHORT).show()
                }else{
                    resenaModificada= Review(resenaParts[0].toInt(),texto,resenaParts[2],
                        resenaParts[3].toInt(),resenaParts[4],resenaParts[5])
                }
            }else if(desc){
                if(texto.isEmpty()){
                    Toast.makeText(this,this.getString(R.string.errorVacios),Toast.LENGTH_SHORT).show()
                }else{
                    resenaModificada= Review(resenaParts[0].toInt(),resenaParts[1],texto,
                        resenaParts[3].toInt(),resenaParts[4],resenaParts[5])
                }
            }else if(punt){
                if(texto.isEmpty()){
                    Toast.makeText(this,this.getString(R.string.errorVacios),Toast.LENGTH_SHORT).show()
                }else if(!functions.isInt(texto)){
                    Toast.makeText(this,this.getString(R.string.erroPuntuacion),Toast.LENGTH_SHORT).show()
                }else if(texto.toInt()<1||texto.toInt()>10) {
                    Toast.makeText(this,this.getString(R.string.erroPuntuacion),Toast.LENGTH_SHORT).show()
                }else{
                    resenaModificada= Review(resenaParts[0].toInt(),resenaParts[1],resenaParts[2],
                        texto.toInt(),resenaParts[4],resenaParts[5])
                }
            }
           lifecycleScope.launch (Dispatchers.Main){
               var done:Boolean?=false
               withContext(Dispatchers.IO){
                  done= pettitions.modifyReview(resenaModificada,ip)
               }
               /*Una vez hecha la peticion con la modificacion, manejamos lo que nos devuelve*/
               when(done){
                   null->{
                       Toast.makeText(this@modificaResena,this@modificaResena.getString(R.string.problemas)
                           ,Toast.LENGTH_SHORT).show()
                   }
                   true->{
                       Toast.makeText(this@modificaResena,this@modificaResena.getString(R.string.modR),Toast.LENGTH_SHORT).show()
                       val intentLista=Intent(this@modificaResena,productosList::class.java)
                       text.text.clear()
                       Thread.sleep(1000)
                       startActivity(intentLista)
                   }
                   false->{
                       Toast.makeText(this@modificaResena,this@modificaResena.getString(R.string.errorMOD),Toast.LENGTH_SHORT).show()
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