package com.example.tekulutapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tekulutapp.Models.Modulo;
import com.example.tekulutapp.R;

import java.util.ArrayList;

public class AdapterVideoModulo extends RecyclerView.Adapter<AdapterVideoModulo.ViewHolder> implements View.OnClickListener{
    ArrayList<Modulo> model;
    LayoutInflater inflater;
    //LISTENER
    private View.OnClickListener listener;
    public AdapterVideoModulo(Context context, ArrayList<Modulo> model) {
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }
    @NonNull
    @Override
    public AdapterVideoModulo.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view_videomodulo,parent,false);
        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = model.get(position).getNombre();
        holder.txt_modulo_name.setText(name);
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }

    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_modulo_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_modulo_name = itemView.findViewById(R.id.txt_modulo_name);

        }
    }

}
