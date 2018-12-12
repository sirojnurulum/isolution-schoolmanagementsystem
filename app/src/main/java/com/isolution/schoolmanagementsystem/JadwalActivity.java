package com.isolution.schoolmanagementsystem;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.isolution.schoolmanagementsystem.model.Jadwal;
import com.isolution.schoolmanagementsystem.utilities.JadwalAdapter;
import com.isolution.schoolmanagementsystem.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class JadwalActivity extends AppCompatActivity {
    android.app.AlertDialog loading;
    List<Jadwal> jadwals;
    RecyclerView recyclerView;
    JadwalAdapter jadwalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loading = new SpotsDialog.Builder().setContext(JadwalActivity.this).setMessage("Loading")
                .setCancelable(false).build();

        jadwals = new ArrayList<>();
        new FetchJadwalData().execute(getApplicationContext().getSharedPreferences("isolution", 0)
                .getString("nis", null));
    }

    private void initRecycleView() {
        recyclerView = findViewById(R.id.rv_jadwal);
        jadwalAdapter = new JadwalAdapter(jadwals);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(jadwalAdapter);

        jadwalAdapter.notifyDataSetChanged();
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
            if (s != null) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("jadwal");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject x = jsonArray.getJSONObject(i);
                        jadwals.add(new Jadwal(
                                x.getString("hari"),
                                x.getString("nama_pelajaran"),
                                x.getString("jam_awal"),
                                x.getString("jam_selesai")
                        ));
                        Log.e("size : ", "jadwal size : " + String.valueOf(jadwals.size()));
                    }
                    initRecycleView();
                } catch (JSONException e) {
                    Log.e("--> JSONException : ", "JadwalActivity.FetchJadwal.onPostExecute >> " + e
                            .toString
                                    ());
                }
            }
            loading.dismiss();
        }
    }

}
