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

import com.example.tekulutapp.Models.Curso;
import com.example.tekulutapp.R;

import java.util.ArrayList;

public class AdapterCurso extends RecyclerView.Adapter<AdapterCurso.ViewHolder> implements View.OnClickListener{
    ArrayList<Curso> model;
    LayoutInflater inflater;
    //LISTENER
    private View.OnClickListener listener;
    public AdapterCurso(Context context, ArrayList<Curso> model) {
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public AdapterCurso.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.view_curso_single,parent,false);
        view.setOnClickListener(this);

        return new AdapterCurso.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCurso.ViewHolder holder, int position) {
        String name = model.get(position).getNombre();
        int i = model.get(position).getProgreso();
        String prog = String.valueOf(i);
        holder.txt_curso_name1.setText(name);
        holder.txt_progreso.setText(prog + " %");
        String desc = model.get(position).getDescripcion();
        holder.avance_bar.setProgress(i);

        holder.txt_detalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.txt_curso_name1.getContext());
                builder.setMessage(desc).setTitle("Curso: "+ name).show();

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
        TextView txt_curso_name1, txt_progreso,txt_detalles;
        ProgressBar avance_bar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_curso_name1 = itemView.findViewById(R.id.txt_curso_name);
            txt_progreso = itemView.findViewById(R.id.txt_progreso);
            txt_detalles = itemView.findViewById(R.id.txt_detalles);
            avance_bar = itemView.findViewById(R.id.avance_bar);

        }
    }
}
