package com.example.tekulutapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tekulutapp.Models.Modulo;
import com.example.tekulutapp.R;

import java.util.ArrayList;


public class AdapterModulo extends RecyclerView.Adapter<AdapterModulo.ViewHolder> implements View.OnClickListener{
    ArrayList<Modulo> model;
    LayoutInflater inflater;
    //LISTENER
    private View.OnClickListener listener;
    public AdapterModulo(Context context, ArrayList<Modulo> model) {
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view_modulo_single,parent,false);
        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = model.get(position).getNombre();
        int i = model.get(position).getProgreso();
        String prog = String.valueOf(i);
        holder.txt_modulo_name.setText(name);
        holder.txt_progreso_m.setText(prog + " %");
        String desc = model.get(position).getDescripcion();
        holder.avance_bar_modulo.setProgress(i);

        holder.txt_detallesmodulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.txt_modulo_name.getContext());
                builder.setMessage(desc).setTitle("Modulo: "+ name).show();

            }
        });



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
        TextView txt_modulo_name, txt_progreso_m,txt_detallesmodulo;
        ProgressBar avance_bar_modulo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_modulo_name = itemView.findViewById(R.id.txt_modulo_name);
            txt_progreso_m = itemView.findViewById(R.id.txt_progreso_m);
            txt_detallesmodulo = itemView.findViewById(R.id.txt_detallesmodulo);
            avance_bar_modulo = itemView.findViewById(R.id.avance_bar_modulo);

        }
    }
}
