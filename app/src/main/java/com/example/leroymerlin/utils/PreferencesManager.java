package com.example.leroymerlin.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PreferencesManager {

    public static final String PREFS_NAME = "Leroy Merlin";
    public static Context contextPref;

    public static void setContext(Context context){
        contextPref = context;
    }

    public static List<Integer> getAllPrefAds(){
        List<Integer> list = new ArrayList<>();

        SharedPreferences settings = contextPref.getSharedPreferences(PREFS_NAME, 0);
        if (settings.contains("prefAds")){
            String[] prefAds = settings.getString("prefAds", "").split(",");
            for (String pref : prefAds){
                list.add(Integer.parseInt(pref));
            }
        }
        return list;
    }

    public static List<Integer> getAllUninterestingAds(){
        List<Integer> list = new ArrayList<>();

        SharedPreferences settings = contextPref.getSharedPreferences(PREFS_NAME, 0);
        if (settings.contains("uninAds")){
            String[] uninAds = settings.getString("uninAds", "").split(",");
            for (String unin : uninAds){
                list.add(Integer.parseInt(unin));
            }
        }
        return list;
    }

    public static void addPrefAd(int pid){
        List<Integer> list = getAllPrefAds();
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer id : list){
            stringBuilder.append(id+",");
        }
        stringBuilder.append(pid);

        SharedPreferences settings = contextPref.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("prefAds", stringBuilder.toString());
        editor.apply();
    }

    public static void addUninterestedAd(int pid){
        List<Integer> list = getAllUninterestingAds();
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer id : list){
            stringBuilder.append(id+",");
        }
        stringBuilder.append(pid);

        SharedPreferences settings = contextPref.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("uninAds", stringBuilder.toString());
        editor.apply();
    }

    public static void removePrefAd(int pid){
        List<Integer> list = getAllPrefAds();
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer id : list){
            if (id != pid){
                stringBuilder.append(id+",");
            }
        }

        SharedPreferences settings = contextPref.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("prefAds", stringBuilder.toString());
        editor.apply();
    }
}
