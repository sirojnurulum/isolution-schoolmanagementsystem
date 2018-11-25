package com.isolution.schoolmanagementsystem.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Profil {


    private String idPelajar;
    private String idKelas;
    private String nis;
    private String nisn;
    private String namaLengkap;
    private String tempatLahir;
    private String tanggalLahir;
    private String jenisKelamin;
    private String alamat;
    private String namaAyah;
    private String namaIbu;
    private String kontak;
    private String statusPelajar;
    private String penjelasan;
    private String idPaket;
    private String idAsrama;
    private String anakKe;
    private String agama;
    private String statusKeluarga;
    private String telepon;
    private String tanggalPenerimaanKelas;
    private String sekolahAsal;
    private String kelasDiterima;
    private String tanggalDiterimaKelas;
    private String terakhirDiubah;
    private String urlPhoto;

    public Profil(JSONObject jsonObject) {
        try {
            JSONObject profil = jsonObject.getJSONObject("profil");
            this.idPelajar = profil.getString("id_pelajar");
            this.idKelas = profil.getString("id_kelas");
            this.nis = profil.getString("NIS");
            this.nisn = profil.getString("nisn");
            this.namaLengkap = profil.getString("nama_lengkap");
            this.tempatLahir = profil.getString("tempat_lahir");
            this.tanggalLahir = profil.getString("tanggal_lahir");
            this.jenisKelamin = profil.getString("jenis_kelamin");
            this.alamat = profil.getString("alamat");
            this.namaAyah = profil.getString("nama_ayah");
            this.namaIbu = profil.getString("nama_ibu");
            this.kontak = profil.getString("kontak");
            this.statusPelajar = profil.getString("status_pelajar");
            this.penjelasan = profil.getString("penjelasan");
            this.idPaket = profil.getString("id_paket");
            this.idAsrama = profil.getString("id_asrama");
            this.anakKe = profil.getString("anak_ke");
            this.agama = profil.getString("agama");
            this.statusKeluarga = profil.getString("status_keluarga");
            this.telepon = profil.getString("telepon");
            this.tanggalPenerimaanKelas = profil.getString("tgl_penerimaan_kelas");
            this.sekolahAsal = profil.getString("sekolah_asal");
            this.kelasDiterima = profil.getString("kelas_diterima");
            this.tanggalDiterimaKelas = profil.getString("tgl_diterima_kelas");
            this.terakhirDiubah = profil.getString("terakhir_ubah");

            JSONObject gambar = jsonObject.getJSONObject("gambar");
            this.urlPhoto = gambar.getString("photo");
        } catch (JSONException e) {
            Log.e("-->JSONException", e.toString());
        }
    }

    public String getIdPelajar() {
        return idPelajar;
    }

    public String getIdKelas() {
        return idKelas;
    }

    public String getNis() {
        return nis;
    }

    public String getNisn() {
        return nisn;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNamaAyah() {
        return namaAyah;
    }

    public String getNamaIbu() {
        return namaIbu;
    }

    public String getKontak() {
        return kontak;
    }

    public String getStatusPelajar() {
        return statusPelajar;
    }

    public String getPenjelasan() {
        return penjelasan;
    }

    public String getIdPaket() {
        return idPaket;
    }

    public String getIdAsrama() {
        return idAsrama;
    }

    public String getAnakKe() {
        return anakKe;
    }

    public String getAgama() {
        return agama;
    }

    public String getStatusKeluarga() {
        return statusKeluarga;
    }

    public String getTelepon() {
        return telepon;
    }

    public String getTanggalPenerimaanKelas() {
        return tanggalPenerimaanKelas;
    }

    public String getSekolahAsal() {
        return sekolahAsal;
    }

    public String getKelasDiterima() {
        return kelasDiterima;
    }

    public String getTanggalDiterimaKelas() {
        return tanggalDiterimaKelas;
    }

    public String getTerakhirDiubah() {
        return terakhirDiubah;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }
}