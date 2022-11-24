package com.example.tekulutapp.Fragments;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tekulutapp.Adapter.AdapterAudios;
import com.example.tekulutapp.Models.Videos;
import com.example.tekulutapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FragmentAudios extends Fragment {

    AdapterAudios adapterAudios;
    RecyclerView mirecycler_audios;
    ArrayList<Videos> listavids;
    FirebaseFirestore db;
    String leccionvieja;
    //String correo;
    CardView tarjeta;
    Activity actividad;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.audios_fragment,container,false);

        mirecycler_audios = view.findViewById(R.id.recycler_audios);
        tarjeta = view.findViewById(R.id.tarjeta_audio);
        listavids = new ArrayList<>();
        actividad = (Activity) getContext();
        SharedPreferences preferences = actividad.getSharedPreferences(
                "nav", Context.MODE_PRIVATE);

        leccionvieja = preferences.getString("leccion4","xd");
        cargarLista();


        return view;
    }

    private void cargarLista() {

        db = FirebaseFirestore.getInstance();
        //Query query = db.collection("cursos");
        //db.collection("cursos").whereEqualTo("usuario",correo).get()              Ejemplo de consultas
        db.collection("Listen").whereEqualTo("Leccion",leccionvieja).get()
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
        mirecycler_audios.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterAudios = new AdapterAudios(getContext(),listavids);
        mirecycler_audios.setAdapter(adapterAudios);
        adapterAudios.setOnClickListener(view -> {
            // String nom = listaCursos.get(mirecycler_cursos.getChildAdapterPosition(view)).getNombre();

                    String audioUrl = "";
                    audioUrl = listavids.get(mirecycler_audios.getChildAdapterPosition(view)).getUrl();

                    // initializing media player
                    MediaPlayer mediaPlayer = new MediaPlayer();

                    // below line is use to set the audio
                    // stream type for our media player.
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                    // below line is use to set our
                    // url to our media player.
                    try {
                        mediaPlayer.setDataSource(audioUrl);
                        // below line is use to prepare
                        // and start our media player.
                        mediaPlayer.prepare();
                        mediaPlayer.start();

                    }  catch (IOException e) {
                        e.printStackTrace();
                    }
                    // below line is use to display a toast message.
                    Toast.makeText(getContext(), "El audio se esta reproduciendo", Toast.LENGTH_SHORT).show();


            });





    }
}
