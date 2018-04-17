package com.masbi.cobmnn;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.masbi.cobmnn.tools.MapWrapperLayout;
import com.masbi.cobmnn.tools.OnInfoWindowElemTouchListener;


import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class PemasanganBaru extends FragmentActivity implements OnMapReadyCallback {

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
    EditText nama, alamat, nohp, fittingPaket, sContactPaket, fittingManual, sContactManual;
    int userId;
    String namaS, alamatS, nohpS, id;
    RadioGroup rg1, rg2;
    RadioButton rb1, rb2, hh, hh1;
    LinearLayout denganInstakasi, tanpaInstalasi, paket, manual, pildaya;
    String[] tempc = new String[2];
    String[] tempr1 = new String[7];
    String[] tempr1id = new String[7];
    String[] tempr1a = new String[3];
    String[] tempr1aid = new String[3];
    String[] tempr1adayabaru = new String[15];
    String[] tempr1adayadaya = new String[15];
    String[] tempCabang1 = new String[2];
    int coba, cobaCabang1, cobaCabang2, cobaRayon1, cobaRayon2, cobaRayon3, cobaRayon4, cobaRayon5, cobaRayon6, cobaRayon7, cobaRayon8,
            cobaPemda1, cobaPemda2, cobaPemda3, cobaPemda4;
    String str_Wilayah, str_Cabang, str_Rayon, str_Pemda, str_Gerai,
            str_bpPLN, str_Instalasi, str_Slo, str_gInstalasi, str_Materai,
            str_adminDaya, str_tokenDaya, str_MateraiDaya, str_daya, str_dayaDaya,
            str_eLampOut, str_eLampIn, str_elContactOut, str_elContactIn;
    MaterialSpinner sWilayah, sCabang, sRayon, sPemda, sGerai, sForm, sDayaBaru, sDayaDaya;
    String strWilayah[], strCabang[], strRayon[], strPemda[], strGerai[], strForm[], strDayaBaru[], strDayaDaya[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemasangan_baru);
        harga = (TextView) findViewById(R.id.harga);
        nama = (EditText) findViewById(R.id.namaBaru);
        alamat = (EditText) findViewById(R.id.alamatBaru);
        nohp = (EditText) findViewById(R.id.nohpBaru);
        fittingPaket = (EditText) findViewById(R.id.fittingLampuBaruPaket);
        sContactPaket = (EditText) findViewById(R.id.StopContactLampuBaruPaket);
        fittingManual = (EditText) findViewById(R.id.fittingLampuBaruManual);
        sContactManual = (EditText) findViewById(R.id.StopContactLampuManual);
        String setHarga;
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
        wilayah = FirebaseDatabase.getInstance().getReference("wilayah");

        strWilayah = new String[]{
                "Pilih Wilayah",
                "Kalimantan Selatan dan Tengah",
                "Tambah",

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
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        geocoder = new Geocoder(this, Locale.getDefault());

        mSearchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                //get suggestions based on newQuery

                //pass them on to the search view
                mSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
                    @Override
                    public void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView, SearchSuggestion item, int itemPosition) {

                        //here you can set some attributes for the suggestion's left icon and text. For example,
                        //you can choose your favorite image-loading library for setting the left icon's image.
                    }

                });
            }
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                String g = currentQuery;
                System.out.println("masuk sini " + currentQuery);
                geocoder = new Geocoder(getBaseContext());
                List<Address> addresses = null;

                try {
                    // Getting a maximum of 3 Address that matches the input
                    // text
                    addresses = geocoder.getFromLocationName(g, 3);
                    if (addresses != null && !addresses.equals(""))
                        search(addresses);

                } catch (Exception e) {

                }
            }
        });

        // Get the button view
        View locationButton = ((View) findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));

        // and next place it, for exemple, on bottom right (as Google Maps app)
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.setMargins(0, 0, 30, 30);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapWrapperLayout = (MapWrapperLayout) findViewById(R.id.map_relative_layout);
        // Add a marker in Sydney and move the camera

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker


                addresses = new ArrayList<>();
                try {
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                android.location.Address address = addresses.get(0);

                if (address != null) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                        if (i == (address.getMaxAddressLineIndex() - 1)) {
                            sb.append(address.getAddressLine(i));
                        } else {
                            sb.append(address.getAddressLine(i) + ", ");
                        }
                    }
                    mSearchView.setSearchText(sb.toString());
                }
                markerOptions.title("Pilih Lokasi");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

                // Clears the previously touched position
                mMap.clear();

                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);


            }
        });

        // MapWrapperLayout initialization
        // 39 - default marker height
        // 20 - offset between the default InfoWindow bottom edge and it's content bottom edge
        mapWrapperLayout.init(mMap, getPixelsFromDp(this, 39 + 20));

        // We want to reuse the info window for all the markers,
        // so let's create only one class member instance
        this.infoWindow = (ViewGroup) getLayoutInflater().inflate(R.layout.info_window, null);
        this.infoTitle = (TextView) infoWindow.findViewById(R.id.title);

        // Setting custom OnTouchListener which deals with the pressed state
        // so it shows up
//        this.infoButtonListener = new GoogleMap.OnInfoWindowLongClickListener(infoWindow,
//                getResources().getDrawable(R.color.black),
//                getResources().getDrawable(R.color.colorAccent)) //btn_default_pressed_holo_light
//
//        {
//
//            @Override
//            public void onInfoWindowLongClick (Marker marker){
//
//        }
//        } ;
        this.infoButtonListener = new OnInfoWindowElemTouchListener(infoWindow,
                getResources().getDrawable(R.color.black), //btn_default_normal_holo_light
                getResources().getDrawable(R.color.colorAccent)) //btn_default_pressed_holo_light
        {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {


//                System.out.println("Alamat " + addresses);
//                lat = String.valueOf(mMap.getMyLocation().getLatitude());
//                lng = String.valueOf(mMap.getMyLocation().getLongitude());
//
//                sendLat = String.valueOf(mMap.getMyLocation().getLatitude());
//                sendLng = String.valueOf(mMap.getMyLocation().getLongitude());
                sendLat = String.valueOf(mMap.getMyLocation().getLatitude());
                sendLng = String.valueOf(mMap.getMyLocation().getLongitude());
//                Intent page = new Intent();


                Toast.makeText(PemasanganBaru.this, "lat " + sendLat + " lng " + sendLng, Toast.LENGTH_SHORT).show();
                System.out.println("Alamatbt " + addresses);
                System.out.println("latitude " + lat);
                System.out.println("longitude " + lng);
                // Here we can perform some action triggered after clicking the button
                Intent page = new Intent(PemasanganBaru.this, FormOrder.class);
                page.putExtra("lat", sendLat);
                page.putExtra("lon", sendLng);
                startActivity(page);
                //  Toast.makeText(PenambahanDaya.this, marker.getTitle() + "'s button clicked!", Toast.LENGTH_SHORT).show();
            }
        };
        this.infoWindow.setOnTouchListener(infoButtonListener);

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Setting up the infoWindow with current's marker info
                infoTitle.setText(marker.getTitle());
                infoButtonListener.setMarker(marker);

                // We must call this to set the current marker and infoWindow references
                // to the MapWrapperLayout
                mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
                return infoWindow;
            }
        });
    }

    protected void search(List<Address> addresses) {

        Address address = (Address) addresses.get(0);
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

        System.out.println("adress");
        MarkerOptions markerOptions = new MarkerOptions();
        if (address != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                if (i == (address.getMaxAddressLineIndex() - 1)) {
                    sb.append(address.getAddressLine(i));
                } else {
                    sb.append(address.getAddressLine(i) + ", ");
                }
            }
            mSearchView.setSearchText(sb.toString());
        }

        markerOptions.position(latLng);
        markerOptions.title("Pilih Lokasi");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mMap.clear();
        mMap.addMarker(markerOptions);


        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));


    }

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    public void btCekBiaya(View view) {
        harga.setText("Besar daya " + pilihan[posisi] + " seharga " + hargaBaru[posisi]);
        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
        alBuilder.setTitle("Cek Biaya");
        //  alBuilder.setIcon(R.drawable.ic_clear_black_24dp);
        alBuilder.setMessage(" " + pilihan[posisi]
                + " seharga " + hargaBaru[posisi]).setCancelable(false)
                .setPositiveButton("Lihat Rincian", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.out.println("Pilihan Ya");
                        startActivity(new Intent(PemasanganBaru.this, DriveMcc.class));
                        System.out.println("Cek Pemasangan baru");
                    }
                }).setNegativeButton("Oke", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.out.println("Pilihan Tidak");

                harga.requestFocus();
                //  startActivity(new Intent(PemasanganBaru.this, RecycleActivity.class));
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = alBuilder.create();
        alertDialog.show();

    }

    public void btPesanPasangBaru(View view) {
        lat = String.valueOf(mMap.getMyLocation().getLatitude());
        lng = String.valueOf(mMap.getMyLocation().getLongitude());
        System.out.println("Alamatbt " + addresses);
        System.out.println("latitude " + lat);
        System.out.println("longitude " + lng);
        harga.setText("Besar daya " + pilihan[posisi] + " seharga " + hargaBaru[posisi]);
        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
        alBuilder.setTitle("Pemesanan");
        //  alBuilder.setIcon(R.drawable.ic_clear_black_24dp);
        alBuilder.setMessage("Anda yakin pesan penambahan baru dengan besar daya " + pilihan[posisi]
                + " seharga " + hargaBaru[posisi]).setCancelable(false)
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
        AlertDialog alertDialog = alBuilder.create();
        alertDialog.show();
    }

    public void btUploadFile(View view) {
        startActivity(new Intent(PemasanganBaru.this, SendGmail.class));

    }

    private void saveDatabase(int userid) {
        //Tidak Boleh Kosong
        String str_nama = nama.getText().toString();
        String str_alamat = alamat.getText().toString();
        String str_nohp = nohp.getText().toString();
        String str_id = pemesanan.push().getKey();
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
//            AmbilData user = new AmbilData(str_id, "Pemasangan Baru ", str_nama, str_alamat, str_nohp
//                    , pilihan[posisi], hargaBaru[posisi], lat, lng, "");
//        AmbilData user = new AmbilData(str_id, "Pemasangan Baru ", str_nama, str_alamat, str_nohp
//                , pilihan[posisi], hargaBaru[posisi], lat, lng, time);
//            pemesanan.child(str_id).setValue(user);
            Toast.makeText(PemasanganBaru.this, "Pemesanan sedang di proses", Toast.LENGTH_SHORT).show();
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
                    tanpaInstalasi.setVisibility(LinearLayout.VISIBLE);
                    manual.setVisibility(LinearLayout.GONE);
                    paket.setVisibility(LinearLayout.GONE);
                  //  pildaya.setVisibility(LinearLayout.VISIBLE);

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
                break;
            case R.id.tpInstalasi:
                if (pilCek) {
                    Toast.makeText(PemasanganBaru.this, "coba", Toast.LENGTH_SHORT).show();
                    hh1.setChecked(false);
                    denganInstakasi.setVisibility(LinearLayout.GONE);
                    tanpaInstalasi.setVisibility(LinearLayout.VISIBLE);
                //    pildaya.setVisibility(LinearLayout.VISIBLE);
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
                }
                break;
            case R.id.pilihanManual:
                if (pilCek) {
                    paket.setVisibility(LinearLayout.GONE);
                    manual.setVisibility(LinearLayout.VISIBLE);
                    rb2.setChecked(false);
                }
                break;
        }
    }

    private void spinnerCabang() {

        if (coba == 2) {
            strCabang = new String[]{
                    "Pilih Cabang",
                    "" + tempc[0], "" + tempc[1],
                    "Tambah",

            };
        } else if (coba == 3) {
            strCabang = new String[]{
                    "Pilih Cabang",
                    tempc[0], tempc[1], tempc[2],
                    "Tambah",

            };
        } else if (coba == 4) {
            strCabang = new String[]{
                    "Pilih Cabang",
                    tempc[0], tempc[1], tempc[2], tempc[3],
                    "Tambah",

            };
        } else if (coba == 5) {
            strCabang = new String[]{
                    "Pilih Cabang",
                    tempc[0], tempc[1], tempc[2], tempc[3], tempc[4],
                    "Tambah",

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
                    "Tambah",

            };
        } else if (cobaCabang2 == 3) {
            strRayon = new String[]{
                    "Pilih Rayon",
                    "" + tempr1[0], "" + tempr1[1], "" + tempr1[2],
                    "Tambah",

            };
        } else if (cobaCabang2 == 4) {
            strRayon = new String[]{
                    "Pilih Rayon",
                    "" + tempr1[0], tempr1[1], tempr1[2], tempr1[3],

                    "Tambah",

            };
        } else if (cobaCabang2 == 5) {
            strRayon = new String[]{
                    "Pilih Rayon",
                    tempr1[0], tempr1[1], tempr1[2], tempr1[3], tempr1[4],
                    "Tambah",

            };
        } else if (cobaCabang2 == 6) {
            strRayon = new String[]{
                    "Pilih Rayon",
                    tempr1[0], tempr1[1], tempr1[2], tempr1[3], tempr1[4], tempr1[5],
                    "Tambah",

            };
        } else if (cobaCabang2 == 7) {
            strRayon = new String[]{
                    "Pilih Rayon",
                    tempr1[0], tempr1[1], tempr1[2], tempr1[3], tempr1[4], tempr1[5], tempr1[6],
                    "Tambah",

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
                    "" + tempr1a[0],
                    "Tambah",

            };
        } else if (cobaCabang2 == 2) {
            strGerai = new String[]{
                    "" + tempr1a[0], "" + tempr1a[1],
                    "Tambah",

            };
        } else if (cobaCabang2 == 3) {
            strGerai = new String[]{
                    "" + tempr1a[0], "" + tempr1a[1], "" + tempr1a[2],
                    "Tambah",

            };
        } else if (cobaCabang2 == 4) {
            strGerai = new String[]{
                    "" + tempr1a[0], tempr1a[1], tempr1a[2], tempr1a[3],
                    "Tambah",

            };
        } else if (cobaCabang2 == 5) {
            strGerai = new String[]{
                    tempr1a[0], tempr1a[1], tempr1a[2], tempr1a[3], tempr1a[4],
                    "Tambah",

            };
        } else if (cobaCabang2 == 6) {
            strGerai = new String[]{
                    tempr1a[0], tempr1a[1], tempr1a[2], tempr1a[3], tempr1a[4], tempr1a[5],
                    "Tambah",

            };
        } else if (cobaCabang2 == 7) {
            strGerai = new String[]{
                    tempr1a[0], tempr1a[1], tempr1a[2], tempr1a[3], tempr1a[4], tempr1a[5], tempr1a[6],
                    "Tambah",

            };
        } else if (cobaCabang2 == 8) {
            strGerai = new String[]{
                    tempr1a[0], tempr1a[1], tempr1a[2], tempr1a[3], tempr1a[4], tempr1a[5], tempr1a[6], tempr1a[7],
                    "Tambah",

            };
        }

        sGerai.setItems(strGerai);
        sGerai.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                switch (position) {
                    case 0:
                        System.out.println("cek 2");
                        cobaPemda1 = 1;
                        cobaRayon2 = 2;
                        System.out.println("COba Pemda " + cobaPemda1);
                        //            Toast.makeText(DaftarAplikasi.this, "coba " + cobaPemda1 + " df " + tempr1aid[cobaPemda1 - 1], Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        System.out.println("cek 3");
                        cobaPemda1 = 2;
                        cobaRayon2 = 3;
                        //          Toast.makeText(DaftarAplikasi.this, "coba " + cobaPemda1, Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        cobaPemda1 = 3;
                        cobaRayon2 = 4;
                        System.out.println("cek 3");
                        break;
                    case 3:
                        cobaPemda1 = 4;
                        cobaRayon2 = 5;
                        System.out.println("cek 3");
                        //lGerai.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        cobaPemda1 = 5;
                        System.out.println("cek 3");
                        break;
                    case 5:
                        cobaPemda1 = 6;
                        System.out.println("cek 3");
                        break;
                    case 6:
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
