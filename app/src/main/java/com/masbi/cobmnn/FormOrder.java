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
import java.util.Map;
import java.util.Scanner;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class FormOrder extends AppCompatActivity {
    private Thread threadBackground, threadBackground1;
    private GoogleMap mMap;
    Geocoder geocoder;
    String lat, lng, sendLat, sendLng;
    String time;
    Map timesmap;
    FloatingSearchView mSearchView;
    MapWrapperLayout mapWrapperLayout;
    List<Address> addresses;
    TextView harga;
    String pilihan[];
    String[] hargaBaru;
    int posisi;
    private ViewGroup infoWindow;
    private TextView infoTitle;
    private OnInfoWindowElemTouchListener infoButtonListener;
    FirebaseDatabase database;
    DatabaseReference myRef, pesan, pemesanan, wilayah;
    EditText nama, alamat, nohp, kelurahan, noBangunan, fittingPaket, sContactPaket, fittingManual, sContactManual;
    int userId;
    String namaS, alamatS, nohpS, id;
    RadioGroup rg1, rg2;
    RadioButton rb1, rb2, hh, hh1;
    LinearLayout denganInstakasi, tanpaInstalasi, paket, manual, pildaya, tampilBiaya;
    String[] tempc = new String[2];
    String[] tempr1 = new String[7];
    String[] tempr1id = new String[7];
    String[] tempr1a = new String[3];
    String[] tempr1aid = new String[3];
    String[] tempr1adayabaru = new String[15];
    String[] tempr1adayadaya = new String[15];
    String[] tempr1adayaBP = new String[15];
    String[] tempr1adayaSLO = new String[15];
    String[] tempr1adayaGI = new String[15];
    String[] tempr1adayaM = new String[15];
    String[] tempr1adayaI = new String[15];
    String[] tempr1adayaOB = new String[15];
    String[] tempr1adayaIB = new String[15];
    String[] tempr1adayaSOB = new String[15];
    String[] tempr1adayaSIB = new String[15];
    String[] tempr1adayaVou = new String[15];
    String[] tempCabang1 = new String[2];
    int coba, cobaCabang1, cobaCabang2, cobaRayon1, cobaRayon2, cobaRayon3, cobaRayon4, cobaRayon5, cobaRayon6, cobaRayon7, cobaRayon8,
            cobaPemda1, cobaPemda2, cobaPemda3, cobaPemda4;
    String str_Wilayah, str_Cabang, str_Rayon, str_Pemda, str_Gerai,
            str_bpPLN, str_Instalasi, str_Slo, str_gInstalasi, str_Materai,
            str_adminDaya, str_tokenDaya, str_MateraiDaya, str_daya, str_dayaDaya,
            str_eLampOut, str_eLampIn, str_elContactOut, str_elContactIn;
    MaterialSpinner sWilayah, sCabang, sRayon, sPemda, sGerai, sForm, sDayaBaru, sDayaDaya, sLamp, sCont;
    String strWilayah[], strCabang[], strRayon[], strPemda[], strGerai[], strForm[], strDayaBaru[], strDayaDaya[], strLamp[], strCont[];
    TextView t_bpPLN, t_Instalasi, t_Slo, t_gInstalasi, t_Materai, t_jumlah, t_daya, t_vou;
    int inFM, inFP, inSM, inSP, inMCB, p;
    double jumlah;
    int lamp, cont;
    LinearLayout contLin, lampLin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_order);
        lat = getIntent().getExtras().getString("lat");
        lng = getIntent().getExtras().getString("lon");
        nama = (EditText) findViewById(R.id.namaBaru);
        alamat = (EditText) findViewById(R.id.alamatBaru);
        nohp = (EditText) findViewById(R.id.nohpBaru);
        kelurahan = (EditText) findViewById(R.id.kelurahanBaru);
        noBangunan = (EditText) findViewById(R.id.noBangunanBaru);
        fittingPaket = (EditText) findViewById(R.id.fittingLampuBaruPaket);
        sContactPaket = (EditText) findViewById(R.id.StopContactLampuBaruPaket);
        fittingManual = (EditText) findViewById(R.id.fittingLampuBaruManual);
        sContactManual = (EditText) findViewById(R.id.StopContactLampuManual);
        //   String setHarga;
        final int[] cek;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        pemesanan = FirebaseDatabase.getInstance().getReference("pemesanan");
        pesan = myRef.child("users");
        rg1 = (RadioGroup) findViewById(R.id.pilInstalasi);
        rg2 = (RadioGroup) findViewById(R.id.pilPaket);
        hh = (RadioButton) findViewById(R.id.tpInstalasi);
        hh1 = (RadioButton) findViewById(R.id.dgInstalasi);
        denganInstakasi = (LinearLayout) findViewById(R.id.denganInstalasi);
        tanpaInstalasi = (LinearLayout) findViewById(R.id.tanpaInstalasi);
        paket = (LinearLayout) findViewById(R.id.pilihanPaketPaket);
        manual = (LinearLayout) findViewById(R.id.pilihanPaketManual);
        // pildaya = (LinearLayout) findViewById(R.id.pilDaya);
        rb1 = (RadioButton) findViewById(R.id.pilihanManual);
        rb2 = (RadioButton) findViewById(R.id.pilihanPaket);
        sWilayah = (MaterialSpinner) findViewById(R.id.spinnerWilBaru);
        sCabang = (MaterialSpinner) findViewById(R.id.spinnerCabangBaru);
        sRayon = (MaterialSpinner) findViewById(R.id.spinnerRayonBaru);
        //    sPemda = (MaterialSpinner) findViewById(R.id.spinnerPemdaBaru);
        sGerai = (MaterialSpinner) findViewById(R.id.spinnerGeraiBaru);
        sDayaBaru = (MaterialSpinner) findViewById(R.id.sDayaBaru);
        sLamp = (MaterialSpinner) findViewById(R.id.spinnerLamp);
        sCont = (MaterialSpinner) findViewById(R.id.spinnerCont);
        tampilBiaya = (LinearLayout) findViewById(R.id.tampilBiaya);
        wilayah = FirebaseDatabase.getInstance().getReference("wilayah");
        t_bpPLN = (TextView) findViewById(R.id.textBP);
        t_daya = (TextView) findViewById(R.id.tDaya);
        t_gInstalasi = (TextView) findViewById(R.id.textGinstalasi);
        t_Instalasi = (TextView) findViewById(R.id.textIntalasi);
        t_Slo = (TextView) findViewById(R.id.textSLO);
        t_Materai = (TextView) findViewById(R.id.textMaterai);
        t_vou = (TextView) findViewById(R.id.textVoucher);
        lampLin = (LinearLayout) findViewById(R.id.lampLin);
        contLin = (LinearLayout) findViewById(R.id.contLin);
        lat = getIntent().getExtras().getString("lat");
        lng = getIntent().getExtras().getString("lon");


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


    public void btCekBiaya(View view) {
//        System.out.println("Sebelum "+tempr1aid[cobaPemda1 - 1]);
//        wilayah.child("-L8RZ6tzs-N_R2LTlzom").child("cabang").child("-L8W31ly20ZfYmbS5VWk")
//                .child("rayon").child("" + tempr1id[cobaRayon1 - 2]).child("gerai")
//                .child("" + tempr1aid[cobaPemda1 - 1]).child("penambahan").
//                addValueEventListener(new ValueEventListener() {
//
//                                          @Override
//                                          public void onDataChange(DataSnapshot dataSnapshot) {
//                                              System.out.println("Cek id "+tempr1aid[cobaPemda1 - 1]);
//                                              System.out.println("OnData Change");
//                                              //   ambilDataList.clear();
//                                              cobaCabang2 = 0;
//                                              //tempr1a = new String[2];
//                                              int i = 0;
//                                              for (DataSnapshot pesanSnpshot : dataSnapshot.getChildren()) {
//                                                  //                   pbAll.setVisibility(View.GONE);
//                                                  AmbilData ambilData = pesanSnpshot.getValue(AmbilData.class);
//
////                                                                  tempr1a[i] = ambilData.getStr_Gerai();
////                                                                  tempr1aid[i] = ambilData.getId();
//                                                  tempr1adayadaya[i] = ambilData.getStr_dayaDaya();
//                                                  tempr1adayaBP[i] = ambilData.getStr_bpPLN();
//                                                  tempr1adayaI[i] = ambilData.getStr_Instalasi();
//                                                  tempr1adayaSLO[i] = ambilData.getStr_Slo();
//                                                  tempr1adayaGI[i] = ambilData.getStr_gInstalasi();
//                                                  tempr1adayaM[i] = ambilData.getStr_Materai();
//                                                  tempr1adayaIB[i] = ambilData.getStr_eLampIn();
//                                                  tempr1adayaOB[i] = ambilData.getStr_eLampOut();
//                                                  tempr1adayaSOB[i] = ambilData.getStr_elContactOut();
//                                                  tempr1adayaSIB[i] = ambilData.getStr_elContactIn();
//                                                  tempr1adayaVou[i] = ambilData.getStr_voucher();
//
//
//                                                  System.out.println("NO " + i + 1 + "Ambil Data " + ambilData);
//                                                  System.out.println("" + ambilData.getStr_Gerai());
//                                                  System.out.println("" + tempr1aid[i]);
//
//                                                  i++;
//
//
//                                              }
//
//                                              System.out.println("cabang " + coba + tempr1a.length);
////                                                                                                            adapter = new Arraylist(DaftarAplikasi.this, ambilDataList);
////                                                                                                            listView.setAdapter(adapter);
//                                              System.out.println("sss " + tempc[0] + tempc[1]);
//
//                                          }
//
//                                          @Override
//                                          public void onCancelled(DatabaseError databaseError) {
//
//                                          }
//
//                                      }
//                );
//
//        harga.setText("Besar daya " + pilihan[posisi] + " seharga " + hargaBaru[posisi]);
//
////        inFM = Integer.parseInt(fittingManual.getText().toString()) - 3;
////        inSM = Integer.parseInt(sContactManual.getText().toString()) - 1;
////        inFP = 3;
////        inSP = 1;
//        //      if (p == 1) {
//        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
//        alBuilder.setTitle("Cek Biaya");
//
//        alBuilder.setMessage(" " + pilihan[posisi]
//                + " seharga " + hargaBaru[posisi]).setCancelable(false)
//                .setPositiveButton("Lihat Rincian", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        System.out.println("Pilihan Ya");
//                        //   startActivity(new Intent(PemasanganBaru.this, DriveMcc.class));
//                        double jumlah = Double.parseDouble(tempr1adayaI[cobaPemda1] + (lamp * Double.parseDouble(tempr1adayaIB[cobaPemda1])
//                                + (cont * Double.parseDouble(tempr1adayaSIB[cobaPemda1])))) +
//                                Double.parseDouble(tempr1adayaI[cobaPemda1]) +
//                                Double.parseDouble(tempr1adayaGI[cobaPemda1]) +
//                                Double.parseDouble(tempr1adayaSLO[cobaPemda1]) +
//                                Double.parseDouble(tempr1adayaM[cobaPemda1]);
//                        t_bpPLN.setText(String.valueOf(Double.parseDouble(tempr1adayaBP[cobaPemda1])));
//                        t_daya.setText("Biaya dengan daya " + String.valueOf(Double.parseDouble(tempr1adayadaya[cobaPemda1])));
//                        t_Instalasi.setText(String.valueOf(Double.parseDouble(tempr1adayaI[cobaPemda1] + (lamp * Double.parseDouble(tempr1adayaIB[cobaPemda1])
//                                + (cont * Double.parseDouble(tempr1adayaSIB[cobaPemda1]))))));
//                        t_gInstalasi.setText(String.valueOf(Double.parseDouble(tempr1adayaGI[cobaPemda1])));
//                        t_Slo.setText(String.valueOf(Double.parseDouble(tempr1adayaSLO[cobaPemda1])));
//                        t_Materai.setText(String.valueOf(Double.parseDouble(tempr1adayaM[cobaPemda1])));
//
//                        if (tempr1adayaVou[cobaPemda1] != null) {
//                            double voucher = jumlah * Double.parseDouble(tempr1adayaVou[cobaPemda1]);
//                            t_jumlah.setText(String.valueOf(jumlah - voucher));
//                            t_vou.setText(String.valueOf(voucher));
//
//                        } else {
//                            t_jumlah.setText(String.valueOf(jumlah));
//                        }
//                        tampilBiaya.setVisibility(View.VISIBLE);
//                        System.out.println("Cek Pemasangan baru");
//                        dialogInterface.cancel();
//                    }
//                }).setNegativeButton("Oke", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                System.out.println("Pilihan Tidak");
//                jumlah = Double.parseDouble(tempr1adayaI[cobaPemda1] + (lamp * Double.parseDouble(tempr1adayaOB[cobaPemda1])
//                        + (cont* Double.parseDouble(tempr1adayaSOB[cobaPemda1])))) +
//                        Double.parseDouble(tempr1adayaI[cobaPemda1]) +
//                        Double.parseDouble(tempr1adayaGI[cobaPemda1]) +
//                        Double.parseDouble(tempr1adayaSLO[cobaPemda1]) +
//                        Double.parseDouble(tempr1adayaM[cobaPemda1]);
//                t_bpPLN.setText(String.valueOf(Double.parseDouble(tempr1adayaBP[cobaPemda1])));
//                t_daya.setText("Biaya dengan daya " + String.valueOf(Double.parseDouble(tempr1adayadaya[cobaPemda1])));
//                t_Instalasi.setText(String.valueOf(Double.parseDouble(tempr1adayaI[cobaPemda1] + (lamp * Double.parseDouble(tempr1adayaOB[cobaPemda1])
//                        + (cont * Double.parseDouble(tempr1adayaSOB[cobaPemda1]))))));
//                t_gInstalasi.setText(String.valueOf(Double.parseDouble(tempr1adayaGI[cobaPemda1])));
//                t_Slo.setText(String.valueOf(Double.parseDouble(tempr1adayaSLO[cobaPemda1])));
//                t_Materai.setText(String.valueOf(Double.parseDouble(tempr1adayaM[cobaPemda1])));
//
//                if (tempr1adayaVou[cobaPemda1] != null) {
//                    double voucher = jumlah * Double.parseDouble(tempr1adayaVou[cobaPemda1]);
//                    t_jumlah.setText(String.valueOf(jumlah - voucher));
//                    t_vou.setText(String.valueOf(voucher));
//
//                } else {
//                    t_jumlah.setText(String.valueOf(jumlah));
//                }
//                tampilBiaya.setVisibility(View.VISIBLE);
//                dialogInterface.cancel();
//            }
//        });
//            AlertDialog alertDialog = alBuilder.create();
//            alertDialog.show();
//            t_daya.setText("Biaya dengan daya " + String.valueOf(Double.parseDouble(tempr1adayadaya[cobaPemda1])));
//            t_Instalasi.setText(String.valueOf(Double.parseDouble(tempr1adayaI[cobaPemda1])));
//            t_gInstalasi.setText(String.valueOf(Double.parseDouble(tempr1adayaGI[cobaPemda1])));
//            t_Slo.setText(String.valueOf(Double.parseDouble(tempr1adayaSLO[cobaPemda1])));
//            t_Materai.setText(String.valueOf(Double.parseDouble(tempr1adayaM[cobaPemda1])));
//            t_jumlah.setText();
//            if (tempr1adayaVou[cobaPemda1] != null) {
//
//            }
//            tampilBiaya.setVisibility(View.VISIBLE);
        //  } else {
//            t_daya.setText("Biaya dengan daya " + String.valueOf(Double.parseDouble(tempr1adayadaya[cobaPemda1])));
//            t_bpPLN.setText(String.valueOf(Double.parseDouble(tempr1adayaBP[cobaPemda1])));
//            t_Instalasi.setText(String.valueOf(Double.parseDouble(tempr1adayaI[cobaPemda1])));
//            t_gInstalasi.setText(String.valueOf(Double.parseDouble(tempr1adayaGI[cobaPemda1])));
//            t_Slo.setText(String.valueOf(Double.parseDouble(tempr1adayaSLO[cobaPemda1])));
//            t_Materai.setText(String.valueOf(Double.parseDouble(tempr1adayaM[cobaPemda1])));
//            tampilBiaya.setVisibility(View.VISIBLE);
        //   }

        //   double gg = inFM * Double.parseDouble(tempr1adayaBP[cobaPemda1]);

        //  Integer.parseInt()
        //  alBuilder.setIcon(R.drawable.ic_clear_black_24dp);
        tampilBiaya.setVisibility(View.VISIBLE);

    }

    public void btPesanPasangBaru(View view) {
        lat = String.valueOf(mMap.getMyLocation().getLatitude());
        lng = String.valueOf(mMap.getMyLocation().getLongitude());
        System.out.println("Alamatbt " + addresses);
        System.out.println("latitude " + lat);
        System.out.println("longitude " + lng);
        //     harga.setText("Besar daya " + pilihan[posisi] + " seharga " + hargaBaru[posisi]);
        android.app.AlertDialog.Builder alBuilder = new android.app.AlertDialog.Builder(this);
        alBuilder.setTitle("Pemesanan");
        //  alBuilder.setIcon(R.drawable.ic_clear_black_24dp);
        alBuilder.setMessage("Anda yakin pesan pasang baru dengan besar daya " + strDayaBaru[posisi]).setCancelable(false)
                .setPositiveButton("ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.out.println("Pilihan Ya");

                        userId = userId + 1;
                        //       sendNotication();
                        saveDatabase(userId);
                        //   PemasanganBaru.this.finish();
                        System.out.println("Cek Pmasangan baru");
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

    public void btUploadFile(View view) {
        startActivity(new Intent(FormOrder.this, SendGmail.class));

    }

    private void saveDatabase(int userid) {
        //Tidak Boleh Kosong
        String str_nama = nama.getText().toString();
        String str_alamat = alamat.getText().toString();
        String str_nohp = nohp.getText().toString();
        String str_kelurahan = kelurahan.getText().toString();
        String str_nBangunan = noBangunan.getText().toString();
        String fitting = String.valueOf(lamp);
        String sContact = String.valueOf(cont);

        // String str_id = pemesanan.push().getKey();
        timesmap = ServerValue.TIMESTAMP;
        time = String.valueOf(timesmap);
        System.out.println("timestamp " + timesmap);
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
        } else {
            String str_id;
            System.out.println("COba cabang1 " + cobaRayon1);
            int i = cobaRayon1;

            int h = 0;
            for (int g = 0; g < tempr1adayabaru.length; g++) {
                if (strDayaBaru[posisi].equals(tempr1adayabaru[g])) {
                    Toast.makeText(FormOrder.this, "Daya " + tempr1adayabaru[g] + " sudah ada di database ", Toast.LENGTH_SHORT).show();
                    break;
                }
                h++;
                System.out.println("cek " + h);
                //              Toast.makeText(DaftarAplikasi.this, "Daya " + tempr1adayabaru[g] + " tudak ada di database ", Toast.LENGTH_SHORT).show();
            }
            if (h == 15) {
                str_Wilayah = "Kalimantaan Selatan dan Kalimantan Tengah";
                str_Cabang = "Banjarmasin";
                str_Rayon = tempr1[cobaRayon1 - 2];
                str_Gerai = tempr1a[cobaPemda1];

                str_id = wilayah.child("-L8RZ6tzs-N_R2LTlzom").child("cabang").child("-L8W31ly20ZfYmbS5VWk")
                        .child("rayon").child("" + tempr1id[cobaRayon1 - 2]).child("gerai")
                        .child("" + tempr1aid[cobaPemda1 - 1]).child("pemesanan").push().getKey();
                AmbilData wil = new AmbilData(str_id, "Pemasangan Baru", str_nama, str_alamat, str_nohp, "Biaya " + strDayaBaru[posisi], String.valueOf(jumlah),
                        lat, lng, "", str_kelurahan, str_nBangunan, fitting, "voucher",
                        "mcb 1", sContact, str_Wilayah, str_Cabang, str_Rayon, str_Gerai);
                wilayah.child("-L8RZ6tzs-N_R2LTlzom").child("cabang")
                        .child("-L8W31ly20ZfYmbS5VWk").child("rayon").
                        child("" + tempr1id[cobaRayon1 - 2]).child("gerai")
                        .child("" + tempr1aid[cobaPemda1 - 1]).child("pemesanan").child(str_id).setValue(wil);
                System.out.println(cobaPemda1);
                System.out.println("dsds" + cobaPemda1);
//            AmbilData user = new AmbilData(str_id, "Pemasangan Baru ", str_nama, str_alamat, str_nohp
//                    , pilihan[posisi], hargaBaru[posisi], lat, lng, "");
//        AmbilData user = new AmbilData(str_id, "Pemasangan Baru ", str_nama, str_alamat, str_nohp
//                , pilihan[posisi], hargaBaru[posisi], lat, lng, time);
//            pemesanan.child(str_id).setValue(user);
                Toast.makeText(FormOrder.this, "Pemesanan sedang di proses", Toast.LENGTH_SHORT).show();
            }
        }
//        AmbilData user = new AmbilData(str_alamat);

//        AmbilData user = new AmbilData("Pemasangan Baru ", str_nama, str_alamat, str_nohp
//                , pilihan[posisi], hargaBaru[posisi], lat, lng);
//        pesan.push().setValue(user);
//        AmbilData user = new AmbilData(str_id, "Pemasangan Baru ", str_nama, str_alamat, str_nohp
//                , pilihan[posisi], hargaBaru[posisi], lat, lng);


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

    }

    public void pilInstall(View view) {
        boolean pilCek = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.dgInstalasi:
                if (pilCek) {
                    hh.setChecked(false);
                    tanpaInstalasi.setVisibility(LinearLayout.GONE);
                    denganInstakasi.setVisibility(LinearLayout.VISIBLE);
//                    rb1.setChecked(true);
                    //                   tanpaInstalasi.setVisibility(LinearLayout.VISIBLE);
                    manual.setVisibility(LinearLayout.GONE);
                    paket.setVisibility(LinearLayout.GONE);
                    //  pildaya.setVisibility(LinearLayout.VISIBLE);


                }
                break;
            case R.id.tpInstalasi:
                if (pilCek) {
                    //          Toast.makeText(PemasanganBaru.this, "coba", Toast.LENGTH_SHORT).show();
                    hh1.setChecked(false);
                    denganInstakasi.setVisibility(LinearLayout.GONE);
                    tanpaInstalasi.setVisibility(LinearLayout.VISIBLE);
                    //    pildaya.setVisibility(LinearLayout.VISIBLE);

                }
                break;
        }
    }

    public void pilPaket(View view) {
        boolean pilCek = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.pilihanPaket:
                if (pilCek) {
                    manual.setVisibility(LinearLayout.GONE);
                    paket.setVisibility(LinearLayout.VISIBLE);
                    rb1.setChecked(false);
                    lamp = 3;
                    cont = 1;
                }
                break;
            case R.id.pilihanManual:
                if (pilCek) {
                    paket.setVisibility(LinearLayout.GONE);
                    manual.setVisibility(LinearLayout.VISIBLE);
                    rb2.setChecked(false);

                    strLamp = new String[]{
                            "Pilih banyak titik lampu",
                            "1 titik lampu",
                            "2 titik lampu",
                            "3 titik lampu",
                            "4 titik lampu",
                            "5 titik lampu",
                            "6 titik lampu",
                            "7 titik lampu",
                            "8 titik lampu",
                            "9 titik lampu",
                            "10 titik lampu",
                            "Lain-lain",

                    };
                    sLamp.setItems(strLamp);
                    sLamp.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                        @Override
                        public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                            switch (position) {
                                case 0:
                                    System.out.println("cek 1");
                                    break;
                                case 1:
                                    System.out.println("cek 2");
                                    lamp = 1;
                                    break;
                                case 2:
                                    System.out.println("cek 3");
                                    lamp = 2;
                                    break;
                                case 3:
                                    System.out.println("cek 4");
                                    lamp = 3;
                                    break;
                                case 4:
                                    System.out.println("cek 5");
                                    lamp = 4;
                                    break;
                                case 6:
                                    System.out.println("cek 6");
                                    lamp = 6;
                                    break;
                                case 7:
                                    System.out.println("cek 6");
                                    lamp = 7;
                                    break;
                                case 8:
                                    System.out.println("cek 6");
                                    lamp = 8;
                                    break;
                                case 9:
                                    System.out.println("cek 6");
                                    lamp = 9;
                                    break;
                                case 10:
                                    System.out.println("cek 6");
                                    lamp = 10;
                                    break;
                                case 11:
                                    System.out.println("cek 6");
                                    lampLin.setVisibility(View.VISIBLE);
                                    contLin.setVisibility(View.GONE);
                                    lamp = Integer.parseInt(fittingManual.getText().toString());
                                    break;
                            }


                        }
                    });
                    sLamp.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

                        @Override
                        public void onNothingSelected(MaterialSpinner spinner) {
                            Snackbar.make(spinner, "Belum di pilih", Snackbar.LENGTH_LONG).show();
                        }
                    });
                    strCont = new String[]{
                            "Pilih banyak titik stop kontak",
                            "1 titik stop kontak",
                            "2 titik stop kontak",
                            "3 titik stop kontak",
                            "4 titik stop kontak",
                            "5 titik stop kontak",
                            "6 titik stop kontak",
                            "7 titik stop kontak",
                            "8 titik stop kontak",
                            "9 titik stop kontak",
                            "10 titik stop kontak",
                            "Lain-lain",

                    };
                    sCont.setItems(strCont);
                    sCont.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                        @Override
                        public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                            switch (position) {
                                case 0:
                                    System.out.println("cek 1");
                                    break;
                                case 1:
                                    System.out.println("cek 2");
                                    cont = 1;
                                    break;
                                case 2:
                                    System.out.println("cek 3");
                                    cont = 2;
                                    break;
                                case 3:
                                    System.out.println("cek 4");
                                    cont = 3;
                                    break;
                                case 4:
                                    System.out.println("cek 5");
                                    cont = 4;
                                    break;
                                case 6:
                                    System.out.println("cek 6");
                                    cont = 6;
                                    break;
                                case 7:
                                    System.out.println("cek 6");
                                    cont = 7;
                                    break;
                                case 8:
                                    System.out.println("cek 6");
                                    cont = 8;
                                    break;
                                case 9:
                                    System.out.println("cek 6");
                                    cont = 9;
                                    break;
                                case 10:
                                    System.out.println("cek 6");
                                    cont = 10;
                                    break;
                                case 11:
                                    System.out.println("cek 6");
                                    contLin.setVisibility(View.VISIBLE);
                                    lampLin.setVisibility(View.GONE);
                                    cont = Integer.parseInt(sContactManual.getText().toString());
                                    break;
                            }


                        }
                    });
                    sLamp.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

                        @Override
                        public void onNothingSelected(MaterialSpinner spinner) {
                            Snackbar.make(spinner, "Belum di pilih", Snackbar.LENGTH_LONG).show();
                        }
                    });
                }
                break;
        }
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

        strDayaBaru = new String[]{
                "Pilih Daya",
                "450 VA",
                "900 VA",
                "1300 VA",
                "2200 VA",
                "3500 VA",
                "4400 VA",
                "5500 VA",
                "6600 VA",
                "7700 VA",
                "110000 VA",
                "132000 VA",
                "165000 VA",
                "230000 VA",
                "330000 VA",
                "415000 VA"
        };
        sDayaBaru.setItems(strDayaBaru);
        sDayaBaru.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                posisi = position;
                //         Snackbar.make(view, "Besar daya " + strDayaBaru[position], Snackbar.LENGTH_LONG).show();
                //  String setHarga = harga.setText();
                //    Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });
        sDayaBaru.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

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

