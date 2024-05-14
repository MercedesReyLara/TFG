package com.example.tfg

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Category
import model.Review
import model.SharedPreff
import model.User
import java.util.Locale


class logIn : AppCompatActivity() {
    private lateinit var context: Context
    private lateinit var sharedPreff:SharedPreff
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        Thread.sleep(1500)
        setTheme(R.style.Base_Theme_TFG)
        super.onCreate(savedInstanceState)
        context = baseContext
        sharedPreff = SharedPreff(context)
        applyLanguage()
        setContentView(R.layout.log_in)
        /*^(?:[0-9]{1,3}\.){3}[0-9]{1,3}$ regex ip igual la uso*/
        //Búsqueda de elementos visuales
        val logIn: Button = findViewById(R.id.iniciarSesion)
        val registerBut: Button = findViewById(R.id.registrar)
        val email: EditText = findViewById(R.id.editTextUsername)
        val password: EditText = findViewById(R.id.passwordUser)
        val validator = generalFunctions()
        val ajustesButton:ImageButton=findViewById(R.id.ajustes)
        //Declaración de variables
        val httPettitions=httPettitions()
        /*Si las shared preferences tienen inciada sesión, vamos directamente al menú principal
        si no nos quedamos en la actividad y hacemos las operaciones adecuadas
         */
        if (sharedPreff.getLogin(baseContext)) {
            val intent = Intent(this, mainMenu::class.java)
            startActivity(intent)
        } else {
            validator.clearHint(email)
            validator.clearHint(password)

            /*Iniciar sesión:
            1-Comprueba que los campos no estén vacíos
            2-Comprueba si el email cumple el patrón: @gmail.com
            3-Comrpueba si la contraseña cumple las reglas: mayúscula, minúscula,numero y símbolo
            4-Si es válido,inicia sesión si es que encuentra el usuario en la base de datos
            TODOS LOS MENSAJES DE LOS TOAST SON PERSONALIZADOS SEGÚN EL TIPO DE ERROR
             */
            logIn.setOnClickListener {
                val emailText = email.text.toString().trim()
                val passwordText = password.text.toString().trim()

                if (emailText.isEmpty() || passwordText.isEmpty()) {
                    Toast.makeText(
                        this,
                        this.getString(R.string.errorVacios),
                        Toast.LENGTH_LONG
                    )
                        .show()
                    /*} else if (!validator.validateEmail(emailText)) {
                        Toast.makeText(
                            this,
                            this.getString(R.string.errorCorreo),
                            Toast.LENGTH_LONG
                        )
                            .show()*/
                    /*} else if (!validator.validatePassword(passwordText)) {
                        Toast.makeText(
                            this,
                            this.getString(R.string.errorContraseña),
                            Toast.LENGTH_LONG
                        ).show()*/
                } else {
                    /*val direcIP = sharedPreff.getIp(this@logIn)
                    if (direcIP != sharedPreff.ipReal(context)) {
                        Toast.makeText(
                            this@logIn,
                            "IP no válida, fallo en la conexión con el servidor",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {*/
                       lifecycleScope.launch(Dispatchers.Main) {
                            val encontrado:User?
                            withContext(Dispatchers.IO) {
                                 encontrado=httPettitions.getUser(emailText, passwordText)
                            }
                                if (encontrado!=null) {
                                    sharedPreff.saveLogin(context, true)
                                    sharedPreff.saveUser(context, encontrado.dni)
                                    val intentLogIn = Intent(this@logIn, mainMenu::class.java)
                                    startActivity(intentLogIn)
                                } else{
                                    Toast.makeText(
                                        this@logIn,
                                        "Nombre de usuario o contraseña incorrectos",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }


                    }

                }

            registerBut.setOnClickListener {
                val intentRegister = Intent(this, register::class.java)
                startActivity(intentRegister)

            }
        ajustesButton.setOnClickListener {
        val intentAjustes=Intent(this,ajustes::class.java)
        startActivity(intentAjustes)
        }
            }
    fun Context.applyLanguage() {
        val language = sharedPreff.getLanguage(this)
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
        }



