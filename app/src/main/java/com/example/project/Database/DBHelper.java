package com.example.project.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.project.Model.User;

public class DBHelper extends SQLiteOpenHelper {
    private static final int SCHEMA = 1;
    private static final String DATABASE_NAME = "coursework";
    private static final String USER_TABLE = "users";
    private static final String COLLeCTIONS_TABLE = "collections";
    private static final String COLLTAGS_TABLE = "collection_tags";
    private static final String TAGS_TABLE = "tags";
    private static final String ITEMS_TABLE = "items";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + COLLeCTIONS_TABLE + "("
                +"id SERIAL PRIMARY KEY," +
                "title TEXT NOT NULL," +
                "description TEXT NOT NULL," +
                "image TEXT NOT NULL," +
                "owner_id integer )"
        );

        db.execSQL("create table " + ITEMS_TABLE + " (                    "
                + "id SERIAL PRIMARY KEY," +
                "title  TEXT NOT NULL," +
                "description TEXT NOT NULL," +
                "image TEXT NOT NULL," +
                "collection_id integer," +
                "FOREIGN KEY(collection_id) REFERENCES " + COLLeCTIONS_TABLE + "(Id)        " +
                "   ON DELETE CASCADE ON UPDATE CASCADE );"

        );
        db.execSQL("create table " + TAGS_TABLE + " (                    "
                + "id SERIAL PRIMARY KEY,"
                + "text TEXT NOT NULL )"

        );

        db.execSQL("create table " + COLLTAGS_TABLE + " (                    "
                + "collection_id integer NOT NULL,"
                + "tags_id integer NOT NULL,"
                + "FOREIGN KEY(collection_id) REFERENCES " + COLLeCTIONS_TABLE + "(Id)        " +
                "   ON DELETE CASCADE ON UPDATE CASCADE , "
                + "FOREIGN KEY(tags_id) REFERENCES " + TAGS_TABLE + "(Id)        " +
                "   ON DELETE CASCADE ON UPDATE CASCADE                      )"

        );
    }


    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + USER_TABLE);
        db.execSQL("drop table if exists " + COLLTAGS_TABLE);
        db.execSQL("drop table if exists " + TAGS_TABLE);
        db.execSQL("drop table if exists " + ITEMS_TABLE);
        db.execSQL("drop table if exists " + COLLTAGS_TABLE);
        onCreate(db);
    }
}
