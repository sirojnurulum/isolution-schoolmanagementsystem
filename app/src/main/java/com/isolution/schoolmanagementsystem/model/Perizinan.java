package com.isolution.schoolmanagementsystem.model;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;

public class Perizinan {
    private Profil profil;
    private Bitmap buktiIzin;
    private String jenisIzin;

    public Profil getProfil() {
        return profil;
    }

    public Bitmap getBuktiIzin() {
        return buktiIzin;
    }

    public String getJenisIzin() {
        return jenisIzin;
    }

    public Perizinan(Profil profil, Bitmap buktiIzin, String jenisIzin) {
        this.profil = profil;
        this.buktiIzin = buktiIzin;
        this.jenisIzin = jenisIzin;
    }


}
