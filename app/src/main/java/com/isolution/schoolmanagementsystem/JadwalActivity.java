package com.isolution.schoolmanagementsystem;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.isolution.schoolmanagementsystem.model.Jadwal;
import com.isolution.schoolmanagementsystem.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class JadwalActivity extends AppCompatActivity {
    TextView text;
    android.app.AlertDialog loading;
    ArrayList<Jadwal> jadwals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        text = findViewById(R.id.textView);
        loading = new SpotsDialog.Builder().setContext(JadwalActivity.this).setMessage("Loading")
                .setCancelable(false).build();
        jadwals = new ArrayList<>();

        new FetchJadwalData().execute(getApplicationContext().getSharedPreferences("isolution", 0)
                .getString("nis", null));
    }

    public class FetchJadwalData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            return NetworkUtils.getJadwalData(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
            if (s != null) {
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject x = jsonArray.getJSONObject(i);
                        jadwals.add(new Jadwal(
                                x.getString("hari"),
                                x.getString("nama_pelajaran"),
                                x.getString("jam_awal"),
                                x.getString("jam_selesai")
                        ));
                    }
                } catch (JSONException e) {
                    Log.e("--> JSONArray jadwal : ", e.toString());
                }
            }
        }
    }

}
