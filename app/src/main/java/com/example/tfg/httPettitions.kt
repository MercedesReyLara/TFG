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


    suspend fun getUser(nombre:String,contrasena:String): User? {
        /*La hago suspend para poder lanzarla en hilo secundario*/
        /*El return este te lo pone automático pero basicamente le estás diciendo
        que te mande la respuesta al hilo secundario
         */
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            /*Peticion en este caso con nombre y contraseña*/
            val uri:String= Uri.parse("http://192.168.5.14:8080/logIn").buildUpon()
                .appendQueryParameter("nombreU",nombre)
                .appendQueryParameter("contrasena",contrasena)
                .build().toString()
            var user:User=User()
            val request: Request = Request.Builder()
                .url(uri)
                .build()
            val response:Response
            /*Try catch para manejar la excepcion de conexion*/
            try {
                response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                /*Si responde manda un objeto parsedo de json a user*/
                if (response.isSuccessful&&!responseBody.isNullOrEmpty()) {
                    val gson = Gson()
                    user=gson.fromJson(responseBody, User::class.java)
                    return@withContext user
                }else {
                    /*Si no returnea el user vacío para saber que el fallo ha sido en la peticion*/
                    return@withContext user
                }
            }catch(e:IOException){
                /*En cambio aqui returnea null para saber que el fallo ha sido en la conexion*/
                return@withContext null
            }

        }
    }
    suspend fun postUser(user: User): Boolean {
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val gson = Gson()
            val json = gson.toJson(user)
            val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
            val requestBody = json.toRequestBody(mediaType)
            val url = "http://192.168.5.14:8080/postUser"
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


    suspend fun deleteUser(dni:String):Boolean {
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val url = "http://192.168.5.14:8080/delete/$dni"

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

        suspend fun deleteReview(review:Review):Boolean {
            return withContext(Dispatchers.IO) {
                val client = OkHttpClient()
                val url = "http://192.168.5.14:8080/deleteReview"

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
            val url:String="http://192.168.5.14:8080/getUser/$dni"
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

    suspend fun getProductos(user:User?,ip:String):ArrayList<Product>{
        return withContext(Dispatchers.IO) {
            val products = object : TypeToken<ArrayList<Product>>() {}.type
            val client = OkHttpClient()
            val url:String= "http://$ip:8080/getUserProducts"
            val gson = Gson()
            val json = gson.toJson(user)
            val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
            val requestBody = json.toRequestBody(mediaType)
            var listProductos:ArrayList<Product> = arrayListOf()
            val request: Request = Request.Builder()
                .url(url)
                .post(requestBody)
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

    suspend fun getReviewsByUser(user:User):ArrayList<Review>{
        return withContext(Dispatchers.IO) {
            val reviews = object : TypeToken<ArrayList<Review>>() {}.type
            val client = OkHttpClient()
            val url:String= "http://192.168.5.14:8080/getReviewsUser"
            val gsonA = Gson()
            val json = gsonA.toJson(user)
            val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
            val requestBody = json.toRequestBody(mediaType)
            var listReviews:ArrayList<Review> = arrayListOf()
            val request: Request = Request.Builder()
                .url(url)
                .post(requestBody)
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

    suspend fun getAllCategories():ArrayList<Category>{
        return withContext(Dispatchers.IO) {
            val categories = object : TypeToken<ArrayList<Category>>() {}.type
            val client = OkHttpClient()
            val url:String= "http://192.168.5.14:8080/getCategories"
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

    suspend fun getAllReviews():ArrayList<Review>?{
        return withContext(Dispatchers.IO) {
            val reviews = object : TypeToken<ArrayList<Review>>() {}.type
            val client = OkHttpClient()
            val url:String= "http://192.168.5.14:8080/getReviews"
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
                return@withContext null
            }

        }
    }

    suspend fun getAllProducts():ArrayList<Product>?{
        return withContext(Dispatchers.IO) {
            val products = object : TypeToken<ArrayList<Product>>() {}.type
            val client = OkHttpClient()
            val url:String= "http://192.168.5.14:8080/getProducts"
            var listProducts:ArrayList<Product> = arrayListOf()
            val request: Request = Request.Builder()
                .url(url)
                .build()
            val response:Response
            try {
                response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                if (response.isSuccessful&&!responseBody.isNullOrEmpty()) {
                    val gson = Gson()
                    listProducts= gson.fromJson(responseBody, products)
                    return@withContext listProducts
                }else {
                    return@withContext listProducts
                }
            }catch(e:IOException){
                return@withContext null
            }

        }
    }

    suspend fun getProductsByCategorie(category: Category):ArrayList<Product>?{
        return withContext(Dispatchers.IO) {
            val products = object : TypeToken<ArrayList<Product>>() {}.type
            val client = OkHttpClient()
            val url:String= "http://192.168.5.14:8080/getProducts"
            val gsonA = Gson()
            val json = gsonA.toJson(category)
            val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
            val requestBody = json.toRequestBody(mediaType)
            var listProducts:ArrayList<Product> = arrayListOf()
            val request: Request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()
            val response:Response
            try {
                response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                if (response.isSuccessful&&!responseBody.isNullOrEmpty()) {
                    val gson = Gson()
                    listProducts= gson.fromJson(responseBody, products)
                    return@withContext listProducts
                }else {
                    return@withContext listProducts
                }
            }catch(e:IOException){
                return@withContext null
            }

        }
    }
}