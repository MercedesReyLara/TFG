package com.example.tfg


import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.util.Base64
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import java.util.Locale
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.DisplayMetrics
import model.SharedPreff
import java.io.ByteArrayOutputStream


class generalFunctions {
    public var languages=arrayListOf<String>("Select Language","Español","Galego")

    constructor()
    fun validateEmail(email:String):Boolean{
        val regexEmail = "^[a-zA-Z0-9_-]+@gmail\\.com$";
        val patternEmail: Pattern = Pattern.compile(regexEmail)
        val matcherEmail: Matcher =patternEmail.matcher(email)
        return matcherEmail.matches()
    }

   fun validatePassword(password:String):Boolean{
        val regexPassword="^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9!@#\$%^&*()-_+=]{8,}$"
        val patternPassword: Pattern = Pattern.compile(regexPassword)
        val matcherPassword: Matcher =patternPassword.matcher(password)
        return matcherPassword.matches()
    }

    fun clearHint(text: EditText) {
        text.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                text.hint = ""
            }else if(text.text.isEmpty()){
                text.hint=text.hint
            }
        }
    }

    fun putHint(text:EditText){
        text.revealOnFocusHint
    }
    fun spinnerLanguages(context:Context,spinner: Spinner):Spinner{
        val adapterSpinner:ArrayAdapter<String> = ArrayAdapter(context,android.R.layout.simple_spinner_item,languages)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter=adapterSpinner
        spinner.setSelection(0)

        return spinner
    }

    fun validateDNI(dni:String):Boolean{
        //Hacemos el string de las letras en orden según la página web
        val letters = "TRWAGMYFPDXBNJZSQVHLCKE"
        /*Hacemos una regex para obligar al usuario a que cumpla x condiciones
        en este caso son: que empiece obligatoriamente por 8 numeros que sean exclusivamente del 0-9
        y que acabe obligatoriamente en una letra que sea entre a y z
         */
        val dniRegex = Regex("^[0-9]{8}[A-Z]$")
        if (!dni.matches(dniRegex)) {
            //No cumple nuestro formato
            return false
        }

        /*Cogemos el último caracter del string y lo parseamos a un entero. Si no se pudiera
        parsear, devolvería false y saldría de la función
         */
        val dniNumbers = dni.substring(0, 8).toIntOrNull() ?: return false
        val expectedLetter = letters[dniNumbers % 23]
        /*Dividimos nuestro string sin letra entre la cantidad de letras, el número que de de resto
        es la letra que debería tener
         */

        return dni[8] == expectedLetter
    }

    fun clearText(list: List<EditText>) {
        for (editText in list) {
            editText.text.clear()
        }
    }

    val clave = "estaesmiclave123"
    fun encrypt(value:String,key:String):String{
        /*Hacemos un método para pasarle el valor y la key*/
        /*Usamos la librería cipher que nos proporciona funcionalidades de cifrado
        y ciframos a través de el algoritmo AES.
        Luego configuramos el IV que utilizaremos para la encriptacion y lo encriptamos.
        Por ultimo hacemos un return para poder guardarlo en la preferencias
         */
        val encryptSystem = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val secretKeySpec = SecretKeySpec(key.toByteArray(Charsets.UTF_8), "AES")
        val ivParameterSpec = IvParameterSpec(key.toByteArray(Charsets.UTF_8))
        encryptSystem.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)
        val encrypted = encryptSystem.doFinal(value.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(encrypted, Base64.DEFAULT)
    }

    fun decrypt(key:String,encryptedValue:String):String?{
        /*Prodceso inverso a la encriptación, es decir, declaramos el mismo algoritmo
        Pero en vez de encriptar desencriptamos y devolvemos el string que encriptamos que en mi
        caso lo guardé en las shared preferences.
         */
        val  encryptSystem= Cipher.getInstance("AES/CBC/PKCS5Padding")
        val secretKeySpec = SecretKeySpec(key.toByteArray(Charsets.UTF_8), "AES")
        val ivParameterSpec = IvParameterSpec(key.toByteArray(Charsets.UTF_8))
        encryptSystem.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)
        val decrypted = encryptSystem.doFinal(Base64.decode(encryptedValue, Base64.DEFAULT))
        return String(decrypted, Charsets.UTF_8)
    }



    fun imageToByteArray(context: Context, resourceId: Int): ByteArray {
        // Obtener el Bitmap de los recursos
        val bitmap = BitmapFactory.decodeResource(context.resources, resourceId)

        // Convertir el Bitmap en un ByteArrayOutputStream
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

        // Obtener el byte array desde el ByteArrayOutputStream
        return stream.toByteArray()
    }

    fun setLanguage(context:Context) {
        val resources:Resources=context.resources
        val DM:DisplayMetrics=resources.displayMetrics
        val configuration:Configuration=resources.configuration
        val sharedPreff=SharedPreff(context)
        configuration.setLocale(Locale(sharedPreff.getLanguage(context)))
        resources.updateConfiguration(configuration,DM)
    }
    /*fun writePDF(work:String):File{
        document.add(Paragraph("Lista de jornadas"))
        document.add(Paragraph(work))
        document.close()
        return pdfFile
    }*/
    /*companion object {

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }*/
    /*val pdfFilePath = "jornadas.pdf"

    val pdfFile = File(pdfFilePath)
    val pdfWriter = PdfWriter(pdfFile)
    val pdfDocument = PdfDocument(pdfWriter)
    val document = Document(pdfDocument)*/
}