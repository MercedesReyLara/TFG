package com.example.tfg

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class logIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        Thread.sleep(1500)
        setTheme(R.style.Base_Theme_TFG)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_in)

        //Búsqueda de elementos visuales
        val logIn: Button =findViewById(R.id.buttonLogin)
        val register:Button=findViewById(R.id.buttonRegister)
        val email:EditText=findViewById(R.id.editTextUsername)
        val password:EditText=findViewById(R.id.editTextPassword)

        //Declaración de variables


        register.setOnClickListener {
            val intentRegister= Intent(this,register::class.java)
            startActivity(intentRegister)
        }

    }
}