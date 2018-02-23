package com.masbi.cobmnn;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Daftar extends AppCompatActivity {
    EditText nama, username, alamat, email, no_hp, password;
    String str_nama, str_username, str_alamat, str_email, str_no_hp, str_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        nama = (EditText) findViewById(R.id.nama);
        username = (EditText) findViewById(R.id.username);
        alamat = (EditText) findViewById(R.id.alamat);
        email = (EditText) findViewById(R.id.email);
        no_hp = (EditText) findViewById(R.id.telp);
        password = (EditText) findViewById(R.id.password);
    }

    public void daftarMain(View view) {
        str_nama = nama.getText().toString();
        str_username = username.getText().toString();
        str_alamat = alamat.getText().toString();
        str_email = email.getText().toString();
        str_no_hp = no_hp.getText().toString();
        str_password = password.getText().toString();
        String tipe = "daftar";
        if (str_nama.equals("") || str_email.equals("") || str_alamat.equals("") || str_no_hp.equals("") || str_username.equals("") || str_password.equals("")) {
            Toast.makeText(Daftar.this, "Harus terisi semua", Toast.LENGTH_LONG).show();
            return;
        } else if (str_username.length() <= 3 || password.length() <= 3) {
            Toast.makeText(Daftar.this, "Username atau password harus lebih dari 3 karakter", Toast.LENGTH_LONG).show();
            return;
        }else{
            BackgroundWorkerReg backgroundWorkerReg = new BackgroundWorkerReg(this);
            backgroundWorkerReg.execute(tipe, str_nama, str_username, str_alamat, str_email, str_no_hp, str_password);
            System.out.println("onDaftar sfldjf;jf;sjfsjf;sjf;sfj;s");
        }

    }
}
