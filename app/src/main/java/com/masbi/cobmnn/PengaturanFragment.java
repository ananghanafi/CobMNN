package com.masbi.cobmnn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PengaturanFragment extends Fragment {


    public PengaturanFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PengaturanFragment newInstance() {
        PengaturanFragment fragment = new PengaturanFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pengaturan, container, false);
        String[] namaguru = new String[]{
                "Edit Profile ",
                "Ganti Password ",

        };

        int[] imagekiri = new int[]{
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,

        };
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 2; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();

            hm.put("namaguru", namaguru[i]);
            hm.put("imagekiri", Integer.toString(imagekiri[i]));
            list.add(hm);


        }
        String[] dari = {"imagekiri", "namaguru"};
        int[] ke = {R.id.imagekiri, R.id.nama};
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), list, R.layout.list_pengaturan, dari, ke);
        ListView listView = (ListView) v.findViewById(R.id.list_pengaturanid);
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent page = new Intent(getActivity(), EditProfile.class);
                        startActivity(page);
                        break;
                    case 1:
                        Intent page1 = new Intent(getActivity(), GantiPassword.class);
                        startActivity(page1);
                        break;
//                    case 2:
//                        Intent page2 = new Intent(DaftarGuru.this, Guru.class);
//                        startActivity(page2);
//                        break;
//                    case 3:
//                        Intent page3 = new Intent(DaftarGuru.this, Guru.class);
//                        startActivity(page3);
//                        break;
//                    case 4:
//                        Intent page4 = new Intent(DaftarGuru.this, Guru.class);
//                        startActivity(page4);
//                        break;
                }
            }
        });
        return v;
    }

}
