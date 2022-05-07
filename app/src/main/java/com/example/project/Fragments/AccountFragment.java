package com.example.project.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

import com.example.project.Activity.MainActivity;
import com.example.project.Activity.SelectedTripActivity;
import com.example.project.Activity.createCollection;
import com.example.project.Database.DBHelper;
import com.example.project.Database.TripDB;
import com.example.project.Helper.SearchHelper;
import com.example.project.Model.Trip;
import com.example.project.R;

import java.util.ArrayList;
import java.util.Collections;

public class AccountFragment extends Fragment {

    private ListView orderedListView;
    private TextView emailTextView;
    private Button logout, createCollBtn;

    private int userId;
    private SQLiteDatabase db;
    private SearchFragment.CustomListAdapter customListAdapter;
    ArrayList<Trip> orderedList;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "userInfo";
    private static final String KEY_ID = "userId";
    private static final String KEY_EMAIL = "LOGIN";




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
        orderedListView = (ListView)view.findViewById(R.id.orderedListView);

        db = new DBHelper(getContext()).getReadableDatabase();

        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        String userEmail = sharedPreferences.getString(KEY_EMAIL, null);
        userId = sharedPreferences.getInt(KEY_ID, 0);

        emailTextView.setText(userEmail);

        orderedList = new ArrayList<>();
//        customListAdapter = new SearchFragment.CustomListAdapter(getContext(), orderedList);
        getOrderedTickets();


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
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

    private void getOrderedTickets () {
        try {
            String[] strData;
            int i = 0;

            Cursor cursor = TripDB.getOrderTickets(db, userId);
            strData = new String[cursor.getCount()];
            while(cursor.moveToNext()){
                strData[i++] = "From: " + cursor.getString(0) + "\n\t\t" + "To: " + cursor.getString(1) +
                        "\n\t\t" + "Start Time: " + cursor.getString(2) +
                        "\n\t\t" + "Tickets count: " + cursor.getString(3) +
                        "\n\t\t" + "Final price: " + cursor.getString(4) + "$";
            }
            ArrayAdapter<String> adapter = new ArrayAdapter(getContext(),
                    android.R.layout.simple_list_item_1, strData);
            orderedListView.setAdapter(adapter);
//            orderedListView.setAdapter(orderedTickets);

        } catch (Exception e) {
            Toast.makeText(getContext(), "Something was wrong", Toast.LENGTH_SHORT).show();
        }
    }
}
