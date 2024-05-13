package com.example.tfg

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import model.User

class register : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        //Registro de elementos visuales

        val backButton: Button =findViewById(R.id.goBackLogIn)
        val registerButton:Button=findViewById(R.id.rexistro)
        val DNIT:EditText=findViewById(R.id.DNI)
        val name:EditText=findViewById(R.id.nameUser)
        val lastName:EditText=findViewById(R.id.lastNameUser)
        val mail:EditText=findViewById(R.id.mailUser)
        val password:EditText=findViewById(R.id.passwordUser)
        val passwordConfirm:EditText=findViewById(R.id.confirmPassword)
        val validatorCleaner=generalFunctions()
        val camera:ImageButton=findViewById(R.id.camara)
        var profileP: ImageView =findViewById(R.id.profileP)
        //Declaracion de variables



        //Utilizamos el método para limpiar los inputs cuando esten on click
        validatorCleaner.clearHint(DNIT)
        validatorCleaner.clearHint(name)
        validatorCleaner.clearHint(lastName)
        validatorCleaner.clearHint(mail)
        validatorCleaner.clearHint(password)
        validatorCleaner.clearHint(passwordConfirm)
        val resultado=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                activityResult->
            if(activityResult.resultCode== RESULT_OK){
                val imagenRecogida: Bitmap = activityResult.data?.extras?.get("data") as Bitmap
                profileP.setImageBitmap(imagenRecogida)
            }
        }
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
            }else if(passwordTXT == passwordConfTXT){
                Toast.makeText(this,this.getString(R.string.coincidir),Toast.LENGTH_LONG).show()
                passwordConfirm.text.clear()
            }else{
                val newUser= User()
                val intentMainMenu= Intent(this,mainMenu::class.java)
                startActivity(intentMainMenu)
            }

        }
        backButton.setOnClickListener {
            val intentBack=Intent(this,logIn::class.java)
            startActivity(intentBack)
        }

        camera.setOnClickListener {
            val imagenCaptura=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            setResult(RESULT_OK,imagenCaptura)
            resultado.launch(imagenCaptura)
        }
        }
    }
