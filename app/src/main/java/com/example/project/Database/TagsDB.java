package com.example.project.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.project.models.Root;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagsDB {
    private static final String TAGS_TABLE = "tags";

    public static void addTags(SQLiteDatabase db, ArrayList<Root> tags) {
        for(Root tag: tags) {
            ContentValues tagsValue = new ContentValues();

            tagsValue.put("id", tag.getId());
            tagsValue.put("text", tag.getText());

            long insertedDishId = db.insert(TAGS_TABLE, null, tagsValue);
        }
    }

    public static void deleteAllTags(SQLiteDatabase db) {
        db.execSQL("delete from "+ TAGS_TABLE);
    }

    public static List<Root> getTags(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TAGS_TABLE , null);
        List<Root> tags = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
//                tags.add(String.valueOf(cursor.getInt(0)) +". " + cursor.getString(1));
                tags.add(new Root(cursor.getInt(0), cursor.getString(1)));
//                tags.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        Log.d("JWT_DECODED", "spin: " + tags);
        for(Root root : tags) {
            Log.d("JWT_DECODED", "spin: " + root.getId() + " " + root.getText());
        }
//        if(cursor.moveToNext()) {
////            Root tags = new Root(
////                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
////                    cursor.getString(cursor.getColumnIndexOrThrow("text"))
////            );
//            tags.add(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("id"))));
//            tags.add(cursor.getString(cursor.getColumnIndexOrThrow("text")));
            return tags;
//        }
//        return null;
    }
}
