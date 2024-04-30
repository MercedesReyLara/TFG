package com.example.tfg

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import model.SharedPreff


class logIn : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {

        Thread.sleep(1500)
        setTheme(R.style.Base_Theme_TFG)
        super.onCreate(savedInstanceState)
        context = baseContext
        setContentView(R.layout.log_in)

        //Búsqueda de elementos visuales
        val logIn: Button = findViewById(R.id.iniciarSesion)
        val registerBut: Button = findViewById(R.id.registrar)
        val email: EditText = findViewById(R.id.editTextUsername)
        val password: EditText = findViewById(R.id.passwordUser)
        val spinnerIdiomas: Spinner = findViewById(R.id.spinnerLanguages)
        val validator = generalFunctions()
        //Declaración de variables
        /*val languages = arrayListOf("Selecciona idioma","Castelan", "Galego")
        val adapter:ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_spinner_item,languages)
        spinnerIdiomas.setSelection(0)
        spinnerIdiomas.adapter=adapter
        spinnerIdiomas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedLanguage = parent?.getItemAtPosition(position).toString()
                if (selectedLanguage == "Galego") {*/

                /*} else if (selectedLanguage == "Castelan") {
                    validator.setLanguage(this@logIn,"es")
                    finish()
                    startActivity(intent)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }*/
        val sharedPreff = SharedPreff(context)

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
                    } else if (!validator.validateEmail(emailText)) {
                        Toast.makeText(
                            this,
                            this.getString(R.string.errorCorreo),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    } else if (!validator.validatePassword(passwordText)) {
                        Toast.makeText(
                            this,
                            this.getString(R.string.errorContraseña),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        sharedPreff.saveLogin(context, true)
                        sharedPreff.saveUser(context, 1)
                        val intentLogIn = Intent(this, mainMenu::class.java)
                        startActivity(intentLogIn)
                    }

                }
            registerBut.setOnClickListener {
                val intentRegister = Intent(this, register::class.java)
                startActivity(intentRegister)

            }
            }

        }
    }

