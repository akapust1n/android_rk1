package com.example.alexey.myapp;

import android.content.Context;
import android.content.SharedPreferences;


public class Storage {

    private static com.example.alexey.myapp.Storage INSTANCE;
    private SharedPreferences preferences;

    public static synchronized com.example.alexey.myapp.Storage getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new com.example.alexey.myapp.Storage(context.getApplicationContext());
        }

        return INSTANCE;
    }

    private Storage(Context context) {
        this.preferences = context.getSharedPreferences("Mem", 0);
    }


    public void saveMem(String mem) {
        if (mem == null) {
            this.preferences.edit().remove("Mem").apply();
        } else {
            this.preferences.edit().putString("Mem", mem).apply();
        }
    }

    public String getLastSavedMem() {
        String result = this.preferences.getString("Mem", "");
        return result.isEmpty() ? null : result;
    }


    public String loadCurrentTopic() {
        return this.preferences.getString("Topic", "");
    }

    public void saveCurrentTopic(String topic) {
        this.preferences.edit().putString("Topic", topic).apply();
    }

}
