package com.example.myvoca;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {
    TextView textView;
    TextView textView2;
    DBHelper mydb;
    int todayCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        textView = rootView.findViewById(R.id.welcomeMsg);
        textView2 = rootView.findViewById(R.id.motivatingMsg);

        todayCount = 0;
        mydb = new DBHelper(getContext());
        Cursor cursor = mydb.getAllData();

        Date dateNow = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String date = format.format(dateNow);

        while (cursor.moveToNext()) {
            if (date.equals(cursor.getString(cursor.getColumnIndex("date")))) todayCount++;
        }


        if (todayCount > 0 && todayCount < 10) {
            textView.setText("오늘 " + todayCount + "개의\n단어를 찾았어요.");
            textView2.setText("오늘 본 단어는\n오늘 외웁시다!");
        } else if (todayCount >= 10 && todayCount < 100) {
            textView.setText("오늘 찾은 단어만\n벌써 " + todayCount + "개.");
            textView2.setText("엄청\n열심인걸요?");
        } else if (todayCount >= 100) {
            textView.setText("오늘만 벌써\n" + todayCount + "개의 단어.");
            textView2.setText("친구들에게\n자랑할만 한걸요?");
        } else {
            textView.setText("찾은 단어가\n아직 없어요.");
            textView2.setText("오늘도 힘차게\n달려봅시다!");
        }


        // Inflate the layout for this fragment
        return rootView;
    }
}