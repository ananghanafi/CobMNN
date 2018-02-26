package com.masbi.cobmnn;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private EditText InEmail, InPass, InPassConf;
    private Button BtLogin, BtDaftar;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        InEmail = (EditText) findViewById(R.id.email);
        InPass = (EditText) findViewById(R.id.password);
        InPassConf = (EditText) findViewById(R.id.confpassword);
        BtDaftar = (Button) findViewById(R.id.signUp);
        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressReg);

    }

    public void signUp(View view) {
        String email = InEmail.getText().toString();
        String password = InPass.getText().toString();
        String confpassword = InPassConf.getText().toString();
//        String type = "login";
//        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
//        backgroundWorker.execute(type, email, password);
        System.out.println("onLogin sfldjf;jf;sjfsjf;sjf;sfj;s");
        progressBar.setVisibility(View.VISIBLE);
//        finish();
        if (email.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            InEmail.setError("Email harus diisi!!!");
            InEmail.requestFocus();
            return;
        } else if (password.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            InPass.setError("Password harus diisi!!!");
            InPass.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            progressBar.setVisibility(View.GONE);
            InEmail.setError("Masukkan email yang benar");
            InEmail.requestFocus();
            return;
        } else if (password.length() < 6) {
            progressBar.setVisibility(View.GONE);
            InPass.setError("Password harus lebih dari 6");
            InPass.requestFocus();
            return;
        } else if (confpassword.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            InPassConf.setError("Konfirmasi password");
            InPassConf.requestFocus();
            return;
        } else if (password.equals(confpassword)) {
            progressBar.setVisibility(View.GONE);
            InPassConf.setError("Password tidak sama");
            InPass.setError("Password tidak sama");
            InPassConf.requestFocus();
            InPass.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
//                            finish();
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "Pendaftaran Sukses", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Register.this, Login.class));

                        } else {
                            Toast.makeText(getApplicationContext(), "Pendaftaran tidak sukses", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "Kamu sudah terdaftar", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplication(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        // ...
                    }
                });

    }
}
