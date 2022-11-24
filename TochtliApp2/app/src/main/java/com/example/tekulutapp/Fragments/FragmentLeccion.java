package com.example.tekulutapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.tekulutapp.Models.Leccion;
import com.example.tekulutapp.R;
import com.example.tekulutapp.RecibirPDFStream;
import com.example.tekulutapp.iComunicaFragment;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;


public class FragmentLeccion extends Fragment {

    PDFView pdfView;
    FirebaseFirestore dbl;
    String correo, leccionvieja;
    ArrayList<Leccion> listaLecciones;

    iComunicaFragment comunicaFragmentLeccion;
    Activity actividad;

    String urlPdf;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leccion,container,false);

        listaLecciones = new ArrayList<>();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        correo = user.getEmail().toString();
        SharedPreferences preferences = actividad.getSharedPreferences(
                "nav", Context.MODE_PRIVATE);
        leccionvieja = preferences.getString("leccion","xd");

        cargarPDF(correo);
        Button btn_Estado = view.findViewById(R.id.bEstado);
        pdfView = view.findViewById(R.id.pdfContainer);
        // Inflate the layout for this fragment

        //urlPdf = "https://firebasestorage.googleapis.com/v0/b/pp2-uca.appspot.com/o/OLA%20K%20ASE.pdf?alt=media&token=433132c8-1c44-422c-affb-7e147e7ce319";
        btn_Estado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbl.collection("leccion")
                        .whereEqualTo("nombre",leccionvieja)
                        .whereEqualTo("usuario",correo).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            Leccion lec = null;
                            for (DocumentSnapshot d : list) {
                                lec = d.toObject(Leccion.class);
                                //curso.setProgreso((int) progreso);
                            }
                            Toast.makeText(getContext(), "Completado", Toast.LENGTH_SHORT).show();
                            DocumentReference docRef = dbl.collection("leccion").document(lec.getNombre()+correo);
                            docRef.update("estado", true);
                            getActivity().onBackPressed();
                        }}});
                    }
                });

        System.out.println(listaLecciones);
        return view;
    }

    private void cargarPDF(String usuario){
        dbl = FirebaseFirestore.getInstance();
        dbl.collection("leccion")
                .whereEqualTo("nombre",leccionvieja)
                .whereEqualTo("usuario",correo)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> list= queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list){
                                Leccion c = d.toObject(Leccion.class);
                                System.out.println(c);
                                System.out.println("ola");
                                listaLecciones.add(c);
                                System.out.println("imprimiendo lista");
                                System.out.println(listaLecciones);
                                sort(listaLecciones);
                                mostrarPdf();
                            }
                        }
                    }
                });
    }

    public static void sort(ArrayList<Leccion> list) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            list.sort(((leccion, t1) -> leccion.getNombre().compareTo(t1.getNombre())));
        }
    }

    private void mostrarPdf(){
        urlPdf = listaLecciones.get(0).getUrl();
        new RecibirPDFStream(pdfView).execute(urlPdf);
    }

    @Override
    public void onAttach(@androidx.annotation.NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            this.actividad = (Activity) context;
            comunicaFragmentLeccion = (iComunicaFragment) this.actividad;
        }
    }
}