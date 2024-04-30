package com.example.tfg


import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import java.util.Locale
import java.util.regex.Matcher
import java.util.regex.Pattern


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

    fun clearHint(text: EditText){
        text.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                text.hint = ""
            }else{
                text.hint=text.hint
            }
        }
    }

    fun spinnerLanguages(context:Context,spinner: Spinner):Spinner{
        val adapterSpinner:ArrayAdapter<String> = ArrayAdapter(context,android.R.layout.simple_spinner_item,languages)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter=adapterSpinner
        spinner.setSelection(0)

        return spinner
    }

    fun setLanguage(activity:Activity, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val configuration = activity.resources.configuration
        configuration.setLocale(locale)

        val resources = activity.baseContext.resources
        val context = activity.baseContext.createConfigurationContext(configuration)

        val intent = activity.intent
        activity.finish()
        activity.startActivity(intent)
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