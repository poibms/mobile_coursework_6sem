package com.example.project.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;
import android.widget.Toast;

import com.example.project.Activity.MainActivity;
import com.example.project.Model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class UserDB {
    private static final String USER_TABLE = "USER";

    public static long signUp(SQLiteDatabase db, User user) {

        ContentValues values = new ContentValues();
        values.put("LOGIN", user.getLogin());
        values.put("EMAIL", user.getEmail());
        values.put("PASSWORD", user.getPassword());

        return db.insertOrThrow(USER_TABLE, null, values);
    }



    public static int signIn(SQLiteDatabase db, String email,String password ) {

        Cursor query = db.rawQuery("select * from " + USER_TABLE + " where " + " EMAIL = '" + email + "';", null);
        if( query.moveToFirst() && query.getCount() != 0) {
            int id = query.getInt(0);
            String pass = query.getString(3);
            if(pass.equals(password)) {
//                Intent intent = new Intent(ctx, MainActivity.class);
//                ctx.startActivity(intent);
                return id;
            }
        }
        return 0;
    }

    
}
