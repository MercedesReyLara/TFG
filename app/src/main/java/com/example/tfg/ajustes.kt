package com.example.tfg

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import model.SharedPreff

class ajustes : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajustes_tamano_letra)

        val functions=generalFunctions()
        val context: Context =baseContext
        /*Declaración de elementos visuales*/
        val volverMain: ImageButton =findViewById(R.id.backToMain)
        val ip:Button=findViewById(R.id.aplicarIp)
        val direcIP:EditText=findViewById(R.id.direccionIP)
        val spinnerIdiomas: Spinner = findViewById(R.id.spinnerLanguages)
        //Declaración de variables
        val tamanoFuente:Int=12
        val sharedPreff=SharedPreff(context)
        /*Crearemos el spinner que se encarga de los idiomas. Creamos una lista con 3 elementos, 2 códigos y un string
        * indicando al usuario que escoja el idioma. Según el código cambiaremos el local del dispositivo para que
        * se aplique el idioma en cuestión*/

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

        /*Boton para establecer el cambio de ip*/
        ip.setOnClickListener {
            val IP:String=direcIP.text.trim().toString()
            sharedPreff.saveIP(context,IP)
        }
        volverMain.setOnClickListener {
            val intentMain= Intent(this,logIn::class.java)
            startActivity(intentMain)
        }
    }
}