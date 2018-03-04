package com.masbi.cobmnn;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.masbi.cobmnn.tools.MapWrapperLayout;
import com.masbi.cobmnn.tools.OnInfoWindowElemTouchListener;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PenambahanDaya extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Geocoder geocoder;
    double lat;
    double lng;
    FloatingSearchView mSearchView;
    MapWrapperLayout mapWrapperLayout;
    List<Address> addresses;

    private ViewGroup infoWindow;
    private TextView infoTitle;
    private OnInfoWindowElemTouchListener infoButtonListener;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    FirebaseDatabase database;
    DatabaseReference myRef;
    TextView harga;
    String pilihan[];
    String[] hargaBaru;
    int posisi;

    @Override
    protected void onStart() {
        super.onStart();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
            //    Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
           //     Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penambahan_daya);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        harga = (TextView) findViewById(R.id.harga);
        String setHarga;
        pilihan = new String[]{
                "Cupcake",
                "Donut",
                "Eclair",
                "Froyo",
                "Gingerbread",
                "Honeycomb",
                "Ice Cream Sandwich",
                "Jelly Bean",
                "KitKat",
                "Lollipop",
                "Marshmallow",
                "Nougat",
                "Oreo"
        };
        hargaBaru = new String[]{
                "10000",
                "10001",
                "10002",
                "10003",
                "10004",
                "10005",
                "10006",
                "10007",
                "10008",
                "10009",
                "10010",
                "10011",
                "10012",
                "10013",

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
        this.infoButtonListener = new OnInfoWindowElemTouchListener(infoWindow,
                getResources().getDrawable(R.color.black), //btn_default_normal_holo_light
                getResources().getDrawable(R.color.colorAccent)) //btn_default_pressed_holo_light
        {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                // Here we can perform some action triggered after clicking the button
                Intent page = new Intent(PenambahanDaya.this, FormOrder.class);
                startActivity(page);
                System.out.println("Alamat " + addresses);
                System.out.println("Alamat " + addresses);
                lat = mMap.getMyLocation().getLatitude();
                lng = mMap.getMyLocation().getLongitude();
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

    public void btPesanTambahDaya(View view) {
        lat = mMap.getMyLocation().getLatitude();
        lng = mMap.getMyLocation().getLongitude();
        System.out.println("Alamatbt " + addresses);
        System.out.println("latitude " + lat);
        System.out.println("longitude " + lng);
        harga.setText("Besar daya " + pilihan[posisi] + " seharga " + hargaBaru[posisi]);
        AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
        alBuilder.setTitle("Pemesanan");
        //  alBuilder.setIcon(R.drawable.ic_clear_black_24dp);
        alBuilder.setMessage("Anda yakin pesan penambahan daya dengan besar daya " + pilihan[posisi]
                + " seharga " + hargaBaru[posisi]).setCancelable(false)
                .setPositiveButton("ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.out.println("Pilihan Ya");
                        Toast.makeText(PenambahanDaya.this, "Pemesanan sedang di proses",Toast.LENGTH_SHORT).show();
                        PenambahanDaya.this.finish();
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
}
