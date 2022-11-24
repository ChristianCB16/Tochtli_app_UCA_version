package com.example.tekulutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    ImageButton btn_GoogleSignin;
    Button btn_register;
    Button btn_ingresar;
    Button btn_forgot;
    EditText email;
    EditText text_contra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //btn_GoogleSignin = findViewById(R.id.btn_Google);
        btn_register = findViewById(R.id.btn_register);
        btn_ingresar = findViewById(R.id.button_loggin);
        btn_forgot = findViewById(R.id.btn_forgot);
        email = findViewById(R.id.text_email);
        text_contra = findViewById(R.id.text_contra);
        AwesomeValidation awesomeValidation;
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            irAHome();
        }
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.text_email, Patterns.EMAIL_ADDRESS, R.string.invalid_mail);
        awesomeValidation.addValidation(this, R.id.text_contra, ".{6,}", R.string.invalid_pasword);

        /*btn_GoogleSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(MainActivity.this, GoogleSignInActivity.class);
                startActivity(i);
            }
        });
*/
        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RegistrarActivity.class);
                startActivity(i);
            }
        });
        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {
                    String mail = email.getText().toString();
                    String pass = text_contra.getText().toString();

                    firebaseAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                irAHome();
                            } else {
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                Toast.makeText(MainActivity.this, errorCode + " : Ha ocurrido un error inesperado", Toast.LENGTH_LONG).show();
                            }
                        }


                    });
                }
            }
        });
        //Reset password
        btn_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "You can reset your password now", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, ForgotPasswordActivity.class));
            }
        });
    }
        private void irAHome() {
            Intent i = new Intent(this,PrincipalActivity.class);
            i.putExtra("email",email.getText().toString());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        /*Intent i = new Intent(this,Activity_Test.class);
        i.putExtra("email",email.getText().toString());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);*/


    }
}