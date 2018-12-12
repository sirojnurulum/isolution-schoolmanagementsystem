package com.isolution.schoolmanagementsystem;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.isolution.schoolmanagementsystem.model.Hadir;

import java.util.List;

public class HadirAdapter extends RecyclerView.Adapter<HadirAdapter.HadirViewHolder> {
    List<Hadir> hadirs;

    public HadirAdapter(List<Hadir> hadirs) {
        this.hadirs = hadirs;
    }

    @NonNull
    @Override
    public HadirViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .kehadiran_item, viewGroup, false);
        return new HadirViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HadirViewHolder holder, int i) {
        Hadir hadir = hadirs.get(i);
        holder.hari.setText(hadir.getJadwal().getHari());
        holder.namaPelajaran.setText(hadir.getJadwal().getNamaPelajaran());
        holder.jamMulai.setText(hadir.getJadwal().getJamMulai());
        holder.jamSelesai.setText(hadir.getJadwal().getJamSelesai());
        holder.statusKehadiran.setText(hadir.getStatusKehadiran());
    }

    @Override
    public int getItemCount() {
        return hadirs.size();
    }

    class HadirViewHolder extends RecyclerView.ViewHolder {
        TextView hari, namaPelajaran, jamMulai, jamSelesai, statusKehadiran;

        public HadirViewHolder(@NonNull View itemView) {
            super(itemView);
            hari = itemView.findViewById(R.id.tv_kehadiran_hari);
            namaPelajaran = itemView.findViewById(R.id.tv_kehadiran_nama_pelajaran);
            jamMulai = itemView.findViewById(R.id.tv_kehadiran_jam_mulai);
            jamSelesai = itemView.findViewById(R.id.tv_kehadiran_jam_selesai);
            statusKehadiran = itemView.findViewById(R.id.tv_kehadiran_status_kehadiran);
        }
    }
}
