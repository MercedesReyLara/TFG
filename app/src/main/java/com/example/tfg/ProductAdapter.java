package com.example.tfg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import model.Product;
import model.User;

@SuppressLint("MissingInflatedId")
public class ProductAdapter extends BaseAdapter {
    private ArrayList<Product> products;
    private Context context;
    public ProductAdapter(Context context, ArrayList<Product> products){
        this.context=context;
        this.products=products;
    }
    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @Nullable ViewGroup parent) {
        LayoutInflater layout= LayoutInflater.from(context);
        View elemento= layout.inflate(R.layout.plantilla_listview,parent,false);
        Product currentProduct=(Product)getItem(position);
        TextView nombre=elemento.findViewById(R.id.nombreP);
        TextView precio=elemento.findViewById(R.id.precioP);
        TextView descripcion=elemento.findViewById(R.id.descripcionPR);
        ImageView imagen=elemento.findViewById(R.id.imagenP);

        nombre.setText(currentProduct.getNombreP());
        precio.setText( String.valueOf(currentProduct.getPrecio()));
        descripcion.setText(currentProduct.getDescripcionP());
        /*byte[] imagenBytes = currentUser.getProfileP();
        if (imagenBytes != null && imagenBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagenBytes, 0, imagenBytes.length);
            imagen.setImageBitmap(bitmap);
        }*/
        return elemento;
    }
}
