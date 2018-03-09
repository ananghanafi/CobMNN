package com.masbi.cobmnn;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by AnangHanafi on 06/03/2018.
 */

public class Arraylist extends ArrayAdapter<AmbilData> {
    private Activity context;
    private List<AmbilData> ambilDataList;

    public Arraylist(@NonNull Activity context, List<AmbilData> ambilDataList) {
        super(context, R.layout.item_list, ambilDataList);
        this.context = context;
        this.ambilDataList = ambilDataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View itemView = inflater.inflate(R.layout.item_list, null, true);
        TextView pesanT = (TextView) itemView.findViewById(R.id.pesanList);
        TextView namaT = (TextView) itemView.findViewById(R.id.namaList);
        TextView alamatT = (TextView) itemView.findViewById(R.id.alamatList);
        TextView nohpT = (TextView) itemView.findViewById(R.id.nohpList);
        TextView dayaT = (TextView) itemView.findViewById(R.id.dayaList);
        TextView hargaT = (TextView) itemView.findViewById(R.id.hargalist);
        AmbilData ambilData = ambilDataList.get(position);
        pesanT.setText(ambilData.getPesan());
        namaT.setText(ambilData.getNama());
        alamatT.setText(ambilData.getAlamat());
        nohpT.setText(ambilData.getNohp());
        dayaT.setText(ambilData.getDaya());
        hargaT.setText(ambilData.getHarga());
        System.out.println("lat " + ambilData.getLat());
        System.out.println("lon " + ambilData.getLon());

        return itemView;
    }
}
