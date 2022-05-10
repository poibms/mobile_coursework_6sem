package com.example.project.Database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.project.models.Collect;
import com.example.project.models.Items;
import com.example.project.models.Root;

import java.util.ArrayList;

public class ItemsDB {
    private static final String ITEMS_TABLE = "items";

    public static void addItem(SQLiteDatabase db, ArrayList<Items> items) {
        for(Items item: items) {
            ContentValues tagsValue = new ContentValues();

            tagsValue.put("id", item.getId());
            tagsValue.put("title", item.getTitle());
            tagsValue.put("description", item.getDescription());
            tagsValue.put("image", item.getImage());
            tagsValue.put("collection_id", item.getCollection_id());

            long insertedColl = db.insert(ITEMS_TABLE, null, tagsValue);

        }

    }

    public static void deleteItemsByCollID(SQLiteDatabase db, Integer colId) {
        db.execSQL("delete from "+ ITEMS_TABLE + "where collection_id = " + colId + );
    }
}
