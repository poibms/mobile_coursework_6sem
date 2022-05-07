package com.example.project.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.project.Model.User;

public class DBHelper extends SQLiteOpenHelper {
    private static final int SCHEMA = 1;
    private static final String DATABASE_NAME = "TravellerProject";
    private static final String USER_TABLE = "USER";
    private static final String TRIP_TABLE = "TRIP";
    private static final String ORDERTICKETS_TABLE = "ORDERTICETS_TABLE";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + USER_TABLE + " (                    "
                + "IDUSER integer primary key autoincrement not null,"
                + "LOGIN text not null,"
                + "EMAIL text not null,"
                + "PASSWORD text not null);"
        );

        db.execSQL("create table " + TRIP_TABLE + "("
                + " IDTRIP integer primary key autoincrement not null, "
                + " START text not null,"
                + " FINISH text not null, "
                + " STARTTIME text not null, "
                + " TRANSPORTTYPE text not null, "
                + " DATA text not null,"
                + " CAPACITY integer not null,"
                + " PRICE integer not null);"
        );

        db.execSQL("create table " + ORDERTICKETS_TABLE + " (                    "
                + "IDORDERTICKET integer primary key autoincrement not null,"
                + "COUNTICKETS integer not null, "
                + "ORDEREDPRICE integer not null,"
                + "IDUSER integer not null, "
                + "IDTRIP integer not null, "
                + "foreign key (IDUSER) references " + USER_TABLE + "(IDUSER)  "
                + " on delete cascade on update cascade,                            "
                + "foreign key (IDTRIP) references " + TRIP_TABLE + "(IDTRIP) "
                + " on delete cascade on update cascade); "

        );
    }


    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + USER_TABLE);
        db.execSQL("drop table if exists " + TRIP_TABLE);
        db.execSQL("drop table if exists " + ORDERTICKETS_TABLE);
        onCreate(db);
    }
}
