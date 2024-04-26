package com.example.tfg

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class register : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        //Registro de elementos visuales

        val backButton: Button =findViewById(R.id.goBackLogIn)
        val registerButton:Button=findViewById(R.id.rexistro)
        val name:EditText=findViewById(R.id.nameUser)
        val lastName:EditText=findViewById(R.id.lastNameUser)
        val mail:EditText=findViewById(R.id.mailUser)
        val password:EditText=findViewById(R.id.passwordUser)
        val passwordConfirm:EditText=findViewById(R.id.confirmPassword)
        val validatorCleaner=generalFunctions()

        //Declaracion de variables



        //Utilizamos el método para limpiar los inputs cuando esten on click
        validatorCleaner.clearHint(name)
        validatorCleaner.clearHint(lastName)
        validatorCleaner.clearHint(mail)
        validatorCleaner.clearHint(password)
        validatorCleaner.clearHint(passwordConfirm)
        registerButton.setOnClickListener {
            val nameTXT=name.text.toString().trim()
            val lastNameTXT=lastName.text.toString()
            val mailTXT=mail.text.toString().trim()
            val passwordTXT=password.text.toString().trim()
            val passwordConfTXT=passwordConfirm.text.toString().trim()

            if(nameTXT.isEmpty()||lastNameTXT.isEmpty()||
                mailTXT.isEmpty()||passwordTXT.isEmpty()||passwordConfTXT.isEmpty()){
                Toast.makeText(this,this.getString(R.string.errorVacios),Toast.LENGTH_LONG).show()
            }else if(!validatorCleaner.validateEmail(mailTXT)){
                Toast.makeText(this,this.getString(R.string.errorCorreo),Toast.LENGTH_LONG).show()
            }else if(!validatorCleaner.validatePassword(passwordTXT)){
                password.text.clear()
                passwordConfirm.text.clear()
                Toast.makeText(this,this.getString(R.string.errorContraseña),Toast.LENGTH_LONG).show()
            }else if(passwordTXT.equals(passwordConfTXT)){
                Toast.makeText(this,this.getString(R.string.coincidir),Toast.LENGTH_LONG).show()
                passwordConfirm.text.clear()
            }else{
                val intentMainMenu= Intent(this,mainMenu::class.java)
                startActivity(intentMainMenu)
            }

        }
        backButton.setOnClickListener {
            val intentBack=Intent(this,logIn::class.java)
            startActivity(intentBack)
        }
        }
    }
