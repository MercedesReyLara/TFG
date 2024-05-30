package com.example.tfg

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.drawToBitmap
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.tfg.petitionsAndFunctions.generalFunctions
import com.example.tfg.petitionsAndFunctions.httPettitions
import com.example.tfg.sqlite.GalleryDbHelper
import com.example.tfg.sqlite.Image
import com.example.tfg.sqlite.ImageDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.tfg.petitionsAndFunctions.SharedPreff
import model.User
import java.io.IOException


class register : AppCompatActivity() {
    private lateinit var sharedPreff: SharedPreff
    private lateinit var context:Context
    private var imagenRecogida: String=""
    private val functions= generalFunctions()
    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
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
        val buscarFotos:Button=findViewById(R.id.changeProfileP)
        val camera:ImageButton=findViewById(R.id.camara)
        val profileP: ImageView =findViewById(R.id.profileP)
        val insertar:ImageButton=findViewById(R.id.insertar)
        val spinnerImg: Spinner =findViewById(R.id.spinnerImg)

        //Declaracion de variables
        val listaSpinner:List<String> = listOf("-","Imagen 1","Imagen 2","Imagen 3","Imagen 4","Imagen 5")
        val adapter: ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_spinner_item,listaSpinner)
        spinnerImg.adapter=adapter
        spinnerImg.setSelection(0)
        val pettitions= httPettitions()
        context =baseContext
        sharedPreff= SharedPreff(context)
        val ip=sharedPreff.getIp(context)
        val helper:GalleryDbHelper= GalleryDbHelper(context)
        val DAO:ImageDAO= ImageDAO()
        var asigned:Boolean=false
        if(asigned && imagenRecogida.isNotEmpty()){
            profileP.setImageBitmap(functions.stringToBitmap(imagenRecogida))
        }
        //Utilizamos el método para limpiar los inputs cuando esten on focus
        functions.clearHint(listOf(DNIT,name,lastName,mail,password,passwordConfirm),
            listOf(DNIT.hint,name.hint,lastName.hint,mail.hint,password.hint,passwordConfirm.hint))
        profileP.setImageResource(R.drawable.imagen_2024_05_14_213155645_removebg_preview)
        spinnerImg.isVisible=false
        val resultado=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                activityResult->
            if(activityResult.resultCode== RESULT_OK){
                try {
                    val result =activityResult.data?.extras?.get("data") as Bitmap
                    profileP.setImageBitmap(result)
                    imagenRecogida=functions.bitmapToString(profileP.drawToBitmap())
                    asigned = true
                }catch(exception:Exception){
                    exception.toString()
                }
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
            }else if(passwordTXT != passwordConfTXT){
                Toast.makeText(this,this.getString(R.string.coincidir),Toast.LENGTH_LONG).show()
                passwordConfirm.text.clear()
            }else{
                val pfp=functions.bitmapToString(profileP.drawable.toBitmap())
                val newUser= User(dniTXT,nameTXT,lastNameTXT,mailTXT,passwordConfTXT,"Sin descripcion",pfp,true)
                var success:Boolean?=false
                lifecycleScope.launch (Dispatchers.IO){
                    success=pettitions.postUser(newUser,ip)!!
                }
                when (success) {
                    null -> {
                        Toast.makeText(this,this@register.getString(R.string.problemas),Toast.LENGTH_SHORT).show()
                    }
                    true -> {
                        Toast.makeText(this,"Usuario registrado con exito",Toast.LENGTH_SHORT).show()
                        functions.clearText(listOf(DNIT,name,lastName,password,passwordConfirm,mail))
                        functions.manipulateEdits(listOf(DNIT,name,lastName,mail,password,passwordConfirm))
                        sharedPreff.saveLogin(context, true)
                        val encryptedDNI = functions.encrypt(dniTXT, functions.clave)
                        sharedPreff.saveUser(context, encryptedDNI)
                        val intentMainMenu= Intent(this,mainMenu::class.java)
                        startActivity(intentMainMenu)
                    }
                    else -> {
                        Toast.makeText(this,this@register.getString(R.string.errorObtencion),Toast.LENGTH_SHORT).show()
                    }
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

        buscarFotos.setOnClickListener {
            var listaObtenidas:ArrayList<Image> = arrayListOf()
            try{
              listaObtenidas=DAO.visualizarImagenes(helper)
                spinnerImg.isVisible=true
                spinnerImg.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                        val imgSelect=spinnerImg.selectedItem.toString()
                        for(img in listaObtenidas){
                            when(imgSelect){
                                "Imagen 1"->profileP.setImageBitmap(functions.byteArrayToBitmap(listaObtenidas[0].valor))
                                "Imagen 2"->profileP.setImageBitmap(functions.byteArrayToBitmap(listaObtenidas[1].valor))
                                "Imagen 3"->profileP.setImageBitmap(functions.byteArrayToBitmap(listaObtenidas[2].valor))
                                "Imagen 4"->profileP.setImageBitmap(functions.byteArrayToBitmap(listaObtenidas[3].valor))
                                "Imagen 5"->profileP.setImageBitmap(functions.byteArrayToBitmap(listaObtenidas[4].valor))
                            }
                        }
                    }
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
                }
            }catch (exception:IOException){
                Toast.makeText(this,"Error en la visualizacion de imagenes",
                    Toast.LENGTH_SHORT).show()
            }
        }

        insertar.setOnClickListener {
            val listaImg:ArrayList<Image> = arrayListOf()
            val listaFotos:ArrayList<ByteArray> = arrayListOf(
                functions.imageToByteArray(context,R.drawable.uno),
                functions.imageToByteArray(context,R.drawable.dos),
                functions.imageToByteArray(context,R.drawable.tres),
                functions.imageToByteArray(context,R.drawable.cuatro),
                functions.imageToByteArray(context,R.drawable.cinco))

            helper.writableDatabase

            for(foto in listaFotos){
                listaImg.add(Image(foto))
            }
            try{
                DAO.crearImagen(helper, listaImg)
            }catch(exception:IOException){
                Toast.makeText(this,"Error en la inserción de imagenes",
                    Toast.LENGTH_SHORT).show()
            }

        }
        }

   override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Guardar datos en el Bundle
       if(imagenRecogida.isNotEmpty()) {
           val imagen=imagenRecogida
           sharedPreff.saveImg(context,imagen)
       }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        imagenRecogida=sharedPreff.getImg(context)
    }
    }
