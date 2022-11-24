package com.example.tekulutapp.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tekulutapp.Models.Correctas;
import com.example.tekulutapp.Models.Preguntas;
import com.example.tekulutapp.Models.Respuestas;
import com.example.tekulutapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FragmentQuiz extends Fragment {
    String leccionvieja;
    Activity actividad;
    ArrayList<Preguntas> listaPreguntas = new ArrayList<>();
    ArrayList<Respuestas> listaRespuestas = new ArrayList<>();
    ArrayList<Correctas> listaCorrectas = new ArrayList<>();
    
    FirebaseFirestore db;    
    String correo;
    Button R1;
    Button R2;
    Button R3;
    Button R4;
    Button continuar;


    TextView llenar_numero;
    TextView llenar_pregunta;

    String respuestaG;

    float nota;
    float aciertos;
    float num_preguntas;
    int actual;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz,container,false);

        listaCorrectas = new ArrayList<>();

        R1 = view.findViewById(R.id.R1);
        R2 = view.findViewById(R.id.R2);
        R3 = view.findViewById(R.id.R3);
        R4 = view.findViewById(R.id.R4);
        continuar = view.findViewById(R.id.Avanzar);
        llenar_numero = view.findViewById(R.id.num_preguntas);
        llenar_pregunta = view.findViewById(R.id.llenar_pregunta);

        respuestaG = "";

        actual = 0;
        nota = 0;
        aciertos = 0;


        actividad = (Activity) getContext();
        SharedPreferences preferences = actividad.getSharedPreferences(
                "nav", Context.MODE_PRIVATE);
        leccionvieja = preferences.getString("leccion3","xd");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        correo = user.getEmail().toString();
        
        cargarPreguntas(correo, leccionvieja);
        
        return view;
    }



    private void cargarCorrectas(String leccionvieja) {

        db = FirebaseFirestore.getInstance();
        db.collection("Correctas").whereEqualTo("Leccion",leccionvieja).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Correctas c = d.toObject(Correctas.class);
                                listaCorrectas.add(c);
                            }
                            cargarpantalla(listaPreguntas.size());

                        } else {
                            Toast.makeText(getContext(), "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void cargarRespuestas( String leccionvieja) {
        db = FirebaseFirestore.getInstance();
        //Query query = db.collection("cursos");
        //db.collection("cursos").whereEqualTo("usuario",correo).get()              Ejemplo de consultas
        db.collection("Respuestas").whereEqualTo("Leccion",leccionvieja).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Respuestas c = d.toObject(Respuestas.class);
                                listaRespuestas.add(c);
                            }
                            cargarCorrectas( leccionvieja);
                        } else {
                            Toast.makeText(getContext(), "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void cargarPreguntas(String correo, String leccionvieja) {

        db = FirebaseFirestore.getInstance();
        //Query query = db.collection("cursos");
        //db.collection("cursos").whereEqualTo("usuario",correo).get()              Ejemplo de consultas
        db.collection("Preguntas").whereEqualTo("Leccion",leccionvieja).whereEqualTo("correo",correo).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Preguntas c = d.toObject(Preguntas.class);
                                listaPreguntas.add(c);
                                Toast.makeText(getContext(), c.getLeccion(), Toast.LENGTH_SHORT).show();
                            }
                            num_preguntas = listaPreguntas.size();

                            cargarRespuestas(leccionvieja);
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(getContext(), "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void cargarpantalla(int numero) {
            if(actual < numero){


                llenar_numero.setText(String.valueOf(actual +1));

                String preg = listaRespuestas.get(actual).getPregunta();
                llenar_pregunta.setText(preg);

                R1.setText(listaRespuestas.get(actual).getR1());
                R2.setText(listaRespuestas.get(actual).getR2());
                R3.setText(listaRespuestas.get(actual).getR3());
                R4.setText(listaRespuestas.get(actual).getR4());
                R1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        respuestaG = listaRespuestas.get(actual).getR1();
                        R1.setBackgroundColor(Color.rgb(130,48,56));
                        R2.setBackgroundColor(Color.rgb(164,186,183));
                        R3.setBackgroundColor(Color.rgb(164,186,183));
                        R4.setBackgroundColor(Color.rgb(164,186,183));
                    }
                });
                R2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        respuestaG = listaRespuestas.get(actual).getR2();
                        R2.setBackgroundColor(Color.rgb(130,48,56));
                        R1.setBackgroundColor(Color.rgb(164,186,183));
                        R3.setBackgroundColor(Color.rgb(164,186,183));
                        R4.setBackgroundColor(Color.rgb(164,186,183));
                    }
                });
                R3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        respuestaG = listaRespuestas.get(actual).getR3();
                        R3.setBackgroundColor(Color.rgb(130,48,56));
                        R2.setBackgroundColor(Color.rgb(164,186,183));
                        R1.setBackgroundColor(Color.rgb(164,186,183));
                        R4.setBackgroundColor(Color.rgb(164,186,183));
                    }
                });
                R4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        respuestaG = listaRespuestas.get(actual).getR4();
                        R4.setBackgroundColor(Color.rgb(130,48,56));
                        R2.setBackgroundColor(Color.rgb(164,186,183));
                        R3.setBackgroundColor(Color.rgb(164,186,183));
                        R1.setBackgroundColor(Color.rgb(164,186,183));
                    }
                });

                continuar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        actual++;
                        verificar(preg,respuestaG );
                        R1.setBackgroundColor(Color.rgb(164,186,183));
                        R2.setBackgroundColor(Color.rgb(164,186,183));
                        R3.setBackgroundColor(Color.rgb(164,186,183));
                        R4.setBackgroundColor(Color.rgb(164,186,183));

                    }
                });
            }else{
                nota = aciertos/num_preguntas;
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Su calificacion es: " + String.valueOf(nota *10) + "\n\nCorrectas: " + String.valueOf(aciertos)+
                        "\n\nSobre: "+ String.valueOf(num_preguntas) ).setTitle("Resultados").show();

                getActivity().onBackPressed();

            }
    }

    private void verificar(String pregunta, String respuesta) {
        for( int i = 0; i< num_preguntas;){

            if( listaCorrectas.get(i).getPregunta().equals(pregunta)){
                Toast.makeText(getContext(), "se encontro la pregunta" , Toast.LENGTH_SHORT).show();
                if(listaCorrectas.get(i).getRespuesta().equals(respuesta)){
                    Toast.makeText(getContext(), "se encontro la respuesta" , Toast.LENGTH_SHORT).show();
                    aciertos++;
                    break;
                }else{

                    Toast.makeText(getContext(), respuesta , Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), "R " + listaCorrectas.get(i).getRespuesta() , Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            i++;
        }
        cargarpantalla(((int) num_preguntas));

    }


}
