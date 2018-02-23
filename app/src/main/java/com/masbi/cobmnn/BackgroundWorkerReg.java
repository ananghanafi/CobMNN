package com.masbi.cobmnn;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by AnangHanafi on 17/02/2018.
 */

public class BackgroundWorkerReg extends AsyncTask<String, String, String> {
    Context context;
    AlertDialog alertDialog;

    BackgroundWorkerReg(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "http://127.0.0.1/plnb/login.php";
        String daftar_url = "http://127.0.0.1/plnb/daftar.php";

        if (type.equals("daftar")) {
            try {
                String nama = params[1];
                String user_name = params[2];
                String alamat = params[3];
                String email = params[4];
                String no_hp = params[5];
                String user_pass = params[6];
                System.out.println("nama =" + nama);
                URL url = new URL(daftar_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("nama", "UTF-8") + "=" + URLEncoder.encode(nama, "UTF-8") + "&" +
                        URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" +
                        URLEncoder.encode("alamat", "UTF-8") + "=" + URLEncoder.encode(alamat, "UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("no_hp", "UTF-8") + "=" + URLEncoder.encode(no_hp, "UTF-8") + "&" +
                        URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder.encode(user_pass, "UTF-8");
                bufferedWriter.write(post_data);
                System.out.println("Post Data "+ post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String hasil = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    hasil += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                System.out.println("Daftar sfldjf;jf;sjfsjf;sjf;sfj;s");
                return hasil;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {

            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Status");
    }

    @Override
    protected void onPostExecute(String s) {
        alertDialog.setMessage(s);
        alertDialog.show();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }
}
