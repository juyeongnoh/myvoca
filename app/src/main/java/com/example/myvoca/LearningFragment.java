package com.example.myvoca;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class LearningFragment extends Fragment {
    private RecyclerView recyclerView;
    private Adapter adapter;

    DBHelper mydb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_learning, container, false);

        mydb = new DBHelper(getContext());
        Context context = getContext();
        Cursor cursor = mydb.getAllData();

        // 리사이클러뷰 xml 객체화
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        //수직 스크롤
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new Adapter();

        /*
        if(cursor.getCount() == 0) {
            Toast.makeText(getContext(), "데이터 없음!", Toast.LENGTH_LONG).show();
        } else {
            while(cursor.moveToNext()) {
                String date = cursor.getString(cursor.getColumnIndex("date"));
                adapter.setArrayData(date);
            }
        }
        */
        int count = 0;
        String date;
        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "데이터 없음!", Toast.LENGTH_LONG).show();
        } else {
            cursor.moveToFirst();
            String temp = cursor.getString(cursor.getColumnIndex("date"));
            System.out.println("temp: " + temp);
            while (cursor.moveToNext()){
                date = cursor.getString(cursor.getColumnIndex("date"));
                System.out.println("date: " + date);
                count++;
                if(date.equals(temp) == false) {
                    adapter.setArrayData(temp, count + "개 단어");
                    temp = date;
                    count = 0;
                }
            }
            cursor.moveToLast();
            adapter.setArrayData(cursor.getString(cursor.getColumnIndex("date")), count + 1 + "개 단어");
        }


        recyclerView.setAdapter(adapter);


        // Inflate the layout for this fragment
        return rootView;
    }
}