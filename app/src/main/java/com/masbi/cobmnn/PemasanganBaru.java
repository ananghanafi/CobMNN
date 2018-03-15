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
    DatabaseReference myRef, pesan, pemesanan;
    EditText nama, alamat, nohp;
    int userId;
    String namaS, alamatS, nohpS, id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemasangan_baru);
        harga = (TextView) findViewById(R.id.harga);
        nama = (EditText) findViewById(R.id.namaBaru);
        alamat = (EditText) findViewById(R.id.alamatBaru);
        nohp = (EditText) findViewById(R.id.nohpBaru);
        String setHarga;
        final int[] cek;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        pemesanan = FirebaseDatabase.getInstance().getReference("pemesanan");
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
                "1.703.000",
                "2.170.000",
                "2.583.000",
                "3.442.000",
                "4.766.000",
                "5.665.600",
                "6.764.500",

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
                // Here we can perform some action triggered after clicking the button
                Intent page = new Intent(PemasanganBaru.this, FormOrder.class);

//                System.out.println("Alamat " + addresses);
//                lat = String.valueOf(mMap.getMyLocation().getLatitude());
//                lng = String.valueOf(mMap.getMyLocation().getLongitude());

                sendLat = String.valueOf(mMap.getMyLocation().getLatitude());
                sendLng = String.valueOf(mMap.getMyLocation().getLongitude());
//                Intent page = new Intent();
                page.putExtra("lat", sendLat);
                page.putExtra("lon", sendLng);
                startActivity(page);

                Toast.makeText(PemasanganBaru.this, "lat " + sendLat + " lng " + sendLng, Toast.LENGTH_SHORT).show();
                System.out.println("Alamatbt " + addresses);
                System.out.println("latitude " + lat);
                System.out.println("longitude " + lng);
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
                        Toast.makeText(PemasanganBaru.this, "Pemesanan sedang di proses", Toast.LENGTH_SHORT).show();
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


    private void saveDatabase(int userid) {
        //Tidak Boleh Kosong
        String str_nama = nama.getText().toString();
        String str_alamat = alamat.getText().toString();
        String str_nohp = nohp.getText().toString();
        String str_id = pemesanan.push().getKey();
        timesmap = ServerValue.TIMESTAMP;
        time = String.valueOf(timesmap);
        System.out.println("timestamp "+ timesmap);
//        AmbilData user = new AmbilData(str_alamat);

//        AmbilData user = new AmbilData("Pemasangan Baru ", str_nama, str_alamat, str_nohp
//                , pilihan[posisi], hargaBaru[posisi], lat, lng);
//        pesan.push().setValue(user);
//        AmbilData user = new AmbilData(str_id, "Pemasangan Baru ", str_nama, str_alamat, str_nohp
//                , pilihan[posisi], hargaBaru[posisi], lat, lng);

        AmbilData user = new AmbilData(str_id, "Pemasangan Baru ", str_nama, str_alamat, str_nohp
                , pilihan[posisi], hargaBaru[posisi], lat, lng,"");
//        AmbilData user = new AmbilData(str_id, "Pemasangan Baru ", str_nama, str_alamat, str_nohp
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
