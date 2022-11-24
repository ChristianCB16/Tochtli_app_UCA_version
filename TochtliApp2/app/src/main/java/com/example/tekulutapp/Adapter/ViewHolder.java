package com.example.tekulutapp.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tekulutapp.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView audio;
    CardView btn;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        audio = itemView.findViewById(R.id.txt_audioname);
        btn = itemView.findViewById(R.id.tarjeta_audio);

    }
}
