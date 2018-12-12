package com.isolution.schoolmanagementsystem.model;

public class Hadir {
    private Jadwal jadwal;
    private String statusKehadiran;

    public Hadir(Jadwal jadwal, String statusKehadiran) {
        this.jadwal = jadwal;
        this.statusKehadiran = statusKehadiran;
    }

    public Jadwal getJadwal() {
        return jadwal;
    }

    public String getStatusKehadiran() {
        return statusKehadiran;
    }
}
