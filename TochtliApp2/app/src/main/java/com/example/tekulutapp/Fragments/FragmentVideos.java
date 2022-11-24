package com.example.tekulutapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tekulutapp.Adapter.AdapterVideos;
import com.example.tekulutapp.Models.Videos;
import com.example.tekulutapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FragmentVideos extends Fragment {

    AdapterVideos adapterVideos;
    RecyclerView mirecycler_videos;
    ArrayList<Videos> listavids;
    FirebaseFirestore db;
    String leccionvieja;
    //String correo;

    Activity actividad;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.videos_fragment,container,false);

        mirecycler_videos = view.findViewById(R.id.recycler_videos);
        listavids = new ArrayList<>();
        actividad = (Activity) getContext();
        SharedPreferences preferences = actividad.getSharedPreferences(
                "nav",Context.MODE_PRIVATE);

        leccionvieja = preferences.getString("leccion2","xd");
        cargarLista();
        return view;
    }

    private void cargarLista() {

        db = FirebaseFirestore.getInstance();
        //Query query = db.collection("cursos");
        //db.collection("cursos").whereEqualTo("usuario",correo).get()              Ejemplo de consultas
        db.collection("videos").whereEqualTo("Leccion",leccionvieja).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Videos c = d.toObject(Videos.class);
                                listavids.add(new Videos(c.getNombre(), c.getUrl()));
                                //Toast.makeText(getContext(), c.getUrl(), Toast.LENGTH_SHORT).show();
                            }
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
        mirecycler_videos.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterVideos = new AdapterVideos(getContext(),listavids);
        mirecycler_videos.setAdapter(adapterVideos);



    }
}
