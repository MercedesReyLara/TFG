package com.example.tfg.petitionsAndFunctions


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.util.Base64
import android.widget.EditText
import java.util.Locale
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.DisplayMetrics
import android.util.Log
import com.example.tfg.logIn
import java.io.ByteArrayOutputStream


class generalFunctions {

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

    fun clearHint(text:List<EditText>,hint:List<CharSequence>){
        for (editText in text) {
            val position=text.indexOf(editText)
            editText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    editText.hint = ""
                }else{
                    editText.hint= hint[position].toString()
                }
            }
        }
    }
    fun manipulateEdits(edit: List<EditText>){
        for (editText in edit) {
            editText.text.clear()
        }
        for(e in edit){
            if(e.hasFocus()){
                e.clearFocus()
            }
        }
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


   /* fun imageViewToByteArray(imageView: ImageView): ByteArray {
        // Obtener el Bitmap del ImageView
        val bitmap = imageView.drawable.toBitmap()

        // Convertir el Bitmap en un ByteArrayOutputStream
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

        // Obtener el byte array desde el ByteArrayOutputStream
        return stream.toByteArray()
    }*/
   fun isInt(puntuacion: String): Boolean {
       try {
           puntuacion.toInt()
       } catch (e: NumberFormatException) {
           return false
       }
       return true
   }

    fun bitmapToString(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.WEBP_LOSSLESS, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.URL_SAFE and Base64.NO_WRAP)
    }


    @SuppressLint("SuspiciousIndentation")
    fun byteArrayToBitmap(image:ByteArray):Bitmap ?{
        /*var bitmap = Bitmap()*/
        try{
       val bitmap=BitmapFactory.decodeByteArray(image,0,image!!.size)
            return bitmap
        }catch(exception:Exception){
            Log.i("excepcion",exception.toString())
        }
        return null
    }

    fun stringToBitmap(encodedString: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            null
        }
    }

    fun setLanguage(context:Context) {
        val resources:Resources=context.resources
        val DM:DisplayMetrics=resources.displayMetrics
        val configuration:Configuration=resources.configuration
        val sharedPreff= SharedPreff(context)
        configuration.setLocale(Locale(sharedPreff.getLanguage(context)))
        resources.updateConfiguration(configuration,DM)
    }
    fun logOutFun(context:Context):Intent {
        /*Esta función nos redirige a la pantalla principal
         Si le damos al botón establece el login como false y nos pide introducir nuestros datos de nuevo*/
        val sharedPreff = SharedPreff(context)
        sharedPreff.saveLogin(context, false)
        /*sharedPreff.savePressed(context,false)*/
        val intentSalir = Intent(context, logIn::class.java)
        return intentSalir
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