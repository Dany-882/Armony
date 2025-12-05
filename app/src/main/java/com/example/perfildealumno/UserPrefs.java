package com.example.perfildealumno;


import android.content.Context;
import android.content.SharedPreferences;

public class UserPrefs {
    private static final String PREF_NAME = "user_data";
    private SharedPreferences prefs;

    public UserPrefs(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveString(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    public String getString(String key, String def) {
        return prefs.getString(key, def);
    }

    public void saveInt(String key, int value) {
        prefs.edit().putInt(key, value).apply();
    }

    public int getInt(String key, int def) {
        return prefs.getInt(key, def);
    }
}
