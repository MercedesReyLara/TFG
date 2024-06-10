package com.example.tfg

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.tfg.petitionsAndFunctions.SharedPreff
import com.example.tfg.petitionsAndFunctions.generalFunctions
import com.example.tfg.petitionsAndFunctions.httPettitions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Review
import model.User

class modifyUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_user)


        //Búsqueda de elementos visuales
        val modificar: Button =findViewById(R.id.modUser)
        val radioGroup: RadioGroup =findViewById(R.id.modificationRadioGroup)
        val text: EditText =findViewById(R.id.modificationEditText)
        val back: ImageButton =findViewById(R.id.backToMainButton)
        /*Declaraion de variables*/
        val functions= generalFunctions()
        val pettitions= httPettitions()
        val context: Context =baseContext
        val sharedPreff= SharedPreff(context)
        var nombre:Boolean=false
        var descripcion:Boolean=false
        var contrasena:Boolean=false
        var userModificado: User = User()
        val ip=sharedPreff.getIp(context)
        /*Recuperamos la resena que he mandado a través del intent y hacemos el split
        * para poder coger cada una de las partes y poder crear un objeto reseña con la nueva modificacion*/
        text.isVisible=false
        functions.clearHint(listOf(text), listOf(text.hint))
        val userParts=intent.getStringExtra("user").toString().split("/")
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            val selectedOption = radioButton.text
            when(selectedOption){
                "Modificar nome de usuario","Modificar nombre de usuario" ->{
                    text.hint=this.getString(R.string.nNombreUser)
                    text.isVisible=true
                    nombre=true
                    descripcion=false
                    contrasena=false
                }
                "Modificar descripción","Modificar descrición" ->{
                    text.hint=this.getString(R.string.nDescripcion)
                    text.isVisible=true
                    descripcion=true
                    nombre=false
                    contrasena=false
                }
                "Modificar contraseña","Modificar contrasinal" ->{
                    text.hint=this.getString(R.string.nContrasena)
                    text.isVisible=true
                    contrasena=true
                    nombre=false
                    descripcion=false
                }
            }
        }
        functions.clearHint(listOf(text), listOf(text.hint))
        /*Comprobaremos cual de los botones esta on click y formaremos el objeto con ese texto modificado*/
        modificar.setOnClickListener {
            val texto:String=text.text.toString()
            if(nombre){
                if(texto.isEmpty()) {
                    Toast.makeText(this, this.getString(R.string.errorVacios), Toast.LENGTH_SHORT)
                        .show()
                    }else{
                    userModificado= User(userParts[0],texto,userParts[2],userParts[3],userParts[4],
                        userParts[5],userParts[6].toByteArray(),true)
                }
            }else if(descripcion){
                if(texto.isEmpty()){
                    Toast.makeText(this,this.getString(R.string.errorVacios), Toast.LENGTH_SHORT).show()
                }else{
                    userModificado= User(userParts[0],userParts[1],userParts[2],userParts[3],userParts[4],
                        texto,userParts[6].toByteArray(),true)
                }
            }else if(contrasena){
                if(texto.isEmpty()){
                    Toast.makeText(this,this.getString(R.string.errorVacios), Toast.LENGTH_SHORT).show()
                }else if(!functions.validatePassword(texto)){
                    Toast.makeText(this,this.getString(R.string.errorContraseña),Toast.LENGTH_SHORT).show()
                }else{
                    userModificado= User(userParts[0],userParts[1],userParts[2],userParts[3],texto,
                        userParts[5],userParts[6].toByteArray(),true)
                }
            }
            lifecycleScope.launch (Dispatchers.Main){
                var done:Boolean?=false
                withContext(Dispatchers.IO){
                    done= pettitions.modifyUser(userModificado,ip)
                }
                /*Una vez hecha la peticion con la modificacion, manejamos lo que nos devuelve*/
                when(done){
                    null->{
                        Toast.makeText(this@modifyUser,this@modifyUser.getString(R.string.problemas)
                            , Toast.LENGTH_SHORT).show()
                    }
                    true->{
                        Toast.makeText(this@modifyUser,this@modifyUser.getString(R.string.modU), Toast.LENGTH_SHORT).show()
                        val intentLista= Intent(this@modifyUser,perfilUser::class.java)
                        text.text.clear()
                        Thread.sleep(1000)
                        startActivity(intentLista)
                    }
                    false->{
                        Toast.makeText(this@modifyUser,this@modifyUser.getString(R.string.errorMOD), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        back.setOnClickListener {
            val intentLista= Intent(this,perfilUser::class.java)
            startActivity(intentLista)
        }
    }
}