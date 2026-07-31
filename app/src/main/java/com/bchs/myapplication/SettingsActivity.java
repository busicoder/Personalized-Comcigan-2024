package com.bchs.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    public static class SettingsFragment extends PreferenceFragmentCompat {
        SharedPreferences pref;
        static stInfo stDt;
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            pref= PreferenceManager.getDefaultSharedPreferences(getActivity());

        }

        @Override
        public void onResume() {
            super.onResume();
            pref.registerOnSharedPreferenceChangeListener(listener);
        }

        @Override
        public void onPause() {
            super.onPause();
            pref.unregisterOnSharedPreferenceChangeListener(listener);
        }

        SharedPreferences.OnSharedPreferenceChangeListener listener= new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, @Nullable String key) {
                if(key.equals("grade")) {
                    if (stDt == null)
                        stDt = stInfo.GetstInfoClass();
                    stDt.Grade = Integer.parseInt(pref.getString("grade", null).toString());
                    stDt.setMyTable();
                }else if(key.equals("ban")){
                    if (stDt == null)
                        stDt = stInfo.GetstInfoClass();
                    stDt.Ban = Integer.parseInt(pref.getString("ban", null).toString());
                    stDt.setMyTable();
                }
            }
        };


    }
}