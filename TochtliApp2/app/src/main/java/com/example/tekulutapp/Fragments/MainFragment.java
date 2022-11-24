package com.example.tekulutapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tekulutapp.Adapter.AdapterCurso;
import com.example.tekulutapp.MainActivity;
import com.example.tekulutapp.Models.Curso;
import com.example.tekulutapp.R;
import com.example.tekulutapp.iComunicaFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    AdapterCurso adapterCurso;
    RecyclerView mirecycler_cursos;
    ArrayList<Curso> listaCursos;
    FirebaseFirestore db;
    String correo;
    //referencias comunicar fragment

    iComunicaFragment iComunicaFragment;
    Activity actividad;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment,container,false);

        mirecycler_cursos = view.findViewById(R.id.recycler_cursos);
        listaCursos = new ArrayList<>();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        //Toast.makeText(this, user.getEmail().toString(), Toast.LENGTH_SHORT).show();
        correo = user.getEmail().toString();

        cargarLista(correo);
        return view;

    }
    private void cargarLista(String usuario) {
        db = FirebaseFirestore.getInstance();
        //Query query = db.collection("cursos");
        //db.collection("cursos").whereEqualTo("usuario",correo).get()              Ejemplo de consultas
        db.collection("cursos").whereEqualTo("usuario",correo).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received
                        // query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are
                            // hiding our progress bar and adding
                            // our data in a list.
                            //loadingPB.setVisibility(View.GONE);
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing
                                // that list to our object class.
                                Curso c = d.toObject(Curso.class);

                                listaCursos.add(new Curso(c.getCorreo(), c.getDescripcion(), c.getNombre(),c.getProgreso()));
                                }
                                //Toast.makeText(getContext(), c.getNombre(), Toast.LENGTH_SHORT).show();
                                // and we will pass this object class
                                // inside our arraylist which we have
                                // created for recycler view.
                                //listaCursos.add(c.getCorreo(),c.getDescripcion(),c.getNombre());

                            // after adding the data to recycler view.
                            // we are calling recycler view notifuDataSetChanged
                            // method to notify that data has been changed in recycler view.
                            //mirecycler_cursos.notifyDataSetChanged();
                            mostrarLista();
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(getContext(), "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // if we do not get any data or any error we are displaying
                        // a toast message that we do not get any data
                        Toast.makeText(getContext(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });


    }
    private void mostrarLista() {
        mirecycler_cursos.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterCurso = new AdapterCurso(getContext(),listaCursos);
        mirecycler_cursos.setAdapter(adapterCurso);

        adapterCurso.setOnClickListener(view -> {
           // String nom = listaCursos.get(mirecycler_cursos.getChildAdapterPosition(view)).getNombre();
            iComunicaFragment.enviarCurso(listaCursos.get(mirecycler_cursos.getChildAdapterPosition(view)));
            //Toast.makeText(getContext(),"xd",Toast.LENGTH_LONG).show();
        });

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            this.actividad = (Activity) context;
            iComunicaFragment = (iComunicaFragment) this.actividad;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public static class FragmentSalir extends Fragment {
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_salir, container, false);
            Button btn_cerrar = (Button) view.findViewById(R.id.btn_cerrar) ;

            btn_cerrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(getContext(), "La sesion fue cerrada con exito.",Toast.LENGTH_LONG).show();
                    goLogin();
                }
            });

            return view;
        }


        public void goLogin(){
            Intent i = new Intent(getContext(), MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    }
}


