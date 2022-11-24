package com.example.tekulutapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tekulutapp.Models.Leccion;
import com.example.tekulutapp.R;

import java.util.ArrayList;


public class AdapterLeccion extends RecyclerView.Adapter<AdapterLeccion.ViewHolder> implements View.OnClickListener{
    ArrayList<Leccion> model;
    LayoutInflater inflater;
    //LISTENER
    private View.OnClickListener listener;
    public AdapterLeccion(Context context, ArrayList<Leccion> model) {
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public AdapterLeccion.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view_leccion_single,parent,false);
        view.setOnClickListener(this);

        return new AdapterLeccion.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLeccion.ViewHolder holder, int position) {
        String name = model.get(position).getNombre();
        holder.txt_leccion_name.setText(name);
        String desc = model.get(position).getDescripcion();
        holder.txt_detallesleccion.setText(desc);
        boolean estado = model.get(position).getEstado();
        if(estado == true){
            holder.txt_estado.setText("Completada");
        }

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
        TextView txt_leccion_name, txt_detallesleccion, txt_estado;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_leccion_name = itemView.findViewById(R.id.txt_leccion_name);
            txt_detallesleccion = itemView.findViewById(R.id.txt_detallesleccion);
            txt_estado = itemView.findViewById(R.id.txt_estado);
        }
    }

}
