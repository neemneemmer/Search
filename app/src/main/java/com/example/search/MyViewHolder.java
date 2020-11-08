package com.example.search;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView image_single_view;
    TextView texyView_single_view;
    View view;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        image_single_view =itemView.findViewById(R.id.image_single_view);
        texyView_single_view =itemView.findViewById(R.id.texyView_single_view);
        view = itemView;
    }
}
