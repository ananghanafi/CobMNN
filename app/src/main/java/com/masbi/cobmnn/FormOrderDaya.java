package com.masbi.cobmnn;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.StorageReference;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.masbi.cobmnn.tools.MapWrapperLayout;
import com.masbi.cobmnn.tools.OnInfoWindowElemTouchListener;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class FormOrderDaya extends AppCompatActivity {

    private GoogleMap mMap;
    Geocoder geocoder;
    String lat;
    String lng;
    FloatingSearchView mSearchView;
    MapWrapperLayout mapWrapperLayout;
    List<Address> addresses;

    private ViewGroup infoWindow;
    private TextView infoTitle;
    private OnInfoWindowElemTouchListener infoButtonListener;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    FirebaseDatabase database;
    DatabaseReference myRef, pesan, pemesanan, cekbiaya;
    TextView harga;
    String time;
    String pilihan[];
    String[] hargaBaru;
    EditText nama, alamat, nohp, noplg;
    int userId;
    String namaS, alamatS, nohpS, id;
    int posisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_order_daya);
        lat = getIntent().getExtras().getString("lat");
        lng = getIntent().getExtras().getString("lon");
        harga = (TextView) findViewById(R.id.harga);
        nama = (EditText) findViewById(R.id.namaFormDaya);
        alamat = (EditText) findViewById(R.id.alamatFormDaya);
        nohp = (EditText) findViewById(R.id.nohpFormDaya);
        noplg = (EditText) findViewById(R.id.noFormplg);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        harga = (TextView) findViewById(R.id.harga);
        String setHarga;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        pemesanan = FirebaseDatabase.getInstance().getReference("pemesanan");
        cekbiaya = FirebaseDatabase.getInstance().getReference("cekbiaya");
        pesan = myRef.child("users");


        pilihan = new String[]{
                "450 VA",
                "900 VA",
                "1300 VA",
                "2200 VA",
                "3500 VA",
                "4400 VA",
                "5500 VA",

        };
        hargaBaru = new String[]{
                "??",
                "??",
                "??",
                "??",
                "??",
                "??",
                "??",

        };
        MaterialSpinner spinnerBaru = (MaterialSpinner) findViewById(R.id.spinnerBaru);
        spinnerBaru.setItems(pilihan);
        spinnerBaru.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                posisi = position;
                Snackbar.make(view, "Besar daya " + pilihan[position] + " seharga " + hargaBaru[position], Snackbar.LENGTH_LONG).show();
                //  String setHarga = harga.setText();
                //    Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });
        spinnerBaru.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Belum di pilih", Snackbar.LENGTH_LONG).show();
            }
        });
    }


    public void btCekBiayaDaya(View view) {
        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
        alBuilder.setTitle("Pemesanan");
        //  alBuilder.setIcon(R.drawable.ic_clear_black_24dp);
        alBuilder.setMessage("Biaya untuk penanabahan daya setiap pelanggan beda-beda, untuk itu isi no pelanggan/no meter nanti kami bantu" +
                " cek biaya (Sambung Telp Gerai MCC) ").setCancelable(false)
                .setPositiveButton("ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.out.println("Pilihan Ya");
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:05116723591")));
                        saveDatabaseDaya();
                        System.out.println("Cek Pmasangan daya");
                    }
                }).setNegativeButton("tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.out.println("Pilihan Tidak");

                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = alBuilder.create();
        alertDialog.show();

    }

    private void saveDatabaseDaya() {
        String str_nama = nama.getText().toString();
        String str_alamat = alamat.getText().toString();
        String str_nohp = nohp.getText().toString();
        String str_noplg = noplg.getText().toString();
        String str_id = cekbiaya.push().getKey();
        time = String.valueOf(ServerValue.TIMESTAMP);
//        AmbilData user = new AmbilData(str_alamat);

//        AmbilData user = new AmbilData("Pemasangan Baru ", str_nama, str_alamat, str_nohp
//                , pilihan[posisi], hargaBaru[posisi], lat, lng);
//        pesan.push().setValue(user);
        AmbilData user = new AmbilData(str_id, "Penambahan Daya ", str_nama, str_alamat, str_nohp
                , pilihan[posisi], hargaBaru[posisi], lat, lng, str_noplg);

//        AmbilData user = new AmbilData(str_id, "Pemasangan Daya ", str_nama, str_alamat, str_nohp
//                , pilihan[posisi], hargaBaru[posisi], lat, lng, time);
        cekbiaya.child(str_id).setValue(user);
    }

    public void btPesanTambahDaya(View view) {

        System.out.println("Alamatbt " + addresses);
        System.out.println("latitude " + lat);
        System.out.println("longitude " + lng);
     //   harga.setText("Besar daya " + pilihan[posisi] + " seharga " + hargaBaru[posisi]);
        AlertDialog.Builder blBuilder = new AlertDialog.Builder(this);
        blBuilder.setTitle("Pemesanan");
        //  alBuilder.setIcon(R.drawable.ic_clear_black_24dp);
        blBuilder.setMessage("Anda yakin pesan penambahan daya dengan besar daya " + pilihan[posisi]
                + " seharga tersebut").setCancelable(false)
                .setPositiveButton("ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.out.println("Pilihan Ya");
                        Toast.makeText(FormOrderDaya.this, "Pemesanan sedang di proses", Toast.LENGTH_SHORT).show();
//                        userId = userId + 1;
                        //       sendNotication();
                        saveDatabase();
                        //   PemasanganBaru.this.finish();
                        System.out.println("Cek Pmasangan daya");
                    }
                }).setNegativeButton("tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.out.println("Pilihan Tidak");

                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = blBuilder.create();
        alertDialog.show();
    }


    private void saveDatabase() {
        //Tidak Boleh Kosong
        String str_nama = nama.getText().toString();
        String str_alamat = alamat.getText().toString();
        String str_nohp = nohp.getText().toString();
        String str_id = pemesanan.push().getKey();
        String str_noplg = noplg.getText().toString();
        time = String.valueOf(ServerValue.TIMESTAMP);
//        AmbilData user = new AmbilData(str_alamat);

//        AmbilData user = new AmbilData("Pemasangan Baru ", str_nama, str_alamat, str_nohp
//                , pilihan[posisi], hargaBaru[posisi], lat, lng);
//        pesan.push().setValue(user);
        AmbilData user = new AmbilData(str_id, "Penambahan Daya", str_nama, str_alamat, str_nohp
                , pilihan[posisi], hargaBaru[posisi], lat, lng, str_noplg);

//        AmbilData user = new AmbilData(str_id, "Pemasangan Daya ", str_nama, str_alamat, str_nohp
//                , pilihan[posisi], hargaBaru[posisi], lat, lng, time);
        pemesanan.child(str_id).setValue(user);
//        pesan.child("users")
//                .push().setValue(user);
//       pesan.setValue("users", str_nama);
//        pesan.setValue(str_nama);
//        System.out.println("Nama: " + str_nama);
//        Map<String, PemasanganBaru> user = new HashMap<>();
//
//        user.put("cobaNama", new PemasanganBaru("" + str_nama, "" + str_alamat, "" + str_nohp));
//        String value = dataSnapshot.getValue(String.class);
//        Toast.makeText(PemasanganBaru.this, "Nama " + value, Toast.LENGTH_SHORT).show();
        System.out.println("Storage Firebase = gs://mapsplnbp-1517890549610.appspot.com");
        System.out.println("Database Firebase = https://mapsplnbp-1517890549610.firebaseio.com/");
//        pesan.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //       final String userId = "nama";
//        pesan.child("users").child(userId).addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        // Get user value
//
//                        writeNewPost(str_nama, str_alamat, str_nohp, userId);
//
////                        // [START_EXCLUDE]
////                        if (user == null) {
////                            // User is null, error out
////                            Toast.makeText(PemasanganBaru.this,
////                                    "Error: could not fetch user.",
////                                    Toast.LENGTH_SHORT).show();
////                        } else {
////                            // Write new post
////                            writeNewPost(userId, nama, alamat, nohp);
////                        }
//
//                        // Finish this Activity, back to the stream
//                        //   setEditingEnabled(true);
//                        finish();
//                        // [END_EXCLUDE]
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                        // [START_EXCLUDE]
//                        //   setEditingEnabled(true);
//                        // [END_EXCLUDE]
//                    }
//                });
//        // [END single_value_read]
    }

//    // [START write_fan_out]
//    private void writeNewPost(String userId, String username, String title, String body) {
//        // Create new post at /user-posts/$userid/$postid and at
//        // /posts/$postid simultaneously
//        String key = pesan.child("posts").push().getKey();
//        Post post = new Post(userId, username, title, body);
//        Map<String, Object> postValues = post.toMap();
//
//        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("/posts/" + key, postValues);
//        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
//
//        pesan.updateChildren(childUpdates);
//    }
//    // [END write_fan_out]

//    @Override
//    protected void onStart() {
//        super.onStart();
//        pesan.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                AmbilData ambilData = dataSnapshot.getValue(AmbilData.class);
//                System.out.println(ambilData);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        pesan.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                String value = dataSnapshot.getValue(String.class);
//            //    Toast.makeText(PemasanganBaru.this, "Nama " + value , Toast.LENGTH_SHORT).show();
//                System.out.println("Value "+value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(PemasanganBaru.this, "Error", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void sendNotication() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    try {
                        String jsonResponse;

                        URL url = new URL("https://onesignal.com/api/v1/notifications");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setUseCaches(false);
                        connection.setDoInput(true);
                        connection.setDoInput(true);
                        String jaj = "ananghanafi13@gamil.com";

                        connection.setRequestProperty("Content-Type", "aplication/json charset=UTF-8");
                        connection.setRequestProperty("Authorization", "Basic ZDY3MDI4M2EtMDIzOS00NGUyLWIyYzAtMzg5ZDNiYzAxZDMz");
                        connection.setRequestMethod("POST");

                        String strJsonBody = "{"
                                + "\"app_id\":\"f476f46a-3dc4-4b69-9959-51fce19465d4\","
                                + "\"filters\":[{\"field\",\"tag\",\"key\":\"User_ID\",\"relation\":\"value\":\"" + jaj + "\"}],"
                                + "\"data\":[\"foo\": \"bar\"],"
                                + "\"contents\":[\"en\": \"Ada pesanan\"],"
                                + "}";

                        System.out.println("JsonBody " + strJsonBody);
                        byte[] sendByte = strJsonBody.getBytes("UTF-8");
                        connection.setFixedLengthStreamingMode(sendByte.length);

                        OutputStream outputStream = connection.getOutputStream();
                        outputStream.write(sendByte);

                        int httpResponse = connection.getResponseCode();
                        System.out.println("http response: " + httpResponse);

                        if (httpResponse == HttpURLConnection.HTTP_OK && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                            Scanner scanner = new Scanner(connection.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();

                        } else {
                            Scanner scanner = new Scanner(connection.getErrorStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        }
                        System.out.println("jsonRespone" + jsonResponse);

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("");
                }
            }
        });

    }

}
