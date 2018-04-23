package com.masbi.cobmnn;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.masbi.cobmnn.tools.MapWrapperLayout;
import com.masbi.cobmnn.tools.OnInfoWindowElemTouchListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class FormOrder extends AppCompatActivity {
    private Thread threadBackground, threadBackground1;

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
    DatabaseReference myRef, pesan, pemesanan;
    TextView harga;
    String time;
    String pilihan[];
    String[] hargaBaru;
    EditText nama, alamat, nohp;
    int userId;
    String namaS, alamatS, nohpS, id;
    String[] tempc = new String[2];
    String[] tempr1 = new String[7];
    String[] tempr1id = new String[7];
    String[] tempr1a = new String[3];
    String[] tempr1aid = new String[3];
    String[] tempr1adayabaru = new String[15];
    String[] tempr1adayadaya = new String[15];
    String[] tempCabang1 = new String[2];
    int posisi, posisi2;
    DatabaseReference wilayah;
    LinearLayout tampilBiaya;
    int coba, cobaCabang1, cobaCabang2, cobaRayon1, cobaRayon2, cobaRayon3, cobaRayon4, cobaRayon5, cobaRayon6, cobaRayon7, cobaRayon8,
            cobaPemda1, cobaPemda2, cobaPemda3, cobaPemda4;
    String str_Wilayah, str_Cabang, str_Rayon, str_Pemda, str_Gerai,
            str_bpPLN, str_Instalasi, str_Slo, str_gInstalasi, str_Materai,
            str_adminDaya, str_tokenDaya, str_MateraiDaya, str_daya, str_dayaDaya,
            str_eLampOut, str_eLampIn, str_elContactOut, str_elContactIn;
    MaterialSpinner sWilayah, sCabang, sRayon, sPemda, sGerai, sForm, sDayaDayaBaru, sDayaDaya;
    String strWilayah[], strCabang[], strRayon[], strPemda[], strGerai[], strForm[], strDayaDayaBaru[], strDayaDaya[];
    double hargaDaya[];
    double jumlah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_order);
        lat = getIntent().getExtras().getString("lat");
        lng = getIntent().getExtras().getString("lon");
        harga = (TextView) findViewById(R.id.harga);
        nama = (EditText) findViewById(R.id.namaForm);
        alamat = (EditText) findViewById(R.id.alamatForm);
        nohp = (EditText) findViewById(R.id.nohpForm);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        harga = (TextView) findViewById(R.id.harga);
        String setHarga;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        pemesanan = FirebaseDatabase.getInstance().getReference("pemesanan");
        pesan = myRef.child("users");

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        harga = (TextView) findViewById(R.id.harga);
     //   String setHarga;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        pemesanan = FirebaseDatabase.getInstance().getReference("pemesanan");
       // cekbiaya = FirebaseDatabase.getInstance().getReference("cekbiaya");
        pesan = myRef.child("users");
        sWilayah = (MaterialSpinner) findViewById(R.id.spinnerWilBaru);
        sCabang = (MaterialSpinner) findViewById(R.id.spinnerCabangBaru);
        sRayon = (MaterialSpinner) findViewById(R.id.spinnerRayonBaru);
        //    sPemda = (MaterialSpinner) findViewById(R.id.spinnerPemdaBaru);
        sGerai = (MaterialSpinner) findViewById(R.id.spinnerGeraiBaru);
        sDayaDaya = (MaterialSpinner) findViewById(R.id.sDayaDaya);
        sDayaDayaBaru = (MaterialSpinner) findViewById(R.id.sDayaDayaBaru);
        tampilBiaya = (LinearLayout) findViewById(R.id.tampilBiaya);
        wilayah = FirebaseDatabase.getInstance().getReference("wilayah");
        hargaDaya = new double[]{0, 421000, 843000, 1218000, 2062000,
                2062000, 2062000, 2062000, 2062000, 2062000, 2062000, 2062000, 2062000,
                2062000, 2062000, 2062000};
        strWilayah = new String[]{
                "Pilih Wilayah",
                "Kalimantan Selatan dan Tengah",

        };
        sWilayah.setItems(strWilayah);
        sWilayah.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                switch (position) {
                    case 0:
                        System.out.println("cek 1");
                        break;
                    case 1:
                        System.out.println("cek 3");
                        wilayah.child("-L8RZ6tzs-N_R2LTlzom")
                                .child("cabang").addValueEventListener(new ValueEventListener() {

                                                                           @Override
                                                                           public void onDataChange(DataSnapshot dataSnapshot) {
                                                                               System.out.println("OnData Change");
                                                                               //           ambilDataList.clear();
                                                                               int i = 0;
                                                                               for (DataSnapshot pesanSnpshot : dataSnapshot.getChildren()) {
                                                                                   //                   pbAll.setVisibility(View.GONE);
                                                                                   AmbilData ambilData = pesanSnpshot.getValue(AmbilData.class);
                                                                                   //  ambilDataList.add(ambilData);
                                                                                   tempc[i] = ambilData.getStr_Cabang();
                                                                                   //   tempId[i] = ambilData.getId();
                                                                                   System.out.println("NO " + i + 1 + "Ambil Data " + ambilData);
                                                                                   System.out.println("" + ambilData.getStr_Cabang());
                                                                                   //  listView.setAdapter(ambilData.getId());
                                                                                   i++;


                                                                               }

                                                                               coba = tempc.length;
                                                                               System.out.println("cabang " + coba + tempc[1]);
//                                                                                                            adapter = new Arraylist(DaftarAplikasi.this, ambilDataList);
//                                                                                                            listView.setAdapter(adapter);

                                                                               System.out.println("sss " + tempc[0] + tempc[1]);
                                                                               spinnerCabang();
                                                                           }

                                                                           @Override
                                                                           public void onCancelled(DatabaseError databaseError) {

                                                                           }

                                                                       }
                        );

                        break;
                    case 2:
                        System.out.println("cek 4");
                        Snackbar.make(view, "Segera", Snackbar.LENGTH_LONG).show();
                        break;
                }

                //   Snackbar.make(view, "Besar daya " + pilihan[position] + " seharga " + hargaBaru[position], Snackbar.LENGTH_LONG).show();
                //  String setHarga = harga.setText();
                //    Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });
        sWilayah.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Belum di pilih", Snackbar.LENGTH_LONG).show();
            }
        });

    }


    public void btCekBiayaForm(View view) {
        harga.setText("Besar daya " + pilihan[posisi] + " seharga " + hargaBaru[posisi]);
        android.app.AlertDialog.Builder alBuilder = new android.app.AlertDialog.Builder(this);
        alBuilder.setTitle("Cek Biaya");
        //  alBuilder.setIcon(R.drawable.ic_clear_black_24dp);
        alBuilder.setMessage(" " + pilihan[posisi]
                + " seharga " + hargaBaru[posisi]).setCancelable(false)
                .setPositiveButton("Lihat Rincian", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.out.println("Pilihan Ya");
                        startActivity(new Intent(FormOrder.this, DriveMcc.class));
                        System.out.println("Cek Pemasangan baru");
                    }
                }).setNegativeButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.out.println("Pilihan Tidak");
                //    startActivity(new Intent(PemasanganBaru.this, RecycleActivity.class));
                dialogInterface.cancel();
            }
        });
        android.app.AlertDialog alertDialog = alBuilder.create();
        alertDialog.show();

    }

    public void btPesanForm(View view) {

        System.out.println("Alamatbt " + addresses);
        System.out.println("latitude " + lat);
        System.out.println("longitude " + lng);
   //     harga.setText("Besar daya " + pilihan[posisi] + " seharga " + hargaBaru[posisi]);
        android.app.AlertDialog.Builder alBuilder = new android.app.AlertDialog.Builder(this);
        alBuilder.setTitle("Pemesanan");
        //  alBuilder.setIcon(R.drawable.ic_clear_black_24dp);
        alBuilder.setMessage("Anda yakin pesan penambahan baru dengan besar daya " + pilihan[posisi]
                + " seharga " + hargaBaru[posisi]).setCancelable(false)
                .setPositiveButton("ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.out.println("Pilihan Ya");
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
        android.app.AlertDialog alertDialog = alBuilder.create();
        alertDialog.show();
    }

    public void btUploadFileForm(View view) {
        startActivity(new Intent(FormOrder.this, SendGmail.class));
    }

    private void saveDatabase() {
        //Tidak Boleh Kosong
        String str_nama = nama.getText().toString();
        String str_alamat = alamat.getText().toString();
        String str_nohp = nohp.getText().toString();
        String str_id = pemesanan.push().getKey();
        time = String.valueOf(ServerValue.TIMESTAMP);
        if (str_nama.isEmpty()) {
            nama.setError("Nama harus diisi");
            nama.requestFocus();
            return;
        } else if (str_alamat.isEmpty()) {
            alamat.setError("Alamat harus diisi");
            alamat.requestFocus();
            return;
        } else if (str_nohp.length() < 10) {
            nohp.setError("Masukan no hanphone dengan benar");
            nohp.requestFocus();
            return;
        }else {
//            AmbilData user = new AmbilData(str_id, "Pemasangan Baru ", str_nama, str_alamat, str_nohp
//                    , pilihan[posisi], hargaBaru[posisi], lat, lng, "");
//        AmbilData user = new AmbilData(str_id, "Pemasangan Baru ", str_nama, str_alamat, str_nohp
//                , pilihan[posisi], hargaBaru[posisi], lat, lng, time);
 //           pemesanan.child(str_id).setValue(user);
            Toast.makeText(FormOrder.this, "Pemesanan sedang di proses", Toast.LENGTH_SHORT).show();
        }
//        AmbilData user = new AmbilData(str_alamat);

//        AmbilData user = new AmbilData("Pemasangan Baru ", str_nama, str_alamat, str_nohp
//                , pilihan[posisi], hargaBaru[posisi], lat, lng);
//        pesan.push().setValue(user);
//        AmbilData user = new AmbilData(str_id, "Pemasangan Baru ", str_nama, str_alamat, str_nohp
//                , pilihan[posisi], hargaBaru[posisi], lat, lng);

//        AmbilData user = new AmbilData(str_id, "Pemasangan Daya ", str_nama, str_alamat, str_nohp
//                , pilihan[posisi], hargaBaru[posisi], lat, lng, time);
//        pemesanan.child(str_id).setValue(user);
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


    private void spinnerCabang() {

        if (coba == 2) {
            strCabang = new String[]{
                    "Pilih Cabang",
                    "" + tempc[0], "" + tempc[1],

            };
        } else if (coba == 3) {
            strCabang = new String[]{
                    "Pilih Cabang",
                    tempc[0], tempc[1], tempc[2],

            };
        } else if (coba == 4) {
            strCabang = new String[]{
                    "Pilih Cabang",
                    tempc[0], tempc[1], tempc[2], tempc[3],

            };
        } else if (coba == 5) {
            strCabang = new String[]{
                    "Pilih Cabang",
                    tempc[0], tempc[1], tempc[2], tempc[3], tempc[4],

            };
        }

        sCabang.setItems(strCabang);
        sCabang.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                switch (position) {
                    case 0:
                        System.out.println("cek 1");
                        break;
                    case 1:
                        System.out.println("cek 2");
                        cobaCabang1 = 2;
                        wilayah.child("-L8RZ6tzs-N_R2LTlzom").child("cabang").child("-L8W31ly20ZfYmbS5VWk")
                                .child("rayon").addValueEventListener(new ValueEventListener() {

                                                                          @Override
                                                                          public void onDataChange(DataSnapshot dataSnapshot) {
                                                                              System.out.println("OnData Change");
                                                                              //   ambilDataList.clear();
                                                                              int i = 0;
                                                                              cobaCabang2 = 0;
                                                                              for (DataSnapshot pesanSnpshot : dataSnapshot.getChildren()) {
                                                                                  //                   pbAll.setVisibility(View.GONE);
                                                                                  AmbilData ambilData = pesanSnpshot.getValue(AmbilData.class);

                                                                                  tempr1[i] = ambilData.getStr_Rayon();
                                                                                  tempr1id[i] = ambilData.getId();

                                                                                  System.out.println("NO " + i + 1 + "Ambil Data " + ambilData);
                                                                                  System.out.println("" + ambilData.getStr_Rayon());

                                                                                  i++;


                                                                              }

                                                                              cobaCabang2 = tempr1.length;
                                                                              System.out.println("cabang " + coba + tempc[1]);
//                                                                                                            adapter = new Arraylist(DaftarAplikasi.this, ambilDataList);
//                                                                                                            listView.setAdapter(adapter);
                                                                              System.out.println("sss " + tempc[0] + tempc[1]);
                                                                              spinnerRayon();
                                                                          }

                                                                          @Override
                                                                          public void onCancelled(DatabaseError databaseError) {

                                                                          }

                                                                      }
                        );

                        break;
                    case 2:
                        System.out.println("cek 3");
                        cobaCabang1 = 3;

                        Snackbar.make(view, "Segera akan di buka disana", Snackbar.LENGTH_LONG).show();
//                        wilayah.child("-L8RZ6tzs-N_R2LTlzom").child("cabang").child("-L8WE79wPGisnB2emGcx")
//                                .child("rayon").addValueEventListener(new ValueEventListener() {
//
//                                                                          @Override
//                                                                          public void onDataChange(DataSnapshot dataSnapshot) {
//                                                                              System.out.println("OnData Change");
//                                                                              //   ambilDataList.clear();
//                                                                              int i = 0;
//                                                                              for (DataSnapshot pesanSnpshot : dataSnapshot.getChildren()) {
//                                                                                  //                   pbAll.setVisibility(View.GONE);
//                                                                                  AmbilData ambilData = pesanSnpshot.getValue(AmbilData.class);
//
//                                                                                  tempr1[i] = ambilData.getStr_Rayon();
//
//                                                                                  System.out.println("NO " + i + 1 + "Ambil Data " + ambilData);
//                                                                                  System.out.println("" + ambilData.getStr_Cabang());
//                                                                                  //    ambilDataList.add(ambilData);
//                                                                                  i++;
//
//
//                                                                              }
//
////                                                                              cobaCabang1 = 3;
//                                                                              System.out.println("cabang " + coba + tempc[1]);
////                                                                                                            adapter = new Arraylist(DaftarAplikasi.this, ambilDataList);
////                                                                                                            listView.setAdapter(adapter);
//                                                                              System.out.println("sss " + tempc[0] + tempc[1]);
//                                                                              spinnerRayon();
//                                                                          }
//
//                                                                          @Override
//                                                                          public void onCancelled(DatabaseError databaseError) {
//
//                                                                          }
//
//                                                                      }
//                        );

                        break;
                    case 3:
                        System.out.println("cek 3");
                        Snackbar.make(view, "Nambah Cabang?", Snackbar.LENGTH_LONG).show();
                        break;
                    case 4:
                        System.out.println("cek 3");
                        break;
                    case 5:
                        System.out.println("cek 3");
                        break;
                    case 6:
                        System.out.println("cek 3");
                        break;
                    case 7:
                        System.out.println("cek 3");
                        break;

                }

                //   Snackbar.make(view, "Besar daya " + pilihan[position] + " seharga " + hargaBaru[position], Snackbar.LENGTH_LONG).show();
                //  String setHarga = harga.setText();
                //    Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

        sCabang.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Belum di pilih", Snackbar.LENGTH_LONG).show();
            }
        });
        //     }

    }

    private void spinnerRayon() {
        if (cobaCabang2 == 2) {
            strRayon = new String[]{
                    "Pilih Rayon",
                    "" + tempr1[0], "" + tempr1[1],

            };
        } else if (cobaCabang2 == 3) {
            strRayon = new String[]{
                    "Pilih Rayon",
                    "" + tempr1[0], "" + tempr1[1], "" + tempr1[2],

            };
        } else if (cobaCabang2 == 4) {
            strRayon = new String[]{
                    "Pilih Rayon",
                    "" + tempr1[0], tempr1[1], tempr1[2], tempr1[3],

            };
        } else if (cobaCabang2 == 5) {
            strRayon = new String[]{
                    "Pilih Rayon",
                    tempr1[0], tempr1[1], tempr1[2], tempr1[3], tempr1[4],

            };
        } else if (cobaCabang2 == 6) {
            strRayon = new String[]{
                    "Pilih Rayon",
                    tempr1[0], tempr1[1], tempr1[2], tempr1[3], tempr1[4], tempr1[5],
            };
        } else if (cobaCabang2 == 7) {
            strRayon = new String[]{
                    "Pilih Rayon",
                    tempr1[0], tempr1[1], tempr1[2], tempr1[3], tempr1[4], tempr1[5], tempr1[6],
            };
        }

        sRayon.setItems(strRayon);
        sRayon.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                switch (position) {
                    case 0:
                        System.out.println("cek 1");
                        break;
                    case 1:
                        System.out.println("cek 2");
                        cobaRayon1 = 2;
                        wilayah.child("-L8RZ6tzs-N_R2LTlzom").child("cabang").child("-L8W31ly20ZfYmbS5VWk")
                                .child("rayon").child("-L9mCkiqUTncvvgokQo0")
                                .child("gerai").addValueEventListener(new ValueEventListener() {

                                                                          @Override
                                                                          public void onDataChange(DataSnapshot dataSnapshot) {
                                                                              System.out.println("OnData Change");
                                                                              //   ambilDataList.clear();
                                                                              cobaCabang2 = 0;
                                                                              //tempr1a = new String[2];
                                                                              int i = 0;
                                                                              for (DataSnapshot pesanSnpshot : dataSnapshot.getChildren()) {
                                                                                  //                   pbAll.setVisibility(View.GONE);
                                                                                  AmbilData ambilData = pesanSnpshot.getValue(AmbilData.class);

                                                                                  tempr1a[i] = ambilData.getStr_Gerai();
                                                                                  tempr1aid[i] = ambilData.getId();
                                                                                  System.out.println("NO " + i + 1 + "Ambil Data " + ambilData);
                                                                                  System.out.println("" + ambilData.getStr_Gerai());
                                                                                  System.out.println("ssswe" + tempr1aid[i]);
                                                                                  i++;


                                                                              }

                                                                              cobaCabang2 = tempr1a.length;

                                                                              System.out.println("cabang " + coba + tempr1a.length);
//                                                                                                            adapter = new Arraylist(DaftarAplikasi.this, ambilDataList);
//                                                                                                            listView.setAdapter(adapter);
                                                                              System.out.println("sss " + tempc[0] + tempc[1]);
                                                                              //   cobaPemda1 = 1;
                                                                              spinnerGerai();
                                                                          }

                                                                          @Override
                                                                          public void onCancelled(DatabaseError databaseError) {

                                                                          }

                                                                      }
                        );

                        break;
                    case 2:
                        System.out.println("cek 3");
                        cobaRayon1 = 3;
                        wilayah.child("-L8RZ6tzs-N_R2LTlzom").child("cabang").child("-L8W31ly20ZfYmbS5VWk")
                                .child("rayon").child("-L9mCpS62RIniO2xIhfj")
                                .child("gerai").addValueEventListener(new ValueEventListener() {

                                                                          @Override
                                                                          public void onDataChange(DataSnapshot dataSnapshot) {
                                                                              System.out.println("OnData Change");
                                                                              //   ambilDataList.clear();
                                                                              cobaCabang2 = 0;
                                                                              //tempr1a = new String[2];
                                                                              int i = 0;
                                                                              for (DataSnapshot pesanSnpshot : dataSnapshot.getChildren()) {
                                                                                  //                   pbAll.setVisibility(View.GONE);
                                                                                  AmbilData ambilData = pesanSnpshot.getValue(AmbilData.class);

                                                                                  tempr1a[i] = ambilData.getStr_Gerai();
                                                                                  tempr1aid[i] = ambilData.getId();
                                                                                  System.out.println("NO " + i + 1 + "Ambil Data " + ambilData);
                                                                                  System.out.println("" + ambilData.getStr_Gerai());
                                                                                  System.out.println("" + tempr1aid[i]);
                                                                                  i++;


                                                                              }

                                                                              cobaCabang2 = tempr1a.length;

                                                                              System.out.println("cabang " + coba + tempr1a.length);
//                                                                                                            adapter = new Arraylist(DaftarAplikasi.this, ambilDataList);
//                                                                                                            listView.setAdapter(adapter);
                                                                              System.out.println("sss " + tempc[0] + tempc[1]);
                                                                              //  cobaPemda1 = 2;
                                                                              spinnerGerai();
                                                                          }

                                                                          @Override
                                                                          public void onCancelled(DatabaseError databaseError) {

                                                                          }

                                                                      }
                        );
                        break;
                    case 3:
                        System.out.println("cek 3");
                        cobaRayon1 = 4;
                        wilayah.child("-L8RZ6tzs-N_R2LTlzom").child("cabang").child("-L8W31ly20ZfYmbS5VWk")
                                .child("rayon").child("-L9mCrzfYhLU4SLga5Qr")
                                .child("gerai").addValueEventListener(new ValueEventListener() {

                                                                          @Override
                                                                          public void onDataChange(DataSnapshot dataSnapshot) {
                                                                              System.out.println("OnData Change");
                                                                              //   ambilDataList.clear();
                                                                              cobaCabang2 = 0;
                                                                              //tempr1a = new String[2];
                                                                              int i = 0;
                                                                              for (DataSnapshot pesanSnpshot : dataSnapshot.getChildren()) {
                                                                                  //                   pbAll.setVisibility(View.GONE);
                                                                                  AmbilData ambilData = pesanSnpshot.getValue(AmbilData.class);

                                                                                  tempr1a[i] = ambilData.getStr_Gerai();
                                                                                  tempr1aid[i] = ambilData.getId();
                                                                                  System.out.println("NO " + i + 1 + "Ambil Data " + ambilData);
                                                                                  System.out.println("" + ambilData.getStr_Gerai());
                                                                                  System.out.println("" + tempr1aid[i]);
                                                                                  i++;


                                                                              }

                                                                              cobaCabang2 = tempr1a.length;

                                                                              System.out.println("cabang " + coba + tempr1a.length);
//                                                                                                            adapter = new Arraylist(DaftarAplikasi.this, ambilDataList);
//                                                                                                            listView.setAdapter(adapter);
                                                                              System.out.println("sss " + tempc[0] + tempc[1]);
                                                                              //   cobaPemda1 = 3;
                                                                              spinnerGerai();
                                                                          }

                                                                          @Override
                                                                          public void onCancelled(DatabaseError databaseError) {

                                                                          }

                                                                      }
                        );
                        break;
                    case 4:
                        System.out.println("cek 3");
                        cobaRayon1 = 5;
                        wilayah.child("-L8RZ6tzs-N_R2LTlzom").child("cabang").child("-L8W31ly20ZfYmbS5VWk")
                                .child("rayon").child("-L9mCtWcQobc4S2nszUH")
                                .child("gerai").addValueEventListener(new ValueEventListener() {

                                                                          @Override
                                                                          public void onDataChange(DataSnapshot dataSnapshot) {
                                                                              System.out.println("OnData Change");
                                                                              //   ambilDataList.clear();
                                                                              cobaCabang2 = 0;
                                                                              //tempr1a = new String[2];
                                                                              int i = 0;
                                                                              for (DataSnapshot pesanSnpshot : dataSnapshot.getChildren()) {
                                                                                  //                   pbAll.setVisibility(View.GONE);
                                                                                  AmbilData ambilData = pesanSnpshot.getValue(AmbilData.class);

                                                                                  tempr1a[i] = ambilData.getStr_Gerai();
                                                                                  tempr1aid[i] = ambilData.getId();
                                                                                  System.out.println("NO " + i + 1 + "Ambil Data " + ambilData);
                                                                                  System.out.println("" + ambilData.getStr_Gerai());
                                                                                  System.out.println("" + tempr1aid[i]);
                                                                                  i++;


                                                                              }

                                                                              cobaCabang2 = tempr1a.length;

                                                                              System.out.println("cabang " + coba + tempr1a.length);
//                                                                                                            adapter = new Arraylist(DaftarAplikasi.this, ambilDataList);
//                                                                                                            listView.setAdapter(adapter);
                                                                              System.out.println("sss " + tempc[0] + tempc[1]);
                                                                              //   cobaPemda1 = 4;
                                                                              spinnerGerai();
                                                                          }

                                                                          @Override
                                                                          public void onCancelled(DatabaseError databaseError) {

                                                                          }

                                                                      }
                        );
                        break;
                    case 5:
                        System.out.println("cek 3");
                        cobaRayon1 = 6;
                        wilayah.child("-L8RZ6tzs-N_R2LTlzom").child("cabang").child("-L8W31ly20ZfYmbS5VWk")
                                .child("rayon").child("-L9mCvKP-7QxiXwEyh8G")
                                .child("gerai").addValueEventListener(new ValueEventListener() {

                                                                          @Override
                                                                          public void onDataChange(DataSnapshot dataSnapshot) {
                                                                              System.out.println("OnData Change");
                                                                              //   ambilDataList.clear();
                                                                              cobaCabang2 = 0;
                                                                              //tempr1a = new String[2];
                                                                              int i = 0;
                                                                              for (DataSnapshot pesanSnpshot : dataSnapshot.getChildren()) {
                                                                                  //                   pbAll.setVisibility(View.GONE);
                                                                                  AmbilData ambilData = pesanSnpshot.getValue(AmbilData.class);

                                                                                  tempr1a[i] = ambilData.getStr_Gerai();
                                                                                  tempr1aid[i] = ambilData.getId();
                                                                                  System.out.println("NO " + i + 1 + "Ambil Data " + ambilData);
                                                                                  System.out.println("" + ambilData.getStr_Gerai());
                                                                                  System.out.println("" + tempr1aid[i]);
                                                                                  i++;


                                                                              }

                                                                              cobaCabang2 = tempr1a.length;

                                                                              System.out.println("cabang " + coba + tempr1a.length);
//                                                                                                            adapter = new Arraylist(DaftarAplikasi.this, ambilDataList);
//                                                                                                            listView.setAdapter(adapter);
                                                                              System.out.println("sss " + tempc[0] + tempc[1]);
                                                                              // cobaPemda1 = 5;
                                                                              spinnerGerai();
                                                                          }

                                                                          @Override
                                                                          public void onCancelled(DatabaseError databaseError) {

                                                                          }

                                                                      }
                        );
                        break;
                    case 6:
                        System.out.println("cek 3");
                        cobaRayon1 = 7;
                        wilayah.child("-L8RZ6tzs-N_R2LTlzom").child("cabang").child("-L8W31ly20ZfYmbS5VWk")
                                .child("rayon").child("-L9mCwkwmL6ByVCJlIhv")
                                .child("gerai").addValueEventListener(new ValueEventListener() {

                                                                          @Override
                                                                          public void onDataChange(DataSnapshot dataSnapshot) {
                                                                              System.out.println("OnData Change");
                                                                              //   ambilDataList.clear();
                                                                              cobaCabang2 = 0;
                                                                              //tempr1a = new String[2];
                                                                              int i = 0;
                                                                              for (DataSnapshot pesanSnpshot : dataSnapshot.getChildren()) {
                                                                                  //                   pbAll.setVisibility(View.GONE);
                                                                                  AmbilData ambilData = pesanSnpshot.getValue(AmbilData.class);

                                                                                  tempr1a[i] = ambilData.getStr_Gerai();
                                                                                  tempr1aid[i] = ambilData.getStr_Gerai();
                                                                                  System.out.println("NO " + i + 1 + "Ambil Data " + ambilData);
                                                                                  System.out.println("" + ambilData.getStr_Gerai());
                                                                                  System.out.println("" + tempr1aid[i]);
                                                                                  i++;


                                                                              }

                                                                              cobaCabang2 = tempr1a.length;

                                                                              System.out.println("cabang " + coba + tempr1a.length);
//                                                                                                            adapter = new Arraylist(DaftarAplikasi.this, ambilDataList);
//                                                                                                            listView.setAdapter(adapter);
                                                                              System.out.println("sss " + tempc[0] + tempc[1]);
                                                                              // cobaPemda1 = 6;
                                                                              spinnerGerai();
                                                                          }

                                                                          @Override
                                                                          public void onCancelled(DatabaseError databaseError) {

                                                                          }

                                                                      }
                        );
                        break;
                    case 7:
                        System.out.println("cek 3");
                        cobaRayon1 = 8;
                        wilayah.child("-L8RZ6tzs-N_R2LTlzom").child("cabang").child("-L8W31ly20ZfYmbS5VWk")
                                .child("rayon").child("-L9mCyPJvrjbFUNv2Dch")
                                .child("gerai").addValueEventListener(new ValueEventListener() {

                                                                          @Override
                                                                          public void onDataChange(DataSnapshot dataSnapshot) {
                                                                              System.out.println("OnData Change");
                                                                              //   ambilDataList.clear();
                                                                              cobaCabang2 = 0;
                                                                              //tempr1a = new String[2];
                                                                              int i = 0;
                                                                              for (DataSnapshot pesanSnpshot : dataSnapshot.getChildren()) {
                                                                                  //                   pbAll.setVisibility(View.GONE);
                                                                                  AmbilData ambilData = pesanSnpshot.getValue(AmbilData.class);

                                                                                  tempr1a[i] = ambilData.getStr_Gerai();
                                                                                  tempr1aid[i] = ambilData.getId();

                                                                                  System.out.println("NO " + i + 1 + "Ambil Data " + ambilData);
                                                                                  System.out.println("" + ambilData.getStr_Gerai());
                                                                                  System.out.println("" + tempr1aid[i]);

                                                                                  i++;


                                                                              }

                                                                              cobaCabang2 = tempr1a.length;
                                                                              // cobaPemda1 = 7;
                                                                              System.out.println("cabang " + coba + tempr1a.length);
//                                                                                                            adapter = new Arraylist(DaftarAplikasi.this, ambilDataList);
//                                                                                                            listView.setAdapter(adapter);
                                                                              System.out.println("sss " + tempc[0] + tempc[1]);

                                                                              spinnerGerai();
                                                                          }

                                                                          @Override
                                                                          public void onCancelled(DatabaseError databaseError) {

                                                                          }

                                                                      }
                        );
                        break;
                    case 8: {
                        Snackbar.make(view, "Ada rayon baru?", Snackbar.LENGTH_LONG).show();
                    }

                }

                //   Snackbar.make(view, "Besar daya " + pilihan[position] + " seharga " + hargaBaru[position], Snackbar.LENGTH_LONG).show();
                //  String setHarga = harga.setText();
                //    Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

        sRayon.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Belum di pilih", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public void spinnerGerai() {

        if (cobaCabang2 == 1) {
            strGerai = new String[]{
                    "Pilih Gerai",
                    "" + tempr1a[0],

            };
        } else if (cobaCabang2 == 2) {
            strGerai = new String[]{
                    "Pilih Gerai",
                    "" + tempr1a[0], "" + tempr1a[1],

            };
        } else if (cobaCabang2 == 3) {
            strGerai = new String[]{
                    "Pilih Gerai",
                    "" + tempr1a[0], "" + tempr1a[1], "" + tempr1a[2],
            };
        } else if (cobaCabang2 == 4) {
            strGerai = new String[]{
                    "Pilih Gerai",
                    "" + tempr1a[0], tempr1a[1], tempr1a[2], tempr1a[3],

            };
        } else if (cobaCabang2 == 5) {
            strGerai = new String[]{
                    "Pilih Gerai",
                    tempr1a[0], tempr1a[1], tempr1a[2], tempr1a[3], tempr1a[4],

            };
        } else if (cobaCabang2 == 6) {
            strGerai = new String[]{
                    "Pilih Gerai",
                    tempr1a[0], tempr1a[1], tempr1a[2], tempr1a[3], tempr1a[4], tempr1a[5],

            };
        } else if (cobaCabang2 == 7) {
            strGerai = new String[]{
                    "Pilih Gerai",
                    tempr1a[0], tempr1a[1], tempr1a[2], tempr1a[3], tempr1a[4], tempr1a[5], tempr1a[6],

            };
        } else if (cobaCabang2 == 8) {
            strGerai = new String[]{
                    "Pilih Gerai",
                    tempr1a[0], tempr1a[1], tempr1a[2], tempr1a[3], tempr1a[4], tempr1a[5], tempr1a[6], tempr1a[7],

            };
        }

        sGerai.setItems(strGerai);
        sGerai.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                switch (position) {
                    case 0:
                        System.out.println("cek 2");
                        cobaPemda1 = 0;
                        //    cobaRayon2 = 2;
                        System.out.println("COba Pemda " + cobaPemda1);
                        //            Toast.makeText(DaftarAplikasi.this, "coba " + cobaPemda1 + " df " + tempr1aid[cobaPemda1 - 1], Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        System.out.println("cek 2");
                        cobaPemda1 = 1;
                        //    cobaRayon2 = 2;
                        System.out.println("COba Pemda " + cobaPemda1);
                        //            Toast.makeText(DaftarAplikasi.this, "coba " + cobaPemda1 + " df " + tempr1aid[cobaPemda1 - 1], Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        System.out.println("cek 3");
                        cobaPemda1 = 2;
//                        cobaRayon2 = 3;
                        //          Toast.makeText(DaftarAplikasi.this, "coba " + cobaPemda1, Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        cobaPemda1 = 3;
                        //  cobaRayon2 = 4;
                        System.out.println("cek 3");
                        break;
                    case 4:
                        cobaPemda1 = 4;
                        //  cobaRayon2 = 5;
                        System.out.println("cek 3");
                        //lGerai.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        cobaPemda1 = 5;
                        System.out.println("cek 3");
                        break;
                    case 6:
                        cobaPemda1 = 6;
                        System.out.println("cek 3");
                        break;
                    case 7:
                        cobaPemda1 = 7;
                        System.out.println("cek 3");
                        break;

                }

                //   Snackbar.make(view, "Besar daya " + pilihan[position] + " seharga " + hargaBaru[position], Snackbar.LENGTH_LONG).show();
                //  String setHarga = harga.setText();
                //    Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

        sGerai.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Belum di pilih", Snackbar.LENGTH_LONG).show();
            }
        });
        strDayaDaya = new String[]{
                "Daya Saat ini",
                "450",
                "900",
                "1300",
                "2200",
                "3500",
                "4400",
                "5500",
                "6600",
                "7700",
                "110000",
                "132000",
                "165000",
                "230000",
                "330000",
                "415000"
        };
        sDayaDaya.setItems(strDayaDaya);
        sDayaDaya.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                posisi = position;
                //     Snackbar.make(view, "Besar daya " + strDayaBaru[position], Snackbar.LENGTH_LONG).show();
                //  String setHarga = harga.setText();
                //    Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });
        sDayaDaya.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Belum di pilih", Snackbar.LENGTH_LONG).show();
            }
        });
        strDayaDayaBaru = new String[]{
                "Tambah ke Daya",
                "450",
                "900",
                "1300",
                "2200",
                "3500",
                "4400",
                "5500",
                "6600",
                "7700",
                "110000",
                "132000",
                "165000",
                "230000",
                "330000",
                "415000"
        };
        sDayaDayaBaru.setItems(strDayaDayaBaru);
        sDayaDayaBaru.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                posisi2 = position;
                //     Snackbar.make(view, "Besar daya " + strDayaBaru[position], Snackbar.LENGTH_LONG).show();
                //  String setHarga = harga.setText();
                //    Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });
        sDayaDayaBaru.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Belum di pilih", Snackbar.LENGTH_LONG).show();
            }
        });


    }

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

