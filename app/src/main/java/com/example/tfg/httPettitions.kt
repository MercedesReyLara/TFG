package com.example.tfg

import android.net.Uri
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.Category
import model.Product
import model.Review
import model.User
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException


class httPettitions {


    suspend fun getUser(correo:String,contrasena:String): User? {
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val uri:String= Uri.parse("http://192.168.1.73:8080/logIn").buildUpon()
                .appendQueryParameter("nombreU",correo)
                .appendQueryParameter("contrasena",contrasena)
                .build().toString()
            val request: Request = Request.Builder()
                .url(uri)
                .build()
            val response:Response
            try {
                response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                if (response.isSuccessful&&!responseBody.isNullOrEmpty()) {
                    val gson = Gson()
                    val user=gson.fromJson(responseBody, User::class.java)
                    return@withContext user
                }else {
                    return@withContext null
                }
            }catch(e:IOException){
                return@withContext null
            }

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

    suspend fun getUserByDNI(dni:String): User? {
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val url:String="http://192.168.1.73:8080/getUser/$dni"
            val request: Request = Request.Builder()
                .url(url)
                .build()
            val response:Response
            try {
                response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                if (response.isSuccessful&&!responseBody.isNullOrEmpty()) {
                    val gson = Gson()
                    val user=gson.fromJson(responseBody, User::class.java)
                    return@withContext user
                }else {
                    return@withContext null
                }
            }catch(e:IOException){
                return@withContext null
            }

        }
    }

    suspend fun getProductos(dni:String?):ArrayList<Product>{
        return withContext(Dispatchers.IO) {
            val products = object : TypeToken<ArrayList<Product>>() {}.type
            val client = OkHttpClient()
            val url:String= "http://192.168.1.73:8080/$dni/getUserProducts"
            var listProductos:ArrayList<Product> = arrayListOf()
            val request: Request = Request.Builder()
                .url(url)
                .build()
            val response:Response
            try {
                response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                if (response.isSuccessful&&!responseBody.isNullOrEmpty()) {
                    val gson = Gson()
                    listProductos= gson.fromJson(responseBody, products)
                    return@withContext listProductos
                }else {
                    return@withContext listProductos
                }
            }catch(e:IOException){
                return@withContext listProductos
            }

        }
    }

    suspend fun getReviews():ArrayList<Review>{
        return withContext(Dispatchers.IO) {
            val reviews = object : TypeToken<ArrayList<Review>>() {}.type
            val client = OkHttpClient()
            val url:String= "http://192.168.1.73:8080/getReviews"
            var listReviews:ArrayList<Review> = arrayListOf()
            val request: Request = Request.Builder()
                .url(url)
                .build()
            val response:Response
            try {
                response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                if (response.isSuccessful&&!responseBody.isNullOrEmpty()) {
                    val gson = Gson()
                    listReviews= gson.fromJson(responseBody, reviews)
                    return@withContext listReviews
                }else {
                    return@withContext listReviews
                }
            }catch(e:IOException){
                return@withContext listReviews
            }

        }
    }

    suspend fun getCategories():ArrayList<Category>{
        return withContext(Dispatchers.IO) {
            val categories = object : TypeToken<ArrayList<Category>>() {}.type
            val client = OkHttpClient()
            val url:String= "http://192.168.1.73:8080/getCategories"
            var listCategories:ArrayList<Category> = arrayListOf()
            val request: Request = Request.Builder()
                .url(url)
                .build()
            val response:Response
            try {
                response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                if (response.isSuccessful&&!responseBody.isNullOrEmpty()) {
                    val gson = Gson()
                    listCategories= gson.fromJson(responseBody, categories)
                    return@withContext listCategories
                }else {
                    return@withContext listCategories
                }
            }catch(e:IOException){
                return@withContext listCategories
            }

        }
    }
}