package com.example.myvoca;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class CardAdapter extends PagerAdapter {
    List<Card> cards;
    private LayoutInflater layoutInflater;
    private Context context;

    public CardAdapter(List<Card> cards, Context context) {
        this.cards = cards;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.word_item, container, false);

        TextView word;
        TextView definition;

        word = view.findViewById(R.id.word);
        definition = view.findViewById(R.id.definition);

        // 단어 표시
        word.setText(cards.get(position).getWord());

        // 단어 뜻 표시
        definition.setText(cards.get(position).getDefinition());
        definition.setVisibility(View.INVISIBLE);

        // 뷰 탭하면 단어 뜻 표시
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                definition.setVisibility(View.VISIBLE);
            }
        });

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
