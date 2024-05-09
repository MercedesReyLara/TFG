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

    suspend fun getUser(ip:String?,correo:String,contrasena:String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val client = OkHttpClient()

                val url="http://$ip:8080/getUser?nombreU=$correo&contrasena=$contrasena"
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
                return@withContext  false
            }
            return@withContext false
        }
    }
    suspend fun postUser(ip:String,user: User,dni:String): Boolean {
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val gson = Gson()
            val json = gson.toJson(user)
            val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
            val requestBody = json.toRequestBody(mediaType)
            val url = "http://$ip:8080/postUser/$dni"
            val request: Request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()
            /*Falla al hacer la publicación del json*/
            val response = client.newCall(request).execute()


            /*Pone "Method not allowed*/
            val responseBody = response.body?.string()
            return@withContext response.isSuccessful && !responseBody.isNullOrEmpty()
        }
    }


    suspend fun deleteUser(ip:String,dni:String):Boolean {
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val url = "http://$ip:8080/delete/$dni"

            val request = Request.Builder()
                .url(url)
                .delete()
                .build()

            val response = client.newCall(request).execute()


            /*Pone "Method not allowed*/
            val responseBody = response.body?.string()
            // Verificar si el DNI del usuario encontrado coincide con el DNI buscado y que se ha borrado
            return@withContext response.isSuccessful && !responseBody.isNullOrEmpty()
        }
    }
}