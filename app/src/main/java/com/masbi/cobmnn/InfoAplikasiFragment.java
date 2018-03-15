package com.masbi.cobmnn;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class InfoAplikasiFragment extends Fragment {
    TextView telp1, telp2, telp3, telp4, mail;

    public InfoAplikasiFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_info_aplikasi, container, false);
        telp1 = (TextView) view.findViewById(R.id.telpGerai);
        telp2 = (TextView) view.findViewById(R.id.telpHery);
        telp3 = (TextView) view.findViewById(R.id.telpHery2);
        telp4 = (TextView) view.findViewById(R.id.telpWA);
        mail = (TextView) view.findViewById(R.id.emailGerai);

        telp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0511 6723 591")));
            }
        });
        telp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0812 5191 7096")));
            }
        });
        telp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0878 1712 6470")));
            }
        });
        telp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0811 5002 269")));
            }
        });
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0811 5002 269")));
            }
        });
        return view;
    }


}
