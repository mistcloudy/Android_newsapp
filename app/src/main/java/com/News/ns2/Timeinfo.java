package com.News.ns2;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Timeinfo {
    private String TIME_KEY = "TIME";
    private String KEEP_KEY = "KEEP";

    String time = "";
    String keep = "";

    public Timeinfo(String time, String keep) {
        this.time = time;
        this.keep = keep;
    }


    void Save(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();

        if(time.length() > 0 ){
            editor.putString(TIME_KEY, time)
                    .putString( KEEP_KEY , keep)
                    .apply();
        }
    }

    void Load(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        time = sp.getString(TIME_KEY, time);
        keep = sp.getString( KEEP_KEY , keep);
    }
}
