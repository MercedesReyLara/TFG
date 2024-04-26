package model

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

    fun savePressed(context:Context,boolean:Boolean){
        val sharedPreff=PreferenceManager.getDefaultSharedPreferences(context)
        val editor=sharedPreff.edit()
        editor.putBoolean("pressed",boolean)
        editor.commit()
    }

    fun getPressed(context: Context):Boolean{
        val sharedPreff=PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreff.getBoolean("pressed",false)
    }

    fun saveUser(context:Context,userID:Int){/*Funcion para guardar el id*/
        val sharedPreff=PreferenceManager.getDefaultSharedPreferences(context)
        val editor=sharedPreff.edit()
        editor.putInt("userID",userID)
        editor.commit()
    }

    fun getUser(context:Context):Int{
        val sharedPreff=PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreff.getInt("userID",0)
    }
}