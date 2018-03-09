package com.masbi.cobmnn;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecycleActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    ListView listView;
    Arraylist adapter;
    List<AmbilData> ambilDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        adapter = new Arraylist(RecycleActivity.this, ambilDataList);
//        recyclerView = (RecyclerView) findViewById(R.id.tampilanRecycler);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        System.out.println("onCreate");
        listView = (ListView) findViewById(R.id.listViewPesan);
        ambilDataList = new ArrayList<>();
//        AmbilData user = new AmbilData("Pemasangan Baru ", "adad", "str_alamat", "str_nohp"
//                , "sdd", "dsdsd", 3.77, 6.77);
//        mDatabase.child("" + 1).setValue(user);
        System.out.println("Brosur File : https://drive.google.com/file/d/1WYMYtIDsuu1Kdu0lE5Mt5UooKM_3683v/view");
        System.out.println("Brosur Folder : https://drive.google.com/drive/mobile/folders/12qj40MNw9P0BQwps5oz4FChKZrPzbCfA");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }


        });
    }

    //
    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("on Start");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("OnData Change");
                ambilDataList.clear();
                int i = 0;
                for (DataSnapshot pesanSnpshot : dataSnapshot.getChildren()) {

                    AmbilData ambilData = pesanSnpshot.getValue(AmbilData.class);
                    System.out.println("NO " + i + 1 + "Ambil Data " + ambilData);
                    ambilDataList.add(ambilData);
                }
                //  Arraylist adapter = new Arraylist(RecycleActivity.this, ambilDataList);
                listView.setAdapter(adapter);
                System.out.println(" kclc " + adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
//    FirebaseRecyclerOptions<AmbilData> options = new FirebaseRecyclerOptions.Builder<AmbilData>()
//            .setQuery(mDatabase, AmbilData.class).build();
//
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        System.out.println("OnStart");
//
//        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<AmbilData, AmbilDataHolder>
//                (options) {
//
//            @Override
//            public AmbilDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.list_card_view, parent, false);
//                return new AmbilDataHolder(view);
//
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull AmbilDataHolder holder, int position, @NonNull AmbilData model) {
//
//                holder.setNama(model.getNama());
//                holder.setAlamat(model.getAlamat());
//                holder.setNohp(model.getNohp());
//                holder.setDaya(model.getDaya());
//                holder.setHarga(model.getHarga());
//                holder.setLat(model.getLat());
//                holder.setLon(model.getLon());
//
//            }
//        };
//        recyclerView.setAdapter(adapter);
//        FirebaseRecyclerAdapter<AmbilData, AmbilDataHolder> firebaseRecyclerAdapter =
//                new FirebaseRecyclerAdapter<AmbilData, AmbilDataHolder>(AmbilData.class,R.layout.list_card_view,
//                        AmbilDataHolder.class,mDatabase) {
//
//                    @Override
//                    protected void onBindViewHolder(@NonNull AmbilDataHolder holder, int position, @NonNull AmbilData model) {
//
//                    }
//
//                    @Override
//                    public AmbilDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                        return null;
//                    }
//                };
//   }
//
//    public static class AmbilDataHolder extends RecyclerView.ViewHolder {
//        public AmbilDataHolder(View itemView) {
//            super(itemView);
//
//        }
//
//
//        public void setPesan(String pesan) {
//            TextView pesanT = (TextView) itemView.findViewById(R.id.pesanCard);
//            pesanT.setText(pesan);
//        }
//
//        public void setNama(String nama) {
//            TextView namaT = (TextView) itemView.findViewById(R.id.namaCard);
//            namaT.setText(nama);
//        }
//
//        public void setAlamat(String alamat) {
//
//            TextView alamatT = (TextView) itemView.findViewById(R.id.alamatCard);
//            alamatT.setText(alamat);
//        }
//
//        public void setNohp(String nohp) {
//            TextView nohpT = (TextView) itemView.findViewById(R.id.nohpCard);
//            nohpT.setText(nohp);
//        }
//
//        public void setDaya(String daya) {
//            TextView dayaT = (TextView) itemView.findViewById(R.id.dayaCard);
//            dayaT.setText(daya);
//        }
//
//        public void setHarga(String harga) {
//            TextView hargaT = (TextView) itemView.findViewById(R.id.hargaCard);
//            hargaT.setText(harga);
//        }
//
//        public void setLat(double lat) {
//            System.out.println("lat " + lat);
//        }
//
//        public void setLon(double lon) {
//            System.out.println("lon " + lon);
//        }
//
//    }
}