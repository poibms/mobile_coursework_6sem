package com.example.project.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.Database.DBHelper;
import com.example.project.Database.OrderTicketsDB;
import com.example.project.Database.TripDB;
import com.example.project.Model.OrderedTickets;
import com.example.project.Model.Trip;
import com.example.project.R;

public class SelectedTripActivity extends AppCompatActivity {
    private Trip selectedTrip;
    private Button orderBtn;
    private ImageButton plusBtn, minusBtn;
    private TextView counterPlace, from, to , startDate, startTime, transportType, price, availablePlaces;

    int count = 1, currPrice, tripId, userId, freePlaces, finalCost;
    private SQLiteDatabase db;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "userInfo";
    private static final String KEY_ID = "userId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_trip);


        binding();
        setListener();
        setDefaultValues();

    }

    private void binding() {
        //btn
        orderBtn = findViewById(R.id.orderBtn);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        counterPlace = findViewById(R.id.counterPlace);

        //textView
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        startDate = findViewById(R.id.startDate);
        startTime = findViewById(R.id.startTime);
        transportType = findViewById(R.id.transportType);
        price = findViewById(R.id.price);
        availablePlaces = findViewById(R.id.available);

        db = new DBHelper(this).getWritableDatabase();

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        userId = sharedPreferences.getInt(KEY_ID, 0);

    }

    private void setDefaultValues() {
        Intent intent = getIntent();
        selectedTrip = (Trip)intent.getSerializableExtra("Trip");

        from.setText(selectedTrip.getStart());
        to.setText(selectedTrip.getFinish());
        startDate.setText(selectedTrip.getStartDate());
        startTime.setText(selectedTrip.getStartTime());
        transportType.setText(selectedTrip.getTransportType());
        price.setText(selectedTrip.getPrice() + "");
        availablePlaces.setText(selectedTrip.getCapacity() + "");

        currPrice = selectedTrip.getPrice();
        tripId = selectedTrip.getId();


    }


    private void setListener() {
        plusBtn.setOnClickListener(view -> {
            if(count == Integer.parseInt(availablePlaces.getText().toString())) {
                plusBtn.setEnabled(false);
            } else {
                minusBtn.setEnabled(true);
                count++;
                price.setText(String.valueOf(currPrice * count));
                counterPlace.setText(String.valueOf(count));
            }
        });

        minusBtn.setOnClickListener(view -> {
            if(count <= 1 ) {
                minusBtn.setEnabled(false);
            } else {
                plusBtn.setEnabled(true);
                count--;
                price.setText(String.valueOf(currPrice * count));
                counterPlace.setText(String.valueOf(count));
            }
        });

        orderBtn.setOnClickListener(view -> {

            try {
                freePlaces = Integer.parseInt(availablePlaces.getText().toString());
                finalCost = Integer.parseInt(price.getText().toString());

                OrderedTickets order = new OrderedTickets(userId, tripId, count, finalCost);
                if(OrderTicketsDB.orderTicket(db, order) != -1 && TripDB.updatePlaces(db, tripId, freePlaces - count) != 0) {
                    Toast.makeText(this, "Thanks for using our service", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Tickets was not booked", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Trash", Toast.LENGTH_SHORT).show();
            }
        });
    }

}