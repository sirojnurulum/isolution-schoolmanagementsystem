package com.isolution.schoolmanagementsystem;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.isolution.schoolmanagementsystem.model.Profil;
import com.isolution.schoolmanagementsystem.utilities.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class ProfilActivity extends AppCompatActivity {
    TextView namaLengkap, ttl, jenisKelamin, nis, nisn, statusPelajar, sekolahAsal, namaAyah, namaIbu,
            statusKeluarga, anakKe, alamat, agama;
    ImageView fotoProfil;
    android.app.AlertDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        namaLengkap = findViewById(R.id.tv_profil_nama_lengkap);
        ttl = findViewById(R.id.tv_profil_tempat_tgl_lahir);
        jenisKelamin = findViewById(R.id.tv_profil_jenis_kelamin);
        nis = findViewById(R.id.tv_profil_nis);
        nisn = findViewById(R.id.tv_profil_nisn);
        statusPelajar = findViewById(R.id.tv_profil_status_pelajar);
        sekolahAsal = findViewById(R.id.tv_profil_sekolah_asal);
        namaAyah = findViewById(R.id.tv_profil_nama_ayah);
        namaIbu = findViewById(R.id.tv_profil_nama_ibu);
        statusKeluarga = findViewById(R.id.tv_profil_status_keluarga);
        anakKe = findViewById(R.id.tv_profil_anak_ke);
        alamat = findViewById(R.id.tv_profil_alamat);
        fotoProfil = findViewById(R.id.iv_profil_photo);
        agama = findViewById(R.id.tv_agama);

        loading = new SpotsDialog.Builder().setContext(ProfilActivity.this).setMessage("Loading")
                .setCancelable(false).build();

        new ProfilActivity.FetchProfileData().execute(getApplicationContext()
                .getSharedPreferences("isolution", 0)
                .getString("nis", null));
    }

    @SuppressLint("SetTextI18n")
    private void setView(Profil profil) {
        namaLengkap.setText(profil.getNamaLengkap());
        ttl.setText(String.format("%s, %s", profil.getTempatLahir(), profil.getTanggalLahir()));
        if (profil.getJenisKelamin().equals("1")) {
            jenisKelamin.setText("Laki - Laki");
        } else if (profil.getJenisKelamin().equals("2")) {
            jenisKelamin.setText("Perempuan");
        }
        nis.setText(profil.getNis());
        nisn.setText(profil.getNisn());
        statusPelajar.setText(profil.getStatusPelajar());
        sekolahAsal.setText(profil.getSekolahAsal());
        agama.setText(profil.getAgama());
        namaAyah.setText(profil.getNamaAyah());
        namaIbu.setText(profil.getNamaIbu());
        statusKeluarga.setText(profil.getStatusKeluarga());
        anakKe.setText(profil.getAnakKe());
        alamat.setText(profil.getAlamat());
        new ProfilActivity.DownloadImage(fotoProfil).execute(String.format("https:%s", profil
                .getUrlPhoto()));
    }

    @SuppressLint("StaticFieldLeak")
    public class FetchProfileData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            loading.show();
        }

        @Override
        protected String doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            return NetworkUtils.getProfileData(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            loading.dismiss();
            if (s.length() == 0) {
                Toast.makeText(ProfilActivity.this, "Data Null", Toast.LENGTH_LONG).show();
            } else {
                try {
                    setView(new Profil(new JSONObject(s)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView iv;

        DownloadImage(ImageView iv) {
            this.iv = iv;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            return NetworkUtils.getImage(strings[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                iv.setImageBitmap(bitmap);
            } else {
                Toast.makeText(ProfilActivity.this, "bitmap null", Toast.LENGTH_LONG).show();
            }
        }
    }

}
