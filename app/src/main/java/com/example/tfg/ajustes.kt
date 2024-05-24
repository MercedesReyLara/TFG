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
        val lpequena:CheckBox=findViewById(R.id.pequena)
        val lgrande:CheckBox=findViewById(R.id.grande)
        val aplicar:Button=findViewById(R.id.aplicarButton)
        val volverMain: ImageButton =findViewById(R.id.backToMain)
        val ip:Button=findViewById(R.id.aplicarIp)
        val direcIP:EditText=findViewById(R.id.direccionIP)

        val spinnerIdiomas: Spinner = findViewById(R.id.spinnerLanguages)
        //Declaración de variables
        val tamanoFuente:Int=12
        val sharedPreff=SharedPreff(context)
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
                        val intent=intent
                        functions.setLanguage(context)
                        startActivity(intent)
                    }

                } else if (selectedLanguage == "es") {
                    if (!sharedPreff.getLanguage(context).equals("es")) {
                        sharedPreff.setLanguage(context, "es")
                        val intent=intent
                        functions.setLanguage(context)
                        startActivity(intent)
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        /*Declaracion de elementos de uso*/
        lgrande.isEnabled = !lpequena.isChecked
        aplicar.setOnClickListener {
            val configuration = resources.configuration
            configuration.fontScale = tamanoFuente.toFloat()
            resources.updateConfiguration(configuration, resources.displayMetrics)
            finish()
            val intent=Intent(this,ajustes::class.java)
            startActivity(intent)
        }

        ip.setOnClickListener {
            val IP:String=direcIP.text.toString()
            sharedPreff.saveIP(context,IP)
        }
        volverMain.setOnClickListener {
            val intentMain= Intent(this,logIn::class.java)
            startActivity(intentMain)
        }
    }
}