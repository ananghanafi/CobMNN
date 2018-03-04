package com.masbi.cobmnn;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private TextView mTextMessage;
    private SliderLayout mDemoSlider;
    private static final String TAG = MainActivity.class.getSimpleName();
    private Fragment fragment;
    private FragmentManager fragmentManager;


//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener;

    //    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//         FragmentManager fragmentManager =getSupportFragmentManager();
//         FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            int id = item.getItemId();
//            System.out.println("id "+id);
//            switch (id){
//                case R.id.navigation_home:
//                    fragmentTransaction.replace(R.id.frameLayout, new HomeFragment()).commit();
//                 //   fragment = new HomeFragment();
//                    System.out.println("idhome "+id);
//                    return true;
//                case R.id.navigation_infoAplikasi:
//                    fragmentTransaction.replace(R.id.frameLayout, new InfoAplikasiFragment()).commit();
//                  //  fragment = new PengaturanFragment();
//                    System.out.println("idprofile "+id);
//                    return true;
//                case R.id.navigation_Profile:
//                    fragmentTransaction.replace(R.id.frameLayout, new PengaturanFragment()).commit();
//                //    fragment = new InfoAplikasiFragment();
//                    System.out.println("idInfo "+id);
//                    return true;
//            }
//
//            return false;
//        }
//    };
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            System.out.println("id " + id);
            switch (id) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    //    fragmentTransaction.replace(R.id.frameLayout, new HomeFragment()).commit();
                    System.out.println("idhome " + id);
                    break;
                case R.id.navigation_Profile:
                    fragment = new PengaturanFragment();
                    //    fragmentTransaction.replace(R.id.frameLayout, new PengaturanFragment()).commit();
                    System.out.println("idprofile " + id);
                    break;
                case R.id.navigation_infoAplikasi:
                    fragment = new InfoAplikasiFragment();
                    //       fragmentTransaction.replace(R.id.frameLayout, new InfoAplikasiFragment()).commit();
                    System.out.println("idInfo " + id);
                    break;
            }
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frameLayout, fragment).commit();

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new HomeFragment()).commit();
        OneSignal.startInit(this).init();
        System.out.println("MapsPLNBP = AIzaSyCGVfc_sZgkzRt6VRHlc02J7swSb59y6uk");
        System.out.println("MapsPlacePLNBP = AIzaSyDaIT_c7cTpoEhLjELRiE-Szz3hHe9Nilc");
        System.out.println("GeocodinPLNBP = AIzaSyAkbtW4B5fA4G2UF1Sw-wTKGmCTWKzZyuY");
        System.out.println("DirectionPLNBP = AIzaSyCdKN3qGr2R8Nadc4DBEhFULSPLxuB9O8s");

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
