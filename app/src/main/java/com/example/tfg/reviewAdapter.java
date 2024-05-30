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

import model.Review;

public class reviewAdapter extends BaseAdapter {
        private ArrayList<Review> reviews;
        private Context context;
        public reviewAdapter(Context context, ArrayList<Review> reviews){
            this.context=context;
            this.reviews=reviews;
        }
        @Override
        public int getCount() {
            return reviews.size();
        }

        @Override
        public Object getItem(int i) {
            return reviews.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }


        @NonNull
        @Override
        @SuppressLint("MissingInflatedId")
        public View getView(int position, @Nullable View view, @Nullable ViewGroup parent) {
            LayoutInflater layout= LayoutInflater.from(context);
            View elemento= layout.inflate(R.layout.plantilla_review,parent,false);
            Review currentReview=(Review)getItem(position);
            TextView nombre=elemento.findViewById(R.id.nombreR);
            TextView puntuacion=elemento.findViewById(R.id.precioP);
            TextView descripcion=elemento.findViewById(R.id.descripcionR);

            nombre.setText(currentReview.getNombre());
            puntuacion.setText( String.valueOf(currentReview.getPuntuacion()));
            descripcion.setText(currentReview.getDescripcion());
            descripcion.setText(currentReview.getDescripcion());
            return elemento;
        }
    }

