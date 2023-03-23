package com.example.myvoca;

import static com.example.myvoca.R.drawable.bg_gradient;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

// 플래시카드 액티비티
// 상단바 숨김, inflating word_item
public class FlashcardsActivity extends AppCompatActivity {
    ViewPager viewPager;
    CardAdapter cardAdapter;
    public List<Card> cards;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    DBHelper mydb;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcards);

        intent = getIntent();
        String d = intent.getExtras().getString("date");

        // 액션바에 날짜 넣기
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(d);

        // DB 사용
        mydb = new DBHelper(getApplicationContext());

        Context context = getApplicationContext();
        Cursor cursor = mydb.getAllData();

        cards = new ArrayList<>();

        // 플래시카드에 단어 추가
        while (cursor.moveToNext()) {
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String word = cursor.getString(cursor.getColumnIndex("word"));
            String definition = cursor.getString(cursor.getColumnIndex("definition"));
            String urlfrom = cursor.getString(cursor.getColumnIndex("urlfrom"));

            if(date.equals(d) == true) {
                cards.add(new Card(word, definition, urlfrom));
            }
        }
        cursor.close();

        cardAdapter = new CardAdapter(cards, this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(cardAdapter);
        viewPager.setPadding(130, 360, 130, 0);

        /*
        Integer[] colors_temp = {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4)
        };
        */

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                /*if (position < (cardAdapter.getCount() - 1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset, colors[position], colors[position + 1]));
                } else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }*/
                //viewPager.getResources().getColor(R.color.colorPrimaryDark);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}