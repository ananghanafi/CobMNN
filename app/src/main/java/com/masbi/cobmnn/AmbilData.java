package com.masbi.cobmnn;

import java.util.Map;

/**
 * Created by AnangHanafi on 03/03/2018.
 */

public class AmbilData {
    String pesan, nama, alamat, nohp, daya, harga, id, lat, lon, time;

    //   Map time;
    //  double lat, lon;

    public AmbilData() {

    }

    public AmbilData(String nama) {
        this.nama = nama;
    }

    public AmbilData(String id, String pesan, String nama, String alamat, String nohp, String daya, String harga,
                     String lat, String lon, String time) {
        this.pesan = pesan;
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.nohp = nohp;
        this.daya = daya;
        this.harga = harga;
//        this.lat = lat;
//        this.lon = lon;
        this.lat = lat;
        this.lon = lon;
        this.time = time;
    }

    public AmbilData(String id, String pesan, String nama, String alamat, String nohp, String daya, String harga,
                     String lat, String lon) {
        this.pesan = pesan;
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.nohp = nohp;
        this.daya = daya;
        this.harga = harga;
//        this.lat = lat;
//        this.lon = lon;
        this.lat = lat;
        this.lon = lon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getDaya() {
        return daya;
    }

    public void setDaya(String daya) {
        this.daya = daya;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    //    public double getLat() {
//        return lat;
//    }
//
//    public void setLat(double lat) {
//        this.lat = lat;
//    }
//
//    public double getLon() {
//        return lon;
//    }
//
//    public void setLon(double lon) {
//        this.lon = lon;
//    }
    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    //
//    public Map getTime() {
//        return time;
//    }
//
//    public void setTime(Map time) {
//        this.time = time;
//    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}