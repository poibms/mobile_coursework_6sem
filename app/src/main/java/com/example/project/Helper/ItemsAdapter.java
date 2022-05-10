package com.example.project.Helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Activity.selectedCollection;
import com.example.project.R;
import com.example.project.models.Collect;
import com.example.project.models.Items;
import com.example.project.models.Root;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class ItemsAdapter extends RecyclerView.Adapter<DishesViewHolder> {

    private Context context;
    private ArrayList<Items> collect;

    public ItemsAdapter(Context context, ArrayList<Items> dishes) {
        this.context = context;
        this.collect = dishes;
    }

    @Override public int getItemCount() { return collect.size(); }

    @NonNull @Override
    public DishesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DishesViewHolder(LayoutInflater.from(context).inflate(R.layout.collect_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DishesViewHolder holder, int position) {
        //Исправление ошибки стрикт мода из-за запроса в сеть
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if(SystemHelper.isNetworkAvailable(context)){
            URL newurl = null;
            try {
                newurl = new URL("http://192.168.0.104:7000/" + collect.get(position).getImage());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap mIcon_val = null;
            try {
                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            holder.image.setImageBitmap(mIcon_val);
        }
        else{
            holder.image.setImageResource(R.drawable.accbg);

        }
        holder.title.setText(collect.get(position).getTitle());
//        for(Root tag : collect.get(position).getTags()) {
//            String value = "";
//            value = tag.getText() + "";
//
//            holder.colTags.setText(value);
//        }
        holder.description.setText(collect.get(position).getDescription());

        holder.toCollect.setOnClickListener(view -> {
//            Log.d("JWT_DECODED", "spin: " + collect.get(position).getId());
//            Intent intent = new Intent(context, selectedCollection.class);
//            intent.putExtra("id", collect.get(position).getId());
//            context.startActivity(intent);
        });
    }
}
