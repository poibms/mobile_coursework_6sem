package com.example.project.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Activity.MainActivity;
import com.example.project.Activity.SelectedTripActivity;
import com.example.project.Activity.createCollection;
import com.example.project.Database.CollectDB;
import com.example.project.Database.DBHelper;
import com.example.project.Database.TripDB;
import com.example.project.Helper.DishesAdapter;
import com.example.project.Helper.SearchHelper;
import com.example.project.Helper.SharedPreferencesHelper;
import com.example.project.Helper.SystemHelper;
import com.example.project.Model.Trip;
import com.example.project.R;
import com.example.project.api.OnFetchDataListener;
import com.example.project.api.requestManager.RequestCollectionManager;
import com.example.project.models.Account;
import com.example.project.models.Collect;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Response;

public class AccountFragment extends Fragment {

//    private ListView orderedListView;
    private TextView emailTextView, roleTextView;
    private Button logout, createCollBtn;
    private RecyclerView collectionRecycler;


    private int userId;
    private SQLiteDatabase db;
    Account account;
    private ArrayList<Collect> collArray;

    SharedPreferences sharedPreferences;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logout = (Button) view.findViewById(R.id.logoutBtn);
        createCollBtn = (Button) view.findViewById(R.id.createCollBtn);
        emailTextView = (TextView) view.findViewById(R.id.email);
        roleTextView = (TextView) view.findViewById(R.id.role);
        collectionRecycler = (RecyclerView) view.findViewById(R.id.collect_recycler);
//        orderedListView = (ListView)view.findViewById(R.id.orderedListView);

        db = new DBHelper(getContext()).getReadableDatabase();

        account = SharedPreferencesHelper.getUserInfo(getContext());
        emailTextView.setText(account.getEmail());
        roleTextView.setText(account.getRole());

//        customListAdapter = new SearchFragment.CustomListAdapter(getContext(), orderedList);
        getData();


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesHelper.logOut(getContext());
                getActivity().finish();
            }
        });

        createCollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), createCollection.class);
                startActivity(intent);
            }
        });


    }

    private void getData() {
       if(SystemHelper.isNetworkAvailable(getContext())) {
           RequestCollectionManager collectionManager = new RequestCollectionManager(getContext());
           collectionManager.getCollByUserId(getCollListener, account.getToken());
       } else {
           Log.e("JWT_DECODED", "body: " + "error");
       }
    }

    private final OnFetchDataListener<ArrayList<Collect>> getCollListener = new OnFetchDataListener<ArrayList<Collect>>() {
        @Override
        public void onFetchData(Response<ArrayList<Collect>> response) {
            if(response.isSuccessful()) {
                collArray = response.body();
                displayCollect(collArray);
            }
            else {
                Log.e("JWT_DECODED", "body: " + response.code());
                Toast.makeText(getContext(), "Error by getting ", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        public void onFetchError(Throwable error) {
            Log.e("JWT_DECODED", "body: " + error);
            Toast.makeText(getContext(), "Error by getting dishes", Toast.LENGTH_LONG).show();
        }
    };

    private void displayCollect(ArrayList<Collect> collections) {
        collectionRecycler.setHasFixedSize(true);
        collectionRecycler.setLayoutManager(new GridLayoutManager(getContext(), 1));
        DishesAdapter dishesAdapter = new DishesAdapter(getContext(), collections);
        collectionRecycler.setAdapter(dishesAdapter);
    }
}
