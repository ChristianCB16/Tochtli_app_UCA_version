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

import com.example.tekulutapp.Adapter.AdapterLeccion;
import com.example.tekulutapp.MainActivity;
import com.example.tekulutapp.Models.Leccion;
import com.example.tekulutapp.Models.Modulo;
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

public class FragmentLecciones extends Fragment {

    AdapterLeccion adapterLeccion;
    RecyclerView recycler_lecciones;
    ArrayList<Leccion> listaLecciones;
    FirebaseFirestore dbl;
    DocumentReference docRef;
    //
    String correo,moduloviejo;
    int total, completos;
    float progreso;
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
        //Toast.makeText(this, user.getEmail().toString(), Toast.LENGTH_SHORT).show();
        correo = user.getEmail().toString();
        SharedPreferences preferences = actividad.getSharedPreferences(
                "nav",Context.MODE_PRIVATE);
        moduloviejo = preferences.getString("modulo","xd");

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
                            total = 0;
                            completos = 0;
                            for (DocumentSnapshot d : list) {
                                Leccion c = d.toObject(Leccion.class);
                                System.out.println(c);
                                System.out.println("ola");
                                //cursoActu[total] = c.getCurso();
                                total++;
                                if(c.getEstado()){

                                    completos++;
                                    //Toast.makeText(getContext(),  "completos" + completos, Toast.LENGTH_SHORT).show();
                                }
                                listaLecciones.add(c);
                            }

                            //Toast.makeText(getContext(),  completos, Toast.LENGTH_SHORT).show();
                            if(completos==total){
                                progreso = 100;
                                dbl.collection("modulo").whereEqualTo("nombre",moduloviejo).whereEqualTo("usuario",correo).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                        if (!queryDocumentSnapshots.isEmpty()) {
                                            //Toast.makeText(getContext(),  "completos", Toast.LENGTH_SHORT).show();
                                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                                            Modulo mod = null;
                                            for (DocumentSnapshot d : list) {
                                                mod = d.toObject(Modulo.class);
                                                //curso.setProgreso((int) progreso);
                                            }
                                            //Toast.makeText(getContext(), "completo", Toast.LENGTH_SHORT).show();
                                            DocumentReference docRef = dbl.collection("modulo").document(mod.getNombre()+correo);
                                            docRef.update("progreso", progreso);
                                            docRef.update("estado", true);
                                        }}});
                            }
                            else{
                            progreso = Math.round(((float)completos / total)*100);
                            //Toast.makeText(getContext(),  "completos" + progreso, Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getContext(), "a"+progreso, Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getContext(), "P"+completos, Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getContext(), "T"+total, Toast.LENGTH_SHORT).show();
                            dbl.collection("modulo").whereEqualTo("nombre",moduloviejo).whereEqualTo("usuario",correo).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                    if (!queryDocumentSnapshots.isEmpty()) {
                                        //Toast.makeText(getContext(),  "completos", Toast.LENGTH_SHORT).show();
                                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                                        Modulo mod = null;
                                        for (DocumentSnapshot d : list) {
                                            mod = d.toObject(Modulo.class);
                                            //curso.setProgreso((int) progreso);
                                        }
                                        //Toast.makeText(getContext(), "completo", Toast.LENGTH_SHORT).show();
                                        DocumentReference docRef = dbl.collection("modulo").document(mod.getNombre()+correo);
                                        docRef.update("progreso", progreso);
                                    }}});
                            }
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
        adapterLeccion = new AdapterLeccion(getContext(),listaLecciones);
        recycler_lecciones.setAdapter(adapterLeccion);

        adapterLeccion.setOnClickListener(view -> {
            comunicaFragmentLecciones.enviarLeccion(listaLecciones.get(recycler_lecciones.getChildAdapterPosition(view)));
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
