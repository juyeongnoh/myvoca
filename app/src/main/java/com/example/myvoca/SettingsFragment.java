package com.example.myvoca;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsFragment extends Fragment {
    Switch autoAddSwitch;
    public static boolean autoAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_settings, container, false);

        autoAddSwitch = rootView.findViewById(R.id.autoAdd);

        getAutoAdd();
        if (autoAdd == true) {
            autoAddSwitch.setChecked(true);
        } else {
            autoAddSwitch.setChecked(false);
        }

        autoAddSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    autoAdd = true;
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("autoadd", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("autoadd", autoAdd);
                    editor.commit();
                    sharedPreferences.getBoolean("autoadd", true);
                } else {
                    autoAdd = false;
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("autoadd", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("autoadd", autoAdd);
                    editor.commit();
                    sharedPreferences.getBoolean("autoadd", true);
                }
            }
        });

        return rootView;
    }

    public void getAutoAdd() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("autoadd", Context.MODE_PRIVATE);
        autoAdd = sharedPreferences.getBoolean("autoadd", true);
        //Toast.makeText(getContext(), "불러오기 완료", Toast.LENGTH_SHORT).show();
    }
}