package com.example.tfg.petitionsAndFunctions

import android.content.Context
import androidx.preference.PreferenceManager

/*Clase especial para las sharred preferences, para guardarlas y recuperarlas*/
class SharedPreff(val contexto:Context) {


    fun saveLogin(context: Context,boolean: Boolean){
        val sharedPreff=PreferenceManager.getDefaultSharedPreferences(context)
        val editor=sharedPreff.edit()
        editor.putBoolean("login",boolean)
        editor.commit()
    }

    fun getLogin(context: Context):Boolean{
        val sharedPreff=PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreff.getBoolean("login",false)
    }


    fun saveUser(context:Context,userID:String){/*Funcion para guardar el DNI*/
        val sharedPreff=PreferenceManager.getDefaultSharedPreferences(context)
        val editor=sharedPreff.edit()
        editor.putString("userID",userID)
        editor.commit()
    }

    fun getUser(context:Context): String? {
        val sharedPreff=PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreff.getString("userID","A")
    }

    fun getLanguage(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("idioma", "gl") ?: "gl"
    }

    fun setLanguage(context: Context, language: String) {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("idioma", language).apply()
    }

    fun getIp(context: Context): String {
        val sharedPreff=PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreff.getString("IP","192.168.5.14").toString()
    }

    fun saveIP(context:Context,ip:String){
        val sharedPreff=PreferenceManager.getDefaultSharedPreferences(context)
        val editor=sharedPreff.edit()
        editor.putString("IP",ip)
        editor.commit()
    }

    fun saveNumResenas(context: Context,num:Int){
        val sharedPreff=PreferenceManager.getDefaultSharedPreferences(context)
        val editor=sharedPreff.edit()
        editor.putInt("numProductos",num)
        editor.commit()
    }

    fun getNumResenas(context: Context): Int {
        val sharedPreff=PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreff.getInt("numProductos",0)
    }

    fun savePermisos(context: Context,pedidos:Boolean){
        val sharedPreff=PreferenceManager.getDefaultSharedPreferences(context)
        val editor=sharedPreff.edit()
        editor.putBoolean("permisos",pedidos)
        editor.commit()
    }

    fun getPermisos(context: Context):Boolean {
        val sharedPreff=PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreff.getBoolean("permisos",false)
    }

    fun saveReview(context: Context,review:String){
        val sharedPreff=PreferenceManager.getDefaultSharedPreferences(context)
        val editor=sharedPreff.edit()
        editor.putString("reseña",review)
        editor.commit()
    }

    fun getReview(context: Context):String{
        val sharedPreff=PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreff.getString("reseña","").toString()
    }
}