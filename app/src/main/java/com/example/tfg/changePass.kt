package com.example.tfg

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.tfg.petitionsAndFunctions.SharedPreff
import com.example.tfg.petitionsAndFunctions.generalFunctions
import com.example.tfg.petitionsAndFunctions.httPettitions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.User

class changePass : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pass)

        //Declaracion de elementos visuales
        val nombre:EditText=findViewById(R.id.nombreV)
        val contrasena:EditText=findViewById(R.id.newPass)
        val contrasenaConf:EditText=findViewById(R.id.newPassConf)
        val buscar: Button =findViewById(R.id.searchN)
        val confiCambio:Button=findViewById(R.id.changePass)
        val back:ImageButton=findViewById(R.id.backMainP)
        val textC:TextView=findViewById(R.id.textView24)
        /*Declaracion de variables de uso*/
        val contexto: Context =baseContext
        val functions= generalFunctions()
        val pettitions= httPettitions()
        val sharedPreff= SharedPreff(contexto)
        val ip=sharedPreff.getIp(contexto)

        /*Ponemos invisibles los campos de contraseña para que solo se vean si existe el usuario*/
        contrasena.isVisible=false
        contrasenaConf.isVisible=false
        confiCambio.isVisible=false
        textC.isVisible=false
        var encontrado:User?=User()
        var user:String=""
        functions.clearHint(listOf(nombre,contrasena,contrasenaConf), listOf(nombre.hint,contrasena.hint,contrasenaConf.hint))
        buscar.setOnClickListener {
            val nombreText=nombre.text.toString()
            if(nombreText.isEmpty()){
                Toast.makeText(this,this.getString(R.string.errorVacios),Toast.LENGTH_SHORT).show()
            }else{
                lifecycleScope.launch(Dispatchers.Main) {
                    withContext(Dispatchers.IO){
                          encontrado=pettitions.getUserByCorreo(User(nombreText,true),ip)
                        }
                    if(encontrado==null){
                        Toast.makeText(this@changePass,this@changePass.getString(R.string.problemas),Toast.LENGTH_SHORT).show()
                    }else if(encontrado.toString().isEmpty()){
                        Toast.makeText(this@changePass,this@changePass.getString(R.string.errorObtencion),Toast.LENGTH_SHORT).show()
                    }else{
                        contrasena.isVisible=true
                        contrasenaConf.isVisible=true
                        confiCambio.isVisible=true
                        textC.isVisible=true
                        user=encontrado.toString()
                    }
                    }
                }
            }
        confiCambio.setOnClickListener {
            val contrasenaText=contrasena.text.toString()
            val confContrasena=contrasenaConf.text.toString()
            if(!functions.validatePassword(contrasenaText)){
                Toast.makeText(this,this.getString(R.string.errorContraseña),Toast.LENGTH_SHORT).show()
            }else if(contrasenaText!=confContrasena){
                Toast.makeText(this,this.getString(R.string.coincidir),Toast.LENGTH_SHORT).show()
            }else{
                val parts=user.split("/")
                val userAModificar=User(parts[0],parts[1],parts[2],parts[3],parts[4],
                    parts[5],parts[6].toByteArray(),true)
                userAModificar.contrasena=contrasenaText
                lifecycleScope.launch(Dispatchers.Main) {
                    var cambiado:Boolean?=false
                    withContext(Dispatchers.IO){
                        cambiado=pettitions.modifyUser(userAModificar,ip)
                    }
                    when (cambiado) {
                        null -> {
                            Toast.makeText(this@changePass,this@changePass.getString(R.string.problemas),Toast.LENGTH_SHORT).show()
                        }
                        false -> {
                            Toast.makeText(this@changePass,this@changePass.getString(R.string.errorObtencion),Toast.LENGTH_SHORT).show()
                        }
                        true -> {
                            Toast.makeText(this@changePass,this@changePass.getString(R.string.cambiadaC),Toast.LENGTH_SHORT).show()
                            functions.manipulateEdits(listOf(nombre,contrasena,contrasenaConf))
                            Thread.sleep(500)
                            val intentMain=Intent(this@changePass,logIn::class.java)
                            startActivity(intentMain)
                        }
                    }
                }
            }
        }

        back.setOnClickListener {
            val intentMain=Intent(this,logIn::class.java)
            startActivity(intentMain)
        }
    }
}