package com.isolution.schoolmanagementsystem;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.isolution.schoolmanagementsystem.model.Perizinan;
import com.isolution.schoolmanagementsystem.model.Profil;
import com.isolution.schoolmanagementsystem.utilities.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class IzinActivity extends AppCompatActivity {
    RadioButton rbIzin, rbSakit;
    Button btnPilihFile;
    ImageView ivProfile;
    TextView tvNamaLengkap, tvTempatTanggalLahir, tvJenisKelamin, tvNis, tvNisn, tvStatusPelajar;
    android.app.AlertDialog loading;
    Profil profil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        rbIzin = findViewById(R.id.rb_izin_izin);
        rbSakit = findViewById(R.id.rb_izin_sakit);
        btnPilihFile = findViewById(R.id.btn_izin_pilih_file);
        ivProfile = findViewById(R.id.iv_izin_photo);
        tvNamaLengkap = findViewById(R.id.tv_izin_nama_lengkap);
        tvTempatTanggalLahir = findViewById(R.id.tv_izin_tempat_tgl_lahir);
        tvJenisKelamin = findViewById(R.id.tv_izin_jenis_kelamin);
        tvNis = findViewById(R.id.tv_izin_nis);
        tvNisn = findViewById(R.id.tv_izin_nisn);
        tvStatusPelajar = findViewById(R.id.tv_izin_status_pelajar);
        loading = new SpotsDialog.Builder().setContext(IzinActivity.this).setMessage("Loading")
                .setCancelable(false).build();
        initLayoutState();
        btnPilihFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihFileAction();
            }
        });

        rbIzin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLayoutState();
            }
        });
        rbSakit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLayoutState();
            }
        });

        new IzinActivity.FetchProfileData().execute(getApplicationContext()
                .getSharedPreferences("isolution", 0)
                .getString("nis", null));
    }

    private void initLayoutState() {
        if (rbSakit.isChecked() || rbIzin.isChecked()) {
            btnPilihFile.setEnabled(true);
        } else {
            btnPilihFile.setEnabled(false);
        }
    }

    private void pilihFileAction() {
        if (rbSakit.isChecked() || rbIzin.isChecked()) {
            AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
            String[] items = {"Pilih Gambar Dari Galeri", "Ambil Gambar Dengan Kamera"};
            pictureDialog.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            actionGallery();
                            break;
                        case 1:
                            actionCamera();
                            break;
                    }
                }
            });
            pictureDialog.show();
        } else {
            Toast.makeText(IzinActivity.this, "Jenis Izi Belum Dipilih", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("IntentReset")
    private void actionGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    private void actionCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            if (requestCode == 0 && resultCode == RESULT_OK) {
                try {
                    kirimPerizinan(MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData()));
                } catch (IOException e) {
                    Log.e("-->", e.toString());
                    Toast.makeText(IzinActivity.this, "gambar kosong", Toast.LENGTH_LONG).show();
                }
            } else if (requestCode == 1 && resultCode == RESULT_OK) {
                kirimPerizinan((Bitmap) data.getExtras().get("data"));
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(IzinActivity.this, "Gambar Gagal Dipilih", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(IzinActivity.this, "data null", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setView() {
        tvNamaLengkap.setText(profil.getNamaLengkap());
        tvTempatTanggalLahir.setText(String.format("%s, %s", profil.getTempatLahir(), profil.getTanggalLahir()));
        if (profil.getJenisKelamin().equals("1")) {
            tvJenisKelamin.setText("Laki - Laki");
        } else if (profil.getJenisKelamin().equals("2")) {
            tvJenisKelamin.setText("Perempuan");
        }
        tvNis.setText(profil.getNis());
        tvNisn.setText(profil.getNisn());
        tvStatusPelajar.setText(profil.getStatusPelajar());
        new IzinActivity.DownloadImage(ivProfile).execute(String.format("https:%s", profil
                .getUrlPhoto()));
    }

    private void initView() {
        rbSakit.setChecked(false);
        rbIzin.setChecked(false);
    }

    private void kirimPerizinan(Bitmap bitmap) {
        String jenisIzin = null;
        if (rbIzin.isChecked()) {
            jenisIzin = "3";
        } else if (rbSakit.isChecked()) {
            jenisIzin = "4";
        }
        new IzinActivity.UploadIzin().execute(new Perizinan(profil, bitmap, jenisIzin));
        initView();
    }


    @SuppressLint("StaticFieldLeak")
    public class UploadIzin extends AsyncTask<Perizinan, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setMessage("Mengirim Perizinan, Mohon Tunggu... !");
            loading.show();
        }

        @Override
        protected String doInBackground(Perizinan... perizinans) {
            return NetworkUtils.uploadPerizinan(perizinans[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
            Toast.makeText(IzinActivity.this, "pesan : " + s, Toast.LENGTH_LONG).show();
        }
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
            if (s.length() == 0) {
                Toast.makeText(IzinActivity.this, "Data Null", Toast.LENGTH_LONG).show();
            } else {
                try {
                    profil = new Profil(new JSONObject(s));
                    setView();
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
            loading.dismiss();
            if (bitmap != null) {
                iv.setImageBitmap(bitmap);
            } else {
                Toast.makeText(IzinActivity.this, "bitmap null", Toast.LENGTH_LONG).show();
            }
        }
    }
}
