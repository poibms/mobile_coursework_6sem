package com.example.project.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.project.Model.OrderedTickets;
import com.example.project.Model.Trip;

public class TripDB {
    private static final String TRIP_TABLE = "TRIP";
    private static final String ORDERTICKETS_TABLE = "ORDERTICETS_TABLE";

    public static long createTrip (SQLiteDatabase db, Trip trip) {
        ContentValues values = new ContentValues();
        values.put("START", trip.getStart());
        values.put("FINISH", trip.getFinish());
        values.put("STARTTIME", trip.getStartTime());
        values.put("TRANSPORTTYPE", trip.getTransportType());
        values.put("DATA", trip.getStartDate());
        values.put("CAPACITY", trip.getCapacity());
        values.put("PRICE", trip.getPrice());

        return db.insert(TRIP_TABLE, null, values);
    }

    public static Cursor getTrip(SQLiteDatabase db, String from, String to, String data, Integer capacity) {

        db.execSQL("create index if not exists TRPTBL_CRSR on " + TRIP_TABLE + "("
                + "START, " + "FINISH, " + "DATA," + "CAPACITY)");

        return db.rawQuery("select * from " + TRIP_TABLE + " where START = ('" + from + "')"
                + " and FINISH = ('" + to + "')"
                + " and DATA = ('" +  data + "')"
                + " and CAPACITY >= " + capacity + ";", null);
    }

    public static long updatePlaces(SQLiteDatabase db, int tripId, int count) {
        ContentValues values = new ContentValues();
        values.put("CAPACITY", count);
       return db.update(TRIP_TABLE, values, "IDTRIP =" + tripId , null);
    }

    public static Cursor getOrderTickets(SQLiteDatabase db, int userId) {
        return db.rawQuery("select START, FINISH, STARTTIME, COUNTICKETS, ORDEREDPRICE from " + TRIP_TABLE
                + " join " + ORDERTICKETS_TABLE + " on " + ORDERTICKETS_TABLE + ".IDTRIP = " + TRIP_TABLE + ".IDTRIP"
                + " where " + ORDERTICKETS_TABLE + ".IDUSER = " + userId, null);
    }
}
