package com.isolution.schoolmanagementsystem.model;

import java.util.List;

public class Kehadiran {
    private String statusJadwal;
    private String hadir;
    private List<Hadir> kehadiran;

    public Kehadiran(String statusJadwal, String hadir, List<Hadir> kehadiran) {
        this.statusJadwal = statusJadwal;
        this.hadir = hadir;
        this.kehadiran = kehadiran;
    }

    public String getStatusJadwal() {
        return statusJadwal;
    }

    public String getHadir() {
        return hadir;
    }

    public List<Hadir> getKehadiran() {
        return kehadiran;
    }
}
