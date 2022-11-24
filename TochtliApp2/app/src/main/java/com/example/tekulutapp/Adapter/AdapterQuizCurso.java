package com.example.tekulutapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tekulutapp.Models.Curso;
import com.example.tekulutapp.R;

import java.util.ArrayList;

public class AdapterQuizCurso extends RecyclerView.Adapter<AdapterQuizCurso.ViewHolder> implements View.OnClickListener{

    ArrayList<Curso> model;
    LayoutInflater inflater;
    //LISTENER
    private View.OnClickListener listener;
    public AdapterQuizCurso(Context context, ArrayList<Curso> model) {
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public AdapterQuizCurso.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.view_videocurso,parent,false);
        view.setOnClickListener(this);

        return new AdapterQuizCurso.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = model.get(position).getNombre();
        holder.txt_curso_name1.setText(name);
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
        TextView txt_curso_name1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_curso_name1 = itemView.findViewById(R.id.txt_curso_name);
        }
    }
}
