package com.example.project.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.example.project.api.OnFetchDataListener;
import com.example.project.models.Account;
import com.google.gson.Gson;

import java.util.Map;

public class SharedPreferencesHelper {
    private static final String KEY_ACCOUNT = "Account";
    private static final String SHARED_PREF_NAME = "userInfo";
//    sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

    public static void addAccount(Context context, Account account) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor sharedPrefEditor = sharedPref.edit();
        Gson gson = new Gson();

        JWT parsedJWT = new JWT(account.getToken());
        Claim id = parsedJWT.getClaim("id");
        Claim email = parsedJWT.getClaim("email");
        Claim role = parsedJWT.getClaim("role");
        Claim status = parsedJWT.getClaim("status");
        String token = account.getToken();
        Log.d("JWT_DECODED", "Acc: " + status.asString());
        Account acc = new Account(id.asInt(), email.asString(), role.asString(), status.asString(), token);
        String accountJson = gson.toJson(acc);
        Log.d("JWT_DECODED", "Acc: " + accountJson);
        sharedPrefEditor.putString("Account", accountJson).apply();
    }

    public static Account getUserInfo(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String accountJson = sharedPref.getString(KEY_ACCOUNT, null);
        if(accountJson == null) return null;
        Gson gson = new Gson();
        Account account = gson.fromJson(accountJson, Account.class);
        Log.d("JWT_DECODED", "Acc: " + account.getToken());
        return account;
    }

    public static boolean isLogin(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String accountJson = sharedPref.getString(KEY_ACCOUNT, null);

        if(accountJson == null) return false;

        Gson gson = new Gson();
        Account account = gson.fromJson(accountJson, Account.class);

        return account != null && account.getToken() != null;
    }

    public static void logOut(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor sharedPrefEditor = sharedPref.edit();

        sharedPrefEditor.remove("Account").apply();
    }
}
