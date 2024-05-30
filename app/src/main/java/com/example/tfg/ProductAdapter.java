package com.example.tfg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import model.Product;

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
        View elemento= layout.inflate(R.layout.plantilla_producto,parent,false);
        Product currentProduct=(Product)getItem(position);
        TextView nombre=elemento.findViewById(R.id.nombreP);
        TextView precio=elemento.findViewById(R.id.precioP);
        TextView descripcion=elemento.findViewById(R.id.descripcionP);
        nombre.setText(currentProduct.getNombreP());
        precio.setText( String.valueOf(currentProduct.getPrecio()));
        descripcion.setText(currentProduct.getDescripcionP());
        return elemento;
    }
}
