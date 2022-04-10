    package com.brandshoutcom.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by LANReHNiCs on 11/08/2016.
 */
public class SharedPrefs {

    private Context mContext;
    private static SharedPrefs self;
    private static String SHARED = "SHARED_PREFERENCE";
    private SharedPreferences preferences;

    protected SharedPrefs(Context context) {
        this.mContext = context;
        preferences = mContext.getSharedPreferences(SHARED, Context.MODE_PRIVATE);
    }

    public static SharedPrefs getPreference(Context context) {
        if (self == null) {
            synchronized (SharedPrefs.class) {
                if (self == null) {
                    self = new SharedPrefs(context);
                }
            }
        }
        return self;
    }

    public boolean getPreferenceBoolean(String key) {
        if (preferences != null) {
            if (preferences.contains(key)) {
                return preferences.getBoolean(key, false);
            } else {
                return false;
            }
        }
        return false;
    }

    public String getPreferenceString(String key) {
        if (preferences != null) {
            if (preferences.contains(key)) {
                return preferences.getString(key, "");
            } else {
                return "";
            }
        }
        return "";
    }

    public int getPreferenceInt(String key) {
        if (preferences != null) {
            if (preferences.contains(key)) {
                return preferences.getInt(key, 1);
            }
        }
        return 0;

    }

    public long getPrefrenceLong(String key) {
        if (preferences != null) {
            if (preferences.contains(key)) {
                return preferences.getLong(key, 0);
            }
        }
        return 0;
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }
}


