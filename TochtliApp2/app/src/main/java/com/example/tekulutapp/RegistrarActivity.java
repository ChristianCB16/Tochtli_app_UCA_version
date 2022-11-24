package com.example.tekulutapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.tekulutapp.Models.Curso;
import com.example.tekulutapp.Models.Leccion;
import com.example.tekulutapp.Models.Modulo;
import com.example.tekulutapp.Models.Preguntas;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
//import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RegistrarActivity extends AppCompatActivity {
    FirebaseFirestore db;
    ArrayList<Curso> listaCursos = new ArrayList<>();
    ArrayList<Modulo> listaModulos = new ArrayList<>();
    ArrayList<Leccion> listaLecciones = new ArrayList<>();
    ArrayList<Preguntas> listaPreguntas = new ArrayList<>();
    //Declaraciones Firebase

    FirebaseAuth firebaseAuth;
    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        //Declaracion Campos y botones
        EditText email = findViewById(R.id.text_emailR);
        EditText nombre = findViewById(R.id.text_name);
        EditText passwd = findViewById(R.id.text_passwd);
        EditText telefono = findViewById(R.id.text_telefono);
        Button btn_registro = findViewById(R.id.btn_registro);




        firebaseAuth = FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.text_emailR, Patterns.EMAIL_ADDRESS,R.string.invalid_mail);
        awesomeValidation.addValidation(this,R.id.text_passwd,".{6,}",R.string.invalid_pasword);

        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString();
                String pass = passwd.getText().toString();

                String name = nombre.getText().toString();
                String num = telefono.getText().toString();

                if(awesomeValidation.validate() && name != "" && num !=""){
                    firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                cursos(mail);
                                modulos(mail);
                                lecciones(mail);
                                quiiz(mail);
                                Toast.makeText(RegistrarActivity.this, "Usuario Creado Exitosamente",Toast.LENGTH_LONG).show();
                                finish();
                            }else{
                                String errorCode = ((FirebaseAuthException)task.getException()).getErrorCode();
                                Toast.makeText(RegistrarActivity.this,errorCode + " : Ha ocurrido un error inesperado",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });




                }else{
                    Toast.makeText(RegistrarActivity.this,"Complete todos los campos correctamente",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    void cursos(String mail){
        db = FirebaseFirestore.getInstance();
        //Query query = db.collection("cursos");
        //db.collection("cursos").whereEqualTo("usuario",correo).get()              Ejemplo de consultas
        db.collection("cursos").whereEqualTo("usuario","doradeahugo@gmail.com").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Curso c = d.toObject(Curso.class);
                                listaCursos.add(new Curso(c.getCorreo(), c.getDescripcion(), c.getNombre(),c.getProgreso()));
                            }
                            cargarCursos(mail);
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // if we do not get any data or any error we are displaying
                        // a toast message that we do not get any data
                        //Toast.makeText(this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    void cargarCursos(String mail){
        int num = listaCursos.size();
        for(int i =0; i<num;i++)
        {
            Map<String, Object> curso = new HashMap<>();
            Curso cursosubida = listaCursos.get(i);
            curso.put("descripcion", cursosubida.getDescripcion());
            curso.put("nombre", cursosubida.getNombre());
            curso.put("progreso", 0);
            curso.put("usuario", mail.toLowerCase(Locale.ROOT));

            db.collection("cursos").document(cursosubida.getNombre()+mail.toLowerCase(Locale.ROOT))
                    .set(curso);
        }


    }
    void modulos(String mail){
        db = FirebaseFirestore.getInstance();
        //Query query = db.collection("cursos");
        //db.collection("cursos").whereEqualTo("usuario",correo).get()              Ejemplo de consultas
        db.collection("modulo").whereEqualTo("usuario","doradeahugo@gmail.com").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Modulo c = d.toObject(Modulo.class);
                                listaModulos.add(c);
                            }
                            cargarModulos(mail);
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // if we do not get any data or any error we are displaying
                        // a toast message that we do not get any data
                        //Toast.makeText(this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    void cargarModulos(String mail){
        int num = listaModulos.size();
        for(int i =0; i<num;i++)
        {
            Map<String, Object> modulo = new HashMap<>();
            Modulo moduloSubida = listaModulos.get(i);
            modulo.put("Progreso", 0);
            modulo.put("curso", moduloSubida.getCurso());
            modulo.put("descripcion", moduloSubida.getDescripcion());
            modulo.put("nombre", moduloSubida.getNombre());
            modulo.put("usuario", mail.toLowerCase(Locale.ROOT));
            modulo.put("estado", moduloSubida.getEstado());

            db.collection("modulo").document(moduloSubida.getNombre()+mail.toLowerCase(Locale.ROOT))
                    .set(modulo);
        }
}
    void lecciones(String mail){
        db = FirebaseFirestore.getInstance();
        //Query query = db.collection("cursos");
        //db.collection("cursos").whereEqualTo("usuario",correo).get()              Ejemplo de consultas
        db.collection("leccion").whereEqualTo("usuario","doradeahugo@gmail.com").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Leccion c = d.toObject(Leccion.class);
                                listaLecciones.add(c);
                            }
                            cargarLecciones(mail);
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // if we do not get any data or any error we are displaying
                        // a toast message that we do not get any data
                        //Toast.makeText(this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    void cargarLecciones(String mail){
        int num = listaLecciones.size();
        for(int i =0; i<num;i++)
        {
            Map<String, Object> leccion = new HashMap<>();
            Leccion leccionsubida = listaLecciones.get(i);
            leccion.put("curso", leccionsubida.getCurso());
            leccion.put("descripcion", leccionsubida.getDescripcion());
            leccion.put("modulo", leccionsubida.getModulo());
            leccion.put("nombre", leccionsubida.getNombre());
            leccion.put("usuario", mail.toLowerCase(Locale.ROOT));
            leccion.put("estado", leccionsubida.getEstado());
            leccion.put("url", leccionsubida.getUrl());


            db.collection("leccion").document(leccionsubida.getNombre()+mail.toLowerCase(Locale.ROOT))
                    .set(leccion);
        }
    }
    void quiiz(String mail){
        db = FirebaseFirestore.getInstance();
        db.collection("Preguntas").whereEqualTo("correo","doradeahugo@gmail.com").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Preguntas c = d.toObject(Preguntas.class);
                                listaPreguntas.add(new Preguntas(c.getCorreo(),c.getLeccion(),c.getNota(),c.getPregunta()));

                            }
                            cargarQuiz(mail);
                        } else {
                            Toast.makeText(RegistrarActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegistrarActivity.this, "Fail to get data", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getContext(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    void cargarQuiz(String mail){
        int num = listaPreguntas.size();
        for(int i =0; i<num;i++)
        {
            Map<String, Object> leccion = new HashMap<>();
            Preguntas preguntaSubida = listaPreguntas.get(i);

            leccion.put("correo", mail.toLowerCase(Locale.ROOT));
            leccion.put("nota", preguntaSubida.getNota());
            leccion.put("Leccion", preguntaSubida.getLeccion());
            leccion.put("pregunta", preguntaSubida.getPregunta());

            db.collection("Preguntas")
                    .add(leccion)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        }
    }



}

/*
firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegistrarActivity.this, "Usuario Creado Exitosamente",Toast.LENGTH_LONG).show();


                                finish();
                            }else{
                                String errorCode = ((FirebaseAuthException)task.getException()).getErrorCode();
                                Toast.makeText(RegistrarActivity.this,errorCode + " : Ha ocurrido un error inesperado",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
* */

/*
FirebaseFirestore db = FirebaseFirestore.getInstance();
                    Map<String, Object> curso = new HashMap<>();
                    curso.put("descripcion", "prueba");
                    curso.put("nombre", "prueba");
                    curso.put("progreso", 5);
                    curso.put("usuario", "hugoe@gmail.com");

                    db.collection("cursos")
                            .add(curso)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });
* */