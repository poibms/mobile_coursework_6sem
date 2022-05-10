package com.example.project.Helper;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;

public class DishesViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView description;
    public TextView colTags;
    public ImageView image;
    public Button toCollect;

    public DishesViewHolder(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.imageView);
        title = itemView.findViewById(R.id.collect_name);
        colTags = itemView.findViewById(R.id.collect_tags);
        description = itemView.findViewById(R.id.collect_description);
        toCollect = (Button) itemView.findViewById(R.id.collection_btn);
    }
}
