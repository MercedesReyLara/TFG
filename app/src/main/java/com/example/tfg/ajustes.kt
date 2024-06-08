package com.example.tfg

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
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
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.tfg.petitionsAndFunctions.generalFunctions
import com.example.tfg.petitionsAndFunctions.SharedPreff
import com.example.tfg.petitionsAndFunctions.httPettitions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.User

class ajustes : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajustes_tamano_letra)

        val functions= generalFunctions()
        val context: Context =baseContext
        /*Declaración de elementos visuales*/
        val volverMain: ImageButton =findViewById(R.id.backToMain)
        val ip:Button=findViewById(R.id.aplicarIp)
        val eliminar:Button=findViewById(R.id.eliminarCuenta)
        val direcIP:EditText=findViewById(R.id.direccionIP)
        val spinnerIdiomas: Spinner = findViewById(R.id.spinnerLanguages)
        //Declaración de variables
        val sharedPreff= SharedPreff(context)
        val pettitions=httPettitions()
        functions.clearHint(listOf(direcIP), listOf(ip.hint))
        /*Crearemos el spinner que se encarga de los idiomas. Creamos una lista con 3 elementos, 2 códigos y un string
        * indicando al usuario que escoja el idioma. Según el código cambiaremos el local del dispositivo para que
        * se aplique el idioma en cuestión*/
        val DNIu:String=functions.decrypt(functions.clave,sharedPreff.getUser(context).toString()).toString()
        val logeado=sharedPreff.getLogin(context)
        if(!logeado){
            eliminar.isVisible=false
        }
        val iP:String=sharedPreff.getIp(context)
        /*if(DNIu.isEmpty()){
            eliminar.isVisible=false
        }*/
        val languages = arrayListOf("es", "gl","SELECCIONE IDIOMA")
        val adapter: ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_spinner_item,languages)
        spinnerIdiomas.adapter=adapter
        spinnerIdiomas.setSelection(2)
        spinnerIdiomas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedLanguage = parent?.getItemAtPosition(position).toString()
                if (selectedLanguage == "gl") {
                    if(!sharedPreff.getLanguage(context).equals("gl")){
                        sharedPreff.setLanguage(context, "gl")
                        functions.setLanguage(context)
                        recreate()
                    }

                } else if (selectedLanguage == "es") {
                    if (!sharedPreff.getLanguage(context).equals("es")) {
                        sharedPreff.setLanguage(context, "es")
                        functions.setLanguage(context)
                        recreate()
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        eliminar.setOnClickListener {
            val builderOpciones: AlertDialog.Builder =
                AlertDialog.Builder(this)/*Creamos el objeto diálogo*/
            builderOpciones.setTitle(this.getString(R.string.eliminarC))/*Establecemos el título, el mensaje principal y las dos opciones*/
            builderOpciones.setMessage(this.getString(R.string.eliminarD))
            builderOpciones.setPositiveButton("Eliminar") { _, _ ->
                lifecycleScope.launch(Dispatchers.Main) {
                    var done: Boolean? = false
                    withContext(Dispatchers.IO) {
                        done = pettitions.deleteUser(DNIu, iP)
                    }
                    when (done) {
                        null -> {
                            Toast.makeText(
                                this@ajustes, this@ajustes.getString(R.string.problemas),
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(functions.logOutFun(this@ajustes))
                        }

                        true -> {
                            Toast.makeText(
                                this@ajustes,
                                "Usuario eliminado con éxito",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(functions.logOutFun(this@ajustes))
                        }

                        else -> {
                            Toast.makeText(this@ajustes, "ERROR", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
                /*Si cancelamos no hace nada*/
                builderOpciones.setNegativeButton("Cancelar"){ _, _ -> }
                val dialog = builderOpciones.create()/*Lo construímos con las distintas partes*/
                dialog.show()/*Lo mostramos*/
            }

        /*Boton para establecer el cambio de ip*/
        ip.setOnClickListener {
            val IP:String=direcIP.text.trim().toString()
            sharedPreff.saveIP(context,IP)
            Toast.makeText(this,"Dirección ip actuaizada",Toast.LENGTH_SHORT).show()
            direcIP.text.clear()
            functions.manipulateEdits(listOf(direcIP))
        }
        volverMain.setOnClickListener {
            val intentMain= Intent(this,logIn::class.java)
            startActivity(intentMain)
        }
    }
}