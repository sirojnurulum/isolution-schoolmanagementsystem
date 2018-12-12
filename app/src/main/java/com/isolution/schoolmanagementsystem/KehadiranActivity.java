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
import android.view.View;
import android.widget.TextView;

import com.isolution.schoolmanagementsystem.model.Hadir;
import com.isolution.schoolmanagementsystem.model.Jadwal;
import com.isolution.schoolmanagementsystem.model.Kehadiran;
import com.isolution.schoolmanagementsystem.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class KehadiranActivity extends AppCompatActivity {
    TextView tvKehadiranInfo;
    android.app.AlertDialog loading;
    Kehadiran kehadiran;
    RecyclerView recyclerView;
    HadirAdapter hadirAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kehadiran);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvKehadiranInfo = findViewById(R.id.tv_kehadiran_info);
        recyclerView = findViewById(R.id.rv_kehadiran);

        loading = new SpotsDialog.Builder().setContext(KehadiranActivity.this).setMessage
                ("Loading").setCancelable(false).build();

        new FetchKehadiranData().execute(getApplicationContext()
                .getSharedPreferences("isolution", 0)
                .getString("nis", null));
    }

    private void initRecycleView() {
        hadirAdapter = new HadirAdapter(kehadiran.getKehadiran());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(hadirAdapter);

        hadirAdapter.notifyDataSetChanged();
    }

    private void setVisibility(boolean isJadwal) {
        if (isJadwal) {
            tvKehadiranInfo.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            tvKehadiranInfo.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    public class FetchKehadiranData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            return NetworkUtils.getKehadiranData(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            loading.dismiss();
            if (s != null) {
                try {
                    List<Hadir> hadirs = new ArrayList<>();
                    JSONObject obj = new JSONObject(s);
                    if (!obj.getString("status_jadwal").equals("0")) {
                        JSONArray objArray = obj.getJSONArray("kehadiran");
                        for (int i = 0; i <= objArray.length(); i++) {
                            JSONObject x = objArray.getJSONObject(i);
                            hadirs.add(
                                    new Hadir(
                                            new Jadwal(
                                                    x.getString("hari"),
                                                    x.getString("nama_pelajaran"),
                                                    x.getString("jam_awal"),
                                                    x.getString("jam_selesai")
                                            ),
                                            x.getString("kehadiran")
                                    )
                            );
                        }
                        kehadiran = new Kehadiran(
                                obj.getString("status_jadwal"),
                                obj.getString("hadir"),
                                hadirs
                        );
                        setVisibility(true);
                        initRecycleView();
                    } else {
                        setVisibility(false);
                    }
                } catch (JSONException e) {
                    Log.e("--> JSONException : ", "KehadiranActivity.FetchKehadiranData" +
                            ".OnPostExecute >> " + e.toString());
                }
            }
        }
    }

}
