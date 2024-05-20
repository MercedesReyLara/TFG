package com.example.tfg

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.SharedPreff
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
        val functions=generalFunctions()
        val pettitions=httPettitions()
        val camera:ImageButton=findViewById(R.id.camara)
        val profileP: ImageView =findViewById(R.id.profileP)
        val context: Context =baseContext
        val sharedPreff=SharedPreff(context)
        //Declaracion de variables



        //Utilizamos el método para limpiar los inputs cuando esten on click
        functions.clearHint(DNIT)
        functions.clearHint(name)
        functions.clearHint(lastName)
        functions.clearHint(mail)
        functions.clearHint(password)
        functions.clearHint(passwordConfirm)
        val resultado=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                activityResult->
            if(activityResult.resultCode== RESULT_OK){
                val imagenRecogida = activityResult.data?.extras?.get("data") as Bitmap
                profileP.setImageBitmap(imagenRecogida)
            }
        }
        registerButton.setOnClickListener {
            val dniTXT=DNIT.text.toString().trim()
            val nameTXT=name.text.toString().trim()
            val lastNameTXT=lastName.text.toString()
            val mailTXT=mail.text.toString().trim()
            val passwordTXT=password.text.toString().trim()
            val passwordConfTXT=passwordConfirm.text.toString().trim()

            if(nameTXT.isEmpty()||lastNameTXT.isEmpty()||
                mailTXT.isEmpty()||passwordTXT.isEmpty()||passwordConfTXT.isEmpty()||dniTXT.isEmpty()){
                Toast.makeText(this,this.getString(R.string.errorVacios),Toast.LENGTH_LONG).show()
            }else if(!functions.validateDNI(dniTXT)){
                Toast.makeText(this,this.getString(R.string.DNI),Toast.LENGTH_LONG).show()
            } else if(!functions.validateEmail(mailTXT)){
                Toast.makeText(this,this.getString(R.string.errorCorreo),Toast.LENGTH_LONG).show()
            }else if(!functions.validatePassword(passwordTXT)){
                password.text.clear()
                passwordConfirm.text.clear()
                Toast.makeText(this,this.getString(R.string.errorContraseña),Toast.LENGTH_LONG).show()
            }else if(passwordTXT == passwordConfTXT){
                Toast.makeText(this,this.getString(R.string.coincidir),Toast.LENGTH_LONG).show()
                passwordConfirm.text.clear()
            }else{
                val newUser= User(dniTXT,nameTXT,lastNameTXT,mailTXT,passwordConfTXT,"")
                var success:Boolean=false
                lifecycleScope.launch (Dispatchers.IO){
                    success=pettitions.postUser(newUser)
                }
                if(success){
                    Toast.makeText(this,"rwr",Toast.LENGTH_SHORT).show()
                    functions.clearText(listOf(DNIT,name,lastName,password,passwordConfirm,mail))
                    sharedPreff.saveLogin(context, true)
                    val encryptedDNI = functions.encrypt(dniTXT, functions.clave)
                    sharedPreff.saveUser(context, encryptedDNI)
                    val intentMainMenu= Intent(this,mainMenu::class.java)
                    startActivity(intentMainMenu)
                }else{
                    Toast.makeText(this,"ERROR",Toast.LENGTH_SHORT).show()
                }

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
   /* override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Guardar datos en el Bundle
        val imagen=imagenRecogida.
        outState.putByteArray("profileP", imagen)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Restaurar datos desde el Bundle
        val value = savedInstanceState.getBoolean("key")
        start.isPressed=value
    }*/
    }
