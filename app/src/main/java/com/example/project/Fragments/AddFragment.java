package com.example.project.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project.Database.DBHelper;
import com.example.project.Database.TripDB;
import com.example.project.Helper.DateTimeHelper;
import com.example.project.Model.Trip;
import com.example.project.R;

import java.util.Calendar;

public class AddFragment extends Fragment {
    public EditText startDateEdit, startTimeEdit, fromEdit, toEdit, priceEdit, capacityEdit;
    private Calendar date, time;
    private Spinner typeSpinner;
    private String[] strTypesOfTransport;
    private Button addTripBtn;
    private String from, to, startDate, startTime, transportType;
    private int price, capacity;
    private SQLiteDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startDateEdit = (EditText)getActivity().findViewById(R.id.dateEdit);
        startTimeEdit = (EditText)view.findViewById(R.id.timeEdit);
        fromEdit = (EditText)view.findViewById(R.id.fromEdit);
        toEdit = (EditText)view.findViewById(R.id.toEdit);
        priceEdit = (EditText)view.findViewById(R.id.priceEdit);
        capacityEdit = (EditText)view.findViewById(R.id.capacityEdit);

        typeSpinner = (Spinner)view.findViewById(R.id.typeSpnr);
        addTripBtn = (Button)view.findViewById(R.id.addTripBtn);

        date = Calendar.getInstance();
        time = Calendar.getInstance();

        setDateListeners();
        setTimeListeners();

        strTypesOfTransport = new String[] { "Car", "Minibus", "Bus"};
        ArrayAdapter<String> typeOfLastPeriodAdapter =
                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, strTypesOfTransport);
        typeSpinner.setAdapter(typeOfLastPeriodAdapter);

        db = new DBHelper(getContext()).getWritableDatabase();

        addTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    bindingValues();
                    Trip trip = new Trip(from, to, startDate, startTime, transportType,capacity, price);
                    if(TripDB.createTrip(db, trip) != -1) {
                        clearInputs();
                        Toast.makeText(getContext(), "Trip was created successfully", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Error add function", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Check your input data", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setDateListeners() {
        startDateEdit.setInputType(InputType.TYPE_NULL);

        DatePickerDialog.OnDateSetListener d = (view, year, monthOfYear, dayOfMonth) ->
                startDateEdit.setText(DateTimeHelper.getGeneralDateFormat(year, monthOfYear + 1, dayOfMonth));

        DatePickerDialog birthdayDialog = new DatePickerDialog(getContext(), d,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH));

        startDateEdit.setOnClickListener(view -> {
            birthdayDialog.show();
        });
        startDateEdit.setOnFocusChangeListener((view, hasFocus) -> {
            if(hasFocus) {
                birthdayDialog.show();
            }
        });
    }

    private void setTimeListeners() {
        startTimeEdit.setInputType(InputType.TYPE_NULL);

        TimePickerDialog.OnTimeSetListener startT = (view, hourOfDay, minutes) -> {
            startTimeEdit.setText(DateTimeHelper.getGeneralTimeFormat(hourOfDay, minutes));
        };

        TimePickerDialog startWorkDialog = new TimePickerDialog(getContext(), startT,
                time.get(Calendar.HOUR_OF_DAY),
                time.get(Calendar.MINUTE), true);


        startTimeEdit.setOnClickListener(view -> {
            startWorkDialog.show();
        });
        startTimeEdit.setOnFocusChangeListener((view, hasFocus) -> {
            if(hasFocus) {
                startWorkDialog.show();
            }
        });

    }

    private void bindingValues() {
        from = fromEdit.getText().toString().trim();
        to = toEdit.getText().toString().trim();
        startDate = startDateEdit.getText().toString();
        startTime = startTimeEdit.getText().toString();
        transportType = typeSpinner.getSelectedItem().toString();
        price = Integer.parseInt(priceEdit.getText().toString().trim());
        capacity = Integer.parseInt(capacityEdit.getText().toString().trim());
    }

    private void clearInputs() {
        fromEdit.setText("");
        toEdit.setText("");
        startDateEdit.setText("");
        startTimeEdit.setText("");
        priceEdit.setText("");
        capacityEdit.setText("");

    }


}
