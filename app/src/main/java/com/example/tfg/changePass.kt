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
        buscar.setOnClickListener {
            val nombreText=nombre.text.toString()
            if(nombreText.isEmpty()){
                Toast.makeText(this,this.getString(R.string.errorVacios),Toast.LENGTH_SHORT).show()
            }else{
                lifecycleScope.launch(Dispatchers.Main) {
                    withContext(Dispatchers.IO){
                          encontrado=pettitions.getUserByName(User(nombreText),ip)
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
                val userModificado=User(encontrado!!.dni,encontrado!!.nombreU,encontrado!!.apellidosU,encontrado!!.correo,
                    contrasenaText,encontrado!!.descripcion,encontrado!!.profileP,encontrado!!.activo)
                lifecycleScope.launch(Dispatchers.Main) {
                    var cambiado:Boolean?=false
                    withContext(Dispatchers.IO){
                        cambiado=pettitions.modifyUser(userModificado,ip)
                    }
                    when (cambiado) {
                        null -> {
                            Toast.makeText(this@changePass,this@changePass.getString(R.string.problemas),Toast.LENGTH_SHORT).show()
                        }
                        false -> {
                            Toast.makeText(this@changePass,this@changePass.getString(R.string.errorObtencion),Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(this@changePass,this@changePass.getString(R.string.cambiadaC),Toast.LENGTH_SHORT).show()
                            nombre.text.clear()
                            contrasena.text.clear()
                            contrasenaConf.text.clear()
                            Thread.sleep(1000)
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