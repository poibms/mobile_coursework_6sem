package com.example.project.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.project.models.Account;
import com.google.gson.Gson;

public class SharedPreferencesHelper {

    public static void addAccount(Context context, Account account) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor sharedPrefEditor = sharedPref.edit();
        Gson gson = new Gson();

        String accountJson = gson.toJson(account);
        sharedPrefEditor.putString("Account", accountJson).apply();
    }

    public static Account getAccount(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String accountJson = sharedPref.getString("Account", null);

        if(accountJson == null) return null;

        Gson gson = new Gson();
        Account account = gson.fromJson(accountJson, Account.class);

        if(account == null || account.getEmail() == null || account.getToken() == null)
            return null;

        return account;
    }

    public static void deleteAccount(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor sharedPrefEditor = sharedPref.edit();

        sharedPrefEditor.remove("Account").apply();
    }

    public static boolean isAccountExist(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String accountJson = sharedPref.getString("Account", null);

        if(accountJson == null) return false;

        Gson gson = new Gson();
        Account account = gson.fromJson(accountJson, Account.class);

        return account != null && account.getEmail() != null && account.getToken() != null;
    }
}
