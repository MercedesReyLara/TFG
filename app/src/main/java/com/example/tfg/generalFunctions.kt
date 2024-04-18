package com.example.tfg


import android.widget.EditText
import java.util.regex.Matcher
import java.util.regex.Pattern


class generalFunctions {

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
            }
        }
    }
    /*fun writePDF(work:String):File{
        document.add(Paragraph("Lista de jornadas"))
        document.add(Paragraph(work))
        document.close()
        return pdfFile
    }*/
}