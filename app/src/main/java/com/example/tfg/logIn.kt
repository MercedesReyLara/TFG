package com.example.tfg

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class logIn : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        Thread.sleep(1500)
        setTheme(R.style.Base_Theme_TFG)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_in)

        //Búsqueda de elementos visuales
        val logIn: Button =findViewById(R.id.iniciarSesion)
        val register:Button=findViewById(R.id.registrar)
        val email:EditText=findViewById(R.id.editTextUsername)
        val password:EditText=findViewById(R.id.editTextPassword)
        val validator=generalFunctions()
        //Declaración de variables


        register.setOnClickListener {
            val intentRegister= Intent(this,register::class.java)
            startActivity(intentRegister)
        }

        logIn.setOnClickListener {
            val emailText=email.text.toString().trim()
            val passwordText=password.text.toString().trim()

            if(emailText.isEmpty()||passwordText.isEmpty()){
               Toast.makeText(this,this.getString(R.string.errorVacios),Toast.LENGTH_LONG).show()
            }else if(!validator.validateEmail(emailText)){
                Toast.makeText(this,this.getString(R.string.errorCorreo),Toast.LENGTH_LONG).show()
            }else if(!validator.validatePassword(passwordText)){
                Toast.makeText(this,this.getString(R.string.errorContraseña),Toast.LENGTH_LONG).show()
            }else{
                val intentLogIn=Intent(this,mainMenu::class.java)
                startActivity(intentLogIn)
            }

        }

    }
}