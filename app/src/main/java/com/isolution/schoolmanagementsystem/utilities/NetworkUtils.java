package com.isolution.schoolmanagementsystem.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.isolution.schoolmanagementsystem.model.Perizinan;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class NetworkUtils {
    private static String BASE_URL_DEMO = "https://demo.simak.id/";
    private static String BASE_URL_DEV = "https://dev.isolution.id/";

    private static String convertStreamToString(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        return bos.toByteArray();
    }

    public static Bitmap convertByteArrayToBitmap(byte[] bitmap) {
        return BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);
    }

    public static String getProfileData(String nis) {
        String URL_GET_PROFILE = BASE_URL_DEMO + "pelajar/api_profile_siswa/";
        String reqUrl = URL_GET_PROFILE + nis;
        String response = null;
        HttpURLConnection con = null;
        try {
            URL url = new URL(reqUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            InputStream in = new BufferedInputStream(con.getInputStream());
            response = convertStreamToString(in);
            con.disconnect();
            in.close();
        } catch (IOException e) {
            Log.e("--> getProfilData : ", e.toString());
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return response;
    }

    public static String getJadwalData(String nis) {
        String URL_GET_JADWAL = BASE_URL_DEMO + "jadwal/api_lihat_jadwal_siswa/";
        String reqUrl = URL_GET_JADWAL + nis;
        String response = null;
        HttpURLConnection con = null;
        try {
            URL url = new URL(reqUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(con.getInputStream());
            response = convertStreamToString(in);
            con.disconnect();
            in.close();
        } catch (IOException e) {
            Log.e("--> getJadwalData : ", e.toString());
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return response;
    }

    public static String getKehadiranData(String nis) {
        String URL_GET_KEHADIRAN = BASE_URL_DEMO + "kehadiran/api_kehadiran_siswa_hari_ini/";
        String reqUrl = URL_GET_KEHADIRAN + nis;
        String response = null;
        HttpURLConnection con = null;
        try {
            URL url = new URL(reqUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(con.getInputStream());
            response = convertStreamToString(in);
            con.disconnect();
            in.close();
        } catch (IOException e) {
            Log.e("--> getDataKehadiran : ", e.toString());
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return response;
    }

    public static Bitmap getImage(String url) {
        Bitmap response = null;
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("GET");
            InputStream in = con.getInputStream();
            response = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            Log.e("--> downloadImage : ", e.toString());
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return response;
    }


    public static String uploadPerizinan(Perizinan perizinanData) {
        String URL_POST_PERIZINAN = BASE_URL_DEMO + "kehadiran/api_pengajuan_absen";
        String response = null;
        String boundary = "*****";
        String crlf = "\r\n";
        String stripDua = "--";
        HttpURLConnection con = null;
        String fileName = perizinanData.getProfil().getNis() + Calendar.getInstance().getTime()
                .toString();
        try {
            con = (HttpURLConnection) new URL(URL_POST_PERIZINAN).openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            con.setRequestProperty("Cache-Control", "no-cache");

            DataOutputStream dos = new DataOutputStream(con.getOutputStream());
            //
            dos.writeBytes(stripDua + boundary + crlf);
            dos.writeBytes("Content-Disposition: form-data; name=\"user_image\";" +
                    "filename=\"" + fileName + "\"" + crlf);
            dos.writeBytes(crlf);
            dos.write(convertBitmapToByteArray(perizinanData.getBuktiIzin()));
            //
            dos.writeBytes(crlf);
            dos.writeBytes(stripDua + boundary + crlf);
            dos.writeBytes("Content-Disposition: form-data; name=\"nis\"" + crlf);
            dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + crlf);
            dos.writeBytes(crlf);
            dos.writeBytes(perizinanData.getProfil().getNis() + crlf);
            //
            dos.writeBytes(crlf);
            dos.writeBytes(stripDua + boundary + crlf);
            dos.writeBytes("Content-Disposition: form-data; name=\"absen\"" + crlf);
            dos.writeBytes("Content-Type: text/plain; charset=UTF-8" + crlf);
            dos.writeBytes(crlf);
            dos.writeBytes(perizinanData.getJenisIzin() + crlf);
            //
            dos.writeBytes(crlf);
            dos.writeBytes(stripDua + boundary + stripDua + crlf);
            dos.flush();
            dos.close();
            Log.e("--> cek cek : ", "sampai");

            //
            InputStream in = new BufferedInputStream(con.getInputStream());
            Log.e("--> in1 : ", in.toString());
            response = convertStreamToString(in);
            Log.e("--> in2 : ", response);
            con.disconnect();
            in.close();
        } catch (IOException e) {
            Log.e("--> uploadPerizinan : ", e.toString());
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return response;
    }

}
