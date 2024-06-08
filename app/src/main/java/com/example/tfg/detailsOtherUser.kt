package com.example.tfg

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tfg.adapters.reviewAdapter
import com.example.tfg.petitionsAndFunctions.SharedPreff
import com.example.tfg.petitionsAndFunctions.generalFunctions
import com.example.tfg.petitionsAndFunctions.httPettitions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Review
import model.User

class detailsOtherUser : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_other_user)



        /*Declaracion de elementos visuales*/
        val nombre:TextView=findViewById(R.id.userName2)
        val p:TextView=findViewById(R.id.cantProductos)
        val r:TextView=findViewById(R.id.cantResenas)
        val profileP:ImageView=findViewById(R.id.userPhoto)
        val lista:ListView=findViewById(R.id.listaResenasU)
        val backReview: ImageButton =findViewById(R.id.backR)
        /*Decalaracion de elementos de uso*/
        val pettitions=httPettitions()
        val context: Context =baseContext
        val preferncias=SharedPreff(context)
        val list:ArrayList<Review> = arrayListOf()
        val functions=generalFunctions()
        /*Recuperamos el usuario*/
        val user=intent.getStringExtra("usuario").toString()
        val imagen: ByteArray? =intent.getByteArrayExtra("image")
        /*Lo dividimos en partes*/
        val partes=user.split("/")
        /*Asignamos a cada campo su valor*/
        nombre.text=partes[1]
        nombre.setTextColor(Color.BLACK)
        p.text=partes[9]
        p.setTextColor(Color.BLACK)
        r.text=partes[8]
        r.setTextColor(Color.BLACK)
        profileP.setImageBitmap(imagen?.let { functions.byteArrayToBitmap(it) })
        val adapterLista= reviewAdapter(context,list)
        lista.adapter=adapterLista
        lifecycleScope.launch(Dispatchers.Main) {
            var listaR:ArrayList<Review>?= arrayListOf()
            withContext(Dispatchers.IO){
               listaR=pettitions.getReviewsByUser(User(partes[0]),preferncias.getIp(context))
            }
            if(listaR==null){
                    Toast.makeText(this@detailsOtherUser,this@detailsOtherUser.getString(R.string.problemas),
                        Toast.LENGTH_SHORT).show()
            }else{
              list.clear()
              list.addAll(listaR!!)
              adapterLista.notifyDataSetChanged()
            }
        }

        backReview.setOnClickListener {
            val intentR= Intent(this,detailsReview::class.java)
            startActivity(intentR)
        }
    }
}