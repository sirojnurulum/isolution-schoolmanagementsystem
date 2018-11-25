package com.isolution.schoolmanagementsystem;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.isolution.schoolmanagementsystem.utilities.NetworkUtils;

import dmax.dialog.SpotsDialog;

public class KehadiranActivity extends AppCompatActivity {
    TextView tvKehadiran;
    android.app.AlertDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kehadiran);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvKehadiran = findViewById(R.id.tv_kehadiran);
        loading = new SpotsDialog.Builder().setContext(KehadiranActivity.this).setMessage
                ("Loading").setCancelable(false).build();
        new FetchKehadiranData().execute(getApplicationContext()
                .getSharedPreferences("isolution", 0)
                .getString("nis", null));
    }

    public class FetchKehadiranData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            return NetworkUtils.getDataKehadiran(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
            tvKehadiran.setText(s);
        }
    }

}
