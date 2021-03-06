package com.masbi.cobmnn;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SliderLayout mDemoSlider;

    private OnFragmentInteractionListener mListener;
    DatabaseReference promosi;
    String promosiAtas[] = new String[5];

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDemoSlider = (SliderLayout) view.findViewById(R.id.slider);
        CardView imgTambah = (CardView) view.findViewById(R.id.penambahanDaya);
        CardView imgPasang = (CardView) view.findViewById(R.id.pemasanganBaru);
        CardView imgPerbaikan = (CardView) view.findViewById(R.id.perbaikan);
        CardView imgPLn = (CardView) view.findViewById(R.id.infoPLN);
        promosi = FirebaseDatabase.getInstance().getReference("promosi");
        //  FloatingActionButton fabLogin = (FloatingActionButton) view.findViewById(R.id.fab);
//        promosi.addValueEventListener(new ValueEventListener() {
//
//                                          @Override
//                                          public void onDataChange(DataSnapshot dataSnapshot) {
//                                              System.out.println("OnData Change");
//                                              //           ambilDataList.clear();
//                                              int i = 0;
//                                              for (DataSnapshot pesanSnpshot : dataSnapshot.getChildren()) {
//                                                  //                   pbAll.setVisibility(View.GONE);
//                                                  AmbilData ambilData = pesanSnpshot.getValue(AmbilData.class);
//                                                  //  ambilDataList.add(ambilData);
//                                                  promosiAtas[i] = ambilData.getPromosi1();
//                                                  System.out.println("cek Promosi "+promosiAtas[1]);
//
//                                                  i++;
//
//                                              }
//                                          }
//                                          @Override
//                                          public void onCancelled(DatabaseError databaseError) {
//
//                                          }
//
//                                      }
//        );
        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("Promosi 1", "https://1.bp.blogspot.com/-55MSTK-khkg/WuPE5HNxIbI/AAAAAAAAADw/Aj6kvxnSqNAWHnMCM4bHo41pvupF2pKwgCLcBGAs/s1600/Promosi2.png");
        url_maps.put("Promosi 2", "https://1.bp.blogspot.com/-55MSTK-khkg/WuPE5HNxIbI/AAAAAAAAADw/Aj6kvxnSqNAWHnMCM4bHo41pvupF2pKwgCLcBGAs/s1600/Promosi2.png");
        url_maps.put("Promosi 3", "https://1.bp.blogspot.com/-55MSTK-khkg/WuPE5HNxIbI/AAAAAAAAADw/Aj6kvxnSqNAWHnMCM4bHo41pvupF2pKwgCLcBGAs/s1600/Promosi2.png");
//        url_maps.put("Promosi 3", "https://1.bp.blogspot.com/-55MSTK-khkg/WuPE5HNxIbI/AAAAAAAAADw/Aj6kvxnSqNAWHnMCM4bHo41pvupF2pKwgCLcBGAs/s1600/Promosi2.png");
//        url_maps.put("Promosi 4", "https://1.bp.blogspot.com/-55MSTK-khkg/WuPE5HNxIbI/AAAAAAAAADw/Aj6kvxnSqNAWHnMCM4bHo41pvupF2pKwgCLcBGAs/s1600/Promosi2.png");
//        url_maps.put("Promosi 5", "https://1.bp.blogspot.com/-55MSTK-khkg/WuPE5HNxIbI/AAAAAAAAADw/Aj6kvxnSqNAWHnMCM4bHo41pvupF2pKwgCLcBGAs/s1600/Promosi2.png");
//

//        url_maps.put("Promosi 1", ""+promosiAtas[0]);
//        url_maps.put("Promosi 2", ""+promosiAtas[1]);
//        url_maps.put("Promosi 3", ""+promosiAtas[2]);
//        url_maps.put("Promosi 3", ""+promosiAtas[3]);
//        url_maps.put("Promosi 4", ""+promosiAtas[4]);
//        url_maps.put("Promosi 5", "https://1.bp.blogspot.com/-55MSTK-khkg/WuPE5HNxIbI/AAAAAAAAADw/Aj6kvxnSqNAWHnMCM4bHo41pvupF2pKwgCLcBGAs/s1600/Promosi2.png");

        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
        imgTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tambah = new Intent(getActivity(), PenambahanDaya.class);
                startActivity(tambah);
            }
        });
        imgPasang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pemasangan = new Intent(getActivity(), PemasanganBaru.class);
                startActivity(pemasangan);
            }
        });
        imgPerbaikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pemasangan = new Intent(getActivity(), PemasanganBaru.class);
                startActivity(pemasangan);
            }
        });
        imgPLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pemasangan = new Intent(getActivity(), InfoPLN.class);
                startActivity(pemasangan);
            }
        });
//        fabLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), Login.class));
//            }
//        });
//clickPasang();
//clickTambah();

    }

//    public void clickTambah() {
//        Intent tambah= new Intent(getActivity(), PenambahanDaya.class);
//        startActivity(tambah);
//    }
//    public void clickPasang() {
//        Intent pemasangan = new Intent(getActivity(), PemasanganBaru.class);
//        startActivity(pemasangan);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            Toast.makeText(context, "Home", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
