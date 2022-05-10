package com.example.project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.Database.CollectDB;
import com.example.project.Helper.DishesAdapter;
import com.example.project.Helper.ItemsAdapter;
import com.example.project.Helper.SharedPreferencesHelper;
import com.example.project.Helper.SystemHelper;
import com.example.project.Model.Trip;
import com.example.project.R;
import com.example.project.api.OnFetchDataListener;
import com.example.project.api.requestManager.RequestCollectionManager;
import com.example.project.models.Account;
import com.example.project.models.Collect;
import com.example.project.models.FullCollection;
import com.example.project.models.Items;
import com.example.project.models.Root;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Response;

public class selectedCollection extends AppCompatActivity {

    TextView title, description, tagsView;
    Collect collect;
    Integer a;
    FullCollection fullColl;
    String value = "";
    ImageView imageView;
    Button deleteBtn;
    private final Context context = this;
    private RecyclerView collectionRecycler;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_collection);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        tagsView = findViewById(R.id.tags);
        imageView = findViewById(R.id.imageView2);
        collectionRecycler = findViewById(R.id.items_recycler);
        deleteBtn = findViewById(R.id.deleteBtn);

        account = SharedPreferencesHelper.getUserInfo(context);

        Intent intent = getIntent();
        a = (Integer) intent.getSerializableExtra("id");
        Log.d("JWT_DECODED", "spin: " + a);

        getData();

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SystemHelper.isNetworkAvailable(context)){
                    RequestCollectionManager collectionManager = new RequestCollectionManager(context);
                    collectionManager.deleteColl(deleteCollListener, account.getToken(), a);
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);

                }
            }
        });

    }

    public void getData() {
        if(SystemHelper.isNetworkAvailable(this)) {
            RequestCollectionManager collectionManager = new RequestCollectionManager(this);
            collectionManager.getCollById(getCollListener, a);
        }
    }

    public void setData() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        title.setText(fullColl.collection.getTitle());
        description.setText(fullColl.collection.getDescription());
        for(Root tags : fullColl.collection.getTags()) {
            value = tags.getText() + "";
        }
        tagsView.setText("Tags: " + value);

        if(SystemHelper.isNetworkAvailable(context)){
            URL newurl = null;
            try {
                newurl = new URL("http://192.168.0.104:7000/" + fullColl.collection.getImage());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap mIcon_val = null;
            try {
                mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(mIcon_val);
        }
        else{
            imageView.setImageResource(R.drawable.accbg);

        }
    }

    private void displayCollect(ArrayList<Items> items) {
        collectionRecycler.setHasFixedSize(true);
        collectionRecycler.setLayoutManager(new GridLayoutManager(context, 1));
        ItemsAdapter dishesAdapter = new ItemsAdapter(context, items);
        collectionRecycler.setAdapter(dishesAdapter);
    }

    private final OnFetchDataListener<FullCollection> getCollListener = new OnFetchDataListener<FullCollection>() {
        @Override
        public void onFetchData(Response<FullCollection> response) {
            if(response.isSuccessful()) {
                fullColl = response.body();
                setData();


                if(account.getId() == fullColl.collection.getOwnerId()) {
                    deleteBtn.setVisibility(View.VISIBLE);
                }
                displayCollect(fullColl.collectItems);
//                displayCollect(collArray);

//                CollectDB.deleteAllCollect(db);
//                CollectDB.addCollections(db, collArray);
            }
            else {
                Toast.makeText(context, "Error by getting ", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        public void onFetchError(Throwable error) {
            Log.e("JWT_DECODED", "body: " + error);
            Toast.makeText(context, "Error by getting dishes", Toast.LENGTH_LONG).show();
        }
    };

    private final OnFetchDataListener<String> deleteCollListener = new OnFetchDataListener<String>() {
        @Override
        public void onFetchData(Response<String> response) {
            if(response.isSuccessful()) {
//                fullColl = response.body();
//                setData();
            }
            else {
                Log.e("JWT_DECODED", "body: " + response.code());
                Toast.makeText(context, "Error by getting ", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        public void onFetchError(Throwable error) {
            Log.e("JWT_DECODED", "body: " + error);
            Toast.makeText(context, "Error by getting dishes", Toast.LENGTH_LONG).show();
        }
    };

}