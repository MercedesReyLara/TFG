package com.example.tfg

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import model.SharedPreff

class ajustes : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajustes_tamano_letra)


        /*Declaración de elementos visuales*/
        val lpequena:CheckBox=findViewById(R.id.pequena)
        val lgrande:CheckBox=findViewById(R.id.grande)
        val aplicar:Button=findViewById(R.id.aplicarButton)
        val volverMain: ImageButton =findViewById(R.id.backToMain)
        val ip:Button=findViewById(R.id.aplicarIp)
        val direcIP:EditText=findViewById(R.id.direccionIP)

        /*Declaracion de elementos de uso*/
        val context: Context =baseContext
        val tamanoFuente:Int=12
        val sharedPreff=SharedPreff(context)
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
            val intentMain= Intent(this,mainMenu::class.java)
            startActivity(intentMain)
        }
    }
}