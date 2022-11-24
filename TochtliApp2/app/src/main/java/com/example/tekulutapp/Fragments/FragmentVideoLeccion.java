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

import com.example.tekulutapp.Adapter.AdapterVideoLeccion;
import com.example.tekulutapp.MainActivity;
import com.example.tekulutapp.Models.Leccion;
import com.example.tekulutapp.R;
import com.example.tekulutapp.iComunicaFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FragmentVideoLeccion extends Fragment {

    AdapterVideoLeccion adapterLeccion;
    RecyclerView recycler_lecciones;
    ArrayList<Leccion> listaLecciones;
    FirebaseFirestore dbl;
    DocumentReference docRef;
    //
    String correo,moduloviejo;

    //referencias comunicar fragment
    iComunicaFragment comunicaFragmentLecciones;
    Activity actividad;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lecciones,container,false);


        recycler_lecciones = view.findViewById(R.id.recycler_lecciones);
        listaLecciones = new ArrayList<>();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        correo = user.getEmail().toString();
        SharedPreferences preferences = actividad.getSharedPreferences(
                "nav", Context.MODE_PRIVATE);
        moduloviejo = preferences.getString("modulo2","xd");

        cargarLista(correo);

        return view;
    }

    private void cargarLista(String usuario) {
        dbl = FirebaseFirestore.getInstance();
        //Query query = db.collection("cursos");
        dbl.collection("leccion").whereEqualTo("modulo",moduloviejo).whereEqualTo("usuario",correo).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Leccion c = d.toObject(Leccion.class);
                                listaLecciones.add(c);
                            }
                            // after adding the data to recycler view.
                            // we are calling recycler view notifuDataSetChanged
                            // method to notify that data has been changed in recycler view.
                            //mirecycler_cursos.notifyDataSetChanged();
                            sort(listaLecciones);
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
    public static void sort(ArrayList<Leccion> list) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            list.sort(((leccion, t1) -> leccion.getNombre().compareTo(t1.getNombre())));
        }
    }
    private void mostrarLista() {
        recycler_lecciones.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterLeccion = new AdapterVideoLeccion(getContext(),listaLecciones);
        recycler_lecciones.setAdapter(adapterLeccion);

        adapterLeccion.setOnClickListener(view -> {
            comunicaFragmentLecciones.enviarLeccionVideo(listaLecciones.get(recycler_lecciones.getChildAdapterPosition(view)));
        });

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            this.actividad = (Activity) context;
            comunicaFragmentLecciones = (iComunicaFragment) this.actividad;
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
