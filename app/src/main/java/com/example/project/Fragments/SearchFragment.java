package com.example.project.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project.Activity.SelectedTripActivity;
import com.example.project.Database.DBHelper;
import com.example.project.Helper.DateTimeHelper;
import com.example.project.Helper.SearchHelper;
import com.example.project.Model.Trip;
import com.example.project.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;


public class SearchFragment extends Fragment {

    public EditText startDateEdit, fromEdit, toEdit, capacityEdit;
    private Calendar date;
    private Button searchBtn;
    private SQLiteDatabase db;
    private String from, to, startDate;
    private int capacity;
    private ListView tripListView;
    private CustomListAdapter customListAdapter;


    TextView textView;
    boolean[] selectedLanguage;
    ArrayList<Integer> langList = new ArrayList<>();
    String[] langArray = {"Java", "C++", "Kotlin", "C", "Python", "Javascript"};

    ArrayList<Trip> listTrip;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fromEdit = (EditText) view.findViewById(R.id.fromEdit);
        toEdit = (EditText) view.findViewById(R.id.toEdit);
        capacityEdit = (EditText) view.findViewById(R.id.capacityEdit);
        tripListView = (ListView) view.findViewById(R.id.tripListView);

        db = new DBHelper(getContext()).getReadableDatabase();

        listTrip = new ArrayList<>();


        customListAdapter = new CustomListAdapter(getContext(), listTrip);
        tripListView.setAdapter(customListAdapter);


        // assign variable
        textView = view.findViewById(R.id.textView);

        // initialize selected language array
        selectedLanguage = new boolean[langArray.length];

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // set title
                builder.setTitle("Select Language");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(langArray, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            langList.add(i);
                            // Sort array list
                            Collections.sort(langList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            langList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < langList.size(); j++) {
                            // concat array value
                            stringBuilder.append(langArray[langList.get(j)]);
                            // check condition
                            if (j != langList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        textView.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedLanguage.length; j++) {
                            // remove all selection
                            selectedLanguage[j] = false;
                            // clear language list
                            langList.clear();
                            // clear text view value
                            textView.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });
    }



    public class CustomListAdapter extends BaseAdapter {

        private ArrayList<Trip> trips;
        private Context context;

        public CustomListAdapter(Context context, ArrayList<Trip> trips) {
            this.context = context;
            this.trips = trips;
        }

        @Override
        public int getCount() {return trips.size();}

        @Override
        public Object getItem(int i) {return null;}

        @Override
        public long getItemId(int i) {return 0;}

        public void updateStudentsList(ArrayList<Trip> trips) {
            this.trips.clear();
            this.trips.addAll(trips);
            this.notifyDataSetChanged();
        }

        @Override
        public View getView(int pos, View vw, ViewGroup viewGroup) {
            View view = getLayoutInflater().inflate(R.layout.list_item, null);

            TextView from = (TextView) view.findViewById(R.id.from);
            TextView to = (TextView) view.findViewById(R.id.to);
            TextView time = (TextView) view.findViewById(R.id.startTime);
            TextView transportType = (TextView) view.findViewById(R.id.transportType);
            TextView capacity = (TextView) view.findViewById(R.id.capacity);
            TextView price = (TextView) view.findViewById(R.id.price);

            from.setText(trips.get(pos).getStart());
            to.setText(trips.get(pos).getFinish());
            time.setText(trips.get(pos).getStartTime());
            transportType.setText(trips.get(pos).getTransportType());
            capacity.setText(trips.get(pos).getCapacity() + "");
            price.setText(trips.get(pos).getPrice() + "");

            view.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), SelectedTripActivity.class);
                intent.putExtra("Trip", trips.get(pos));
                startActivity(intent);
            });

            return view;
        }
    }
}
