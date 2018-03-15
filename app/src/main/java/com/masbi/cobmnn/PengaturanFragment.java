package com.masbi.cobmnn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PengaturanFragment extends Fragment {
    EditText kritikSaran;
    FirebaseDatabase database;
    DatabaseReference kritikSaranDf;
    private WebView mywebview;
    Button kirimSaran;

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
        kritikSaran = (EditText) v.findViewById(R.id.inputSaran);
        kirimSaran = (Button) v.findViewById(R.id.kirimsaran);
        kritikSaranDf = FirebaseDatabase.getInstance().getReference("pesan");
        mywebview = (WebView) v.findViewById(R.id.webviewSaran);
//        String newUA = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
//        mywebview.getSettings().setUserAgentString(newUA);
        mywebview.setFocusable(true);
        mywebview.setDuplicateParentStateEnabled(true);
        mywebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mywebview.getSettings().setJavaScriptEnabled(true);
        mywebview.loadUrl("http://drive.google.com/drive/mobile/folders/12qj40MNw9P0BQwps5oz4FChKZrPzbCfA");
        mywebview.setWebViewClient(new WebViewClient());

        kirimSaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        String str_id = kritikSaranDf.push().getKey();
        String str_kritik = kritikSaran.getText().toString();
        AmbilData user = new AmbilData(str_id, str_kritik);
        kritikSaranDf.child(str_id).setValue(user);
                Toast.makeText(getActivity(), "Kritik/Saran Sudah dikirim terimakasih ", Toast.LENGTH_SHORT).show();
            }
        });

//        String[] namaguru = new String[]{
//                "Edit Profile ",
//                "Ganti Password ",
//
//        };
//
//        int[] imagekiri = new int[]{
//                R.mipmap.ic_launcher,
//                R.mipmap.ic_launcher,
//
//        };
//        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
//        for (int i = 0; i < 2; i++) {
//            HashMap<String, String> hm = new HashMap<String, String>();
//
//            hm.put("namaguru", namaguru[i]);
//            hm.put("imagekiri", Integer.toString(imagekiri[i]));
//            list.add(hm);
//
//
//        }
//        String[] dari = {"imagekiri", "namaguru"};
//        int[] ke = {R.id.imagekiri, R.id.nama};
//        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), list, R.layout.list_pengaturan, dari, ke);
//        ListView listView = (ListView) v.findViewById(R.id.list_pengaturanid);
//        listView.setAdapter(simpleAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                switch (position) {
//                    case 0:
//                        Intent page = new Intent(getActivity(), EditProfile.class);
//                        startActivity(page);
//                        break;
//                    case 1:
//                        Intent page1 = new Intent(getActivity(), GantiPassword.class);
//                        startActivity(page1);
//                        break;
////                    case 2:
////                        Intent page2 = new Intent(DaftarGuru.this, Guru.class);
////                        startActivity(page2);
////                        break;
////                    case 3:
////                        Intent page3 = new Intent(DaftarGuru.this, Guru.class);
////                        startActivity(page3);
////                        break;
////                    case 4:
////                        Intent page4 = new Intent(DaftarGuru.this, Guru.class);
////                        startActivity(page4);
////                        break;
//                }
//            }
//        });

        return v;
    }


//    public void kirimSaran(View view) {
//        String str_id = kritikSaranDf.push().getKey();
//        String str_kritik = kritikSaran.getText().toString();
//        AmbilData user = new AmbilData(str_id, str_kritik);
//        kritikSaranDf.child(str_id).setValue(user);
//        Toast.makeText(getActivity(), "Kritik/Saran Sudah dikirim terimakasih ", Toast.LENGTH_SHORT).show();
//    }
}
