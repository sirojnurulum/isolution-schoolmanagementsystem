package com.isolution.schoolmanagementsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {
    LinearLayout btnProfile, btnKehadiran, btnJadwal, btnIzin, btnPembayaran;
    android.app.AlertDialog loading;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getApplicationContext().getSharedPreferences("isolution", 0);
        if (!pref.contains("nis")) {
            pref.edit().putString("nis", "999002").apply();
        } else {
            pref.edit().putString("nis", "999002").apply();
        }

        btnJadwal = findViewById(R.id.btn_jadwal);
        btnProfile = findViewById(R.id.btn_profile);
        btnKehadiran = findViewById(R.id.btn_kehadiran);
        btnIzin = findViewById(R.id.btn_medis);
        btnPembayaran = findViewById(R.id.btn_pembayaran);

        loading = new SpotsDialog.Builder().setContext(MainActivity.this).setMessage("Loading")
                .setCancelable(false).build();

        btnJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JadwalActivity.class);
                startActivity(intent);
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfilActivity.class);
                intent.putExtra("id_siswa", pref.getString("nis", null));
                startActivity(intent);
            }
        });
        btnKehadiran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KehadiranActivity.class);
                startActivity(intent);
            }
        });
        btnIzin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IzinActivity.class);
                startActivity(intent);
            }
        });
        btnPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PembayaranActivity.class);
                startActivity(intent);
            }
        });
    }
}
