package com.masbi.cobmnn;

import java.util.Map;

/**
 * Created by AnangHanafi on 03/03/2018.
 */

public class AmbilData {
    String pesan, nama, alamat, nohp, daya, harga, id, lat, lon, time, noplg, kelurahan, noBangunan, fitting, mcb, sContact, dayaBaru;
    String str_Wilayah, str_Cabang, str_Rayon, str_Pemda, str_Gerai,
            str_bpPLN, str_Instalasi, str_Slo, str_gInstalasi, str_Materai,
            str_adminDaya, str_tokenDaya, str_materaiDaya, str_daya, str_dayaDaya,
            str_eLampOut, str_eLampIn, str_elContactOut, str_elContactIn, str_voucher;
    String promosi1, promosi2, promosi3, promosi4, promosi5;


//   Map time;


    //  double lat, lon;


    public AmbilData() {

    }

//    public AmbilData(String id, String kritik) {
//        this.id = id;
//        this.kritik = kritik;
//    }
//
//    public AmbilData(String id, String pesan, String nama, String alamat, String nohp, String daya, String harga,
//                     String lat, String lon, String time) {
//        this.pesan = pesan;
//        this.id = id;
//        this.nama = nama;
//        this.alamat = alamat;
//        this.nohp = nohp;
//        this.daya = daya;
//        this.harga = harga;
////        this.lat = lat;
////        this.lon = lon;
//        this.lat = lat;
//        this.lon = lon;
//        this.time = time;
//    }

    public AmbilData(String id, String str_Wilayah) {
        this.id = id;
        this.str_Wilayah = str_Wilayah;
    }

    public AmbilData(String id, String str_Wilayah, String str_Cabang) {
        this.id = id;
        this.str_Wilayah = str_Wilayah;
        this.str_Cabang = str_Cabang;
    }

    public AmbilData(String id, String str_Wilayah, String str_Cabang, String str_Rayon) {
        this.id = id;
        this.str_Wilayah = str_Wilayah;
        this.str_Cabang = str_Cabang;
        this.str_Rayon = str_Rayon;
    }


    public AmbilData(String id, String str_Wilayah, String str_Cabang, String str_Rayon, String str_Gerai) {
        this.id = id;
        this.str_Wilayah = str_Wilayah;
        this.str_Cabang = str_Cabang;
        this.str_Rayon = str_Rayon;
        this.str_Gerai = str_Gerai;
    }

    public AmbilData(String pesan, String str_Wilayah, String str_Cabang, String str_Rayon, String str_Gerai,
                     String str_adminDaya, String str_tokenDaya, String str_materaiDaya,
                     String str_dayaDaya) {
        this.pesan = pesan;
        this.str_Wilayah = str_Wilayah;
        this.str_Cabang = str_Cabang;
        this.str_Rayon = str_Rayon;
        this.str_Gerai = str_Gerai;
        this.str_adminDaya = str_adminDaya;
        this.str_tokenDaya = str_tokenDaya;
        this.str_materaiDaya = str_materaiDaya;
        this.str_dayaDaya = str_dayaDaya;
    }

    public AmbilData(String id, String str_Wilayah, String str_Cabang, String str_Rayon,
                     String str_Gerai, String str_bpPLN, String str_Instalasi, String str_Slo, String str_gInstalasi, String str_Materai,
                     String str_daya, String str_eLampOut, String str_eLampIn, String str_elContactOut, String str_elContactIn) {
        this.id = id;
        this.str_Wilayah = str_Wilayah;
        this.str_Cabang = str_Cabang;
        this.str_Rayon = str_Rayon;
        this.str_Gerai = str_Gerai;
        this.str_bpPLN = str_bpPLN;
        this.str_Instalasi = str_Instalasi;
        this.str_Slo = str_Slo;
        this.str_gInstalasi = str_gInstalasi;
        this.str_Materai = str_Materai;
        this.str_daya = str_daya;
        this.str_eLampOut = str_eLampOut;
        this.str_eLampIn = str_eLampIn;
        this.str_elContactOut = str_elContactOut;
        this.str_elContactIn = str_elContactIn;
    }

    public AmbilData(String id, String pesan, String nama, String alamat, String daya, String dayaBaru,
                     String harga, String lat, String lon, String noplg,
                     String str_Wilayah, String str_Cabang, String str_Rayon, String str_Gerai) {
        this.pesan = pesan;
        this.nama = nama;
        this.alamat = alamat;
        this.daya = daya;
        this.harga = harga;
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.noplg = noplg;
        this.str_Wilayah = str_Wilayah;
        this.str_Cabang = str_Cabang;
        this.str_Rayon = str_Rayon;
        this.str_Gerai = str_Gerai;
        this.dayaBaru = dayaBaru;
    }

    public AmbilData(String id, String pesan, String nama, String alamat, String nohp, String daya, String harga,
                     String lat, String lon, String noplg, String kelurahan, String noBangunan, String fitting, String voucher,
                     String mcb, String sContact, String str_Wilayah, String str_Cabang, String str_Rayon, String str_Gerai) {
        this.pesan = pesan;
        this.nama = nama;
        this.alamat = alamat;
        this.nohp = nohp;
        this.daya = daya;
        this.harga = harga;
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.noplg = noplg;
        this.kelurahan = kelurahan;
        this.noBangunan = noBangunan;
        this.fitting = fitting;
        this.mcb = mcb;
        this.sContact = sContact;
        this.str_Wilayah = str_Wilayah;
        this.str_Cabang = str_Cabang;
        this.str_Rayon = str_Rayon;
        this.str_voucher = voucher;
        this.str_Gerai = str_Gerai;
    }
//
//        public AmbilData(String id, String pesan, String nama, String alamat, String nohp, String daya, String harga,
//                     String lat, String lon, String str_Wilayah, String str_Cabang, String str_Rayon,
//                         String str_Gerai) {
//        this.pesan = pesan;
//        this.id = id;
//        this.nama = nama;
//        this.alamat = alamat;
//        this.nohp = nohp;
//        this.daya = daya;
//        this.harga = harga;
////        this.lat = lat;
////        this.lon = lon;
//        this.lat = lat;
//        this.lon = lon;
//    }

    //    public AmbilData(String id, String pesan, String nama, String alamat, String nohp, String daya, String harga,
//                     String lat, String lon, String noplg) {
//        this.pesan = pesan;
//        this.id = id;
//        this.nama = nama;
//        this.alamat = alamat;
//        this.nohp = nohp;
//        this.daya = daya;
//        this.harga = harga;
////        this.lat = lat;
////        this.lon = lon;
//        this.lat = lat;
//        this.lon = lon;
//        this.noplg = noplg;
//    }
    public String getStr_adminDaya() {
        return str_adminDaya;
    }

    public void setStr_adminDaya(String str_adminDaya) {
        this.str_adminDaya = str_adminDaya;
    }

    public String getStr_tokenDaya() {
        return str_tokenDaya;
    }

    public void setStr_tokenDaya(String str_tokenDaya) {
        this.str_tokenDaya = str_tokenDaya;
    }

    public String getStr_materaiDaya() {
        return str_materaiDaya;
    }

    public void setStr_materaiDaya(String str_materaiDaya) {
        this.str_materaiDaya = str_materaiDaya;
    }

    public String getStr_Wilayah() {
        return str_Wilayah;
    }

    public void setStr_Wilayah(String str_Wilayah) {
        this.str_Wilayah = str_Wilayah;
    }

    public String getStr_Cabang() {
        return str_Cabang;
    }

    public void setStr_Cabang(String str_Cabang) {
        this.str_Cabang = str_Cabang;
    }

    public String getStr_Rayon() {
        return str_Rayon;
    }

    public void setStr_Rayon(String str_Rayon) {
        this.str_Rayon = str_Rayon;
    }

    public String getStr_Pemda() {
        return str_Pemda;
    }

    public void setStr_Pemda(String str_Pemda) {
        this.str_Pemda = str_Pemda;
    }

    public String getStr_Gerai() {
        return str_Gerai;
    }

    public void setStr_Gerai(String str_Gerai) {
        this.str_Gerai = str_Gerai;
    }

    public String getStr_bpPLN() {
        return str_bpPLN;
    }

    public void setStr_bpPLN(String str_bpPLN) {
        this.str_bpPLN = str_bpPLN;
    }

    public String getStr_Instalasi() {
        return str_Instalasi;
    }

    public void setStr_Instalasi(String str_Instalasi) {
        this.str_Instalasi = str_Instalasi;
    }

    public String getStr_Slo() {
        return str_Slo;
    }

    public void setStr_Slo(String str_Slo) {
        this.str_Slo = str_Slo;
    }

    public String getStr_gInstalasi() {
        return str_gInstalasi;
    }

    public void setStr_gInstalasi(String str_gInstalasi) {
        this.str_gInstalasi = str_gInstalasi;
    }

    public String getStr_Materai() {
        return str_Materai;
    }

    public void setStr_Materai(String str_Materai) {
        this.str_Materai = str_Materai;
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

    public String getStr_daya() {
        return str_daya;
    }

    public void setStr_daya(String str_daya) {
        this.str_daya = str_daya;
    }

    public String getStr_dayaDaya() {
        return str_dayaDaya;
    }

    public void setStr_dayaDaya(String str_dayaDaya) {
        this.str_dayaDaya = str_dayaDaya;
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

    public String getStr_voucher() {
        return str_voucher;
    }

    public void setStr_voucher(String str_voucher) {
        this.str_voucher = str_voucher;
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

//    public String getKritik() {
//        return kritik;
//    }
//
//    public void setKritik(String kritik) {
//        this.kritik = kritik;
//    }

    public String getNoplg() {
        return noplg;
    }

    public void setNoplg(String noplg) {
        this.noplg = noplg;
    }

    public String getStr_eLampOut() {
        return str_eLampOut;
    }

    public void setStr_eLampOut(String str_eLampOut) {
        this.str_eLampOut = str_eLampOut;
    }

    public String getStr_eLampIn() {
        return str_eLampIn;
    }

    public void setStr_eLampIn(String str_eLampIn) {
        this.str_eLampIn = str_eLampIn;
    }

    public String getStr_elContactOut() {
        return str_elContactOut;
    }

    public void setStr_elContactOut(String str_elContactOut) {
        this.str_elContactOut = str_elContactOut;
    }

    public String getStr_elContactIn() {
        return str_elContactIn;
    }

    public void setStr_elContactIn(String str_elContactIn) {
        this.str_elContactIn = str_elContactIn;
    }

    public String getDayaBaru() {
        return dayaBaru;
    }

    public void setDayaBaru(String dayaBaru) {
        this.dayaBaru = dayaBaru;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public String getNoBangunan() {
        return noBangunan;
    }

    public void setNoBangunan(String noBangunan) {
        this.noBangunan = noBangunan;
    }

    public String getFitting() {
        return fitting;
    }

    public void setFitting(String fitting) {
        this.fitting = fitting;
    }

    public String getMcb() {
        return mcb;
    }

    public void setMcb(String mcb) {
        this.mcb = mcb;
    }

    public String getsContact() {
        return sContact;
    }

    public void setsContact(String sContact) {
        this.sContact = sContact;
    }

    public String getPromosi1() {
        return promosi1;
    }

    public void setPromosi1(String promosi1) {
        this.promosi1 = promosi1;
    }

    public String getPromosi2() {
        return promosi2;
    }

    public void setPromosi2(String promosi2) {
        this.promosi2 = promosi2;
    }

    public String getPromosi3() {
        return promosi3;
    }

    public void setPromosi3(String promosi3) {
        this.promosi3 = promosi3;
    }

    public String getPromosi4() {
        return promosi4;
    }

    public void setPromosi4(String promosi4) {
        this.promosi4 = promosi4;
    }

    public String getPromosi5() {
        return promosi5;
    }

    public void setPromosi5(String promosi5) {
        this.promosi5 = promosi5;
    }
}