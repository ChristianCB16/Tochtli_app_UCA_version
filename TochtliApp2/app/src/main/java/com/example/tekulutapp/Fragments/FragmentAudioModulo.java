package com.example.tekulutapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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

import com.example.tekulutapp.Adapter.AdapterVideoModulo;
import com.example.tekulutapp.MainActivity;
import com.example.tekulutapp.Models.Modulo;
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

public class FragmentAudioModulo extends Fragment {
    AdapterVideoModulo adapterModulo;
    RecyclerView recycler_modulos;
    ArrayList<Modulo> listaModulos;
    FirebaseFirestore dbm;
    String correo,cursoviejo;

    //referencias comunicar fragment
    iComunicaFragment comunicaFragment;
    //com.example.tochtliapp.iComunicaFragment iComunicaFragment;
    Activity actividad;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vid_modulo,container,false);

        recycler_modulos = view.findViewById(R.id.recycler_videos_modulos);
        listaModulos = new ArrayList<>();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        //Toast.makeText(this, user.getEmail().toString(), Toast.LENGTH_SHORT).show();
        correo = user.getEmail().toString();

        Bundle objetoModulo = getArguments();
        //Curso curso = null;
        //if(objetoModulo != null){
        SharedPreferences preferences = actividad.getSharedPreferences(
                "nav", Context.MODE_PRIVATE);
        cursoviejo = preferences.getString("curso4","xd");

        //curso = (Curso) objetoModulo.getSerializable("objeto");
        //Toast.makeText(getContext(), curso.getNombre(), Toast.LENGTH_SHORT).show();
        //cursoviejo = curso.getNombre();
        //Toast.makeText(getContext(), cursoviejo, Toast.LENGTH_SHORT).show();

        cargarLista(correo);
        //}
        return view;

    }
    private void cargarLista(String usuario) {
        dbm = FirebaseFirestore.getInstance();
        dbm.collection("modulo").whereEqualTo("curso",cursoviejo).whereEqualTo("usuario",correo).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing
                                // that list to our object class.
                                Modulo c = d.toObject(Modulo.class);
                                //Toast.makeText(getContext(), c.getNombre(), Toast.LENGTH_SHORT).show();
                                // and we will pass this object class
                                // inside our arraylist which we have
                                // created for recycler view.
                                //listaCursos.add(c.getCorreo(),c.getDescripcion(),c.getNombre());
                                //if(c.getUsuario() == correo && c.getCurso() == cursoviejo){
                                listaModulos.add(new Modulo(c.getNombre() +correo,c.getCurso(), c.getDescripcion(), c.getNombre(),c.getProgreso(), c.getEstado()));
                                // }

                            }
                            sort(listaModulos);
                            mostrarLista();
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(getContext(), "No se encontraron Modulos disponibles", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // if we do not get any data or any error we are displaying
                        // a toast message that we do not get any data
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });


    }
    public static void sort(ArrayList<Modulo> list) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            list.sort(((modulo, t1) -> modulo.getNombre().compareTo(t1.getNombre())));
        }
    }
    private void mostrarLista() {
        recycler_modulos.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterModulo = new AdapterVideoModulo(getContext(),listaModulos);
        recycler_modulos.setAdapter(adapterModulo);

        adapterModulo.setOnClickListener(view -> {
            // String nom = listaCursos.get(mirecycler_cursos.getChildAdapterPosition(view)).getNombre();

            comunicaFragment.enviarAudioModulo(listaModulos.get(recycler_modulos.getChildAdapterPosition(view)));

        });

    }
    //@Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            this.actividad = (Activity) context;
            comunicaFragment = (iComunicaFragment) this.actividad;
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
