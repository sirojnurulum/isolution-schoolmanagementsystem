package com.isolution.schoolmanagementsystem.utilities;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.isolution.schoolmanagementsystem.R;
import com.isolution.schoolmanagementsystem.model.Jadwal;

import java.util.List;

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.JadwalViewHolder> {
    private List<Jadwal> jadwals;

    public JadwalAdapter(List<Jadwal> jadwals) {
        this.jadwals = jadwals;
    }

    @NonNull
    @Override
    public JadwalViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.jadwal_item,
                viewGroup,
                false);

        return new JadwalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JadwalViewHolder jadwalViewHolder, int i) {
        Jadwal jadwal = jadwals.get(i);
        jadwalViewHolder.hari.setText(getHari(jadwal.getHari()));
        jadwalViewHolder.namaPelajaran.setText(jadwal.getNamaPelajaran());
        jadwalViewHolder.jamMulai.setText(jadwal.getJamMulai());
        jadwalViewHolder.jamSelesai.setText(jadwal.getJamSelesai());
    }

    @Override
    public int getItemCount() {
        return jadwals.size();
    }

    private String getHari(String numHari) {
        String returnValue = null;
        if (numHari == null || numHari.length() <= 0) {
            returnValue = "Tidak Ada Jadwal";
        } else {
            if (numHari.equals("0")) {
                returnValue = "Senin";
            } else if (numHari.equals("1")) {
                returnValue = "Selasa";
            } else if (numHari.equals("2")) {
                returnValue = "Rabu";
            } else if (numHari.equals("3")) {
                returnValue = "Kamis";
            } else if (numHari.equals("4")) {
                returnValue = "Jumat";
            } else if (numHari.equals("5")) {
                returnValue = "Sabtu";
            } else if (numHari.equals("6")) {
                returnValue = "Ahad";
            }
        }
        return returnValue;
    }

    class JadwalViewHolder extends RecyclerView.ViewHolder {
        TextView hari, namaPelajaran, jamMulai, jamSelesai;

        public JadwalViewHolder(@NonNull View itemView) {
            super(itemView);
            hari = itemView.findViewById(R.id.tv_jadwal_item_hari);
            namaPelajaran = itemView.findViewById(R.id.tv_jadwal_item_nama_pelajaran);
            jamMulai = itemView.findViewById(R.id.tv_jadwal_item_jam_awal);
            jamSelesai = itemView.findViewById(R.id.tv_jadwal_item_jam_selesai);
        }
    }
}
