package com.isolution.schoolmanagementsystem.model;

import org.json.JSONArray;

public class Jadwal {
    private String hari;
    private String namaPelajaran;
    private String jamMulai;
    private String jamSelesai;

    public Jadwal(String hari, String namaPelajaran, String jamMulai, String jamSelesai) {
        this.hari = hari;
        this.namaPelajaran = namaPelajaran;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
    }

    public String getHari() {
        return hari;
    }

    public String getNamaPelajaran() {
        return namaPelajaran;
    }

    public String getJamMulai() {
        return jamMulai;
    }

    public String getJamSelesai() {
        return jamSelesai;
    }
}
