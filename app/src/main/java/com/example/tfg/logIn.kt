package com.example.tfg

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tfg.petitionsAndFunctions.generalFunctions
import com.example.tfg.petitionsAndFunctions.httPettitions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.tfg.petitionsAndFunctions.SharedPreff
import model.User


class logIn : AppCompatActivity() {
    private lateinit var context: Context
    private lateinit var sharedPreff: SharedPreff
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        /*Dromimos el main para que se vea el splash*/
        Thread.sleep(1500)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        actionBar?.hide()

        setTheme(R.style.Base_Theme_TFG)

        super.onCreate(savedInstanceState)

        /*Instanciamos el contexto y las shared prefferences, que por tema del cambio de idioma tienen que
        estar declaradas fuera del onCreate para poder referenciarlas en la función del cambio de idioma */
        context = baseContext
        sharedPreff = SharedPreff(context)
        val functions = generalFunctions()

        /*Hacemos que las preferencias seleccionamos la ip indicada*/
        /*sharedPreff.saveIP(context,"192.168.1.73")*/

        /*Establecemos el idioma porque al salir de la app se resetea
        entonces buscamos en las preferencias el codigo y aplicamos el idioma*/
        functions.setLanguage(context)

        /*Esta función está más abajo, y se ejecuta en el onCreate porque cuando ejecutamos la funcion,
        esta cuenta con el método recreate() que lo que hace es llamar al onCreate para poder aplicar el idioma */
        setContentView(R.layout.log_in)


        //Búsqueda de elementos visuales
        val logIn: Button = findViewById(R.id.iniciarSesion)
        val registerBut: Button = findViewById(R.id.registrar)
        val email: EditText = findViewById(R.id.editTextUsername)
        val password: EditText = findViewById(R.id.passwordUser)
        val ajustesButton:ImageButton=findViewById(R.id.ajustes)
        val changePassword:ImageButton=findViewById(R.id.changePassword)
        //Declaración de variables
        val httPettitions= httPettitions()
        /*Si las shared preferences tienen inciada sesión, vamos directamente al menú principal
        si no nos quedamos en la actividad y hacemos las operaciones adecuadas
         */
        if (sharedPreff.getLogin(baseContext)) {
            val intent = Intent(this, mainMenu::class.java)
            startActivity(intent)
        } else {
            functions.clearHint(listOf(email, password), listOf(email.hint, password.hint))
        }
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
                   } else if (!functions.validatePassword(passwordText)) {
                        Toast.makeText(
                            this,
                            this.getString(R.string.errorContraseña),
                            Toast.LENGTH_LONG
                        ).show()
                } else {
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
                             }else if(encontrado.dni=="") {
                               Toast.makeText(this@logIn,this@logIn.getString(R.string.errorLogIn),Toast.LENGTH_SHORT).show()
                           }else{
                               sharedPreff.saveLogin(context, true)
                               val encryptedDNI = functions.encrypt(encontrado.dni, functions.clave)
                               sharedPreff.saveUser(context, encryptedDNI)
                               val intentLogIn = Intent(this@logIn, mainMenu::class.java)
                               startActivity(intentLogIn)
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

            changePassword.setOnClickListener {
                val intentCambiarContrasena=Intent(this,changePass::class.java)
                startActivity(intentCambiarContrasena)
            }
    }
}



