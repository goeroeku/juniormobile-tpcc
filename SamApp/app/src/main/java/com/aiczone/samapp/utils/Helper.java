package com.aiczone.samapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;

public class Helper {
    public static String formatDMY(int day, int month, int year) {
        return lPad(String.valueOf(day)) + "-" + lPad(String.valueOf(month)) + "-" + year;
    }

    public static String formatDMY(String tgl) {
        String[] temp = tgl.split("-");

        int day = Integer.parseInt(temp[2]);
        int month = Integer.parseInt(temp[1]);
        return lPad(String.valueOf(day)) + "-" + lPad(String.valueOf(month))  + "-" + temp[0];
    }

    public static String formatDMYLong(String tgl) {
        String[] temp = tgl.split("-");
        int day = Integer.parseInt(temp[2]);

        return lPad(String.valueOf(day)) + " " + getBulan(Integer.parseInt(temp[1])) + " " + temp[0];
    }

    public static String getString(Context context, int stringId){
        return context.getResources().getString(stringId);
    }

    @SuppressLint("DefaultLocale")
    private static String lPad(String string) {
        return String.format("%02d", Integer.parseInt(string));
    }

    private static String getBulan(int bln) {
        String[] aBulan = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juni", "Agustus", "September", "Oktober", "November", "Desember"};

        return aBulan[bln - 1];
    }
}
