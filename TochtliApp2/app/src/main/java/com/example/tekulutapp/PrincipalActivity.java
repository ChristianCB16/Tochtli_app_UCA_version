package com.example.tekulutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.tekulutapp.Fragments.FragmentAudioCurso;
import com.example.tekulutapp.Fragments.FragmentAudioLleccion;
import com.example.tekulutapp.Fragments.FragmentAudioModulo;
import com.example.tekulutapp.Fragments.FragmentAudios;
import com.example.tekulutapp.Fragments.FragmentLeccion;
import com.example.tekulutapp.Fragments.FragmentLecciones;
import com.example.tekulutapp.Fragments.FragmentQuiz;
import com.example.tekulutapp.Fragments.FragmentQuizCurso;
import com.example.tekulutapp.Fragments.FragmentQuizLeccion;
import com.example.tekulutapp.Fragments.FragmentQuizModulo;
import com.example.tekulutapp.Fragments.FragmentVideoCurso;
import com.example.tekulutapp.Fragments.FragmentVideoLeccion;
import com.example.tekulutapp.Fragments.FragmentVideoModulo;
import com.example.tekulutapp.Fragments.FragmentModulos;
import com.example.tekulutapp.Fragments.FragmentVideos;
import com.example.tekulutapp.Fragments.MainFragment;
import com.example.tekulutapp.Models.Curso;
import com.example.tekulutapp.Models.Leccion;
import com.example.tekulutapp.Models.Modulo;
import com.google.android.material.navigation.NavigationView;

public class PrincipalActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,iComunicaFragment{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    //Set MainFragmennt on screen
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    //
    FragmentModulos fragmentModulos;
    FragmentVideoModulo fragmentVideoModulo;
    FragmentLecciones fragmentLecciones;
    FragmentLeccion fragmentLeccion;
    FragmentVideoLeccion fragmentVideoLeccion;

    FragmentQuiz fragmentQuiz;
    FragmentVideos fragmentVideos;

    //Quiz
    //FragmentQuizCurso fragmentQuizCurso;
    FragmentQuizModulo  fragmentQuizModulo;
    FragmentQuizLeccion fragmentQuizLeccion;
    //
    FragmentAudioModulo fragmentAudioModulo;
    FragmentAudioLleccion fragmentAudioLleccion;
    FragmentAudios fragmentAudios;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        //set elemento On Click
        navigationView.setNavigationItemSelectedListener(this);


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        //Set MainFragmennt on screen
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container,new MainFragment());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== R.id.mihome){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,new MainFragment());
            fragmentTransaction.commit();
        }
        if(item.getItemId()== R.id.Cerrar_sesion){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,new MainFragment.FragmentSalir());
            fragmentTransaction.commit();
        }
        if(item.getItemId() == R.id.videos){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,new FragmentVideoCurso());
            fragmentTransaction.commit();
        }if(item.getItemId() == R.id.quiz){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,new FragmentQuizCurso());
            fragmentTransaction.commit();
        }
        if(item.getItemId() == R.id.audios){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,new FragmentAudioCurso());
            fragmentTransaction.commit();
        }

        return false;
    }

    @Override
    public void enviarCurso(Curso cursito) {
        fragmentModulos = new FragmentModulos();
        //Bundle bundleEnvio = new Bundle();
        //bundleEnvio.putSerializable("objeto",cursito);
        //
        SharedPreferences prefs = getSharedPreferences("nav", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("curso",cursito.getNombre());
        editor.commit();

        //fragmentModulos.setArguments(bundleEnvio);
        // abrir fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragmentModulos);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void enviarModulo(Modulo modulito) {
        fragmentLecciones = new FragmentLecciones();
        //Bundle bundleEnvio = new Bundle();
        //bundleEnvio.putSerializable("objeto2",modulito);
        //fragmentModulos.setArguments(bundleEnvio);
        SharedPreferences prefs = getSharedPreferences("nav", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("modulo",modulito.getNombre());
        editor.commit();
        // abriri fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragmentLecciones);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void enviarLeccion(Leccion leccionsita) {
        fragmentLeccion = new FragmentLeccion();
        //Bundle bundleEnvio = new Bundle();
        //bundleEnvio.putSerializable("objeto3",leccionsita);
        //fragmentClase.setArguments(bundleEnvio);
        SharedPreferences prefs = getSharedPreferences("nav", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("leccion",leccionsita.getNombre());
        editor.commit();
        // abriri fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragmentLeccion);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    //Se agregan funciones enviar para apartado de videos
    @Override
    public void enviarCursoVideo(Curso cursito) {
        fragmentVideoModulo = new FragmentVideoModulo();
        //Bundle bundleEnvio = new Bundle();
        //bundleEnvio.putSerializable("objeto",cursito);
        //
        SharedPreferences prefs = getSharedPreferences("nav", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("curso2",cursito.getNombre());
        editor.commit();

        //fragmentModulos.setArguments(bundleEnvio);
        // abrir fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragmentVideoModulo);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void enviarModuloVideo(Modulo modulito) {
        fragmentVideoLeccion = new FragmentVideoLeccion();
        //Bundle bundleEnvio = new Bundle();
        //bundleEnvio.putSerializable("objeto2",modulito);
        //fragmentModulos.setArguments(bundleEnvio);
        SharedPreferences prefs = getSharedPreferences("nav", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("modulo2",modulito.getNombre());
        editor.commit();
        // abriri fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragmentVideoLeccion);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void enviarLeccionVideo(Leccion leccionsita) {
        fragmentVideos = new FragmentVideos();

        SharedPreferences prefs = getSharedPreferences("nav", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("leccion2",leccionsita.getNombre());
        editor.commit();// abriri fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragmentVideos);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    // Se agregan enviar en apartado quiz
    @Override
    public void enviarQuizCurso(Curso cursito) {
        fragmentQuizModulo = new FragmentQuizModulo();
        //Bundle bundleEnvio = new Bundle();
        //bundleEnvio.putSerializable("objeto",cursito);
        //
        SharedPreferences prefs = getSharedPreferences("nav", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("curso3",cursito.getNombre());
        editor.commit();

        //fragmentModulos.setArguments(bundleEnvio);
        // abrir fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragmentQuizModulo);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void enviarModuloQuiz(Modulo modulito) {
        fragmentQuizLeccion = new FragmentQuizLeccion();
        //Bundle bundleEnvio = new Bundle();
        //bundleEnvio.putSerializable("objeto2",modulito);
        //fragmentModulos.setArguments(bundleEnvio);
        SharedPreferences prefs = getSharedPreferences("nav", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("modulo3",modulito.getNombre());
        editor.commit();
        // abriri fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragmentQuizLeccion);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void enviarLeccionQuiz(Leccion leccionsita) {
        fragmentQuiz = new FragmentQuiz();

        SharedPreferences prefs = getSharedPreferences("nav", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("leccion3",leccionsita.getNombre());
        editor.commit();// abriri fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragmentQuiz);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    // Se agregan enviar en apartado AUDIOS
    @Override
    public void enviarAudioCurso(Curso cursito) {
        fragmentAudioModulo = new FragmentAudioModulo();
        //Bundle bundleEnvio = new Bundle();
        //bundleEnvio.putSerializable("objeto",cursito);
        //
        SharedPreferences prefs = getSharedPreferences("nav", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("curso4",cursito.getNombre());
        editor.commit();

        //fragmentModulos.setArguments(bundleEnvio);
        // abrir fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragmentAudioModulo);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void enviarAudioModulo(Modulo modulito) {
        fragmentAudioLleccion = new FragmentAudioLleccion();
        //Bundle bundleEnvio = new Bundle();
        //bundleEnvio.putSerializable("objeto2",modulito);
        //fragmentModulos.setArguments(bundleEnvio);
        SharedPreferences prefs = getSharedPreferences("nav", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("modulo4",modulito.getNombre());
        editor.commit();
        // abriri fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragmentAudioLleccion);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void enviarAudioLeccion(Leccion leccionsita) {
        fragmentAudios = new FragmentAudios();

        SharedPreferences prefs = getSharedPreferences("nav", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("leccion4",leccionsita.getNombre());
        editor.commit();// abriri fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragmentAudios);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}