package com.example.tfg

import android.net.Uri
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.User
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.Base64

class httPettitions {

    suspend fun getUser(correo:String,contrasena:String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val client = OkHttpClient()

                val url="http://192.168.1.73:8080/getUser?nombreU=$correo&contrasena=$contrasena"
                val request: Request = Request.Builder()
                    .url(url)
                    .build()

                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    if (!responseBody.isNullOrEmpty()) {
                        return@withContext true
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return@withContext false
        }
    }
}