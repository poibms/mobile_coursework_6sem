package com.example.project.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.project.models.Collect;
import com.example.project.models.Root;
import com.example.project.models.Tags;

import java.util.ArrayList;
import java.util.List;

public class CollectDB {
    private static final String COLLeCTIONS_TABLE = "collections";
    private static final String COLLTAGS_TABLE = "collection_tags";

    public static void addCollections(SQLiteDatabase db, ArrayList<Collect> collect) {
        for(Collect coll: collect) {
            ContentValues tagsValue = new ContentValues();

            tagsValue.put("id", coll.getId());
            tagsValue.put("title", coll.getTitle());
            tagsValue.put("description", coll.getDescription());
            tagsValue.put("image", coll.getImage());
            tagsValue.put("owner_id", coll.getOwnerId());

            long insertedColl = db.insert(COLLeCTIONS_TABLE, null, tagsValue);

            for(Root tag: coll.getTags()) {
                ContentValues colTags = new ContentValues();

                colTags.put("collection_id", coll.getId());
                colTags.put("tags_id", tag.getId());
                long  insertedCollTags = db.insert(COLLTAGS_TABLE, null, colTags);
            }
        }

    }

    public static void deleteAllCollect(SQLiteDatabase db) {
        db.execSQL("delete from "+ COLLeCTIONS_TABLE);
    }

//    public static List<String> getCollections(SQLiteDatabase db) {
//        Cursor cursor = db.rawQuery("SELECT  col.id, col.title, col.description, col.image, col.owner_id" +
//                "                json_array" +  "(tags)" +
//                "        as tags FROM " + COLLeCTIONS_TABLE , null);

//        select
//        col.id, col.title, col.description, col.image, col.owner_id, users.email,
//                json_agg(tags)
//        as tags
//        from collections as col
//        inner join collection_tags as col_tags
//        on col.id = col_tags.collection_id
//        inner join tags on col_tags.tags_id = tags.id
//        inner join users on col.owner_id = users.id
//        group by col.id, users.id order by col.id asc
//        List<String> tags = new ArrayList<String>();

//        if(cursor.moveToNext()) {
//            Root tags = new Root(
//                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
//                    cursor.getString(cursor.getColumnIndexOrThrow("text"))
//            );
//            tags.add(cursor.getString(cursor.getColumnIndexOrThrow("text")));
//            return tags;
//        }
//        return null;
//    }
}
