package com.example.project.Database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.project.Model.OrderedTickets;

public class OrderTicketsDB {
    private static final String ORDERTICKETS_TABLE = "ORDERTICETS_TABLE";

    public static long orderTicket(SQLiteDatabase db, OrderedTickets orderedTickets) {
        ContentValues values = new ContentValues();
        values.put("COUNTICKETS", orderedTickets.getCountPlaces());
        values.put("ORDEREDPRICE", orderedTickets.getPrice());
        values.put("IDUSER", orderedTickets.getUserId());
        values.put("IDTRIP", orderedTickets.getTripId());

        return db.insert(ORDERTICKETS_TABLE, null, values);
    }

}
