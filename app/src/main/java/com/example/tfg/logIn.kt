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
import androidx.appcompat.app.AlertDialog
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

        /*Dromimos el main para que se vea el splash*/
        Thread.sleep(1500)
        /*Aplicamos el tema por defecto de mi app*/
        setTheme(R.style.Base_Theme_TFG)
        super.onCreate(savedInstanceState)
        /*Instanciamos el contexto y las shared prefferences, que por tema del cambio de idioma tienen que
        estar declaradas fuera del onCreate para poder referenciarlas en la función del cambio de idioma */
        context = baseContext
        sharedPreff = SharedPreff(context)
        val functions = generalFunctions()
        /*Hacemos que las preferencias pillen la ip indicada*/
        sharedPreff.saveIP(context,"192.168.144.2")
        /*Establecemos el idioma porque al salir de la app se resetea
        entonces buscamos en las preferencias el codigo y aplicamos el idioma*/
        functions.setLanguage(context)
        /*Esta función está más abajo, y se ejecuta en el onCreate porque cuando ejecutamos la funcion,
        esta cuenta con el método recreate() que lo que hace es llamar al onCreate para poder aplicar el idioma */
        setContentView(R.layout.log_in)
        /*^(?:[0-9]{1,3}\.){3}[0-9]{1,3}$ regex ip igual la uso*/
        //Búsqueda de elementos visuales
        val logIn: Button = findViewById(R.id.iniciarSesion)
        val registerBut: Button = findViewById(R.id.registrar)
        val email: EditText = findViewById(R.id.editTextUsername)
        val password: EditText = findViewById(R.id.passwordUser)
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
            functions.clearHint(email)
            functions.clearHint(password)

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
                    /*Validamos contraseña con la regex establecida*/
                   /* } else if (!functions.validatePassword(passwordText)) {
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
                    /*Llamo al hilo principal para saber donde tiene que ejecutar las acciones siguiente*/
                       lifecycleScope.launch(Dispatchers.Main) {
                           val encontrado: User?
                           val ip = sharedPreff.getIp(context)
                           /*Lanzo la petición en el hilo secundario para no crashear*/
                           withContext(Dispatchers.IO) {
                               encontrado = httPettitions.getUser(emailText, passwordText, ip)
                           }
                           /*Acaba la petición entonces manejo los datos*/
                           if(encontrado==null){
                               Toast.makeText(this@logIn,this@logIn.getString(R.string.problemas),Toast.LENGTH_SHORT).show()
                             }else if(encontrado.toString().isEmpty()) {

                           }else{
                                    if(!encontrado.activo){
                                        val builderActivar: AlertDialog.Builder =
                                            AlertDialog.Builder(this@logIn)/*Creamos el objeto diálogo*/
                                        builderActivar.setTitle("¿Reactivar cuenta?")/*Establecemos el título, el mensaje principal y las dos opciones*/
                                        builderActivar.setMessage("¿Desea reactivar su cuenta de PRORATER?")
                                        builderActivar.setIcon(R.drawable.imagen_2024_04_22_110039483_removebg_preview)
                                        builderActivar.setPositiveButton("Reactivar") { _, _ ->
                                            /*Si le damos a reactivar se lanzará la petición que hará que nuestro usuario
                                            se reactivo e iniciará sesión de nuevo, volverá a guardar el dni...
                                             */
                                            lifecycleScope.launch(Dispatchers.Main) {
                                                var done:Boolean?=false
                                                withContext(Dispatchers.IO){
                                                    done=httPettitions.reactivarUsuario(encontrado,ip)
                                                }
                                                when (done) {
                                                    null -> {
                                                        Toast.makeText(this@logIn,"error en la conexion",Toast.LENGTH_SHORT).show()
                                                    }
                                                    true -> {
                                                        sharedPreff.saveLogin(context, true)
                                                        val encryptedDNI = functions.encrypt(encontrado.dni, functions.clave)
                                                        sharedPreff.saveUser(context, encryptedDNI)
                                                        val intentLogIn = Intent(this@logIn, mainMenu::class.java)
                                                        startActivity(intentLogIn)
                                                    }
                                                    else -> {
                                                        Toast.makeText(this@logIn,this@logIn.getString(R.string.errorObtencion)
                                                            ,Toast.LENGTH_SHORT).show()
                                                    }
                                                }
                                            }
                                        }
                                        /*Si cancelamos no hace nada*/
                                        builderActivar.setNegativeButton(("Cancelar"), { _, _ -> })
                                        val dialog = builderActivar.create()/*Lo construímos con las distintas partes*/
                                        dialog.show()/*Lo mostramos*/
                                    }else {
                                        sharedPreff.saveLogin(context, true)
                                        val encryptedDNI = functions.encrypt(encontrado.dni, functions.clave)
                                        sharedPreff.saveUser(context, encryptedDNI)
                                        val intentLogIn = Intent(this@logIn, mainMenu::class.java)
                                        startActivity(intentLogIn)
                                    }
                                }
                            }
                        }


                    }

                }

        /*Cambio de pantalla al registro por si no tienes cuenta*/
            registerBut.setOnClickListener {
                val intentRegister = Intent(this, register::class.java)
                startActivity(intentRegister)

            }
        /*Ir al boton de ajustes para cambiar ip e idioma*/
        ajustesButton.setOnClickListener {
        val intentAjustes=Intent(this,ajustes::class.java)
        startActivity(intentAjustes)
        }
            }
        }



