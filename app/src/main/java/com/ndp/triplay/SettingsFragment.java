package com.ndp.triplay;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;
import android.support.v7.preference.*;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 * to handle interaction events.

 * create an instance of this fragment.
 */
public class SettingsFragment extends PreferenceFragmentCompat implements  SharedPreferences.OnSharedPreferenceChangeListener{
    SharedPreferences sharedPreferences;
    CheckBox checkBox;
    public static final String KEY_SWITCH = "key1";
    public static final String KEY_CheckBox = "key3";
    SharedPreferences.Editor editor;

    public SettingsFragment()
    {

    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.app_preferences);


    }

    @Override
    public void onResume() {
        super.onResume();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
    boolean allowInternet = false;

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String storedValue = sharedPreferences.getString("switch", "defaultValue");
        String storedValueCheck = sharedPreferences.getString("check", "defaultValue");
        if (key.equals(KEY_SWITCH))
        {

            Preference preference = findPreference(key);



           if(storedValue == "1")
           {
               editor = sharedPreferences.edit();
               editor.putString("switch", "0");
               editor.apply();


           }
           else
           {
               editor = sharedPreferences.edit();
               editor.putString("switch", "1");
               editor.apply();

           }



        }

        else if(key.equals(KEY_CheckBox))
        {
            if(storedValueCheck == "1")
            {
                editor = sharedPreferences.edit();
                editor.putString("check", "0");
                editor.apply();


            }
            else
            {
                editor = sharedPreferences.edit();
                editor.putString("check", "1");
                editor.apply();

            }

        }

    }







}
