package com.example.myvoca;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {
    private ArrayList<String> arrayList;
    private ArrayList<String> wordCount;

    public Adapter() {
        arrayList = new ArrayList<>();
        wordCount = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_list, parent, false);

        ViewHolder viewholder = new ViewHolder(context, view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String date = arrayList.get(position);
        String count = wordCount.get(position);
        holder.textView.setText(date);
        holder.textView2.setText(count);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setArrayData(String date, String count) {
        arrayList.add(date);
        wordCount.add(count);
    }
}
