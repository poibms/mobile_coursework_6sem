package com.example.project.Helper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.project.Database.TripDB;
import com.example.project.Model.Trip;

import java.util.ArrayList;
import java.util.List;

public class SearchHelper {


    public static ArrayList<Trip> getTrips(SQLiteDatabase db, String from,
                                      String to, String startDate, int capacity) {

        ArrayList<Trip> resultListTrips = new ArrayList<>();
        Cursor cursor = TripDB.getTrip(db, from, to, startDate, capacity);

        int i = 0;

        while (cursor.moveToNext()) {
            Trip trip = new Trip();
            trip.setId(cursor.getInt(cursor.getColumnIndexOrThrow("IDTRIP")));
            trip.setStart(cursor.getString(cursor.getColumnIndexOrThrow("START")));
            trip.setFinish(cursor.getString(cursor.getColumnIndexOrThrow("FINISH")));
            trip.setStartTime(cursor.getString(cursor.getColumnIndexOrThrow("STARTTIME")));
            trip.setTransportType(cursor.getString(cursor.getColumnIndexOrThrow("TRANSPORTTYPE")));
            trip.setCapacity(cursor.getInt(cursor.getColumnIndexOrThrow("CAPACITY")));
            trip.setPrice(cursor.getInt(cursor.getColumnIndexOrThrow("PRICE")));

            resultListTrips.add(i, trip);

        }
        cursor.close();
        return resultListTrips;

    }
}
