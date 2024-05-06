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


    fun saveUser(context:Context,userID:String){/*Funcion para guardar el id*/
        val sharedPreff=PreferenceManager.getDefaultSharedPreferences(context)
        val editor=sharedPreff.edit()
        editor.putString("userID",userID)
        editor.commit()
    }

    fun getUser(context:Context): String? {
        val sharedPreff=PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreff.getString("userID","A")
    }
}