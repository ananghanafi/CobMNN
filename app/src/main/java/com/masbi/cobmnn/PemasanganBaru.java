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
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Executor;

import static com.google.android.gms.location.GeofenceStatusCodes.getStatusCodeString;

public class PemasanganBaru extends FragmentActivity implements OnMapReadyCallback {

    //  private static final String TAG = "Tag";
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
    TextView t_bpPLN, t_Instalasi, t_Slo, t_gInstalasi, t_Materai, t_jumlah, t_daya, t_vou, t_jumlahNow;
    int inFM, inFP, inSM, inSP, inMCB, p;
    int lamp, cont;
    LinearLayout contLin, lampLin;
    String lonCLick, latClick;
    String SITE_KEY = "6LfhcVUUAAAAAPdOegz6qETdhHEXqDJlki3W2ijZ";
    String SECRET_KEY = " 6LfhcVUUAAAAAHVLfztHyzASQNSXlH8TwkVfWGXh";
    String userResponseToken;
    NumberFormat formatIndo;
    DecimalFormat kursIndo = (DecimalFormat) DecimalFormat.getCurrencyInstance();
    DecimalFormatSymbols formatRP = new DecimalFormatSymbols();
    String formatUang;
    int jumlah, alihPilPaket;
    double voucher;
    ScrollView scroll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemasangan_baru);
        //    harga = (TextView) findViewById(R.id.harga);
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
        t_jumlah = (TextView) findViewById(R.id.textJumlah);
        t_jumlahNow = (TextView) findViewById(R.id.textJumlahNow);
        lampLin = (LinearLayout) findViewById(R.id.lampLin);
        contLin = (LinearLayout) findViewById(R.id.contLin);
        formatRP.setCurrencySymbol("Rp. ");
        formatRP.setMonetaryDecimalSeparator(',');
        formatRP.setGroupingSeparator('.');
        kursIndo.setDecimalFormatSymbols(formatRP);
        formatUang = "%s %n";
       scroll = (ScrollView) findViewById(R.id.scrollBaru);

        System.out.println("Site Key : 6LfhcVUUAAAAAPdOegz6qETdhHEXqDJlki3W2ijZ");
        System.out.println("Sercet Key : 6LfhcVUUAAAAAHVLfztHyzASQNSXlH8TwkVfWGXh");

        //Client Side Itegration

//        SafetyNet.getClient(this).verifyWithRecaptcha("6LfhcVUUAAAAAPdOegz6qETdhHEXqDJlki3W2ijZ")
//                .addOnSuccessListener(this, new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
//                    @Override
//                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
//                        if (!response.getTokenResult().isEmpty()) {
//                            handleSiteVerify(response.getTokenResult());
//                        }
//                    }
//                })
//                .addOnFailureListener(this, new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        if (e instanceof ApiException) {
//                            ApiException apiException = (ApiException) e;
//                            Log.d(TAG, "Error message: " +
//                                    CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()));
//                        } else {
//                            Log.d(TAG, "Unknown type of error: " + e.getMessage());
//                        }
//                    }
//                });

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
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker

//
//                addresses = new ArrayList<>();
//                try {
//                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                System.out.println("Lat " + latLng.latitude + "long " + latLng.longitude);
                latClick = String.valueOf(latLng.latitude);
                lonCLick = String.valueOf(latLng.longitude);
//                android.location.Address address = addresses.get(0);
//
//                if (address != null) {
//                    StringBuilder sb = new StringBuilder();
//                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
//                        if (i == (address.getMaxAddressLineIndex() - 1)) {
//                            sb.append(address.getAddressLine(i));
//                        } else {
//                            sb.append(address.getAddressLine(i) + ", ");
//                        }
//                    }
//                    mSearchView.setSearchText(sb.toString());
//                }
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
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                // Creating a marker
//                MarkerOptions markerOptions = new MarkerOptions();
//
//                // Setting the position for the marker
//                markerOptions.position(latLng);
//
//                // Setting the title for the marker.
//                // This will be displayed on taping the marker
//
//
//                addresses = new ArrayList<>();
//                try {
//                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                android.location.Address address = addresses.get(0);
//
//                if (address != null) {
//                    StringBuilder sb = new StringBuilder();
//                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
//                        if (i == (address.getMaxAddressLineIndex() - 1)) {
//                            sb.append(address.getAddressLine(i));
//                        } else {
//                            sb.append(address.getAddressLine(i) + ", ");
//                        }
//                    }
//                    mSearchView.setSearchText(sb.toString());
//                }
//                markerOptions.title("Pilih Lokasi");
//                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//
//                // Clears the previously touched position
//                mMap.clear();
//
//                // Animating to the touched position
//                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//
//                // Placing a marker on the touched position
//                mMap.addMarker(markerOptions);
//
//
//            }
//        });

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
        this.infoButtonListener = new OnInfoWindowElemTouchListener(infoWindow,
                getResources().getDrawable(R.color.black), //btn_default_normal_holo_light
                getResources().getDrawable(R.color.colorAccent)) //btn_default_pressed_holo_light
        {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                // Here we can perform some action triggered after clicking the button
                Intent page = new Intent(PemasanganBaru.this, FormOrder.class);

//                System.out.println("Alamat " + addresses);
//                lat = String.valueOf(mMap.getMyLocation().getLatitude());
//                lng = String.valueOf(mMap.getMyLocation().getLongitude());

                sendLat = latClick;
                sendLng = lonCLick;
//                Intent page = new Intent();
                page.putExtra("lat", sendLat);
                page.putExtra("lon", sendLng);
                startActivity(page);

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
        System.out.println("Sebelum " + tempr1aid[cobaPemda1 - 1]);
        wilayah.child("-L8RZ6tzs-N_R2LTlzom").child("cabang").child("-L8W31ly20ZfYmbS5VWk")
                .child("rayon").child("" + tempr1id[cobaRayon1 - 2]).child("gerai")
                .child("" + tempr1aid[cobaPemda1 - 1]).child("penambahan").
                addValueEventListener(new ValueEventListener() {

                                          @Override
                                          public void onDataChange(DataSnapshot dataSnapshot) {
                                              System.out.println("Cek id " + tempr1aid[cobaPemda1 - 1]);
                                              System.out.println("OnData Change");
                                              //   ambilDataList.clear();
                                              //   cobaCabang2 = 0;
                                              //tempr1a = new String[2];
                                              int i = 0;
                                              for (DataSnapshot pesanSnpshot : dataSnapshot.getChildren()) {
                                                  //                   pbAll.setVisibility(View.GONE);
                                                  AmbilData ambilData = pesanSnpshot.getValue(AmbilData.class);

//                                                                  tempr1a[i] = ambilData.getStr_Gerai();
//                                                                  tempr1aid[i] = ambilData.getId();
                                                  tempr1adayadaya[i] = ambilData.getStr_dayaDaya();
                                                  tempr1adayaBP[i] = ambilData.getStr_bpPLN();
                                                  tempr1adayaI[i] = ambilData.getStr_Instalasi();
                                                  tempr1adayaSLO[i] = ambilData.getStr_Slo();
                                                  tempr1adayaGI[i] = ambilData.getStr_gInstalasi();
                                                  tempr1adayaM[i] = ambilData.getStr_Materai();
                                                  tempr1adayaIB[i] = ambilData.getStr_eLampIn();
                                                  tempr1adayaOB[i] = ambilData.getStr_eLampOut();
                                                  tempr1adayaSOB[i] = ambilData.getStr_elContactOut();
                                                  tempr1adayaSIB[i] = ambilData.getStr_elContactIn();
                                                  tempr1adayaVou[i] = ambilData.getStr_voucher();

                                                  i++;


                                              }
                                              System.out.println(" " + tempr1adayaI[posisi]);
                                              System.out.println("Lamp " + lamp);
                                              System.out.println("Cont " + cont);
                                              System.out.println("cek " +
                                                      Integer.parseInt(tempr1adayaI[posisi]) + "\n" +
                                                      Integer.parseInt(tempr1adayaGI[posisi]) + "\n" +
                                                      Integer.parseInt(tempr1adayaSLO[posisi]) + "\n" +
                                                      Integer.parseInt(tempr1adayaM[posisi]));
                                              if (alihPilPaket == 1) {
                                                  int intalasi = Integer.parseInt(tempr1adayaI[posisi]);
                                                  System.out.println("ins " + jumlah);

                                                  t_bpPLN.setText(String.format(formatUang, kursIndo.format(intalasi)));
                                                  t_daya.setText("Biaya dengan daya " + strDayaBaru[posisi]);
                                                  t_Instalasi.setText(String.format(formatUang, kursIndo.format(Integer.parseInt(tempr1adayaI[posisi]))));
                                                  t_gInstalasi.setText(String.format(formatUang, kursIndo.format(Integer.parseInt(tempr1adayaGI[posisi]))));
                                                  t_Slo.setText(String.format(formatUang, kursIndo.format(Integer.parseInt(tempr1adayaSLO[posisi]))));
                                                  t_Materai.setText(String.format(formatUang, kursIndo.format(Integer.parseInt(tempr1adayaM[posisi]))));

                                                  jumlah = intalasi + Integer.parseInt(tempr1adayaBP[posisi]) +
                                                          Integer.parseInt(tempr1adayaGI[posisi]) +
                                                          Integer.parseInt(tempr1adayaSLO[posisi]) +
                                                          Integer.parseInt(tempr1adayaM[posisi]);
                                                  String.format(formatUang, kursIndo.format(jumlah));
                                                  System.out.println("ins " + jumlah);
                                                  t_jumlahNow.setText(String.format(formatUang, kursIndo.format(jumlah)));

                                                  if (tempr1adayaVou[posisi] != null) {
//                                                  double voucher = jumlah * Double.parseDouble(tempr1adayaVou[posisi]);
                                                      voucher = jumlah * 0.05;
                                                      double hasil = jumlah - voucher;
                                                      // System.out.println("gg " + gg);
                                                      t_jumlah.setText(String.format(formatUang, kursIndo.format(hasil)));
                                                      //t_jumlah.setText(String.valueOf(jumlah));
                                                      // t_jumlah.setText(String.valueOf(Double.parseDouble(gg)));
                                                      t_vou.setText(String.format(formatUang, kursIndo.format(voucher)));

                                                  } else {

                                                      t_jumlahNow.setText(String.format(formatUang, kursIndo.format(jumlah)));
                                                      t_vou.setText(String.format(formatUang, kursIndo.format(voucher)));
                                                      t_jumlah.setText(String.format(formatUang, kursIndo.format(jumlah)));
                                                  }
                                                  tampilBiaya.setVisibility(View.VISIBLE);
                                              } else if (alihPilPaket == 2) {


                                                  AlertDialog.Builder alBuilder = new AlertDialog.Builder(PemasanganBaru.this);
                                                  alBuilder.setTitle("Cek Biaya");
                                                  alBuilder.setMessage("Instalasi pertitik ").setCancelable(false)
                                                          .setPositiveButton("Inbow", new DialogInterface.OnClickListener() {
                                                              @Override
                                                              public void onClick(DialogInterface dialogInterface, int i) {
                                                                  System.out.println("Pilihan Ya");
                                                                  int intalasi = Integer.parseInt(tempr1adayaI[posisi]) + (lamp * Integer.parseInt(tempr1adayaIB[posisi])
                                                                          + (cont * Integer.parseInt(tempr1adayaSIB[posisi])));
                                                                  System.out.println("ins " + jumlah);

                                                                  t_bpPLN.setText(String.format(formatUang, kursIndo.format(Integer.parseInt(tempr1adayaBP[posisi]))));
                                                                  t_daya.setText("Biaya dengan daya " + strDayaBaru[posisi]);
                                                                  t_Instalasi.setText(String.format(formatUang, kursIndo.format(intalasi)));
                                                                  t_gInstalasi.setText(String.format(formatUang, kursIndo.format(Integer.parseInt(tempr1adayaGI[posisi]))));
                                                                  t_Slo.setText(String.format(formatUang, kursIndo.format(Integer.parseInt(tempr1adayaSLO[posisi]))));
                                                                  t_Materai.setText(String.format(formatUang, kursIndo.format(Integer.parseInt(tempr1adayaM[posisi]))));

                                                                  jumlah = intalasi + Integer.parseInt(tempr1adayaBP[posisi]) +
                                                                          Integer.parseInt(tempr1adayaGI[posisi]) +
                                                                          Integer.parseInt(tempr1adayaSLO[posisi]) +
                                                                          Integer.parseInt(tempr1adayaM[posisi]);
                                                                  String.format(formatUang, kursIndo.format(jumlah));
                                                                  System.out.println("ins " + jumlah);
                                                                  t_jumlahNow.setText(String.format(formatUang, kursIndo.format(jumlah)));

                                                                  if (tempr1adayaVou[posisi] != null) {
//                                                  double voucher = jumlah * Double.parseDouble(tempr1adayaVou[posisi]);
                                                                      voucher = jumlah * 0.05;
                                                                      double hasil = jumlah - voucher;
                                                                      // System.out.println("gg " + gg);
                                                                      t_jumlah.setText(String.format(formatUang, kursIndo.format(hasil)));
                                                                      //t_jumlah.setText(String.valueOf(jumlah));
                                                                      // t_jumlah.setText(String.valueOf(Double.parseDouble(gg)));
                                                                      t_vou.setText(String.format(formatUang, kursIndo.format(voucher)));

                                                                  } else {

                                                                      t_jumlahNow.setText(String.format(formatUang, kursIndo.format(jumlah)));
                                                                      t_vou.setText(String.format(formatUang, kursIndo.format(voucher)));
                                                                      t_jumlah.setText(String.format(formatUang, kursIndo.format(jumlah)));
                                                                  }
                                                                  tampilBiaya.setVisibility(View.VISIBLE);
                                                                  dialogInterface.cancel();


                                                              }
                                                          }).setNegativeButton("Outbow", new DialogInterface.OnClickListener() {
                                                      @Override
                                                      public void onClick(DialogInterface dialogInterface, int i) {
                                                          System.out.println("Pilihan Tidak");
                                                          int intalasi = Integer.parseInt(tempr1adayaI[posisi]) + (lamp * Integer.parseInt(tempr1adayaOB[posisi])
                                                                  + (cont * Integer.parseInt(tempr1adayaSOB[posisi])));
                                                          System.out.println("ins " + jumlah);

                                                          t_bpPLN.setText(String.format(formatUang, kursIndo.format(Integer.parseInt(tempr1adayaBP[posisi]))));
                                                          t_daya.setText("Biaya dengan daya " + strDayaBaru[posisi]);
                                                          t_Instalasi.setText(String.format(formatUang, kursIndo.format(0)));
                                                          t_gInstalasi.setText(String.format(formatUang, kursIndo.format(Integer.parseInt(tempr1adayaGI[posisi]))));
                                                          t_Slo.setText(String.format(formatUang, kursIndo.format(Integer.parseInt(tempr1adayaSLO[posisi]))));
                                                          t_Materai.setText(String.format(formatUang, kursIndo.format(Integer.parseInt(tempr1adayaM[posisi]))));

                                                          jumlah = intalasi + Integer.parseInt(tempr1adayaBP[posisi]) +
                                                                  Integer.parseInt(tempr1adayaGI[posisi]) +
                                                                  Integer.parseInt(tempr1adayaSLO[posisi]) +
                                                                  Integer.parseInt(tempr1adayaM[posisi]);
                                                          String.format(formatUang, kursIndo.format(jumlah));
                                                          System.out.println("ins " + jumlah);
                                                          t_jumlahNow.setText(String.format(formatUang, kursIndo.format(jumlah)));

                                                          if (tempr1adayaVou[posisi] != null) {
//                                                  double voucher = jumlah * Double.parseDouble(tempr1adayaVou[posisi]);
                                                              voucher = jumlah * 0.05;
                                                              double hasil = jumlah - voucher;
                                                              // System.out.println("gg " + gg);
                                                              t_jumlah.setText(String.format(formatUang, kursIndo.format(hasil)));
                                                              //t_jumlah.setText(String.valueOf(jumlah));
                                                              // t_jumlah.setText(String.valueOf(Double.parseDouble(gg)));
                                                              t_vou.setText(String.format(formatUang, kursIndo.format(voucher)));

                                                          } else {

                                                              t_jumlahNow.setText(String.format(formatUang, kursIndo.format(jumlah)));
                                                              t_vou.setText(String.format(formatUang, kursIndo.format(voucher)));
                                                              t_jumlah.setText(String.format(formatUang, kursIndo.format(jumlah)));
                                                          }
                                                          tampilBiaya.setVisibility(View.VISIBLE);
                                                          System.out.println("Cek Pemasangan baru");
                                                          dialogInterface.cancel();
                                                      }
                                                  });
                                                  AlertDialog alertDialog = alBuilder.create();
                                                  alertDialog.show();
                                              } else if (alihPilPaket == 3) {
                                                  int intalasi = Integer.parseInt(tempr1adayaI[posisi]);
                                                  System.out.println("ins " + jumlah);

                                                  t_bpPLN.setText(String.format(formatUang, kursIndo.format(intalasi)));
                                                  t_daya.setText("Biaya dengan daya " + strDayaBaru[posisi]);
                                                  t_Instalasi.setText(String.format(formatUang, kursIndo.format(Integer.parseInt(tempr1adayaI[posisi]))));
                                                  t_gInstalasi.setText(String.format(formatUang, kursIndo.format(Integer.parseInt(tempr1adayaGI[posisi]))));
                                                  t_Slo.setText(String.format(formatUang, kursIndo.format(Integer.parseInt(tempr1adayaSLO[posisi]))));
                                                  t_Materai.setText(String.format(formatUang, kursIndo.format(Integer.parseInt(tempr1adayaM[posisi]))));

                                                  jumlah = intalasi + Integer.parseInt(tempr1adayaBP[posisi]) +
                                                          Integer.parseInt(tempr1adayaGI[posisi]) +
                                                          Integer.parseInt(tempr1adayaSLO[posisi]) +
                                                          Integer.parseInt(tempr1adayaM[posisi]);
                                                  String.format(formatUang, kursIndo.format(jumlah));
                                                  System.out.println("ins " + jumlah);
                                                  t_jumlahNow.setText(String.format(formatUang, kursIndo.format(jumlah)));

                                                  if (tempr1adayaVou[posisi] != null) {
//                                                  double voucher = jumlah * Double.parseDouble(tempr1adayaVou[posisi]);
                                                      voucher = jumlah * 0.05;
                                                      double hasil = jumlah - voucher;
                                                      // System.out.println("gg " + gg);
                                                      t_jumlah.setText(String.format(formatUang, kursIndo.format(hasil)));
                                                      //t_jumlah.setText(String.valueOf(jumlah));
                                                      // t_jumlah.setText(String.valueOf(Double.parseDouble(gg)));
                                                      t_vou.setText(String.format(formatUang, kursIndo.format(voucher)));

                                                  } else {

                                                      t_jumlahNow.setText(String.format(formatUang, kursIndo.format(jumlah)));
                                                      t_vou.setText(String.format(formatUang, kursIndo.format(voucher)));
                                                      t_jumlah.setText(String.format(formatUang, kursIndo.format(jumlah)));
                                                  }
                                                  tampilBiaya.setVisibility(View.VISIBLE);
                                              }


                                          }

                                          @Override
                                          public void onCancelled(DatabaseError databaseError) {

                                          }

                                      }
                );
        scroll.post(new Runnable() {
            @Override
            public void run() {
                scroll.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

    }

    public void btPesanPasangBaru(final View view) {
        lat = String.valueOf(mMap.getMyLocation().getLatitude());
        lng = String.valueOf(mMap.getMyLocation().getLongitude());
        System.out.println("Alamatbt " + addresses);
        System.out.println("latitude " + lat);
        System.out.println("longitude " + lng);
        //     harga.setText("Besar daya " + pilihan[posisi] + " seharga " + hargaBaru[posisi]);
//        SafetyNet.getClient(PemasanganBaru.this).verifyWithRecaptcha(SITE_KEY)
//                .addOnSuccessListener((Executor) PemasanganBaru.this,
//                        new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
//                            @Override
//                            public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
//                                // Indicates communication with reCAPTCHA service was
//                                // successful.
//                                userResponseToken = response.getTokenResult();
//                                if (!userResponseToken.isEmpty()) {
//                                    // Validate the user response token using the
//                                    // reCAPTCHA siteverify API.
//                                    sendRequest();
//
//                                }
//                            }
//                        })
//                .addOnFailureListener((Executor) PemasanganBaru.this, new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        if (e instanceof ApiException) {
//                            // An error occurred when communicating with the
//                            // reCAPTCHA service. Refer to the status code to
//                            // handle the error appropriately.
//                            ApiException apiException = (ApiException) e;
//                            int statusCode = apiException.getStatusCode();
//                            Toast.makeText(PemasanganBaru.this, "Error\n " + getStatusCodeString(statusCode), Toast.LENGTH_SHORT).show();
//
//                        } else {
//                            // A different, unknown type of error occurred.
//                            Toast.makeText(PemasanganBaru.this, "Error\n " + e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                            //                 Log.d(TAG, "Error: " + e.getMessage());
//                        }
//                    }
//                });

//        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
//        alBuilder.setTitle("Pemesanan");
//        //  alBuilder.setIcon(R.drawable.ic_clear_black_24dp);
//        alBuilder.setMessage("Anda yakin pesan pasang baru dengan besar daya " + strDayaBaru[posisi]).setCancelable(false)
//                .setPositiveButton("ya", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        System.out.println("Pilihan Ya");
//
//                        userId = userId + 1;
//                        SafetyNet.getClient(PemasanganBaru.this).verifyWithRecaptcha(SITE_KEY)
//                                .addOnSuccessListener((Executor) PemasanganBaru.this,
//                                        new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
//                                            @Override
//                                            public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
//                                                // Indicates communication with reCAPTCHA service was
//                                                // successful.
//                                                String userResponseToken = response.getTokenResult();
//                                                if (!userResponseToken.isEmpty()) {
//                                                    // Validate the user response token using the
//                                                    // reCAPTCHA siteverify API.
//                                                }
//                                            }
//                                        })
//                                .addOnFailureListener((Executor) PemasanganBaru.this, new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        if (e instanceof ApiException) {
//                                            // An error occurred when communicating with the
//                                            // reCAPTCHA service. Refer to the status code to
//                                            // handle the error appropriately.
//                                            ApiException apiException = (ApiException) e;
//                                            int statusCode = apiException.getStatusCode();
//                                            Toast.makeText(PemasanganBaru.this, "Error\n " + getStatusCodeString(statusCode), Toast.LENGTH_SHORT).show();
//
//                                        } else {
//                                            // A different, unknown type of error occurred.
//                                            Toast.makeText(PemasanganBaru.this, "Error\n " + e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                                            //                 Log.d(TAG, "Error: " + e.getMessage());
//                                        }
//                                    }
//                                });
//
//                        //       sendNotication();
//                        saveDatabase(userId);
//                        //   PemasanganBaru.this.finish();
//                        System.out.println("Cek Pmasangan baru");
//                    }
//                }).setNegativeButton("tidak", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                System.out.println("Pilihan Tidak");
//
//                dialogInterface.cancel();
//            }
//        });
//        AlertDialog alertDialog = alBuilder.create();
//        alertDialog.show();
    }

    private void sendRequest() {
        String URL_VERIFY_ON_SERVER = "https://developers.google.com/recaptcha/docs/verify";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                URL_VERIFY_ON_SERVER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //        Log.d(TAG, response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    String message = jsonObject.getString("message");

                    if (success) {
                        // Congrats! captcha verified successfully on server
                        // TODO - submit the feedback to your server

//                        layoutFeedbackForm.setVisibility(View.GONE);
//                        messageFeedbackDone.setVisibility(View.VISIBLE);
                        saveDatabase(userId);
                    } else {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("recaptcha-response", SECRET_KEY);
                params.put("user-respon", userResponseToken);

                return params;
            }
        };
        MyApplication.getIntance(this).addToRequestQueue(strReq);
    }

    public void btUploadFile(View view) {
        startActivity(new Intent(PemasanganBaru.this, SendGmail.class));

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
                    Toast.makeText(PemasanganBaru.this, "Daya " + tempr1adayabaru[g] + " sudah ada di database ", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(PemasanganBaru.this, "Pemesanan sedang di proses", Toast.LENGTH_SHORT).show();
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
                    //Toast.makeText(PemasanganBaru.this, "Pilih button cek manual apa paket", Toast.LENGTH_SHORT);
                    tampilBiaya.setVisibility(View.GONE);
                }
                break;
            case R.id.tpInstalasi:
                if (pilCek) {
                    //          Toast.makeText(PemasanganBaru.this, "coba", Toast.LENGTH_SHORT).show();
                    hh1.setChecked(false);
                    denganInstakasi.setVisibility(LinearLayout.GONE);
                    tanpaInstalasi.setVisibility(LinearLayout.VISIBLE);
                    //    pildaya.setVisibility(LinearLayout.VISIBLE);
                    alihPilPaket = 3;
                    tampilBiaya.setVisibility(View.GONE);
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
//                    lamp = 3;
//                    cont = 1;
                    alihPilPaket = 1;
                    tampilBiaya.setVisibility(View.GONE);
                }
                break;
            case R.id.pilihanManual:
                if (pilCek) {
                    paket.setVisibility(LinearLayout.GONE);
                    manual.setVisibility(LinearLayout.VISIBLE);
                    rb2.setChecked(false);
                    alihPilPaket = 2;
                    tampilBiaya.setVisibility(View.GONE);
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
