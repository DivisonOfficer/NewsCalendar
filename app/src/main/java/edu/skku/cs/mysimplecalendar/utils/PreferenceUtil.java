package edu.skku.cs.mysimplecalendar.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private PreferenceUtil(SharedPreferences prefs)
    {
        this.prefs = prefs;
        this.editor = prefs.edit();
    }


    public String getString(String key, String nullValue)
    {
        return prefs.getString(key,nullValue);
    }
    public void putString(String key, String value)
    {
        editor.putString(key,value);
        editor.apply();
    }
    public Boolean getBoolean(String key, Boolean nullValue)
    {
        return prefs.getBoolean(key,nullValue);
    }
    public void setBoolean(String key, Boolean value)
    {
        editor.putBoolean(key,value);
        editor.apply();
    }





    public static void init(Context context){
        instance = new PreferenceUtil(context.getSharedPreferences(PREFS_FILE,0));
    }

    public static PreferenceUtil instance;

    public static final String PREFS_FILE = "NEWS_APP_PREFS";



}


