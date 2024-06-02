package com.example.tfg.petitionsAndFunctions

import android.net.Uri
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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


    suspend fun getUser(nombre:String,contrasena:String,ip:String): User? {
        /*La hago suspend para poder lanzarla en hilo secundario*/
        /*El return este te lo pone automático pero basicamente le estás diciendo
        que te mande la respuesta al hilo secundario
         */
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            /*Peticion en este caso con nombre y contraseña*/
            val uri:String= Uri.parse("http://$ip:8080/logIn").buildUpon()
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
                    val gson = GsonBuilder()
                        .registerTypeAdapter(ByteArray::class.java, ByteArrayFunctions())
                        .create()
                   val  userF=gson.fromJson(responseBody, User::class.java)
                    return@withContext userF
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
    suspend fun postUser(user: User,ip:String): Boolean? {
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val gson = Gson()
            val json = gson.toJson(user)
            val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
            val requestBody = json.toRequestBody(mediaType)
            val url = "http://$ip:8080/postUser"
            val request: Request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()
            try{

            val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
            if (response.isSuccessful&&!responseBody.isNullOrEmpty()) {
                return@withContext true
            }else{
                return@withContext false
            }
        }catch (exception:IOException){
            return@withContext  null
        }
        }
    }


    suspend fun deleteUser(dni:String,ip:String):Boolean? {
        /*Le mando el DNI para que pueda buscar a usuario por DNI*/
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val url = "http://$ip:8080/deleteUser/$dni"

            val request = Request.Builder()
                .url(url)
                .delete()
                .build()

            try {
                /*Try catch para que no crashee por error de conexion*/
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                /*Verificar si el DNI del usuario encontrado coincide con el DNI buscado y que se ha borrado
                Si es así devuelve true, si no devuelve false
                 */
                if (response.isSuccessful&&!responseBody.isNullOrEmpty()) {
                    return@withContext true
                }else{
                    return@withContext false
                }
            }catch (exception:IOException){
                return@withContext  null
            }
        }
    }

        suspend fun deleteReview(id:Int,ip:String):Boolean? {
            return withContext(Dispatchers.IO) {
                val client = OkHttpClient()
                val url = "http://$ip:8080/deleteReview/$id"

                val request = Request.Builder()
                    .url(url)
                    .delete()
                    .build()

                try{
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                if (response.isSuccessful&&!responseBody.isNullOrEmpty()) {
                    return@withContext true
                }else{
                    return@withContext false
                }
            }catch (exception:IOException){
                return@withContext  null
            }
            }
    }

    suspend fun getUserByDNI(user:User,ip:String): User? {
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val userEmpty=User()
            val url:String="http://$ip:8080/getUser"
            val gson = Gson()
            val json = gson.toJson(user)
            val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
            val requestBody = json.toRequestBody(mediaType)
            val request: Request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()
            val response:Response
            try {
                response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                if (response.isSuccessful&&!responseBody.isNullOrEmpty()) {
                    /*Transformamos el response body recibido en nuestra clase usuario*/
                    val gson2 = GsonBuilder()
                        .registerTypeAdapter(ByteArray::class.java, ByteArrayFunctions())
                        .create()
                    val useR=gson2.fromJson(responseBody, User::class.java)
                    return@withContext useR
                }else {
                    return@withContext userEmpty
                }
            }catch(e:IOException){
                return@withContext null
            }

        }
    }

    suspend fun getProductos(user:User?,ip:String):ArrayList<Product>?{
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
                return@withContext null
            }

        }
    }

    suspend fun getReviewsByUser(user:User,ip:String):ArrayList<Review>?{
        return withContext(Dispatchers.IO) {
            val reviews = object : TypeToken<ArrayList<Review>>() {}.type
            val client = OkHttpClient()
            val url:String= "http://$ip:8080/getReviewsUser"
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
                return@withContext null
            }

        }
    }

    suspend fun getAllCategories(ip:String):ArrayList<Category>?{
        return withContext(Dispatchers.IO) {
            val categories = object : TypeToken<ArrayList<Category>>() {}.type
            val client = OkHttpClient()
            val url:String= "http://$ip:8080/getCategories"
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
                return@withContext null
            }

        }
    }

    suspend fun getAllReviews(ip:String):ArrayList<Review>?{
        return withContext(Dispatchers.IO) {
            val reviews = object : TypeToken<ArrayList<Review>>() {}.type
            val client = OkHttpClient()
            val url:String= "http://$ip:8080/getReviews"
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

    suspend fun getAllProducts(ip:String):ArrayList<Product>?{
        return withContext(Dispatchers.IO) {
            val products = object : TypeToken<ArrayList<Product>>() {}.type
            val client = OkHttpClient()
            val url:String= "http://$ip:8080/getProducts"
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

    suspend fun getReviewsByCategorie(category: Category,ip:String):ArrayList<Review>?{
        return withContext(Dispatchers.IO) {
            val reviewType = object : TypeToken<ArrayList<Review>>() {}.type
            val client = OkHttpClient()
            val url:String= "http://$ip:8080/getPBC"
            val gsonA = Gson()
            val json = gsonA.toJson(category)
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
                    listReviews= gson.fromJson(responseBody, reviewType)
                    return@withContext listReviews
                }else {
                    return@withContext listReviews
                }
            }catch(e:IOException){
                return@withContext null
            }

        }
    }

    suspend fun postReview(review: Review,ip:String): Boolean? {
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val gson = Gson()
            val json = gson.toJson(review)
            val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
            val requestBody = json.toRequestBody(mediaType)
            val url = "http://$ip:8080/postReview"
            val request: Request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()
            try{
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                if (response.isSuccessful&&!responseBody.isNullOrEmpty()) {
                    return@withContext true
                }else{
                    return@withContext false
                }
            }catch (exception:IOException){
                return@withContext  null
            }
        }
    }
    /*Por falta de tiempo no las implementé, pero tenía pensado hacerlo*/
    /*suspend fun bajaUser(user:User,ip:String):Boolean? {
        /*Le mando el DNI para que pueda buscar a usuario por DNI*/
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val url = "http://$ip:8080/darDeBaja"
            val gson = Gson()
            val json = gson.toJson(user)
            val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
            val requestBody = json.toRequestBody(mediaType)
            val request = Request.Builder()
                .url(url)
                .put(requestBody)
                .build()

            try {
                /*Try catch para que no crashee por error de conexion*/
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                /*Verificar si el DNI del usuario encontrado coincide con el DNI buscado y que se ha borrado
                Si es así devuelve true, si no devuelve false
                 */
                if (response.isSuccessful&&!responseBody.isNullOrEmpty()) {
                    return@withContext true
                }else{
                    return@withContext false
                }
            }catch (exception:IOException){
                return@withContext  null
            }
        }
    }*/
   /* suspend fun reactivarUsuario(user:User,ip:String):Boolean?{
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val gson = Gson()
            val json = gson.toJson(user)
            val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
            val requestBody = json.toRequestBody(mediaType)
            val url = "http://$ip:8080/reactivarUser"
            val request: Request = Request.Builder()
                .url(url)
                .put(requestBody)
                .build()
            try{
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                if (response.isSuccessful&&!responseBody.isNullOrEmpty()) {
                    return@withContext true
                }else{
                    return@withContext false
                }
            }catch (exception:IOException){
                return@withContext  null
            }
        }
    }*/
    suspend fun modifyReview(review: Review,ip:String): Boolean? {
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val gson = Gson()
            val json = gson.toJson(review)
            val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
            val requestBody = json.toRequestBody(mediaType)
            val url = "http://$ip:8080/updateR"
            val request: Request = Request.Builder()
                .url(url)
                .put(requestBody)
                .build()
            try{
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                if (response.isSuccessful&&!responseBody.isNullOrEmpty()) {
                    return@withContext true
                }else{
                    return@withContext false
                }
            }catch (exception:IOException){
                return@withContext  null
            }
        }
    }
    suspend fun getProductsHigh(ip:String):ArrayList<Product>?{
        return withContext(Dispatchers.IO) {
            val products = object : TypeToken<ArrayList<Product>>() {}.type
            val client = OkHttpClient()
            val url:String= "http://$ip:8080/highProducts"
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
                return@withContext null
            }

        }
    }
    suspend fun getProductsLow(ip:String):ArrayList<Product>?{
        return withContext(Dispatchers.IO) {
            val products = object : TypeToken<ArrayList<Product>>() {}.type
            val client = OkHttpClient()
            val url:String= "http://$ip:8080/lowProducts"
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
                return@withContext null
            }

        }
    }

    suspend fun modifyUser(user: User,ip:String): Boolean? {
        return withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val gson = Gson()
            val json = gson.toJson(user)
            val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
            val requestBody = json.toRequestBody(mediaType)
            val url = "http://$ip:8080/updateUser"
            val request: Request = Request.Builder()
                .url(url)
                .put(requestBody)
                .build()
            try{
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string()
                if (response.isSuccessful&&!responseBody.isNullOrEmpty()) {
                    return@withContext true
                }else{
                    return@withContext false
                }
            }catch (exception:IOException){
                return@withContext  null
            }
        }
    }
}