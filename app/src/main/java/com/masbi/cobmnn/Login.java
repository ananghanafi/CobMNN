package com.masbi.cobmnn;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private EditText InEmail, InPass;
    private Button BtLogin, BtDaftar;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InEmail = (EditText) findViewById(R.id.email);
        InPass = (EditText) findViewById(R.id.password);
        BtLogin = (Button) findViewById(R.id.BtLogin);
        BtDaftar = (Button) findViewById(R.id.daftar);
        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.login_progress);

   }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (mAuth.getCurrentUser() != null) {
//            finish();
//            Intent keHome = new Intent(Login.this, MainActivity.class);
//            keHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(keHome);
//        }
//    }

    public void onLogin(View view) {
        String email = InEmail.getText().toString();
        String password = InPass.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
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
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            progressBar.setVisibility(View.GONE);
            InEmail.setError("Masukkan email yang benar");
            InEmail.requestFocus();
            return;
        }
//        finish();
//        String type = "login";
//        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
//        backgroundWorker.execute(type, email, password);
        System.out.println("onLogin sfldjf;jf;sjfsjf;sjf;sfj;s");
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
//                    finish();
                    Intent keHome = new Intent(Login.this, MainActivity.class);
                    keHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(keHome);
                } else {
                    Toast.makeText(getApplication(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Toast.makeText(getApplicationContext(), "createUserWithEmail:success",Toast.LENGTH_SHORT).show();
//
//                        } else {
//                            // If sign in fails, display a message to the user.
////                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
////                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
////                                    Toast.LENGTH_SHORT).show();
////                            updateUI(null);
//                        }
//
//                        // ...
//                    }
//                });

    }


    public void onDaftar(View view) {
//        finish();
        startActivity(new Intent(this, Register.class));
    }

}
